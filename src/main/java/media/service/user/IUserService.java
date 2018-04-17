package media.service.user;

import media.data.model.User;
import media.service.base.IBaseService;

public interface IUserService extends IBaseService<Long, User> {
	
	public User getByEmailAndHashedPassword(String username, String password);
	public User getByEmailAndPassword(String login, String password);
	public User getByEmail(String email);
	
	public User getByDoctorId(Long id);

}
