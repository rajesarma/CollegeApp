package in.education.college.dto;

public class StudentAttendanceDto {

	private long studentAttendanceId;

	private long batchId;
	private String batch;

	private String branchId;
	private String branch;

	private long yearId;
	private String year;

	private long semesterId;
	private String semester;

	private long studentId;
	private String studentName;

	private long noOfDays;
	private long daysPresent;

	private String rollNo;
	private String name;

	public long getStudentAttendanceId() {
		return studentAttendanceId;
	}

	public void setStudentAttendanceId(long studentAttendanceId) {
		this.studentAttendanceId = studentAttendanceId;
	}

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public long getYearId() {
		return yearId;
	}

	public void setYearId(long yearId) {
		this.yearId = yearId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public long getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(long semesterId) {
		this.semesterId = semesterId;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public long getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(long noOfDays) {
		this.noOfDays = noOfDays;
	}

	public long getDaysPresent() {
		return daysPresent;
	}

	public void setDaysPresent(long daysPresent) {
		this.daysPresent = daysPresent;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
