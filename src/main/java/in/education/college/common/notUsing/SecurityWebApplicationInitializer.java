package in.education.college.common.notUsing;

import in.education.college.common.security.WebSecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//@Component
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	/*public SecurityWebApplicationInitializer() {
		super(WebSecurityConfig.class);
	}*/

	public SecurityWebApplicationInitializer() {
		super(WebSecurityConfig.class);
	}
}
