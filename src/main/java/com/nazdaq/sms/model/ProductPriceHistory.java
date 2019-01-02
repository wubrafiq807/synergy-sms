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

@Entity
@Table(name = "sms_product_prc_history")
@AttributeOverrides({@AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
		
		@AttributeOverride(name = "createdDate", column = @Column(name = "created_date")),
		@AttributeOverride(name = "modifiedBy", column = @Column(name = "modified_by")),
		@AttributeOverride(name = "modifiedDate", column = @Column(name = "modified_date")),
		@AttributeOverride(name = "status", column = @Column(name = "status")) })
public class ProductPriceHistory extends CommonModel implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
		
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "purchase_quantity")
	private Integer purchaseQuantity;
	
	
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(Integer purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

		
	
	
	
	

}
