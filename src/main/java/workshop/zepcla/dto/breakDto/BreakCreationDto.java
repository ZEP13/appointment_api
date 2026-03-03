package workshop.zepcla.dto.breakDto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record BreakCreationDto(
        @JsonFormat(pattern = "HH:mm") LocalTime startTime,
        @JsonFormat(pattern = "HH:mm") LocalTime endTime,
        DayOfWeek daysOff,
        Long enterpriseId) {
}
