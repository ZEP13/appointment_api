package workshop.zepcla.dto.enterpriseDto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import workshop.zepcla.entities.BreakEntity;
import workshop.zepcla.entities.HolidayEntity;

public record EnterpriseDto(
        Long id,
        String name,
        LocalTime openingTime,
        LocalTime closingTime,
        DayOfWeek daysOff,
        HolidayEntity holidayId,
        BreakEntity timeBreakId) {
}
