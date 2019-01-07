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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sms_requisition")
@AttributeOverrides({@AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
		@AttributeOverride(name = "createdDate", column = @Column(name = "created_date")),
		 @AttributeOverride(name = "remarks", column = @Column(name = "remarks")),
		@AttributeOverride(name = "modifiedBy", column = @Column(name = "modified_by")),
		@AttributeOverride(name = "modifiedDate", column = @Column(name = "modified_date")),
		@AttributeOverride(name = "status", column = @Column(name = "status")) })
public class Requisition extends CommonModel implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
		
	
	@ManyToOne
	@JoinColumn(name="employee_id",nullable=false)
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="settings_id",nullable=false)
	private Settings settings;
	
	@Column(name = "purpose")
	private String purpose;
	
	@Column(name = "rejection_reason")
	private String rejectionReason;
	
	//1=>vip,0= Normal
	@Column(name = "is_vip")
	private Integer isVip=0;
	
	@Transient
	private String reqNo;
	
	@Transient
	private Integer employee_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public Integer getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	
	
}
