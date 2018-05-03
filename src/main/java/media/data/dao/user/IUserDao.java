package media.data.dao.user;

import java.util.List;

import media.data.dao.base.IBaseDao;
import media.data.model.User;

public interface IUserDao extends IBaseDao<Long, User> {
	
	public User getByEmailAndPassword(String username, String password);
	public User getByEmail(String email);
	public User getByDoctorId(Long id);
	public List<User> getAdmins();
	public List<User> getDoctors();
	public List<User> getPatients();
	public User getByPatientId(Long id);

}
