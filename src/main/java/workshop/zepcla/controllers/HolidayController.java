package workshop.zepcla.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import workshop.zepcla.dto.holidayDto.HolidayCreationDto;
import workshop.zepcla.services.HolidayService;

@RestController
@AllArgsConstructor
@RequestMapping("/holidays")
public class HolidayController {

    private final HolidayService service;

    public void createHoliday(@RequestBody HolidayCreationDto dto) {
        service.createHoliday(dto);
    }
}
