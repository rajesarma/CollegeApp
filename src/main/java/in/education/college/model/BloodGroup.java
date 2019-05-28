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
@Table(name="blood_group")
public class BloodGroup implements Serializable {

	private static final long serialVersionUID = 5502334730669218426L;

	@Id
	@NotNull
	@Column(name="blood_group_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long bloodGroupId;

	@Column(name="blood_group")
	private String bloodGroup;

	public long getBloodGroupId() {
		return bloodGroupId;
	}

	public void setBloodGroupId(long bloodGroupId) {
		this.bloodGroupId = bloodGroupId;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
}


