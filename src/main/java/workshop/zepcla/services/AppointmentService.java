package workshop.zepcla.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import workshop.zepcla.dto.appointmentDto.AppointmentCreationByAdminDto;
import workshop.zepcla.dto.appointmentDto.AppointmentCreationDto;
import workshop.zepcla.dto.appointmentDto.AppointmentDto;
import workshop.zepcla.entities.AppointmentEntity;
import workshop.zepcla.entities.BreakEntity;
import workshop.zepcla.entities.EnterpriseEntity;
import workshop.zepcla.entities.HolidayEntity;
import workshop.zepcla.entities.UserEntity;
import workshop.zepcla.exceptions.appointmentException.AppointmentNotFound;
import workshop.zepcla.exceptions.appointmentException.ClientAlreadyHaveAppointment;
import workshop.zepcla.exceptions.appointmentException.ClientCantHaveAppointmentInPast;
import workshop.zepcla.exceptions.appointmentException.NoAvaibleAppointment;
import workshop.zepcla.exceptions.enterpriseException.EnterpriseClosedException;
import workshop.zepcla.exceptions.userException.UserIdNotFoundException;
import workshop.zepcla.mappers.AppointmentMapper;
import workshop.zepcla.repositories.AppointmentRepository;
import workshop.zepcla.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static workshop.zepcla.specifications.AppointmentSpecification.*;

@Service
@AllArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final EnterpriseService enterpriseService;

    // ─── Validation helpers ───────────────────────────────────────────────────

    private void validateEnterpriseAvailability(EnterpriseEntity enterprise,
            LocalDate date,
            LocalTime time,
            Integer duration) {
        LocalTime appointmentEnd = time.plusMinutes(duration);

        // 1. Heures d'ouverture
        if (time.isBefore(enterprise.getOpeningTime()) ||
                appointmentEnd.isAfter(enterprise.getClosingTime())) {
            throw new EnterpriseClosedException(
                    "The enterprise is not open on " + date + " at " + time +
                            ". Opening hours: " + enterprise.getOpeningTime() +
                            " - " + enterprise.getClosingTime());
        }

        // 2. Jour de congé hebdomadaire
        if (enterprise.getDaysOff() != null &&
                enterprise.getDaysOff() == date.getDayOfWeek()) {
            throw new EnterpriseClosedException(
                    "The enterprise is closed every " + enterprise.getDaysOff());
        }

        // 3. Périodes de vacances
        for (HolidayEntity holiday : enterprise.getHolidays()) {
            if (!date.isBefore(holiday.getStartDate()) &&
                    !date.isAfter(holiday.getEndDate())) {
                throw new EnterpriseClosedException(
                        "The enterprise is on holiday from " +
                                holiday.getStartDate() + " to " + holiday.getEndDate() +
                                (holiday.getName() != null ? " (" + holiday.getName() + ")" : ""));
            }
        }

        // 4. Pauses du jour
        for (BreakEntity breakEntity : enterprise.getBreaks()) {
            if (breakEntity.getDaysOff() != null &&
                    breakEntity.getDaysOff() != date.getDayOfWeek()) {
                continue; // cette pause ne s'applique pas ce jour-là
            }
            // chevauchement : le RDV commence avant la fin de la pause
            // ET se termine après le début de la pause
            boolean overlaps = time.isBefore(breakEntity.getEndTime()) &&
                    appointmentEnd.isAfter(breakEntity.getStartTime());
            if (overlaps) {
                throw new EnterpriseClosedException(
                        "The appointment overlaps with a break from " +
                                breakEntity.getStartTime() + " to " + breakEntity.getEndTime());
            }
        }
    }

    // ─── Create ───────────────────────────────────────────────────────────────

    public AppointmentDto createAppointment(AppointmentCreationDto dto) {

        LocalDate date = dto.date_appointment();
        LocalTime time = dto.time_appointment();

        if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now())) {
            throw new ClientCantHaveAppointmentInPast(
                    "on " + date + " at " + time + ". Please select a valid date");
        }

        Long idEnterprise = dto.enterprise().id();
        EnterpriseEntity enterprise = enterpriseService.getEnterpriseById(idEnterprise);

        validateEnterpriseAvailability(enterprise, date, time, dto.duration());

        UserEntity clientEntity = userService.getCurrentUserEntity();

        boolean clientConflict = appointmentRepository.existsByClientAndDateAndTime(clientEntity, date, time);
        if (clientConflict) {
            throw new ClientAlreadyHaveAppointment("on " + date + " at " + time);
        }

        boolean slotTaken = appointmentRepository.existsByDateAndTime(date, time);
        if (slotTaken) {
            throw new NoAvaibleAppointment("on " + date + " at " + time);
        }

        AppointmentEntity entity = appointmentMapper.toEntityForCreation(dto);
        entity.setClient(clientEntity);
        entity.setEnterprise(enterprise);

        AppointmentEntity saved = appointmentRepository.save(entity);
        return appointmentMapper.toDto(saved);
    }

    public AppointmentDto createAppointmentAsAdmin(AppointmentCreationByAdminDto dto) {

        LocalDate date = dto.date_appointment();
        LocalTime time = dto.time_appointment();

        if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now())) {
            throw new ClientCantHaveAppointmentInPast(
                    "on " + date + " at " + time + ". Please select a valid date");
        }

        Long idEnterprise = dto.enterprise().id();
        EnterpriseEntity enterprise = enterpriseService.getEnterpriseById(idEnterprise);

        validateEnterpriseAvailability(enterprise, date, time, dto.duration());

        UserEntity creatorEntity = userService.getCurrentUserEntity();

        boolean clientConflict = appointmentRepository.existsByClientAndDateAndTime(creatorEntity, date, time);
        if (clientConflict) {
            throw new ClientAlreadyHaveAppointment("on " + date + " at " + time);
        }

        boolean slotTaken = appointmentRepository.existsByDateAndTime(date, time);
        if (slotTaken) {
            throw new NoAvaibleAppointment("on " + date + " at " + time);
        }

        AppointmentEntity entity = appointmentMapper.toEntityForCreationByAdmin(dto);
        entity.setCreator(creatorEntity);
        entity.setEnterprise(enterprise);
        entity.setClient(dto.id_client() != null ? userRepository.findById(dto.id_client().id())
                .orElseThrow(() -> new UserIdNotFoundException("User ID not found: " + dto.id_client())) : null);

        AppointmentEntity saved = appointmentRepository.save(entity);
        return appointmentMapper.toDto(saved);
    }

    // ─── Cancel ───────────────────────────────────────────────────────────────

    public AppointmentDto cancelAppointment(Long id) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFound(" with id " + id));

        LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getDate(), appointment.getTime());

        if (appointmentDateTime.minusHours(12).isBefore(LocalDateTime.now())) {
            throw new AppointmentNotFound(
                    "You can't cancel an appointment less than 12 hours before the appointment date");
        }
        appointment.setStatus("CANCELLED");
        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    // ─── Read ─────────────────────────────────────────────────────────────────

    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public AppointmentDto getAppointmentById(Long id) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFound(" with id " + id));
        return appointmentMapper.toDto(appointment);
    }

    public List<AppointmentDto> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByDate(date)
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getMyAppointmentsByDate(LocalDate date) {
        UserEntity clientEntity = userService.getCurrentUserEntity();
        return appointmentRepository.findByClientAndDate(clientEntity, date)
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByCurrentClient() {
        UserEntity clientEntity = userService.getCurrentUserEntity();
        return appointmentRepository.findByClient(clientEntity)
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByClient(Long id_client) {
        UserEntity clientEntity = userRepository.findById(id_client)
                .orElseThrow(() -> new UserIdNotFoundException("User ID not found: " + id_client));
        return appointmentRepository.findByClient(clientEntity)
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByCreator(Long id_creator) {
        UserEntity creatorEntity = userRepository.findById(id_creator)
                .orElseThrow(() -> new UserIdNotFoundException("User ID not found: " + id_creator));
        return appointmentRepository.findByCreator(creatorEntity)
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<AppointmentDto> superSearch(
            Integer page,
            Integer size,
            Long id,
            String date,
            String time,
            String duration,
            String status,
            String token) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<AppointmentEntity> spec = Specification
                .where(hasDate(date))
                .and(hasTime(time))
                .and(hasDuration(duration))
                .and(hasStatus(status))
                .and(hasToken(token));

        return appointmentRepository.findAll(spec, pageable)
                .map(appointmentMapper::toDto);
    }
}
