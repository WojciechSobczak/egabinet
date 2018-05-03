package media.controllers.portalgets.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import media.controllers.BaseController;
import media.data.model.Appointment;
import media.data.model.User;
import media.service.appointment.IAppointmentService;
import media.service.user.IUserService;

@RestController
public class UserGets extends BaseController {
	
	@Autowired
	public IUserService userService;
	
	@Autowired
	public IAppointmentService appointmentService;

	@RequestMapping(value = { "/user/get/me" }, method = RequestMethod.POST)
	public UserJSON getMe() {
		return new UserJSON(this.getLoggedUser());
	}
	
	@RequestMapping(value = { "/user/get/lists" }, method = RequestMethod.POST)
	public UsersListJSON getList() {
		UsersListJSON usersListJSON = new UsersListJSON();
		usersListJSON.setPatients(UserJSON.getList(userService.getPatients()));
		usersListJSON.setRehabs(UserJSON.getList(userService.getDoctors()));
		if (this.isLoggedUserAdmin()) {
			usersListJSON.setAdmins(UserJSON.getList(userService.getAdmins()));
		}
		return usersListJSON;
	}
	
	@RequestMapping(value = { "/user/get/nextappointment" }, method = RequestMethod.POST)
	public AppointmentJSON getNextAppointment() {
		Long patientId = null;
		Long doctorId = null;
		if (this.getLoggedUser().getPatient() != null) {
			patientId = this.getLoggedUser().getPatient().getId();
		}
		if (this.getLoggedUser().getDoctor() != null) {
			doctorId = this.getLoggedUser().getDoctor().getId();
		}
		
		Appointment appointment = appointmentService.getNearestAppointment(patientId, doctorId);
		if (appointment == null) {
			return null;
		}
		User rehab = userService.getByDoctorId(doctorId);
		User patient = userService.getByPatientId(patientId);
		return new AppointmentJSON(appointment, rehab, patient);
	}
	
	@RequestMapping(value = { "/admin/get/appointments" }, method = RequestMethod.POST)
	public List<AppointmentJSON> getAppointments() {
		return AppointmentJSON.getList(appointmentService.getSortedList());
	}
	
	@RequestMapping(value = { "/doctor/get/appointments" }, method = RequestMethod.POST)
	public List<AppointmentJSON> getDoctorAppointments() {
		return AppointmentJSON.getList(appointmentService.getDoctorSortedList(getLoggedUser().getDoctor().getId()));
	}
	
	@RequestMapping(value = { "/user/get/appointments" }, method = RequestMethod.POST)
	public List<AppointmentJSON> getPatientAppointments() {
		return AppointmentJSON.getList(appointmentService.getPatientSortedList(getLoggedUser().getPatient().getId()));
	}
	
	@RequestMapping(value = { "/doctor/get/patients" }, method = RequestMethod.POST)
	public List<UserJSON> getPatients() {
		return UserJSON.getList(userService.getPatients());
	}
	
	@RequestMapping(value = { "/admin/get/doctors" }, method = RequestMethod.POST)
	public List<UserJSON> getDoctors() {
		return UserJSON.getList(userService.getDoctors());
	}
	
	@RequestMapping(value = { "/admin/get/all" }, method = RequestMethod.POST)
	public List<UserJSON> getAll() {
		return UserJSON.getList(userService.getList());
	}
	

}
