package in.education.college.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 2525398598512062264L;

	@Id
	@Column(name = "emp_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long employeeId;

	@Column(name = "name")
	private String name=null;

	@Column(name = "emp_no")
	private String empNo=null;

	@Column(name = "dept_id")
	private long deptId;

	@Column(name = "father_name")
	private String fatherName=null;

	@Temporal(TemporalType.DATE)
	@Column(name = "dob")
	private Date dob=null;

	@Temporal(TemporalType.DATE)
	@Column(name = "doj")
	private Date doj=null;

	@Column(name = "photo_name")
	private String photoName=null;

	@Column(name = "photo")
	@Lob
	private byte[] photo;

	@Column(name = "aadhar")
	private String aadharNo=null;

	@Column(name = "address")
	private String address=null;

	@Column(name = "email")
	private String email=null;

	@Column(name = "mobile")
	@Size(min = 10, max = 10, message = "Phone No. must be 10 Digits")
	private String mobile=null;

	@Column(name = "blood_group_id")
	private long bloodGroupId = 0;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_releiving")
	private Date dateOfRelieving =null;

	@Column(name = "qly_id")
	private long qlyId;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender", length = 10)
	private Gender gender = Gender.MALE;

	@Column(name = "salary")
	private Double salary;

	@Column(name = "joining_academic_year_id")
	private long joiningAcademicYearId;

	@Column(name = "joining_semester_id")
	private long joiningSemesterId;

	@Column(name="disabled")
	private Boolean disabled;


	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getBloodGroupId() {
		return bloodGroupId;
	}

	public void setBloodGroupId(long bloodGroupId) {
		this.bloodGroupId = bloodGroupId;
	}

	public Date getDateOfRelieving() {
		return dateOfRelieving;
	}

	public void setDateOfRelieving(Date dateOfRelieving) {
		this.dateOfRelieving = dateOfRelieving;
	}

	public long getQlyId() {
		return qlyId;
	}

	public void setQlyId(long qlyId) {
		this.qlyId = qlyId;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public long getJoiningAcademicYearId() {
		return joiningAcademicYearId;
	}

	public void setJoiningAcademicYearId(long joiningAcademicYearId) {
		this.joiningAcademicYearId = joiningAcademicYearId;
	}

	public long getJoiningSemesterId() {
		return joiningSemesterId;
	}

	public void setJoiningSemesterId(long joiningSemesterId) {
		this.joiningSemesterId = joiningSemesterId;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"employeeId=" + employeeId +
				", name='" + name + '\'' +
				", empNo='" + empNo + '\'' +
				", deptId='" + deptId + '\'' +
				", fatherName='" + fatherName + '\'' +
				", dob=" + dob +
				", doj=" + doj +
				", photoName='" + photoName + '\'' +
				", photo=" + Arrays.toString(photo) +
				", aadharNo='" + aadharNo + '\'' +
				", address='" + address + '\'' +
				", email='" + email + '\'' +
				", mobile='" + mobile + '\'' +
				", bloodGroupId=" + bloodGroupId +
				", dateOfRelieving=" + dateOfRelieving +
				", qlyId=" + qlyId +
				", gender=" + gender +
				", salary=" + salary +
				", joiningAcademicYearId=" + joiningAcademicYearId +
				", joiningSemesterId=" + joiningSemesterId +
				", disabled=" + disabled +
				'}';
	}
}



