package in.education.college.dto;

import in.education.college.model.Branch;

public class SubjectDto {
	private long subjectId;
	private String subjectName;
	private long credits;
	private Branch branch;

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public long getCredits() {
		return credits;
	}

	public void setCredits(long credits) {
		this.credits = credits;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
}
