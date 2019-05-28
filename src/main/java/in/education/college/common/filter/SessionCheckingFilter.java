package in.education.college.common.filter;

import in.education.college.common.util.Constants.Urls;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//@Component
public class SessionCheckingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String path = httpRequest.getRequestURI();

		// Allow css, js, images
		if (path.equals(Urls.APPLICATION_ROOT) || path.contains(Urls.CSS) || path.contains(
				Urls.JS) || path.contains(Urls.IMAGES)
				|| path.contains(Urls.FAVICON)
		) {
			filterChain.doFilter(request, response);

		} else {

			int pathStartPosition = path.indexOf("/");
			String action = path.substring(pathStartPosition + 1);
//			String link = path.substring(pathStartPosition + 1, path.length());

			/*if ((action.equalsIgnoreCase("login")
				|| action.equalsIgnoreCase("logout")
				|| action.equalsIgnoreCase("home") ))
			*/

			String[] allowedUrls = {"login", "logout", "home"};
			List<String> allowedUrlsList = Arrays.asList(allowedUrls);

			if (allowedUrlsList.stream().anyMatch(action :: equalsIgnoreCase)) {
				filterChain.doFilter(request, response);
			} else {

				RequestDispatcher rd = httpRequest.getRequestDispatcher("/expireSession");

				HttpSession session = httpRequest.getSession();

				if (session == null) {
					rd.forward(httpRequest, httpResponse);
				} else if (session.getAttribute("user") == null) {
					rd.forward(httpRequest, httpResponse);
				} else if (session.getAttribute("services") != null) {
					List<String> services = (List) session.getAttribute("serviceUrls");
					//services.stream().forEach(System.out :: println);

					//if (services.contains(path.trim())) {
					if( services.stream().anyMatch(str -> path.contains(str))
						|| services.stream().anyMatch(str -> str.contains(path))
					) {
						filterChain.doFilter(request, response);
					} else {
						rd.forward(httpRequest, httpResponse);
					}
				} else {
					rd.forward(httpRequest, httpResponse);
				}
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	public void destroy() {

	}

}
