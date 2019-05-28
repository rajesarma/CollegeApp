package in.education.college.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// Acutal logout process is taken care by Spring Sec itself.
// This class only deal with response.

@Component
public class LogoutSuccess implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
//		System.out.println("Logged Out: "+ authentication.getName());

		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
		}
		try {
			httpServletResponse.sendRedirect("redirect:/login?logout");
		} catch (IOException e) {
			e.printStackTrace();
		}*/

//		httpServletResponse.setStatus(HttpServletResponse.SC_OK);	//200

//		httpServletResponse.sendRedirect("/logoutsuccessful");
	}
}
