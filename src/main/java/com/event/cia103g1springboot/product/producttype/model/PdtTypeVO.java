package com.event.cia103g1springboot.product.producttype.model;

import com.event.cia103g1springboot.product.product.model.PdtVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = "pdttypeid")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="producttype")
public class PdtTypeVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="pdttypeid")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer pdtTypeId;
	
	@Column(name="pdttypename")
	@NotEmpty(message="類別名稱:請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "類別名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
	private String pdtTypeName;
	
	@Column(name="pdttypedesc")
	@NotEmpty(message="類別描述:請勿空白")
	private String pdtTypeDesc;

	@OneToMany(mappedBy = "pdtTypeVO", cascade = CascadeType.ALL)
	private Set<PdtVO> products;


	}

