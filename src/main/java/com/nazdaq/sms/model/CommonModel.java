package com.nazdaq.sms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public class CommonModel implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	// common
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "created_by", nullable = true)
	private Employee createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "modified_by", nullable = true)
	private Employee modifiedBy;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "status")
	private Integer status;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Employee getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Employee createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Employee getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Employee modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

}
