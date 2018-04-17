package media.service.token;

import media.data.model.auth.Token;
import media.service.base.IBaseService;

public interface ITokenService extends IBaseService<Long, Token> {

	public Token getByAccessTokenAndRefreshToken(String accessToken, String refreshToken);
	public void removeOldies();
	
}
