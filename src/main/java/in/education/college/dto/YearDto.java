package in.education.college.dto;

import in.education.college.model.Semester;

import java.util.List;

public class YearDto {

	private long yearId;
	private String year;
	private List<Semester> semseters;
	public long getYearId() {
		return yearId;
	}
	public void setYearId(long yearId) {
		this.yearId = yearId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public List<Semester> getSemseters() {
		return semseters;
	}
	public void setSemseters(List<Semester> semseters) {
		this.semseters = semseters;
	}
}
