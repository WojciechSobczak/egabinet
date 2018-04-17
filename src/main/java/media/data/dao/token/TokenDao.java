package media.data.dao.token;

import java.time.LocalDateTime;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import media.data.dao.base.BaseDao;
import media.data.model.auth.Token;

@SuppressWarnings("serial")
@Repository
public class TokenDao extends BaseDao<Long, Token> implements ITokenDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Class<Token> getEntityClass() {
		return Token.class;
	}

	@Override
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public Token getByAccessTokenAndRefreshToken(String accessToken, String refreshToken) {
		String hql = "FROM Token t LEFT JOIN FETCH t.user WHERE t.accessToken = :accessToken AND t.refreshToken = :refreshToken";
		Query query = createQuery(hql);
		query.setParameter("accessToken", accessToken);
		query.setParameter("refreshToken", refreshToken);
		
		try {
			return (Token) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void removeOldies() {
		LocalDateTime now = LocalDateTime.now();
		Query query = createQuery("DELETE Token t WHERE t.accessTokenExpDate < :now AND t.refreshTokenExpDate < :now");
		query.setParameter("now", now);
		query.executeUpdate();
	}


}
