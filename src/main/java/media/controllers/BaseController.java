package media.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import media.config.SpringConfiguration;
import media.config.security.SecurityInterceptor;
import media.data.model.User;
import media.data.model.auth.Token;

@RestController
public class BaseController {
	
	@Getter
	private String languageCode;
	
	@Getter @Setter
	private User loggedUser;
	
	@Getter
	private Token token;

	@ModelAttribute
	public void language(HttpServletRequest request) {
		this.languageCode = request.getHeader("Language");
		Object token = request.getAttribute(SecurityInterceptor.TOKEN_PARAM);
		if (token instanceof Token) {
			this.token = (Token) token;
		}
		
		Object loggedUser = request.getAttribute(SecurityInterceptor.LOGGED_USER);
		if (loggedUser instanceof User) {
			this.loggedUser = (User) loggedUser;
		}
		
	}
	
	public boolean isLoggedUserAdmin() {
		if (SpringConfiguration.BACKEND_TEST) {
			return true;
		}
		
		return this.loggedUser.isAdmin();
	}
	
	
	
}
