package in.education.college.common.security;

import in.education.college.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Acutal logout process is taken care by Spring Sec itself.
// This class only deal with response.


public class CustomLogoutHandler implements LogoutHandler {
//	private static final Logger log = LoggerFactory.getLogger(LogoutSuccess.class);
	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse httpServletResponse, Authentication authentication) {

		/*if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() !=null) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			log.info("User logged out: " + user.getUsername() + " - " + request.getRemoteHost());
		}
*/
//		System.out.println("In Logout: "+authentication.getPrincipal());

		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, httpServletResponse, auth);
		}
		try {
//			httpServletResponse.sendRedirect("redirect:/login?logout");
			httpServletResponse.sendRedirect("redirect:/logout");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}
