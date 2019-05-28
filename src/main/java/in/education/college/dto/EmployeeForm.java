package in.education.college.dto;

import in.education.college.model.Gender;
import org.springframework.web.multipart.MultipartFile;

public class EmployeeForm {

	private long employeeId;
	private String name=null;
	private String empNo =null;
	private long deptId;
	private String department = null;
	private String fatherName=null;
	private String dob=null;
	private String doj=null;
	private String photoName=null;
	private MultipartFile image;
	private String aadharNo=null;
	private String address=null;
	private String email=null;
	private String mobile=null;
	private long bloodGroupId = 0;
	private String bloodGroup = null;
	private String dateOfRelieving=null;
	private long qlyId = 0;
	private String qualification = null;
	private Gender gender = Gender.MALE;
	private Double salary;
	private long joiningAcademicYearId;
	private String joiningAcademicYear=null;
	private long joiningSemesterId;
	private String joiningSemester=null;
	private String photoData;

	private String conditionString;

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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
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

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getDateOfRelieving() {
		return dateOfRelieving;
	}

	public void setDateOfRelieving(String dateOfRelieving) {
		this.dateOfRelieving = dateOfRelieving;
	}

	public long getQlyId() {
		return qlyId;
	}

	public void setQlyId(long qlyId) {
		this.qlyId = qlyId;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public String getJoiningAcademicYear() {
		return joiningAcademicYear;
	}

	public void setJoiningAcademicYear(String joiningAcademicYear) {
		this.joiningAcademicYear = joiningAcademicYear;
	}

	public long getJoiningSemesterId() {
		return joiningSemesterId;
	}

	public void setJoiningSemesterId(long joiningSemesterId) {
		this.joiningSemesterId = joiningSemesterId;
	}

	public String getJoiningSemester() {
		return joiningSemester;
	}

	public void setJoiningSemester(String joiningSemester) {
		this.joiningSemester = joiningSemester;
	}

	public String getPhotoData() {
		return photoData;
	}

	public void setPhotoData(String photoData) {
		this.photoData = photoData;
	}

	public String getConditionString() {
		return conditionString;
	}

	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}
}



