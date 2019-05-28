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
@Table(name = "academic_year")
public class AcademicYear implements Serializable {

	private static final long serialVersionUID = -3927031149215943806L;

	@Id
	@NotNull
	@Column(name = "year_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long academicYearId;

	@Column(name="year")
	private String academicYear;

	public long getAcademicYearId() {
		return academicYearId;
	}

	public void setAcademicYearId(long academicYearId) {
		this.academicYearId = academicYearId;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
}
