package com.event.cia103g1springboot.product.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer pdtId;      // 商品編號
    private String pdtName;     // 商品名稱
    private Integer pdtPrice;    // 商品單價
    private Integer orderQty;   // 購買數量
    private Integer subtotal;    // 小計

	// Constructor
    public CartVO(Integer pdtId, String pdtName, Integer pdtPrice, Integer orderQty) {
        this.pdtId = pdtId;
        this.pdtName = pdtName;
        this.pdtPrice = pdtPrice;
        this.orderQty = orderQty;
        this.subtotal = calculateSubtotal(); // 自動計算小計
    }

    // 計算小計
    private Integer calculateSubtotal() {
        return this.pdtPrice * this.orderQty;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "pdtId=" + pdtId +
                ", pdtName='" + pdtName + '\'' +
                ", pdtPrice=" + pdtPrice +
                ", orderQty=" + orderQty +
                ", subtotal=" + subtotal +
                '}';
    }
    
    public static void main(String[] args) {
        // 創建商品項目
        CartVO item1 = new CartVO(101, "商品A", 100, 2);
        CartVO item2 = new CartVO(102, "商品B", 150, 1);

        // 模擬購物車
        List<CartVO> cart = new ArrayList<>();
        cart.add(item1);
        cart.add(item2);

        // 打印購物車
        for (CartVO item : cart) {
            System.out.println(item);
        }
    }


}
