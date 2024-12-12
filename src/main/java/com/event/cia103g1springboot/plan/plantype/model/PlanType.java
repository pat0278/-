package com.event.cia103g1springboot.plan.plantype.model;

import com.event.cia103g1springboot.plan.plan.model.Plan;
import com.event.cia103g1springboot.plan.planinfo.model.PlanInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(of = "plantypeid")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "plantype")
public class PlanType {

    @Id
    @Column(name = "plantypeid", nullable = false)
    private String planTypeId;

    @NotBlank
    @Column(name = "planname")
    private String planName;

    @NotNull
    @Column(name = "planday")
    private int planDay;

    @OneToMany(mappedBy = "planType",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<PlanInfo> planInfo ;

    @OneToMany(mappedBy = "planType",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Plan> plans;


    @Override
    public String toString() {
        return "PlanType{" +
                "planTypeId='" + planTypeId + '\'' +
                ", planName='" + planName + '\'' +
                ", planDay=" + planDay +
                '}';
    }


}