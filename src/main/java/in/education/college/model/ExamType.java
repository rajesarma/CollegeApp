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
@Table(name="exam_type")
public class ExamType implements Serializable {

	private static final long serialVersionUID = 9135836134122181132L;

	@Id
	@NotNull
	@Column(name="exam_type_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long examTypeId;

	@NotNull
	@Column(name="exam_type")
	private String examType;

	@NotNull
	@Column(name="max_marks")
	private long maxMarks;
}


