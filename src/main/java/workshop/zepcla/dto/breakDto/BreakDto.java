package workshop.zepcla.dto.breakDto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record BreakDto(
        Long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startTime,
        @JsonFormat(pattern = "HH:mm") LocalTime endTime) {
}
