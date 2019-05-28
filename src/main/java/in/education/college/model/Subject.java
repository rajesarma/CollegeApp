package in.education.college.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//@Entity
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

	@NotNull
	@Column(name="credits")
	private long credits;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="branch_id")
	private Branch branch;

/*	Branch Id
	Semester Id
			Relations*/
}
