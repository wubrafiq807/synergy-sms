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
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sms_product_model")
@AttributeOverrides({ @AttributeOverride(name = "remarks", column = @Column(name = "remarks")),
	
	@AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
	@AttributeOverride(name = "createdDate", column = @Column(name = "created_date")),
	@AttributeOverride(name = "modifiedBy", column = @Column(name = "modified_by")),
	@AttributeOverride(name = "modifiedDate", column = @Column(name = "modified_date")),
	@AttributeOverride(name = "status", column = @Column(name = "status")) })
public class Model extends CommonModel implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	
	@Transient
	private Integer categoryId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	

	
	
	

}
