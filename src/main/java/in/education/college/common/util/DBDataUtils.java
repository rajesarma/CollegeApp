package in.education.college.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@Component
public class DBDataUtils {

	private DBUtils dbUtils;

	@Autowired
	DBDataUtils(DBUtils dbUtils) {
		this.dbUtils = dbUtils;
	}

	public Map getAcademicYears() throws SQLException {
		return dbUtils.getSelectMap(GeneralQueries.getAcademicYears);
	}

	public Map getBloodGroups() throws SQLException {
		return dbUtils.getSelectMap(GeneralQueries.getBloodGroups);
	}

	public Map getBranches() throws SQLException {
		return dbUtils.getSelectMap(GeneralQueries.getBranches);
	}

	public Map getYears() throws SQLException {
		return dbUtils.getSelectMap(GeneralQueries.getYears);
	}

	public String isRollNoExists(String rollNo) throws SQLException {

		int count = dbUtils.getNumericValue(GeneralQueries.isRollNoExists(rollNo));

		if(count > 0)
			return "{\"rollNoExists\":\"true\", \"message\":\"Roll No. already exists\"  }";
		else
			return "{\"rollNoExists\":\"false\" }";
	}


}
