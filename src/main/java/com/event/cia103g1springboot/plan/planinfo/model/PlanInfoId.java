package com.event.cia103g1springboot.plan.planinfo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class PlanInfoId implements Serializable {

    @Column(name = "plantypeid")
    private String planTypeId;

    @Column(name = "planday")
    private int planDay;

    }
