package in.education.college.common.authentication;

import in.education.college.common.util.GeneralQueries;
import in.education.college.common.util.DBUtils;
import in.education.college.common.exception.AuthenticationException;
import in.education.college.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

//@Repository("jdbc")
public class AuthenticationRepository {

//	@Autowired
	private DBUtils dbUtils;

	@Autowired
	AuthenticationRepository(DBUtils dbUtils) {
		this.dbUtils = dbUtils;
	}

	User authenticateUser(String userName, String password) throws AuthenticationException {

		try {
			Statement statement = dbUtils.getDBStatement();

			// If User Exists
			if(dbUtils.recordExists(GeneralQueries.isUserExists(userName))) {

				// If User and Password Matches
				if(dbUtils.recordExists(GeneralQueries.isValidUser(userName,password))) {

					User user = getUserData(GeneralQueries.getUserData(userName
							, password));

					statement.execute(GeneralQueries.updateUserLogin(user.getUserId()));	// Update User Last Login
					statement.executeUpdate(GeneralQueries.resetUserFailureAttempt(user.getUserId()));	// Update User Last Login

					return user;
				} else {
					// Update user failure login attempt
					statement.executeUpdate(GeneralQueries.updateUserFailureAttempt(userName));
					throw new AuthenticationException("Username and Password does not match");
				}
			} else {
				throw new AuthenticationException("Username does not exists");
			}

		} catch (SQLException e) {

			throw new AuthenticationException("Something went wrong !!!");
		}
	}

	public User authenticateUser(String userName) throws AuthenticationException {

		// If User Exists
		if(dbUtils.recordExists(GeneralQueries.isUserExists(userName))) {
			User user = getUserData(GeneralQueries.getUserData(userName));

			return user;
		}

		return null;
	}

	private User getUserData(String sql) throws AuthenticationException {
		ResultSet rs = null;
		User user = null;
		try
		{
			rs = dbUtils.getDBStatement().executeQuery(sql);

			if(rs.next()) {
				user = new User(rs.getLong("id"), rs.getString("user_name"),
						rs.getString("real_password")
//						, rs.getString("role_name")
				);
				// rs.getLong("role_id"), rs.getString("user_desc"),

			}
		} catch (SQLException e) {
			throw new AuthenticationException("Something went Wrong");

		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return user;
	}

	ArrayList getServices(String userName, String password)
	{
		ResultSet rs;
		ArrayList<HashMap<String, String>> services = new ArrayList<>();
		HashMap<String, String> serviceMap;

		try
		{
			/*String sql = "select s.service_id, s.service_url, s.service_name,s" +
					".parent_id, s.display_order, s.menu_display" +
					" from users u" +
					" join role_services rs on(u.role_id = rs.role_id )" +
					" join services s on (s.service_id = rs.service_id)" +
					" where user_name = '"+userName+ "' " +
					" and password = '"+password+"'" +
					" order by parent_id, service_id";*/

			String sql = "select s.service_id, s.service_url, s.service_name, " +
					" s.parent_id, s.display_order, s.menu_display" +
					" from users u" +
					" join user_roles ur on (u.id = ur.user_id)" +
					" join roles r on (r.role_id = ur.role_id)" +
					" join role_services rs on (r.role_id = rs.role_id )" +
					" join services s on (s.service_id = rs.service_id)" +
					" where user_name = '"+userName+ "' " +
					" and password = '"+password+"'" +
					" order by parent_id, service_id";


			rs = dbUtils.getDBStatement().executeQuery(sql);
			while (rs.next())
			{
				serviceMap = new HashMap<>();

				serviceMap.put("serviceId", rs.getString("service_id"));
				serviceMap.put("serviceUrl", rs.getString("service_url"));
				serviceMap.put("serviceName", rs.getString("service_name"));
				serviceMap.put("parentId", rs.getString("parent_id"));
				serviceMap.put("displayOrder", rs.getString("display_order"));
				serviceMap.put("menuDisplay", rs.getString("menu_display"));

				services.add(serviceMap);
			}
		}
		catch(SQLException sqle)
		{
			return null;
		}

		return services;
	}

	ArrayList getServices(String userName)
	{
		ResultSet rs;
		ArrayList<HashMap<String, String>> services = new ArrayList<>();
		HashMap<String, String> serviceMap;

		try
		{
			/*String sql = "select s.service_id, s.service_url, s.service_name,s" +
					".parent_id, s.display_order, s.menu_display" +
					" from users u" +
					" join role_services rs on(u.role_id = rs.role_id )" +
					" join services s on (s.service_id = rs.service_id)" +
					" where user_name = '"+userName+ "' " +
					" order by parent_id, service_id";*/

			String sql = "select s.service_id, s.service_url, s.service_name, " +
					" s.parent_id, s.display_order, s.menu_display" +
					" from users u" +
					" join user_roles ur on (u.id = ur.user_id)" +
					" join roles r on (r.role_id = ur.role_id)" +
					" join role_services rs on (r.role_id = rs.role_id )" +
					" join services s on (s.service_id = rs.service_id)" +
					" where user_name = '"+userName+ "' " +
					" order by parent_id, service_id";


			rs = dbUtils.getDBStatement().executeQuery(sql);
			while (rs.next())
			{
				serviceMap = new HashMap<>();

				serviceMap.put("serviceId", rs.getString("service_id"));
				serviceMap.put("serviceUrl", rs.getString("service_url"));
				serviceMap.put("serviceName", rs.getString("service_name"));
				serviceMap.put("parentId", rs.getString("parent_id"));
				serviceMap.put("displayOrder", rs.getString("display_order"));
				serviceMap.put("menuDisplay", rs.getString("menu_display"));

				services.add(serviceMap);
			}
		}
		catch(SQLException sqle)
		{
			return null;
		}

		return services;
	}
}
