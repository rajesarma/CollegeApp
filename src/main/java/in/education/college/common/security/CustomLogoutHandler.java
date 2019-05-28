package in.education.college.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Acutal logout process is taken care by Spring Sec itself.
// This class only deal with response.


public class CustomLogoutHandler implements LogoutHandler {
	@Override
	public void logout(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Authentication authentication) {
//		System.out.println("In Logout: "+authentication.getPrincipal());

		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
		}
		try {
			httpServletResponse.sendRedirect("redirect:/login?logout");
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}
}
