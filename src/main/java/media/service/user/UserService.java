package media.service.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import media.data.dao.base.IBaseDao;
import media.data.dao.user.IUserDao;
import media.data.model.User;
import media.service.base.BaseService;
import media.service.security.ISecurityService;

@SuppressWarnings("serial")
@Service
@Transactional(rollbackOn = Exception.class)
public class UserService extends BaseService<Long, User> implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private ISecurityService securityService;

	@Override
	public IBaseDao<Long, User> getServiceBaseDao() {
		return userDao;
	}

	@Override
	public User getByEmailAndHashedPassword(String email, String password) {
		return userDao.getByEmailAndPassword(email, password);
	}

	@Override
	public User getByEmailAndPassword(String login, String password) {
		return userDao.getByEmailAndPassword(login, securityService.hashPassword(password));
	}

	@Override
	public User getByEmail(String email) {
		return userDao.getByEmail(email);
	}

	@Override
	public User getByDoctorId(Long id) {
		return userDao.getByDoctorId(id);
	}

}
