package com.nazdaq.sms.model;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sms_product_stock")
@AttributeOverrides({@AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
		@AttributeOverride(name = "createdDate", column = @Column(name = "created_date")),
		@AttributeOverride(name = "modifiedBy", column = @Column(name = "modified_by")),
		@AttributeOverride(name = "modifiedDate", column = @Column(name = "modified_date")),
		@AttributeOverride(name = "status", column = @Column(name = "status")) })
public class Stock extends CommonModel implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
		
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
		
	@Column(name = "quantity")
	private Integer quantity;
	
	@Transient
	private Integer productId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	

}
