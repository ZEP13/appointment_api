package workshop.zepcla.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import workshop.zepcla.dto.enterpriseDto.EnterpriseCreationDto;
import workshop.zepcla.dto.enterpriseDto.EnterpriseDto;
import workshop.zepcla.entities.EnterpriseEntity;
import workshop.zepcla.services.EnterpriseService;

@RestController
@AllArgsConstructor
@RequestMapping("/enterprises")
public class EnterpriseController {

    private final EnterpriseService service;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnterpriseEntity> createEnterprise(@RequestBody EnterpriseCreationDto dto) {
        return new ResponseEntity<>(service.createEnterprise(dto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<EnterpriseEntity>> getAllEnterprises() {
        return ResponseEntity.ok(service.getAllEnterprises());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnterpriseEntity> getEnterpriseById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEnterpriseById(id));
    }

    @GetMapping("/{id}/details")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnterpriseDto> getEnterpriseWithDetails(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEnterpriseWithDetails(id));
    }

    @GetMapping("/{id}/breaks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnterpriseDto> getEnterpriseBreakDetails(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEnterpriseBreakDetails(id));
    }

    @GetMapping("/{id}/holidays")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnterpriseDto> getEnterpriseHolidayDetails(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEnterpriseHolidayDetails(id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEnterprise(@PathVariable Long id) {
        service.deleteEnterprise(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnterpriseEntity> updateEnterprise(@PathVariable Long id,
            @RequestBody EnterpriseCreationDto dto) {
        return ResponseEntity.ok(service.updateEnterprise(id, dto));
    }
}
