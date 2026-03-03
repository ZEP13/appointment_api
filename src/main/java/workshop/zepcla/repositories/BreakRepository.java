package workshop.zepcla.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import workshop.zepcla.entities.BreakEntity;

public interface BreakRepository extends JpaRepository<BreakEntity, Long> {

    List<BreakEntity> findByEnterprise_Id(Long enterpriseId);

}
