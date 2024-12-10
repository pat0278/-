package com.event.cia103g1springboot.event.evtimgmodel;



import com.event.cia103g1springboot.event.evtmodel.EvtVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventimg")
public class EvtImgVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evtImgId;

    private String evtImgName;
    @Column(name = "evtImg", columnDefinition = "Mediumblob")
    private byte[] evtImg;

    @ManyToOne
    @JoinColumn(name = "evtId")
    private EvtVO evtVO;

    public EvtImgVO( String evtImgName, byte[] evtImg) {
        this.evtImgName = evtImgName;
        this.evtImg = evtImg;
    }

    @Override
    public String toString() {
        return "EvtImgVO{" +
                "evtImgId=" + evtImgId +
                ", evtImgName='" + evtImgName + '\'' +
                '}';
    }
}

