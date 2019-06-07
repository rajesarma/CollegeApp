package in.education.college.model;

import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="subjects")
public class Subject implements Serializable {

	private static final long serialVersionUID = -370581194732875450L;

	@Id
	@NotNull
	@Column(name="subject_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long subjectId;

	@NotNull
	@Column(name="subject_name")
	private String subjectName;

	@Column(name = "branch_id", insertable = false, updatable = false)
	private String branchId;

	@Column(name = "semester_id", insertable = false, updatable = false)
	private long semesterId;

	@NotNull
	@Column(name="credits")
	private long credits;

	@ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	@JoinColumn(name="branch_id", referencedColumnName="branch_id")
	private Branch branch;

	@ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	@JoinColumn(name="semester_id", referencedColumnName="semester_id")
	private Semester semester;


	@ManyToMany(fetch= FetchType.EAGER)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinTable(name="subject_exam_types",
			joinColumns = {@JoinColumn(name="subject_id", referencedColumnName="subject_id")},
			inverseJoinColumns = {@JoinColumn(name="exam_type_id", referencedColumnName=
					"exam_type_id", unique = false)}
	)
	private List<ExamType> examTypes;

	/*@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="branch_id")
	private Branch branch;*/

/*	Branch Id
	Semester Id
			Relations*/

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public long getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(long semesterId) {
		this.semesterId = semesterId;
	}

	public long getCredits() {
		return credits;
	}

	public void setCredits(long credits) {
		this.credits = credits;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}


	public List<ExamType> getExamTypes() {
		return examTypes;
	}

	public void setExamTypes(List<ExamType> examTypes) {
		this.examTypes = examTypes;
	}
}
