package in.education.college.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DBUtils {

	private DataSource dataSource;
	private Connection connection;
	private Statement statement;

	@Autowired
	public DBUtils(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public Connection getDBConnection() {
		try {
			connection = dataSource.getConnection();
//			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	@Bean
	public Statement getDBStatement() {

		try {
			statement = connection.createStatement();
			return statement;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean recordExists(String sql)
	{
		ResultSet rs = null;
		try
		{
			rs = statement.executeQuery(sql);

			if (rs!=null && rs.next()) {
				if (rs.getString(1) != null && rs.getInt(1) > 0) {
					return true;
				}
			}
		} catch (SQLException e) {

			return false;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public String getTextValue(String sql) {
		ResultSet rs = null;
		try
		{
			rs = statement.executeQuery(sql);
			if (rs!=null && rs.next()) {
				if (rs.getString(1)!=null) {

					return rs.getString(1);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public int getNumericValue(String sql) {
		ResultSet rs = null;
		try
		{
			rs = statement.executeQuery(sql);
			if (rs != null && rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	public static void closeResultSet(ResultSet _rs) {
		try {
			if(_rs!=null) {
				_rs.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeStatement(Statement _stmt) {
		try {
			if(_stmt!=null) {
				_stmt.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<HashMap<String,Object>> getListMap(String sql,
			Connection con,Object[] columnvalues) throws SQLException {

		ArrayList<HashMap<String,Object>> records=new ArrayList<>();
		PreparedStatement ps = con.prepareStatement(sql);

		if (columnvalues!=null) {
			for (int ci=0;ci<columnvalues.length;ci++) {
				ps.setObject(ci+1, columnvalues[ci]);
			}
		}

		ResultSet rs=ps.executeQuery();
		ResultSetMetaData rm = rs.getMetaData();

		int cols = rm.getColumnCount();
		if (rs.next()) {
			do {
				HashMap<String, Object> row = new java.util.HashMap<>(cols);
				for (int i=1; i<=cols; i++) {
					String columnName = rm.getColumnName(i);
					Object columnValue = rs.getObject(i);
					row.put(columnName.trim(), columnValue);
				}
				records.add(row);
				row = null;
			}
			while (rs.next());
		}
		closeResultSet(rs);
		closeStatement(ps);
		return records;
	}

	public static Map<String, Object> getMapData(ResultSet rs)
	{
		ResultSetMetaData rm;
		Map<String, Object> reportData = new HashMap<>();
		int noOfCols;
		try {
			rm = rs.getMetaData();
			noOfCols = rm.getColumnCount();
			if (rs.next()) {
				reportData = new HashMap<>(noOfCols);
				for(int i=1;i<=noOfCols;i++)
				{
					reportData.put(rm.getColumnName(i), ((rs.getObject(i)!=null && !rs.getObject(i).equals(""))?rs.getObject(i):""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return reportData;
	}

	public Map<String, Object> getMapData(String sql)
	{
		Map<String, Object> reportData = new HashMap<>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);

			reportData = getMapData(rs);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportData;
	}


	public Map<String,Object> getSelectMap(String sql) throws SQLException {

		Map<String,Object> record = new HashMap<>();
		PreparedStatement ps = connection.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rm = rs.getMetaData();

		while (rs.next()) {
			record.put(rs.getString(1), rs.getString(2));
		}
		closeResultSet(rs);
		closeStatement(ps);
		return record;
	}

}
