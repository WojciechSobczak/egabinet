package media.service.doctor;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import media.data.dao.base.IBaseDao;
import media.data.dao.doctor.IDoctorDao;
import media.data.model.Doctor;
import media.service.base.BaseService;

@SuppressWarnings("serial")
@Service
@Transactional(rollbackOn = Exception.class)
public class DoctorService extends BaseService<Long, Doctor> implements IDoctorService {
	
	@Autowired
	private IDoctorDao doctorDao;

	@Override
	public IBaseDao<Long, Doctor> getServiceBaseDao() {
		return doctorDao;
	}

}
