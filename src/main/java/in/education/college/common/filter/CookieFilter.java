package in.education.college.common.filter;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class CookieFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// Secure Session Cookie:
		// This configuration option is available since Java servlet 3.
		// By default, http-only is true and secure is false.

		Cookie[] allCookies = httpRequest.getCookies();
		if (allCookies != null) {
			Cookie session =
					Arrays.stream(allCookies).filter(x -> x.getName().equals("JSESSIONID"))
							.findFirst().orElse(null);

			if (session != null) {
				// httpOnly: if true then browser script won’t be able to access the cookie
				// secure: if true then the cookie will be sent only over HTTPS connection
				session.setHttpOnly(true);
				session.setSecure(true);
				httpResponse.addCookie(session);
			}
		}
		filterChain.doFilter(httpRequest, httpResponse);
	}
}
