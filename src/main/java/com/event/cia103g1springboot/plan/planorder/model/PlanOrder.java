        package com.event.cia103g1springboot.plan.planorder.model;

        import com.event.cia103g1springboot.event.evtordermodel.EvtOrderVO;
        import com.event.cia103g1springboot.member.mem.model.MemVO;
        import com.event.cia103g1springboot.plan.plan.model.Plan;
        import com.event.cia103g1springboot.room.roomorder.model.ROVO;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.EqualsAndHashCode;
        import lombok.NoArgsConstructor;

        import javax.persistence.*;
        import javax.validation.constraints.AssertTrue;
        import javax.validation.constraints.NotNull;
        import javax.validation.constraints.Pattern;

        import java.io.Serializable;
        import java.time.LocalDateTime;
        import java.util.Set;

        @EqualsAndHashCode(of = "planorder")
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        @Entity
        @Table(name = "planorder")
        public class PlanOrder implements Serializable {
            private static final long serialVersionUID = 1L;

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "planOrderId", nullable = false)
            private Integer planOrderId;


            @Column(name = "planPrice", nullable = false)
            private Integer planPrice;

            @Column(name = "roomPrice", nullable = false)
            private Integer roomPrice;

            @Column(name = "totalPrice",nullable = false,insertable = false,updatable = false)
            private Integer totalPrice;

            @Column(name = "orderDate", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
            private LocalDateTime orderDate = LocalDateTime.now();

            @Column(name = "orderStat", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
            private Integer orderStat;

            @NotNull(message = "請選擇付款方式")
            @Column(name = "payMethod", nullable = false)
            private Integer payMethod;

            @Column(name = "remAcct", length = 20)
            private String remAcct;

            @Column(name = "cardLast4", length = 4)
            private String cardLast4;

            @ManyToOne(fetch = FetchType.EAGER)
            @JoinColumn(name = "memId",referencedColumnName = "memid")
            private MemVO memVO;

            @ManyToOne(fetch = FetchType.EAGER)
            @JoinColumn(name = "planid",referencedColumnName = "planid")
            private Plan plan;

            @OneToMany(mappedBy = "planOrder",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
            private Set<EvtOrderVO> evtOrderVO;

            @OneToMany(mappedBy = "planOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
            private Set<ROVO> roomOrders;



            @Override
            public String toString() {
                return "PlanOrder{" +
                        "planOrderId=" + planOrderId +
                        ", planPrice=" + planPrice +
                        ", roomPrice=" + roomPrice +
                        ", totalPrice=" + totalPrice +
                        ", orderDate=" + orderDate +
                        ", orderStat=" + orderStat +
                        ", payMethod=" + payMethod +
                        ", remAcct='" + remAcct + '\'' +
                        ", cardLast4='" + cardLast4 + '\'' +
                        '}';
            }
        }
