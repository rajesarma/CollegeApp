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
@Table(name="department")
public class Department implements Serializable {

	private static final long serialVersionUID = -1310257224012837085L;

	@Id
	@NotNull
	@Column(name="dept_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long detpId;

	@Column(name="name")
	private String deptName;

	@Column(name="disabled")
	private Boolean disabled;

	public long getDetpId() {
		return detpId;
	}

	public void setDetpId(long detpId) {
		this.detpId = detpId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
}


