package in.education.college.dto;

import java.util.List;

public class StudentMarksDto {

	private long studentMarksId;

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

	private long subjectId;
	private String subject;

	private long examTypeId;
	private String examType;

	private long marks;
	private long maxMarks;

	private String rollNo;
	private String name;

	private List<Long> studentIds;

	private List<StudentMarksDto> marksDtos;

	public long getStudentMarksId() {
		return studentMarksId;
	}

	public void setStudentMarksId(long studentMarksId) {
		this.studentMarksId = studentMarksId;
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

	public long getMarks() {
		return marks;
	}

	public void setMarks(long marks) {
		this.marks = marks;
	}

	public long getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(long maxMarks) {
		this.maxMarks = maxMarks;
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

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public long getExamTypeId() {
		return examTypeId;
	}

	public void setExamTypeId(long examTypeId) {
		this.examTypeId = examTypeId;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public List<StudentMarksDto> getMarksDtos() {
		return marksDtos;
	}

	public void setMarksDtos(List<StudentMarksDto> marksDtos) {
		this.marksDtos = marksDtos;
	}

	public List<Long> getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(List<Long> studentIds) {
		this.studentIds = studentIds;
	}
}
