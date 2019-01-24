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

@Entity
@Table(name = "sms_settings")

public class Settings implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "stage")
	private Integer stage;
	//1=>reject,0=can not reject
	@Column(name = "can_reject")
	private Integer canReject;
	//1=>mandatory,0=not Mandatory
	@Column(name = "isReject_reason_man")
	private Integer isRejectReasonMandatory;
	
	@Column(name = "view_text")
	private String viewText;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "btn_text")
	private String btnText;
	
	@Column(name = "btn_class")
	private String btnClass;
	
	@Column(name = "auth_role")
	private String authRole;
	
	@Column(name = "status")
	private Integer status=1;
	
	@ManyToOne
	@JoinColumn(name="reject_to",nullable=true)
	private Settings rejectTo;
	
	@Transient
	private Integer rejectToId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public String getViewText() {
		return viewText;
	}

	public void setViewText(String viewText) {
		this.viewText = viewText;
	}

	public String getBtnText() {
		return btnText;
	}

	public void setBtnText(String btnText) {
		this.btnText = btnText;
	}

	public String getBtnClass() {
		return btnClass;
	}

	public void setBtnClass(String btnClass) {
		this.btnClass = btnClass;
	}

	public String getAuthRole() {
		return authRole;
	}

	public void setAuthRole(String authRole) {
		this.authRole = authRole;
	}

	public Settings getRejectTo() {
		return rejectTo;
	}

	public void setRejectTo(Settings rejectTo) {
		this.rejectTo = rejectTo;
	}

	public Integer getRejectToId() {
		return rejectToId;
	}

	public void setRejectToId(Integer rejectToId) {
		this.rejectToId = rejectToId;
	}

	public Integer getCanReject() {
		return canReject;
	}

	public void setCanReject(Integer canReject) {
		this.canReject = canReject;
	}

	public Integer getIsRejectReasonMandatory() {
		return isRejectReasonMandatory;
	}

	public void setIsRejectReasonMandatory(Integer isRejectReasonMandatory) {
		this.isRejectReasonMandatory = isRejectReasonMandatory;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

}
