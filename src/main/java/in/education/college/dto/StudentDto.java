package in.education.college.dto;

import in.education.college.model.Gender;
import org.springframework.web.multipart.MultipartFile;

public class StudentDto {

	private long studentId;
	private String name=null;
	private String fatherName=null;
	private String motherName=null;
	private String dob=null;
	private String doj=null;
	private String photoName=null;
	private String aadharNo=null;
	private String address=null;
	private String email=null;
	private String mobileNo =null;
	private String parentPhoneNo=null;
	private long bloodGroupId = 0;
	private long batchId = 0;
	private String branchId= null;
	private String rollNo =null;
	private Gender gender = Gender.MALE;
	private MultipartFile image;
	private int height;

	private String branch = null;
	private String bloodGroup = null;
	private String year = null;
	private String photoData;
	private String batch = null;

	private long joiningYearId;

	private long joiningAcademicYearId;
	private String joiningAcademicYear= null;

	private long joiningSemesterId;
	private String semester= null;

	private long currentYearId;
	private String currentYear;

	private long currentSemesterId;
	private String currentSemester;

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getBloodGroupId() {
		return bloodGroupId;
	}

	public void setBloodGroupId(long bloodGroupId) {
		this.bloodGroupId = bloodGroupId;
	}

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getParentPhoneNo() {
		return parentPhoneNo;
	}

	public void setParentPhoneNo(String parentPhoneNo) {
		this.parentPhoneNo = parentPhoneNo;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public int getHeight() {
		return height;
	}

	public String getPhotoData() {
		return photoData;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	/*public String getPhotoData() {

		if (getPhoto() != null && getPhoto().length > 0) {
			return new String(java.util.Base64.getEncoder().encode(getPhoto()));
		}
		return null;
	}*/

	public void setPhotoData(String photoData) {
		this.photoData = photoData;
	}

	public long getJoiningYearId() {
		return joiningYearId;
	}

	public void setJoiningYearId(long joiningYearId) {
		this.joiningYearId = joiningYearId;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public long getJoiningSemesterId() {
		return joiningSemesterId;
	}

	public void setJoiningSemesterId(long joiningSemesterId) {
		this.joiningSemesterId = joiningSemesterId;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public long getCurrentYearId() {
		return currentYearId;
	}

	public void setCurrentYearId(long currentYearId) {
		this.currentYearId = currentYearId;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public long getCurrentSemesterId() {
		return currentSemesterId;
	}

	public void setCurrentSemesterId(long currentSemesterId) {
		this.currentSemesterId = currentSemesterId;
	}

	public String getCurrentSemester() {
		return currentSemester;
	}

	public void setCurrentSemester(String currentSemester) {
		this.currentSemester = currentSemester;
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
}



