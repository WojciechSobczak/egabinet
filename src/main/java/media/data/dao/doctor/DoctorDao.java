package media.data.dao.doctor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import media.data.dao.base.BaseDao;
import media.data.model.Doctor;

@SuppressWarnings("serial")
@Repository
public class DoctorDao extends BaseDao<Long, Doctor> implements IDoctorDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Class<Doctor> getEntityClass() {
		return Doctor.class;
	}

	@Override
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

}
