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
@Table(name = "employee")
public class Employee implements Serializable {
	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "lxn_id")
	private String lxnId;

	@Column(name = "name")
	private String name;

	@Column(name = "punch_id")
	private Short punchId;

	@Column(name = "department", nullable = true)
	private String department;

	@Column(name = "designation")
	private String designation;

	@Column(name = "status")
	private Integer status;

	@Column(name = "status_name")
	private String statusName;

	@Column(name = "blood_group")
	private String bloodGroup;

	@Column(name = "personal_phone")
	private String personalPhone;

	@Column(name = "work_phone")
	private String workPhone;

	@Column(name = "office")
	private String office;

	@Column(name = "device")
	private String device;

	@Column(name = "date_joined")
	private String dateJoined;

	@Column(name = "date_terminated")
	private String dateTerminated;

	@Column(name = "date_resigned")
	private String dateResigned;

	@Column(name = "dob")
	private String dob;

	@Column(name = "value1")
	private Integer value1;

	@Column(name = "emp_id")
	private Integer empId;

	@Column(name = "gross_salary")
	private Double grossSalary = 0.0;

	@Column(name = "job_grade")
	private String jobGrade;

	@Column(name = "supervisor_id")
	private Integer supervisorId;

	@Column(name = "2nd_line_manager_id")
	private Integer secondLineManagerId;

	@Column(name = "2nd_line_manager_name")
	private String secondLineManagerName;

	@Column(name = "supervisor_name")
	private String supervisorName;

	@Column(name = "email")
	private String email;

	@Column(name = "gender")
	private String gender;

	@Column(name = "emp_type")
	private String empType;

	// 0= office, 1= office
	@Column(name = "is_project_emp")
	private String projectEmp = "0";

	// 0=no and 1=yes
	@Column(name = "is_admin_get_email")
	private String adminGetEmail = "0";

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@Transient
	private Integer companyId;

	@Transient
	private String employeeIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLxnId() {
		return lxnId;
	}

	public void setLxnId(String lxnId) {
		this.lxnId = lxnId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getPunchId() {
		return punchId;
	}

	public void setPunchId(Short punchId) {
		this.punchId = punchId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(String dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getDateTerminated() {
		return dateTerminated;
	}

	public void setDateTerminated(String dateTerminated) {
		this.dateTerminated = dateTerminated;
	}

	public String getDateResigned() {
		return dateResigned;
	}

	public void setDateResigned(String dateResigned) {
		this.dateResigned = dateResigned;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Integer getValue1() {
		return value1;
	}

	public void setValue1(Integer value1) {
		this.value1 = value1;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getProjectEmp() {
		return projectEmp;
	}

	public void setProjectEmp(String projectEmp) {
		this.projectEmp = projectEmp;
	}

	public Integer getSecondLineManagerId() {
		return secondLineManagerId;
	}

	public void setSecondLineManagerId(Integer secondLineManagerId) {
		this.secondLineManagerId = secondLineManagerId;
	}

	public String getSecondLineManagerName() {
		return secondLineManagerName;
	}

	public void setSecondLineManagerName(String secondLineManagerName) {
		this.secondLineManagerName = secondLineManagerName;
	}

	public Double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(Double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public String getJobGrade() {
		return jobGrade;
	}

	public void setJobGrade(String jobGrade) {
		this.jobGrade = jobGrade;
	}

	public String getAdminGetEmail() {
		return adminGetEmail;
	}

	public void setAdminGetEmail(String adminGetEmail) {
		this.adminGetEmail = adminGetEmail;
	}

	public String getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(String employeeIds) {
		this.employeeIds = employeeIds;
	}

}
