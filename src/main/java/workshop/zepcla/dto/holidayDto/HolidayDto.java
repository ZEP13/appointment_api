package workshop.zepcla.dto.holidayDto;

import java.time.LocalDate;

import workshop.zepcla.dto.enterpriseDto.EnterpriseDto;

public record HolidayDto(
        Long id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        EnterpriseDto enterprise,
        String description) {
}
