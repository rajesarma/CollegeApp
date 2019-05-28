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
@Table(name = "batch")
public class Batch implements Serializable {
	private static final long serialVersionUID = -7207381425810271152L;

	@Id
	@NotNull
	@Column(name = "batch_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long batchId;

	@Column(name = "batch_name")
	private String batchName;

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
}
