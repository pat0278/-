package com.event.cia103g1springboot.plan.planroom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class PlanRoomId implements Serializable {
    private Integer planId;
    private Integer roomTypeId;

}
