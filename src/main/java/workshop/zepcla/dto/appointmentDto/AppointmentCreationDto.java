package workshop.zepcla.dto.appointmentDto;

import java.time.LocalDate;
import java.time.LocalTime;

import workshop.zepcla.dto.enterpriseDto.EnterpriseDto;

public record AppointmentCreationDto(LocalDate date_appointment, LocalTime time_appointment,
        Integer duration, EnterpriseDto id_enterprise) {
}
