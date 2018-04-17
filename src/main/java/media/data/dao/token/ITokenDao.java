package media.data.dao.token;

import media.data.dao.base.IBaseDao;
import media.data.model.auth.Token;

public interface ITokenDao extends IBaseDao<Long, Token> {

	public Token getByAccessTokenAndRefreshToken(String accessToken, String refreshToken);
	public void removeOldies();

}
