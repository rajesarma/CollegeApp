package in.education.college.common.security;

import in.education.college.common.util.Constants.Roles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityConfigurationTest {

	private WebSecurityConfig webSecurityConfig = new WebSecurityConfig();

	@Test
	public void hasRoleTest1() {
		String actual = webSecurityConfig.hasRole(Roles.ADMIN_ROLE, Roles.STUDENT_ROLE);
		String expected = "hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')";
		Assert.assertEquals("Role Matcher should match"	,actual, expected);
	}

	@Test
	public void hasRoleTest2() {
		String actual = webSecurityConfig.hasRole(Roles.ADMIN_ROLE);
		String expected = "hasRole('ROLE_ADMIN')";
		Assert.assertEquals("Role Matcher should match",	actual, expected);
	}
}
