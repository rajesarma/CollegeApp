package in.education.college.common.interceptor;

import in.education.college.common.exception.InvalidHeaderFieldException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class RequestHeaderInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if(request.getHeader("college-auth-key") == null) {
			throw new InvalidHeaderFieldException("Header Filed is not available");
		}

		return super.preHandle(request, response, handler);
	}
}
