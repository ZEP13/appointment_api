package workshop.zepcla.entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
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

    @Column(nullable = true)
    private Enum<DayOfWeek> daysOff;

    @Column(nullable = true)
    @JoinColumn(name = "id_holiday", referencedColumnName = "id")
    private Long holidayId;

    @Column(nullable = true)
    @JoinColumn(name = "id_time_break", referencedColumnName = "id")
    private Long timeBreakId;

}
