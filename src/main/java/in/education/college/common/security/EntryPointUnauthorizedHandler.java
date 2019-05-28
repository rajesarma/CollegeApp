package in.education.college.common.security;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that handles any Unauthorized requests. <br>
 * Returns a HTTP Status 403 if user doesn't have permission<br>
 * 
 * */

// Handles any Unauthorized requests. Returns
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e)
			throws IOException, ServletException {
		
		if(e instanceof InsufficientAuthenticationException){
//			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not Authenticated");	//401
			res.sendRedirect("/");
		} else {
			e.printStackTrace();
			res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");	//403
		}
	}
}
