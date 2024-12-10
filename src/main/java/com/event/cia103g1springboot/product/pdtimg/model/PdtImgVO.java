package com.event.cia103g1springboot.product.pdtimg.model;

import com.event.cia103g1springboot.product.product.model.PdtVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="productImg")
public class PdtImgVO implements java.io.Serializable{

	@Id
	@Column(name="pdtImgId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pdtImgId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pdtId")
	private PdtVO pdtVO;

	@Column(name = "PdtImgName")
	private String pdtImgName;

	@Column(name="pdtImg")
	private byte[] pdtImg;

	
}
