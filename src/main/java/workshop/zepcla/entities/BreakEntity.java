package workshop.zepcla.entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "breaks")
public class BreakEntity extends BaseEntity {

    private LocalTime startTime;

    private LocalTime endTime;

    private Enum<DayOfWeek> dayOfWeek;
}
