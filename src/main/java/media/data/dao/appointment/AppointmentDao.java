package media.data.dao.appointment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import media.data.dao.base.BaseDao;
import media.data.model.Appointment;

@SuppressWarnings("serial")
@Repository
public class AppointmentDao extends BaseDao<Long, Appointment> implements IAppointmentDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Class<Appointment> getEntityClass() {
		return Appointment.class;
	}

	@Override
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

}
