package workshop.zepcla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import workshop.zepcla.entities.BreakEntity;

public interface BreakRepository extends JpaRepository<BreakEntity, Long> {

}
