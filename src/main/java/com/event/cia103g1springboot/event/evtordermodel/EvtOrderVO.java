package com.event.cia103g1springboot.event.evtordermodel;


import com.event.cia103g1springboot.event.evtmodel.EvtVO;
import com.event.cia103g1springboot.member.mem.model.MemVO;
import com.event.cia103g1springboot.plan.planorder.model.PlanOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(of = "evtOrderId")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventorder")
public class EvtOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer evtOrderId;

    private String evtName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime evtDate;

    private Integer evtAttend;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime evtAttendDate;

    private String evtRemarks;

    @Max(2)
    @NotNull
    private Integer evtOrderStat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evtId")
    private EvtVO evtVO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "planOrderId")
    private PlanOrder planOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memId")
    private MemVO memVO;


    public EvtOrderVO(String evtName, LocalDateTime evtDate, Integer evtAttend, Integer evtOrderStat) {
        this.evtName = evtName;
        this.evtDate = evtDate;
        this.evtAttend = evtAttend;
        this.evtOrderStat = evtOrderStat;
    }

}
