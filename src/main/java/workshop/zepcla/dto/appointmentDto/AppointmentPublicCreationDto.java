package workshop.zepcla.dto.appointmentDto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AppointmentPublicCreationDto(
        LocalDate date_appointment,
        @JsonFormat(pattern = "HH:mm") LocalTime time_appointment,
        Integer duration,
        Long enterpriseId,
        String email_client // email pour contact / token
) {
}
