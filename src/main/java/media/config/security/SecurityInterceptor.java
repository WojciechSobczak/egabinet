package media.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import media.config.SpringConfiguration;
import media.controllers.exceptions.HttpBadRequest;
import media.controllers.exceptions.HttpForbidden;
import media.data.model.auth.Token;
import media.service.token.ITokenService;
import media.service.user.IUserService;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
	
	private ITokenService tokenService;
	private IUserService userService;
	
	public SecurityInterceptor(ITokenService tokenService, IUserService userService) {
		this.tokenService = tokenService;
		this.userService = userService;
	}
	
	public static final String TOKEN_PARAM = "TOKEN";
	public static final String LOGGED_USER = "LOGGED_USER";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!SpringConfiguration.BACKEND_TEST) {
			String secToken = request.getHeader("Authorization");
			if (secToken == null) {
				throw new HttpBadRequest();
			}

			String accessToken = null;
			String refreshToken = null;
			try {
				String[] tokens = secToken.split("\\:");
				accessToken = tokens[0];
				refreshToken = tokens[1];
			} catch (Exception e) {
				e.printStackTrace();
				throw new HttpBadRequest();
			}
			
			Token token = tokenService.getByAccessTokenAndRefreshToken(accessToken, refreshToken);
			if (token == null) {
				System.out.println("NO TOKEN FOUND : " + secToken);
				throw new HttpForbidden();
			}
			if (!token.isAccessTokenValid() || !token.getUser().isActive()) {
				System.out.println("INVALID TOKEN : " + token.getAccessTokenExpDateMilis() + ":: " + token.getRefreshTokenExpDateMilis());
				System.out.println("TOKEN: " + token);
				throw new HttpForbidden();
			}
			
			request.setAttribute(TOKEN_PARAM, token);
			request.setAttribute(LOGGED_USER, token.getUser());
		} else {
			request.setAttribute(TOKEN_PARAM, new Token("abc", "abc"));
			request.setAttribute(LOGGED_USER, userService.getAdmins().iterator().next());
		}
		
		return true;
	}
	
	
}
