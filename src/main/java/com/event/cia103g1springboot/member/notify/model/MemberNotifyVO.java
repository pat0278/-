package com.event.cia103g1springboot.member.notify.model;

import com.event.cia103g1springboot.member.mem.model.MemVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "membernotify")
public class MemberNotifyVO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notifyId;
    
    @ManyToOne
    @JoinColumn(name = "memId", nullable = false)
    private MemVO member;
    
    @Column(nullable = false)
    private Integer notifyType;  // 1:活動通知、2:活動訂單通知、3:商品訂單通知、4:商品通知、5:佈告欄通知、6:行程訂單通知
    
    @Column(nullable = false, length = 1500)
    private String notifyCon;
    
    @Column(nullable = false)
    private Boolean isRead = false;
    
    @Column(nullable = false)
    private LocalDateTime notifyTime = LocalDateTime.now();
    
    @Column
    private Integer parentId;  // 父級通知ID，為null表示這是主通知
    
    @Column
    private String businessKey;  // 新增業務關聯鍵

    // 用於前端顯示的輔助方法
    public String getNotifyTypeText() {
        switch (notifyType) {
            case 1: return "活動通知";
            case 2: return "活動訂單通知";
            case 3: return "商品訂單通知";
            case 4: return "商品通知";
            case 5: return "佈告欄通知";
            case 6: return "行程訂單通知";
            default: return "未知類型";
        }
    }
}
