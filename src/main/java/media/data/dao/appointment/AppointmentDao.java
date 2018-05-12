package media.data.dao.appointment;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.ListUtils;
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
	
	
	@Override
	public Appointment getByIdWithEverything(Long id) {
		if (id == null) {
			return null;
		}
		
		String queryString = "FROM Appointment a LEFT JOIN FETCH a.patient p LEFT JOIN FETCH p.user pu ";
		queryString += "LEFT JOIN FETCH a.doctor d LEFT JOIN FETCH d.user du WHERE a.id = :id";
		
		Query query = createQuery(queryString);
		query.setParameter("id", id);
		
		return (Appointment) query.getSingleResult();
	}

	@Override
	public Appointment getNearestAppointment(Long patientId, Long doctorId) {
		if (patientId == null && doctorId == null) {
			return null;
		}
		
		String queryString = "FROM Appointment a ";
		queryString += "LEFT JOIN FETCH a.patient p LEFT JOIN FETCH p.user pu ";
		queryString += "LEFT JOIN FETCH a.doctor d LEFT JOIN FETCH d.user du ";
		queryString += "WHERE ";

		if (patientId != null) {
			queryString += "p.id = :patientId ";
			if (doctorId != null) {
				queryString +=  "OR  ";
			}
		}
		if (doctorId != null) {
			queryString += "d.id = :doctorId ";
		}
		
		queryString += "ORDER BY a.start";
		
		Query query = createQuery(queryString);
		query.setMaxResults(1);
		if (patientId != null) {
			query.setParameter("patientId", patientId);
		}
		if (doctorId != null) {
			query.setParameter("doctorId", doctorId);
		}
		List<Appointment> list = query.getResultList();
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<Appointment> getSortedList() {
		Query query = createQuery("FROM Appointment a "
				+ "LEFT JOIN FETCH a.patient p "
				+ "LEFT JOIN FETCH a.doctor d "
				+ "LEFT JOIN FETCH d.user "
				+ "LEFT JOIN FETCH p.user "
				+ "ORDER BY start");
		return query.getResultList();
	}

	@Override
	public List<Appointment> getDoctorSortedList(Long id) {
		Query query = createQuery("FROM Appointment a "
				+ "LEFT JOIN FETCH a.patient p "
				+ "LEFT JOIN FETCH a.doctor d "
				+ "LEFT JOIN FETCH d.user "
				+ "LEFT JOIN FETCH p.user "
				+ "WHERE d.id = :doctorId "
				+ "ORDER BY start");
		query.setParameter("doctorId", id);
		return query.getResultList();
	}

	@Override
	public List<Appointment> getPatientSortedList(Long id) {
		Query query = createQuery("FROM Appointment a "
				+ "LEFT JOIN FETCH a.patient p "
				+ "LEFT JOIN FETCH a.doctor d  "
				+ "LEFT JOIN FETCH d.user "
				+ "LEFT JOIN FETCH p.user "
				+ "WHERE p.id = :patientId "
				+ "ORDER BY start");
		query.setParameter("patientId", id);
		return query.getResultList();
	}

	@Override
	public void deleteByPatient(Long id) {
		Query query = createQuery("DELETE FROM Appointment a WHERE a.patient.id = :patientId");
		query.setParameter("patientId", id);
		query.executeUpdate();
	}

	@Override
	public void deleteByDoctor(Long id) {
		Query query = createQuery("DELETE FROM Appointment a WHERE a.doctor.id = :doctorId");
		query.setParameter("doctorId", id);
		query.executeUpdate();
	}

}
