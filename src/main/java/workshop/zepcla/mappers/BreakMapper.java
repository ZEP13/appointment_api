package workshop.zepcla.mappers;

import workshop.zepcla.dto.breakDto.BreakDto;
import workshop.zepcla.entities.BreakEntity;

public class BreakMapper {

    public BreakEntity toEntity(BreakEntity dto) {
        if (dto == null) {
            return null;
        }
        BreakEntity timeBreak = new BreakEntity();
        timeBreak.setId(dto.getId());
        timeBreak.setStartTime(dto.getStartTime());
        timeBreak.setEndTime(dto.getEndTime());
        return timeBreak;
    }

    public BreakDto toDto(BreakEntity entity) {
        if (entity == null) {
            return null;
        }
        return new BreakDto(
                entity.getId(),
                entity.getStartTime(),
                entity.getEndTime());
    }
}
