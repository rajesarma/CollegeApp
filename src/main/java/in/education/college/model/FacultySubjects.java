package in.education.college.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "faculty_subjects")
public class FacultySubjects implements Serializable {

	private static final long serialVersionUID = -8647509634320082495L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long facultySubjectId;

	@Column(name = "emp_id")
	private long empId;

	@Column(name = "branch_id")
	private long branchId = 0;

	@Column(name = "academic_year_id")
	private long academicYearId;

	@Column(name = "semester_id")
	private long semesterId;

	@Column(name = "subject_id")
	private long subjectId;

	public long getFacultySubjectId() {
		return facultySubjectId;
	}

	public void setFacultySubjectId(long facultySubjectId) {
		this.facultySubjectId = facultySubjectId;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public long getBranchId() {
		return branchId;
	}

	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}

	public long getAcademicYearId() {
		return academicYearId;
	}

	public void setAcademicYearId(long academicYearId) {
		this.academicYearId = academicYearId;
	}

	public long getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(long semesterId) {
		this.semesterId = semesterId;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}


}



