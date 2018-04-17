package media.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import media.data.model.User;

public class PatientSecurityInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object userObj = request.getAttribute(SecurityInterceptor.LOGGED_USER);
		if (!(userObj instanceof User)) {
			return false;
		}
		User user = (User) userObj;
		return user.hasPatient();
	}

}