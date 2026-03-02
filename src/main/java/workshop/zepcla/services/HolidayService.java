package workshop.zepcla.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import workshop.zepcla.dto.holidayDto.HolidayCreationDto;
import workshop.zepcla.entities.BreakEntity;
import workshop.zepcla.entities.HolidayEntity;
import workshop.zepcla.exceptions.breakException.BreakNotFound;
import workshop.zepcla.exceptions.holidayException.HolidayDateInvalidException;
import workshop.zepcla.exceptions.holidayException.HolidayNotFound;
import workshop.zepcla.mappers.HolidayMapper;
import workshop.zepcla.repositories.HolidayRepository;

@Service
@AllArgsConstructor
public class HolidayService {

    private final HolidayRepository repo;
    private final HolidayMapper mapper;

    public HolidayEntity createHoliday(HolidayCreationDto dto) {
        if (dto.startDate().isAfter(dto.endDate())) {
            throw new HolidayDateInvalidException("Start date must be before end date");
        }
        HolidayEntity entity = mapper.toCreationEntity(dto);
        return repo.save(entity);
    }

    public void deleteHoliday(Long id) {
        HolidayEntity entity = repo.findById(id)
                .orElseThrow(() -> new HolidayNotFound("with id " + id));
        repo.delete(entity);
    }

    public void updateHoliday(Long id, HolidayCreationDto dto) {
        HolidayEntity existing = repo.findById(id)
                .orElseThrow(() -> new HolidayNotFound("Break not found with id " + id));
        existing.setStartDate(dto.startDate());
        existing.setEndDate(dto.endDate());
        repo.save(existing);
    }

    public HolidayEntity getHolidayById(Long id) {
        return repo.findById(id).orElseThrow(() -> new HolidayNotFound("with id " + id));
    }

    public Iterable<HolidayEntity> getAllHolidays() {
        return repo.findAll();
    }

    public List<HolidayEntity> getHolidaysByEnterpriseId(Long enterpriseId) {
        return repo.findByEnterpriseId(enterpriseId);
    }
}
