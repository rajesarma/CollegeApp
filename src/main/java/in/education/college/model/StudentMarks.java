package in.education.college.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity

/*select * from
		(
		select sa.batch_id, sa.branch_id, sa.year_id, sa.semester_id, sd.student_id, sa.subject_id, sa.exam_type_id, roll_no, student_name, coalesce(sa.marks,0) as marks, max_marks
		from student_marks sa
		left join student_details sd on (sd.student_id = sa.student_id )
		where sa.branch_id ='12' and sa.year_id = 1 and sa.semester_id = 1 and sa.subject_id = 1 and sa.exam_type_id = 1
		)list
		union
		(
		select 1 as batch_id, sd.branch_id, 1 as year_id, 1 as semester_id, sd.student_id, 1 as subject_id, 1 as exam_type_id, roll_no, student_name, 0 as marks, 0 as max_marks
		from student_details sd
		where joining_year_id = 1 and sd.branch_id = '12' and
		student_id not in
		( select student_id from student_marks sa
		where sa.branch_id ='12' and sa.year_id = 1 and sa.semester_id = 1 and sa.subject_id = 1 and sa.exam_type_id = 1
		)
		)
		order by roll_no, student_name;*/

@Table(name = "student_marks")

public class StudentMarks implements Serializable {

	private static final long serialVersionUID = -7924148895459404773L;

	@Id
	@NotNull
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // , generator="native"
	private long studentMarksId;

	@Column(name = "batch_id")
	private long batchId;

	@Column(name = "branch_id")
	private String branchId;

	@Column(name = "year_id")
	private long yearId;

	@Column(name = "semester_id")
	private long semesterId;

	@Column(name = "student_id")
	private long studentId;

	@Column(name = "subject_id")
	private long subjectId;

	@Column(name = "exam_type_id")
	private long examTypeId;

	@Column(name = "marks")
	private long marks;

	@Column(name = "max_marks")
	private long maxMarks;


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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public long getYearId() {
		return yearId;
	}

	public void setYearId(long yearId) {
		this.yearId = yearId;
	}

	public long getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(long semesterId) {
		this.semesterId = semesterId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public long getExamTypeId() {
		return examTypeId;
	}

	public void setExamTypeId(long examTypeId) {
		this.examTypeId = examTypeId;
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

	@Override
	public String toString() {
		return "StudentMarks{" +
				"studentMarksId=" + studentMarksId +
				", batchId=" + batchId +
				", branchId='" + branchId + '\'' +
				", yearId=" + yearId +
				", semesterId=" + semesterId +
				", studentId=" + studentId +
				", subjectId=" + subjectId +
				", examTypeId=" + examTypeId +
				", marks=" + marks +
				", maxMarks=" + maxMarks +
				'}';
	}
}
