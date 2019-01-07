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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.nazdaq.sms.model.CommonModel;

@Entity
@Table(name = "sms_product")
@AttributeOverrides({ @AttributeOverride(name = "remarks", column = @Column(name = "remarks")),
		@AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
		
		@AttributeOverride(name = "createdDate", column = @Column(name = "created_date")),
		@AttributeOverride(name = "modifiedBy", column = @Column(name = "modified_by")),
		@AttributeOverride(name = "modifiedDate", column = @Column(name = "modified_date")),
		@AttributeOverride(name = "status", column = @Column(name = "status")) })
public class Product extends CommonModel implements Serializable{

	private static final long serialVersionUID = -7220600828240370258L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "purchase_quantity")
	private Integer purchaseQuantity;
	
	@Column(name = "purchase_quantity_vip")
	private Integer vipPurchaseQuantity;
	
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	
//	@ManyToOne
//	@JoinColumn(name = "model_id", nullable = false)
//	private Model model;
	
	@Transient
	private Integer categoryId;
	
//	@Transient
//	private Integer modelId;
	
	@Transient
	private Double weightedAvgPrice;
	
	@Transient
	private String weightedAvgPriceCurrency;
	
	
	@Transient
	private String totalPriceCurrency;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

//	public Model getModel() {
//		return model;
//	}
//
//	public void setModel(Model model) {
//		this.model = model;
//	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

//	public Integer getModelId() {
//		return modelId;
//	}
//
//	public void setModelId(Integer modelId) {
//		this.modelId = modelId;
//	}

	public Double getWeightedAvgPrice() {
		return weightedAvgPrice;
	}

	public void setWeightedAvgPrice(Double weightedAvgPrice) {
		this.weightedAvgPrice = weightedAvgPrice;
	}

	public Integer getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(Integer purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
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

	public Integer getVipPurchaseQuantity() {
		return vipPurchaseQuantity;
	}

	public void setVipPurchaseQuantity(Integer vipPurchaseQuantity) {
		this.vipPurchaseQuantity = vipPurchaseQuantity;
	}
	
	
}
