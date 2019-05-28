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
@Table(name = "qualifications")
public class Qualification implements Serializable {

	private static final long serialVersionUID = 7378369356289737092L;

	@Id
	@NotNull
	@Column(name = "qly_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long qlyId;

	@Column(name="qly_name")
	private String qlyName;

	public long getQlyId() {
		return qlyId;
	}

	public void setQlyId(long qlyId) {
		this.qlyId = qlyId;
	}

	public String getQlyName() {
		return qlyName;
	}

	public void setQlyName(String qlyName) {
		this.qlyName = qlyName;
	}
}
