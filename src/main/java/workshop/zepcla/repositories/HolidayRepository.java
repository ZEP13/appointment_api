package workshop.zepcla.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import workshop.zepcla.entities.HolidayEntity;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {

    List<HolidayEntity> findByEnterpriseId(Long enterpriseId);
}
