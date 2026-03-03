package workshop.zepcla.dto.appointmentDto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import workshop.zepcla.dto.enterpriseDto.EnterpriseDto;
import workshop.zepcla.dto.userDto.UserDto;

public record AppointmentDto(
        LocalDate date_appointment,
        @JsonFormat(pattern = "HH:mm") LocalTime time_appointment,
        UserDto id_client,
        UserDto id_creator,
        Integer duration,
        String status,
        EnterpriseDto id_enterprise) {
}
