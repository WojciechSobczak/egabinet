package media.config.security;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import media.service.token.ITokenService;
import media.service.user.IUserService;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private ITokenService tokenService;
	
	@Autowired
	private IUserService userService;
	
	public static final boolean SWAGGER = true;
	public static final boolean BACKEND_TEST = false;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		ArrayList<String> exludePaths = new ArrayList<String>(Arrays.asList(
				"/security/access", 
				"/security/refresh", 
				"/security/delete", 
				"/createAdmin", 
				"/removeAdmin", 
				"/getToken",
				"/getUser")
		);
		
		if (SWAGGER) {
			exludePaths.add("/swagger-ui.html");
			exludePaths.add("/swagger-resources/**");
			exludePaths.add("/v2/**");
		}
		
		registry.addInterceptor(new SecurityInterceptor(tokenService, userService))
			.addPathPatterns("/**")
			.excludePathPatterns(exludePaths.toArray(new String[exludePaths.size()]));
		registry.addInterceptor(new AdminSecurityInterceptor()).addPathPatterns("/admin/**");
		registry.addInterceptor(new DoctorSecurityInterceptor()).addPathPatterns("/doctor/**");
		registry.addInterceptor(new PatientSecurityInterceptor()).addPathPatterns("/patient/**");
		
		super.addInterceptors(registry);
	}
	
}
