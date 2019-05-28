package in.education.college.common.notUsing;


import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

//@Component
public class CustomSecurityFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		Principal userPrincipal = request.getUserPrincipal();
//		System.out.println("userPrincipal: "+userPrincipal);

		Set<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(r -> r.getAuthority())
				.collect(Collectors.toSet());

		System.out.print(roles);

		filterChain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}
