package com.event.cia103g1springboot.product.pdtorderitem.model;

import com.event.cia103g1springboot.product.product.model.PdtVO;
import com.event.cia103g1springboot.product.productorder.model.ProductOrderVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="PRODUCTORDERITEM")
@IdClass(PdtOrderPK.class)
public class ProductOrderItemVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//    private ProductOrderItemKey id; // 複合主鍵
	@Id
	@Column(name="PDTID")
	private Integer pdtId;

	@Id
	@Column(name="PDTORDERID")
	private Integer pdtOrderId;

	@Column(name="PDTPRICE")
	private Integer pdtPrice;

	@Column(name="PDTNAME")
	private String pdtName;

	@Column(name="ORDERQTY")
	private Integer orderQty;

	@ManyToOne
	@JoinColumn(name = "PDTID" ,referencedColumnName = "pdtid" ,insertable = false ,updatable = false)
	private PdtVO pdtVO;

	@ManyToOne
	@JoinColumn(name = "PDTORDERID" ,referencedColumnName = "PDTORDERID" ,insertable = false ,updatable = false)
	private ProductOrderVO productOrderVO;

}
