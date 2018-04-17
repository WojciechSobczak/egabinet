package media.data.dao.patient;

import media.data.dao.base.IBaseDao;
import media.data.model.Patient;

public interface IPatientDao extends IBaseDao<Long, Patient> {

	public boolean isPatientOf(Long patientId, Long doctorId);

}
