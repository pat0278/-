package com.event.cia103g1springboot.plan.plan.model;

import javax.persistence.*;

import com.event.cia103g1springboot.plan.planorder.model.PlanOrder;
import com.event.cia103g1springboot.plan.planroom.model.PlanRoom;
import com.event.cia103g1springboot.plan.plantype.model.PlanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = "planid")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "plan")
public class Plan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planid")
    private int planId;

    @ManyToOne
    @JoinColumn(name = "plantypeid", nullable = false)
    private PlanType planType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "startdate", nullable = false)
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "enddate", nullable = false)
    private LocalDate endDate;

    @Column(name = "planprice", nullable = false)
    private int planPrice;

    @Column(name = "attmax", nullable = false)
    private int attMax;

    @Column(name = "attend", nullable = false)
    private int attEnd;

    @OneToMany(mappedBy = "plan",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<PlanOrder> planOrder = new HashSet<PlanOrder>();

    @OneToMany(mappedBy = "plan",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<PlanRoom> planRoom = new HashSet<>();

    @Override
    public String toString() {
        return "Plan{" +
                "planId=" + planId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", planType=" + (planType != null ? planType.getPlanTypeId() : null) +
                '}';
    }


}


