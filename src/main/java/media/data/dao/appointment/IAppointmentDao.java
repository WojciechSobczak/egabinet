package media.data.dao.appointment;

import java.util.List;

import media.data.dao.base.IBaseDao;
import media.data.model.Appointment;

public interface IAppointmentDao extends IBaseDao<Long, Appointment> {

	public Appointment getNearestAppointment(Long patientId, Long doctorId);

	public List<Appointment> getSortedList();

	public List<Appointment> getDoctorSortedList(Long id);

	public List<Appointment> getPatientSortedList(Long id);

	public void deleteByPatient(Long id);

	public void deleteByDoctor(Long id);

}
