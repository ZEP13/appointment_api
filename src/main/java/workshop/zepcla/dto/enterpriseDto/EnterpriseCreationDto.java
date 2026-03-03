package workshop.zepcla.dto.enterpriseDto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public record EnterpriseCreationDto(
        String name,
        @JsonFormat(pattern = "HH:mm") LocalTime openingTime,
        @JsonFormat(pattern = "HH:mm") LocalTime closingTime,
        Set<DayOfWeek> daysOff) {
}
