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
@Table(name="semester")
public class Semester implements Serializable {

	private static final long serialVersionUID = 7720738217464101162L;

	@Id
	@NotNull
	@Column(name="semester_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long semesterId;

	@NotNull
	@Column(name="semester_name")
	private String semesterName;

	@NotNull
	@Column(name="year_id")
	private String yearId;

	/*@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="year_id")
	private Year year;*/

	/*@ManyToOne
	@JoinColumn(name = "year_id")
	private Year year;*/

//	private Year year;

	public long getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(long semesterId) {
		this.semesterId = semesterId;
	}

	public String getSemesterName() {
		return semesterName;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	/*public Year getBatch() {
		return year;
	}

	public void setBatch(Year year) {
		this.year = year;
	}*/

	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
	}
}


