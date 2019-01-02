package com.nazdaq.sms.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "sms_requisition_item")

public class RequisitionItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "requisition_id", nullable = false)
	private Requisition requisition;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "quantity")
	private Integer quantity;
	
	@Transient
	private Integer tempProductId;
	
	@Transient
	private String weightedAvgPriceCurrency;
	
	
	@Transient
	private String totalPriceCurrency;
	
	@Transient
	private Double totalPrice;
	

	public Integer getTempProductId() {
		return tempProductId;
	}

	public void setTempProductId(Integer tempProductId) {
		this.tempProductId = tempProductId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Requisition getRequisition() {
		return requisition;
	}

	public void setRequisition(Requisition requisition) {
		this.requisition = requisition;
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

	public String getWeightedAvgPriceCurrency() {
		return weightedAvgPriceCurrency;
	}

	public void setWeightedAvgPriceCurrency(String weightedAvgPriceCurrency) {
		this.weightedAvgPriceCurrency = weightedAvgPriceCurrency;
	}

	public String getTotalPriceCurrency() {
		return totalPriceCurrency;
	}

	public void setTotalPriceCurrency(String totalPriceCurrency) {
		this.totalPriceCurrency = totalPriceCurrency;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
