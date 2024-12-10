package com.event.cia103g1springboot.event.evtmodel;


import com.event.cia103g1springboot.event.evtimgmodel.EvtImgVO;
import com.event.cia103g1springboot.event.evtordermodel.EvtOrderVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
@Entity
@Table(name="event")
@Data
@EqualsAndHashCode(of = "evtId")
@NoArgsConstructor
@AllArgsConstructor
public class EvtVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer evtId;
    @NotBlank(message = "活動名稱不能空白")
    private String evtName;

    @NotBlank(message = "活動描述不能空白")
    private String evtDesc;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(columnDefinition = "TIMESTAMP")
    @NotNull(message = "開放報名時間不能為空白")
    private LocalDateTime evtDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(columnDefinition = "TIMESTAMP")
    @NotNull(message = "報名截止時間不能為空")
    private LocalDateTime evtStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(columnDefinition = "TIMESTAMP")
    @NotNull(message = "活動日期不能為空")
    private LocalDateTime evtDeadLine;

    @NotNull(message = "人數上限不能為空")
    @Min(value = 0, message = "人數上限必須大於0")
    @Max(value = 150, message = "活動人數上限100人")
    private Integer evtMax;

    @NotNull
    @Min(value = 0, message = "參加人數不能小於0")
    @Max(value = 150,message = "參加人數不能超過活動人數上限")
    private Integer evtAttend;

    @Max(3)
    @NotNull
    private Integer evtStat;

    @OneToMany(mappedBy="evtVO",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set <EvtOrderVO> evtOrders ;

    @OneToMany(mappedBy="evtVO",cascade = CascadeType.ALL)
    private Set <EvtImgVO> evtImg;


    public EvtVO(String evtName, String evtDesc, LocalDateTime evtDate, LocalDateTime evtStart, LocalDateTime evtDeadLine, Integer evtMax, Integer evtAttend, Integer evtStat) {
        this.evtName = evtName;
        this.evtDesc = evtDesc;
        this.evtDate = evtDate;
        this.evtStart = evtStart;
        this.evtDeadLine = evtDeadLine;
        this.evtMax = evtMax;
        this.evtAttend = evtAttend;
        this.evtStat = evtStat;
    }

}