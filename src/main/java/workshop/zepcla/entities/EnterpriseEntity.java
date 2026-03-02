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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "enterprise")
public class EnterpriseEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalTime openingTime;
    @Column(nullable = false)
    private LocalTime closingTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "days_off", nullable = true)
    private DayOfWeek daysOff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_holiday", referencedColumnName = "id", nullable = true)
    private HolidayEntity holiday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_time_break", referencedColumnName = "id", nullable = true)
    private BreakEntity timeBreak;

}
