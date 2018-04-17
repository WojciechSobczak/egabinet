package media.service.token;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import media.data.dao.base.IBaseDao;
import media.data.dao.token.ITokenDao;
import media.data.model.auth.Token;
import media.service.base.BaseService;

@SuppressWarnings("serial")
@Service
@Transactional(rollbackOn = Exception.class)
public class TokenService extends BaseService<Long, Token> implements ITokenService {
	
	@Autowired
	private ITokenDao tokenDao;

	@Override
	public IBaseDao<Long, Token> getServiceBaseDao() {
		return tokenDao;
	}

	@Override
	public Token getByAccessTokenAndRefreshToken(String accessToken, String refreshToken) {
		return tokenDao.getByAccessTokenAndRefreshToken(accessToken, refreshToken);
	}

	@Override
	public void removeOldies() {
		tokenDao.removeOldies();
	}
	

}
