package workshop.zepcla.entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "days_off", nullable = true)
    private DayOfWeek daysOff;
}
