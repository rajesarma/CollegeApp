package in.education.college.common.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//@Component
@Order(2)	// Filter will Fire in this order
public class RequestResponseLoggingFilter implements Filter {

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		//LOG.info( "Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
		chain.doFilter(request, response);

		//LOG.info( "Logging Response :{}", res.getContentType());
	}

	// other methods
}