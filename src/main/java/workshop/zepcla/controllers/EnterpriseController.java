package workshop.zepcla.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import workshop.zepcla.dto.enterpriseDto.EnterpriseCreationDto;
import workshop.zepcla.services.EnterpriseService;

@RestController
@AllArgsConstructor
@RequestMapping("/enterprises")
public class EnterpriseController {

    private final EnterpriseService service;

    @PostMapping("/create")
    public void createEnterprise(@RequestBody EnterpriseCreationDto dto) {
        service.createEnterprise(dto);
    }

}
