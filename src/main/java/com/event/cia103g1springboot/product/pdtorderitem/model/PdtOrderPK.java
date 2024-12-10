package com.event.cia103g1springboot.product.pdtorderitem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


//PdtOrderPK 複合主鍵類
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdtOrderPK implements Serializable {
    private Integer pdtOrderId;
    private Integer pdtId;

    // 覆寫 equals 和 hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdtOrderPK that = (PdtOrderPK) o;
        return Objects.equals(pdtOrderId, that.pdtOrderId) &&
               Objects.equals(pdtId, that.pdtId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pdtOrderId, pdtId);
    }
}
