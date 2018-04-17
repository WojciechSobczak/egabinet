package media.service.patient;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import media.data.dao.base.IBaseDao;
import media.data.dao.patient.IPatientDao;
import media.data.model.Patient;
import media.service.base.BaseService;

@SuppressWarnings("serial")
@Service
@Transactional(rollbackOn = Exception.class)
public class PatientService extends BaseService<Long, Patient> implements IPatientService {
	
	@Autowired
	private IPatientDao patientDao;

	@Override
	public IBaseDao<Long, Patient> getServiceBaseDao() {
		return patientDao;
	}

	@Override
	public boolean isPatientOf(Long patientId, Long doctorId) {
		return patientDao.isPatientOf(patientId, doctorId);
	}
	
}
