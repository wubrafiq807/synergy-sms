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
@Table(name = "sms_requisition_history")
@AttributeOverrides({@AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
		@AttributeOverride(name = "createdDate", column = @Column(name = "created_date")),
		 @AttributeOverride(name = "remarks", column = @Column(name = "remarks")),		
		@AttributeOverride(name = "status", column = @Column(name = "status")) })
public class RequisitionHistory extends CommonModel implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;		
	
	@ManyToOne
	@JoinColumn(name="requisition_id",nullable=false)
	private Requisition requisition;
	
	@ManyToOne
	@JoinColumn(name="settings_id",nullable=false)
	private Settings settings;
	
	//1=>rejected,0=>not Rejected
	@Column(name="is_rejected")
	
	private Integer isRejected=0;
	
	@Column(name = "rejection_reason")
	private String rejectionReason;

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

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public Integer getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(Integer isRejected) {
		this.isRejected = isRejected;
	}
	
	
}
