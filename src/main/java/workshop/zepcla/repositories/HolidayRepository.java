package workshop.zepcla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import workshop.zepcla.entities.HolidayEntity;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {

}
