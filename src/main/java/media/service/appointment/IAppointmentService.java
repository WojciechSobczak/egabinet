package media.service.appointment;

import java.util.List;

import media.data.model.Appointment;
import media.service.base.IBaseService;

public interface IAppointmentService extends IBaseService<Long, Appointment> {

	public Appointment getNearestAppointment(Long patientId, Long doctorId);

	public List<Appointment> getSortedList();

	public List<Appointment> getDoctorSortedList(Long id);

	public List<Appointment> getPatientSortedList(Long id);

	public void deleteByPatient(Long id);

	public void deleteByDoctor(Long id);
	
	public Appointment getByIdWithEverything(Long id);

}
