package workshop.zepcla.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import workshop.zepcla.dto.breakDto.BreakCreationDto;
import workshop.zepcla.entities.BreakEntity;
import workshop.zepcla.services.BreakService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/breaks")
public class BreakController {

    private final BreakService service;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createBreak(@RequestBody BreakCreationDto dto) {
        service.createBreak(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BreakEntity>> getAllBreaks() {
        return ResponseEntity.ok(service.getAllBreaks());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BreakEntity> getBreakById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBreakById(id));
    }

    @GetMapping("/by-enterprise/{id_enterprise}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BreakEntity>> getBreaksByEnterprise(@PathVariable Long id_enterprise) {
        return ResponseEntity.ok(service.getBreaksByEnterpriseId(id_enterprise));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBreak(@PathVariable Long id) {
        service.deleteBreak(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateBreak(@PathVariable Long id, @RequestBody BreakCreationDto dto) {
        service.updateBreak(id, dto);
        return ResponseEntity.noContent().build();
    }
}
