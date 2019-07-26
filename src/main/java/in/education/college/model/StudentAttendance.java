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

/*query="SELECT * FROM todos t WHERE " +
				"LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
				"LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))",*/

/*@NamedNativeQuery(name = "StudentAttendance.findAllByBatchIdAndBranchIdAndAcademicYearIdAndSemesterId",
		query = " select batch_id, branch_id, academic_year_id, semester_id, student_id, roll_no,  student_name, no_of_days, days_present " +
				" from " +
				" (" +
				"   select sd.batch_id, sa.branch_id, sa.academic_year_id, sa.semester_id, sd.student_id,  " +
				"   coalesce(sa.no_of_days,0) as no_of_days, coalesce(sa.days_present,0) as days_present, roll_no, student_name" +
				"   from student_attendance sa " +
				"   left join student_details sd on (sd.student_id = sa.student_id )" +
				"   where sa.batch_id = :batch_id" +
				"   and sa.branch_id = :branch_id" +
				"   and sa.academic_year_id = :academic_year_id" +
				"   and sa.semester_id = :semester_id" +
				" )list" +
				" union" +
				" (" +
				" select sd.batch_id, sd.branch_id, :academic_year_id as academic_year_id, " +
				" :semester_id as semester_id, sd.student_id, 0 as no_of_days, 0 as " +
				"  days_present, roll_no, student_name " +
				"  from student_details sd" +
				"  left join student_attendance sa on (sa.student_id = sd.student_id)" +
				"  where student_id not in" +
				"  ( select student_id from student_attendance " +
				"     where batch_id = :batch_id" +
				"     and branch_id = :branch_id" +
				"     and academic_year_id = :academic_year_id" +
				"     and semester_id = :semester_id" +
				"  )" +
				"   and sd.batch_id = :batch_id" +
				"   and sd.branch_id = :branch_id" +
				" )" +
				" order by roll_no, student_name",
		resultClass = StudentAttendance.class
		)*/

@Table(name = "student_attendance")

public class StudentAttendance implements Serializable {

	private static final long serialVersionUID = 2471633029396797302L;

	@Id
	@NotNull
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.AUTO, generator="native")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long studentAttendanceId;

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

	@Column(name = "no_of_days")
	private long noOfDays;

	@Column(name = "days_present")
	private long daysPresent;

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

	@Override
	public String toString() {
		return "StudentAttendance{" +
				"studentAttendanceId=" + studentAttendanceId +
				", batchId=" + batchId +
				", branchId='" + branchId + '\'' +
				", yearId=" + yearId +
				", semesterId=" + semesterId +
				", studentId=" + studentId +
				", noOfDays=" + noOfDays +
				", daysPresent=" + daysPresent +
				'}';
	}
}
