package media.data.dao.user;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import media.data.dao.base.BaseDao;
import media.data.model.User;

@SuppressWarnings("serial")
@Repository
public class UserDao extends BaseDao<Long, User> implements IUserDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public User getByEmailAndPassword(String username, String password) {
		Query query = createQuery("FROM User u WHERE u.email = :username AND u.password = :password");
		query.setParameter("username", username);
		query.setParameter("password", password);
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User getByEmail(String email) {
		Query query = createQuery("FROM User u WHERE u.email = :email");
		query.setParameter("email", email);
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<User> parse(List<Object[]> list) {
		List<User> users = new ArrayList<>();
		for (Object[] userTable : list) {
			User user = new User();
			if (userTable[0] != null) {
				user.setId(((BigInteger)userTable[0]).longValue());
			}
			if (userTable[1] != null) {
				user.setActive(((Boolean) userTable[1]));
			}
			if (userTable[2] != null) {
				user.setCreationDate(((Timestamp) userTable[2]).toLocalDateTime());
			}
			if (userTable[3] != null) {
				user.setEmail((String) userTable[3]);
			}
			if (userTable[4] != null) {
				user.setPassword((String) userTable[4]);
			}
			users.add(user);
		}
		return users;
	}

	@Override
	public User getByDoctorId(Long id) {
		Query query = createQuery("FROM User u WHERE u.doctor.id = :id");
		query.setParameter("id", id);
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<User> getAdmins() {
		Query query = createQuery("FROM User u WHERE u.admin = true");
		return query.getResultList();
	}

	@Override
	public List<User> getDoctors() {
		Query query = createQuery("FROM User u WHERE u.doctor.id IS NOT NULL");
		return query.getResultList();
	}

	@Override
	public List<User> getPatients() {
		Query query = createQuery("FROM User u WHERE u.patient.id IS NOT NULL");
		return query.getResultList();
	}

	@Override
	public User getByPatientId(Long id) {
		Query query = createQuery("FROM User u WHERE u.patient.id = :id");
		query.setParameter("id", id);
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
