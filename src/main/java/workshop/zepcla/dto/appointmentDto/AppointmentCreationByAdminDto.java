package workshop.zepcla.dto.appointmentDto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AppointmentCreationByAdminDto(
        @JsonFormat(pattern = "HH:mm") LocalDate date_appointment,
        @JsonFormat(pattern = "HH:mm") LocalTime time_appointment,
        Long id_client,
        Integer duration) {
}
