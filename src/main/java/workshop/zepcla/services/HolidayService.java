package workshop.zepcla.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import workshop.zepcla.dto.holidayDto.HolidayCreationDto;
import workshop.zepcla.dto.holidayDto.HolidayDto;
import workshop.zepcla.entities.EnterpriseEntity;
import workshop.zepcla.entities.HolidayEntity;
import workshop.zepcla.exceptions.holidayException.HolidayDateInvalidException;
import workshop.zepcla.exceptions.holidayException.HolidayNotFound;
import workshop.zepcla.mappers.HolidayMapper;
import workshop.zepcla.repositories.EnterpriseRepository;
import workshop.zepcla.repositories.HolidayRepository;

@Service
@AllArgsConstructor
public class HolidayService {

    private final HolidayRepository repo;
    private final HolidayMapper mapper;
    private final EnterpriseRepository enterpriserRepository;

    private void validateHoliday(HolidayCreationDto dto) {

        if (dto.startDate().isAfter(dto.endDate())) {
            throw new HolidayDateInvalidException("Start date must be before or equal to end date");
        }

        if (dto.endDate().isBefore(LocalDate.now())) {
            throw new HolidayDateInvalidException("Holiday end date cannot be in the past");
        }
    }

    public HolidayDto createHoliday(HolidayCreationDto dto) {
        validateHoliday(dto);

        EnterpriseEntity enterprise = enterpriserRepository.findById(dto.enterpriseId())
                .orElseThrow(() -> new HolidayNotFound("Enterprise with id " + dto.enterpriseId() + " not found"));

        HolidayEntity holiday = mapper.toCreationEntity(dto);

        holiday.setEnterprise(enterprise);

        return mapper.toDto(repo.save(holiday));
    }

    public void deleteHoliday(Long id) {
        HolidayEntity entity = repo.findById(id)
                .orElseThrow(() -> new HolidayNotFound("with id " + id));
        repo.delete(entity);
    }

    public void updateHoliday(Long id, HolidayCreationDto dto) {
        HolidayEntity existing = repo.findById(id)
                .orElseThrow(() -> new HolidayNotFound("with id " + id));
        validateHoliday(dto);
        existing.setName(dto.name());
        existing.setStartDate(dto.startDate());
        existing.setEndDate(dto.endDate());
        existing.setDescription(dto.description());
        repo.save(existing);
    }

    public HolidayEntity getHolidayById(Long id) {
        return repo.findById(id).orElseThrow(() -> new HolidayNotFound("with id " + id));
    }

    public Iterable<HolidayEntity> getAllHolidays() {
        return repo.findAll();
    }

    public List<HolidayEntity> getHolidaysByEnterpriseId(Long enterpriseId) {
        return repo.findByEnterprise_Id(enterpriseId);
    }
}
