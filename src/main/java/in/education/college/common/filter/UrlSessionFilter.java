package in.education.college.common.filter;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Component
public class UrlSessionFilter implements Filter {

	/*public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		LOG.info(
				"Starting a transaction for req : {}",
				req.getRequestURI());

		System.out.println("In Filter");
		chain.doFilter(request, response);
		LOG.info(
				"Committing a transaction for req : {}",
				req.getRequestURI());

	}*/

	public void init(FilterConfig filterConfig) {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
//			public String encodeRedirectUrl(String url) {
//				return url;
//			}

			public String encodeRedirectURL(String url) {
				return url;
			}

//			public String encodeUrl(String url) {
//				return url;
//			}

			public String encodeURL(String url) {
				return url;
			}
		};
		chain.doFilter(request, wrappedResponse);
	}



	public void destroy() {
	}

}
