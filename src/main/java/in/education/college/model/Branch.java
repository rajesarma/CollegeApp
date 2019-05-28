package in.education.college.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "branch")
public class Branch implements Serializable {

	private static final long serialVersionUID = 4332929521344124479L;

	@Id
	@NotNull
	@Column(name = "branch_id")
	private String branchId;

	@Column(name = "branch_name")
	private String branchName;

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}
