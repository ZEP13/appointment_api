package workshop.zepcla.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import workshop.zepcla.dto.breakDto.BreakCreationDto;
import workshop.zepcla.entities.BreakEntity;
import workshop.zepcla.mappers.BreakMapper;
import workshop.zepcla.repositories.BreakRepository;

@Service
@AllArgsConstructor
public class BreakService {

    private final BreakRepository repo;
    private final BreakMapper mapper;

    public void createBreak(BreakCreationDto dto) {
        BreakEntity entity = mapper.toCreationEntity(dto);
        repo.save(entity);
    }

    public void deleteBreak(Long id) {
        repo.deleteById(id);
    }

    public void updateBreak(Long id, BreakCreationDto dto) {
        BreakEntity entity = mapper.toCreationEntity(dto);
        entity.setId(id);
        repo.save(entity);
    }

    public BreakEntity getBreakById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Break not found"));
    }

    public List<BreakEntity> getAllBreaks() {
        return repo.findAll();
    }

    public List<BreakEntity> getBreaksByEnterpriseId(Long enterpriseId) {
        return repo.findByEnterpriseId(enterpriseId);
    }

}
