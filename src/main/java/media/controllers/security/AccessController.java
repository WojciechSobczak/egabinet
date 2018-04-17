package media.controllers.security;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import media.controllers.exceptions.HttpBadRequest;
import media.controllers.exceptions.HttpForbidden;
import media.data.model.User;
import media.data.model.auth.Token;
import media.service.security.ISecurityService;
import media.service.token.ITokenService;
import media.service.user.IUserService;

@RestController
public class AccessController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ITokenService tokenService;
	
	@Autowired
	private ISecurityService securityService;
	
	private static final AtomicInteger COUNTER = new AtomicInteger(0);
	
	private final void removeOldies() {
		int val = COUNTER.incrementAndGet();
		if (val > 100) {
			COUNTER.set(0);
			tokenService.removeOldies();
		}
	}
	
	@RequestMapping(value = {"/security/access"}, method = RequestMethod.POST, consumes = "application/json")
	public TokenResponse access(@RequestBody AccessParams params) throws HttpBadRequest, HttpForbidden {
		this.removeOldies();
		if (StringUtils.isAnyEmpty(params.getLogin(), params.getPassword())) {
			throw new HttpBadRequest();
		}
		
		User user = userService.getByEmailAndPassword(params.getLogin(),params.getPassword());
		if (user == null) {
			throw new HttpForbidden();
		}
		
		Token token = securityService.generateToken();
		token.setUser(user);
		tokenService.save(token);
		
		return new TokenResponse(token);
	}
	
	@RequestMapping(value = {"/security/refresh"}, method = RequestMethod.POST, consumes = "application/json")
	public TokenResponse refresh(@RequestBody RefreshDeleteParams params) throws HttpBadRequest, HttpForbidden {
		this.removeOldies();
		if (StringUtils.isAnyEmpty(params.getAccessToken(), params.getRefreshToken())) {
			throw new HttpBadRequest();
		}
		
		Token token = tokenService.getByAccessTokenAndRefreshToken(params.getAccessToken(), params.getRefreshToken());
		if (token == null || !token.isRefreshTokenValid()) {
			throw new HttpForbidden();
		}
		
		Token freshToken = securityService.generateToken();
		freshToken.setUser(token.getUser()); 
		
		tokenService.delete(token);
		tokenService.save(freshToken);
		
		return new TokenResponse(freshToken);
	}
	
	@RequestMapping(value = {"/security/delete"}, method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<HttpStatus> delete(@RequestBody RefreshDeleteParams params) throws HttpBadRequest, HttpForbidden {
		if (StringUtils.isAnyEmpty(params.getAccessToken(), params.getRefreshToken())) {
			throw new HttpBadRequest();
		}
		
		Token token = tokenService.getByAccessTokenAndRefreshToken(params.getAccessToken(), params.getRefreshToken());
		if (token != null) {
			tokenService.delete(token);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@Data
	public static class AccessParams {
		private String login;
		private String password;
	}
	
	@Data
	public static class RefreshDeleteParams {
		private String accessToken;
		private String refreshToken;
	}
	
	@Data
	public static class TokenResponse {
		private String accessToken;
		private String refreshToken;
		private long accessTokenExpDate;
		private long refreshTokenExpDate;
		
		public TokenResponse() {}
		public TokenResponse(Token token) {
			this.accessToken = token.getAccessToken();
			this.refreshToken = token.getRefreshToken();
			this.accessTokenExpDate = token.getAccessTokenExpDateMilis();
			this.refreshTokenExpDate = token.getRefreshTokenExpDateMilis();
		}
	}
	
}
