package media.data.dao.patient;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import media.data.dao.base.BaseDao;
import media.data.model.Patient;

@SuppressWarnings("serial")
@Repository
public class PatientDao extends BaseDao<Long, Patient> implements IPatientDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Class<Patient> getEntityClass() {
		return Patient.class;
	}

	@Override
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public boolean isPatientOf(Long patientId, Long doctorId) {
		Query query = createQuery("FROM Patient p LEFT JOIN p.doctors pd WHERE p.id = :patientId AND pd.id = :doctorId");
		query.setParameter("patientId", patientId);
		query.setParameter("doctorId", doctorId);
		try {
			Patient patient = (Patient) query.getResultList();
			return patient != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
