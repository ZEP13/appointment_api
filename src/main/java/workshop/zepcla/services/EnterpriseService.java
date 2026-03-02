package workshop.zepcla.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import workshop.zepcla.dto.breakDto.BreakDto;
import workshop.zepcla.dto.enterpriseDto.EnterpriseCreationDto;
import workshop.zepcla.dto.enterpriseDto.EnterpriseDto;
import workshop.zepcla.dto.holidayDto.HolidayDto;
import workshop.zepcla.entities.EnterpriseEntity;
import workshop.zepcla.mappers.BreakMapper;
import workshop.zepcla.mappers.EnterpriseMapper;
import workshop.zepcla.mappers.HolidayMapper;
import workshop.zepcla.repositories.EnterpriseRepository;

@Service
@AllArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository repo;
    private final EnterpriseMapper mapper;
    private final BreakMapper breakMapper;
    private final HolidayMapper holidayMapper;
    private final BreakService breakService;
    private final HolidayService holidayService;

    public void createEnterprise(EnterpriseCreationDto dto) {
        EnterpriseEntity entity = mapper.toCreationEntity(dto);
        repo.save(entity);
    }

    public void deleteEnterprise(Long id) {
        repo.deleteById(id);
    }

    public void updateEnterprise(Long id, EnterpriseCreationDto dto) {
        EnterpriseEntity entity = mapper.toCreationEntity(dto);
        entity.setId(id);
        repo.save(entity);
    }

    public EnterpriseEntity getEnterpriseById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Enterprise not found"));
    }

    public Iterable<EnterpriseEntity> getAllEnterprises() {
        return repo.findAll();
    }

    public EnterpriseDto getEnterpriseWithDetails(Long id) {

        EnterpriseEntity enterprise = getEnterpriseById(id);

        List<BreakDto> breakDtos = breakService
                .getBreaksByEnterpriseId(id)
                .stream()
                .map(breakMapper::toDto)
                .toList();

        List<HolidayDto> holidayDtos = holidayService
                .getHolidaysByEnterpriseId(id)
                .stream()
                .map(holidayMapper::toDto)
                .toList();

        return new EnterpriseDto(
                enterprise.getId(),
                enterprise.getName(),
                enterprise.getOpeningTime(),
                enterprise.getClosingTime(),
                enterprise.getDaysOff(),
                breakDtos,
                holidayDtos);
    }

    public EnterpriseDto getEnterpriseBreakDetails(Long id) {

        EnterpriseEntity enterprise = getEnterpriseById(id);

        List<BreakDto> breakDtos = breakService
                .getBreaksByEnterpriseId(id)
                .stream()
                .map(breakMapper::toDto)
                .toList();

        return new EnterpriseDto(
                enterprise.getId(),
                enterprise.getName(),
                enterprise.getOpeningTime(),
                enterprise.getClosingTime(),
                enterprise.getDaysOff(),
                breakDtos,
                null);
    }

    public EnterpriseDto getEnterpriseHolidayDetails(Long id) {

        EnterpriseEntity enterprise = getEnterpriseById(id);

        List<HolidayDto> holidayDtos = holidayService
                .getHolidaysByEnterpriseId(id)
                .stream()
                .map(holidayMapper::toDto)
                .toList();

        return new EnterpriseDto(
                enterprise.getId(),
                enterprise.getName(),
                enterprise.getOpeningTime(),
                enterprise.getClosingTime(),
                enterprise.getDaysOff(),
                null,
                holidayDtos);
    }
}
