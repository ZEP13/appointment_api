package workshop.zepcla.mappers;

import workshop.zepcla.dto.enterpriseDto.EnterpriseDto;
import workshop.zepcla.entities.EnterpriseEntity;

public class EnterpriseMapper {

    public EnterpriseEntity toEntity(EnterpriseDto enterpriseDto) {
        if (enterpriseDto == null) {
            return null;
        }

        EnterpriseEntity enterpriseEntity = new EnterpriseEntity();
        enterpriseEntity.setId(enterpriseDto.id());
        enterpriseEntity.setName(enterpriseDto.name());
        enterpriseEntity.setOpeningTime(enterpriseDto.openingTime());
        enterpriseEntity.setClosingTime(enterpriseDto.closingTime());
        enterpriseEntity.setDaysOff(enterpriseDto.daysOff());

        return enterpriseEntity;
    }

    public EnterpriseDto tDto(EnterpriseEntity enterpriseEntity) {
        if (enterpriseEntity == null) {
            return null;
        }

        return new EnterpriseDto(
                enterpriseEntity.getId(),
                enterpriseEntity.getName(),
                enterpriseEntity.getOpeningTime(),
                enterpriseEntity.getClosingTime(),
                enterpriseEntity.getDaysOff(),
                enterpriseEntity.getHolidayId(),
                enterpriseEntity.getBreakId());
    }
}
