package workshop.zepcla.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "holiday")
public class HolidayEntity extends BaseEntity {

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

}
