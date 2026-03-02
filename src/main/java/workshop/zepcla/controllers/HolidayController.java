package workshop.zepcla.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import workshop.zepcla.dto.holidayDto.HolidayCreationDto;
import workshop.zepcla.entities.HolidayEntity;
import workshop.zepcla.services.HolidayService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/holidays")
public class HolidayController {

    private final HolidayService service;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HolidayEntity> createHoliday(@RequestBody HolidayCreationDto dto) {
        return new ResponseEntity<>(service.createHoliday(dto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<HolidayEntity>> getAllHolidays() {
        return ResponseEntity.ok(service.getAllHolidays());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HolidayEntity> getHolidayById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getHolidayById(id));
    }

    @GetMapping("/by-enterprise/{id_enterprise}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HolidayEntity>> getHolidaysByEnterprise(@PathVariable Long id_enterprise) {
        return ResponseEntity.ok(service.getHolidaysByEnterpriseId(id_enterprise));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        service.deleteHoliday(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateHoliday(@PathVariable Long id, @RequestBody HolidayCreationDto dto) {
        service.updateHoliday(id, dto);
        return ResponseEntity.noContent().build();
    }
}
