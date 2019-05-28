package in.education.college.common.security;

import in.education.college.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFailure extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {

		String username = request.getParameter("username");
		userService.registerFaliureLogin(username);

		super.setDefaultFailureUrl("/");
		super.onAuthenticationFailure(request, response, exception);

	}
}
