package media.service.appointment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import media.data.dao.appointment.IAppointmentDao;
import media.data.dao.base.IBaseDao;
import media.data.model.Appointment;
import media.service.base.BaseService;

@SuppressWarnings("serial")
@Service
@Transactional(rollbackOn = Exception.class)
public class AppointmentService extends BaseService<Long, Appointment> implements IAppointmentService {
	
	@Autowired
	private IAppointmentDao appointmentDao;

	@Override
	public IBaseDao<Long, Appointment> getServiceBaseDao() {
		return appointmentDao;
	}

	@Override
	public Appointment getNearestAppointment(Long patientId, Long doctorId) {
		return appointmentDao.getNearestAppointment(patientId, doctorId);
	}

	@Override
	public List<Appointment> getSortedList() {
		return appointmentDao.getSortedList();
	}

	@Override
	public List<Appointment> getDoctorSortedList(Long id) {
		return appointmentDao.getDoctorSortedList(id);
	}	

	@Override
	public List<Appointment> getPatientSortedList(Long id) {
		return appointmentDao.getPatientSortedList(id);
	}

}
