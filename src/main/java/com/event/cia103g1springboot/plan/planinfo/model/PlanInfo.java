package com.event.cia103g1springboot.plan.planinfo.model;

import com.event.cia103g1springboot.plan.plantype.model.PlanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
@EqualsAndHashCode(of = {"planTypeId", "planDay"})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "planinfo")
@IdClass(PlanInfoId.class)
public class PlanInfo implements Serializable {
    @Id
    @Column(name = "plantypeid")
    private String planTypeId;

    @Id
    @Column(name = "planday")
    private int planDay;

    @Column(name = "plancon", length = 1500)
    private String planCon;

    @ManyToOne
    @JoinColumn(name = "plantypeid", insertable = false, updatable = false)
    private PlanType planType;

    @Override
    public String toString() {
        return "PlanInfo{" +
                "planDay=" + planDay +
                ", planCon='" + planCon + '\'' +
                '}';
    }
}