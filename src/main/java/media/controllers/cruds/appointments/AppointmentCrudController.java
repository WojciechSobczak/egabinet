package media.controllers.cruds.appointments;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import media.controllers.BaseController;
import media.controllers.cruds.CrudResponse;
import media.controllers.cruds.appointments.forms.AppointmentAddForm;
import media.controllers.cruds.appointments.forms.AppointmentEditForm;
import media.data.model.Appointment;
import media.data.model.Doctor;
import media.data.model.Patient;
import media.service.MultilanguageService;
import media.service.appointment.IAppointmentService;
import media.service.doctor.IDoctorService;
import media.service.patient.IPatientService;

@RestController
public class AppointmentCrudController extends BaseController {
	
	@Getter @Setter
	public static class AppointmentCrudResponse extends CrudResponse {
		protected Long addedAppointmentId;
	}
	
	@Autowired
	private IDoctorService doctorService;
	
	@Autowired
	private IPatientService patientService;
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@RequestMapping(value = "/doctor/appointment/add", method = RequestMethod.POST, consumes = "application/json")
	public AppointmentCrudResponse add(@RequestBody AppointmentAddForm appointmentAddForm) {
		if (appointmentAddForm == null) {
			appointmentAddForm = new AppointmentAddForm();
		}
		
		Map<String, String> messages = appointmentAddForm.validate(getLanguageCode()); 
		AppointmentCrudResponse crudResponse = new AppointmentCrudResponse();
		crudResponse.setValid(messages.isEmpty());
		crudResponse.setMessages(messages);
		
		if (crudResponse.isValid()) {
			try {
				
				Doctor doctor = doctorService.getById(appointmentAddForm.getDoctorId());
				if (doctor == null) {
					crudResponse.getMessages().put("doctorId", MultilanguageService.getMessage(getLanguageCode(), "appointment.add.doctor.notexists"));
				}
				
				Patient patient = patientService.getById(appointmentAddForm.getPatientId());
				if (patient == null) {
					crudResponse.getMessages().put("patientId", MultilanguageService.getMessage(getLanguageCode(), "appointment.add.patient.notexists"));
				}
				
				if (doctor == null || patient == null) {
					throw new IllegalStateException();
				}
				
				Appointment appointment = new Appointment();
				appointment.setDoctor(doctor);
				appointment.setPatient(patient);
				appointment.setStart(appointmentAddForm.convertStartDate());
				appointment.setEnd(appointmentAddForm.convertEndDate());
				
				crudResponse.setAddedAppointmentId(appointmentService.save(appointment));
			} catch (Exception e) {
				crudResponse.setValid(false);
			}
		}
		return crudResponse;
	}
	
	@RequestMapping(value = "/doctor/appointment/edit", method = RequestMethod.POST, consumes = "application/json")
	public CrudResponse edit(@RequestBody AppointmentEditForm appointmentEditForm) {
		if (appointmentEditForm == null) {
			appointmentEditForm = new AppointmentEditForm();
		}
		Map<String, String> messages = appointmentEditForm.validate(getLanguageCode()); 
		CrudResponse crudResponse = new CrudResponse();
		crudResponse.setValid(messages.isEmpty());
		crudResponse.setMessages(messages);
		
		if (crudResponse.isValid()) {
			try {
				Appointment appointment = appointmentService.getById(appointmentEditForm.getAppointmentId());
				if (appointment == null) {
					crudResponse.getMessages().put("appointmentId", MultilanguageService.getMessage(getLanguageCode(), "appointment.edit.appointments.notexists"));
				}
				
				Doctor doctor = doctorService.getById(appointmentEditForm.getDoctorId());
				if (doctor == null) {
					crudResponse.getMessages().put("doctorId", MultilanguageService.getMessage(getLanguageCode(), "appointment.edit.doctor.notexists"));
				}
				
				Patient patient = patientService.getById(appointmentEditForm.getPatientId());
				if (patient == null) {
					crudResponse.getMessages().put("patientId", MultilanguageService.getMessage(getLanguageCode(), "appointment.edit.patient.notexists"));
				}
				
				if (appointment == null || doctor == null ||  patient == null) {
					throw new IllegalStateException();
				}
				
				appointment.setDoctor(doctor);
				appointment.setPatient(patient);
				appointment.setStart(appointmentEditForm.convertStartDate());
				appointment.setEnd(appointmentEditForm.convertEndDate());
				
				appointmentService.saveOrUpdate(appointment);
				
			} catch (Exception e) {
				e.printStackTrace();
				crudResponse.setValid(false);
			}
		}
		return crudResponse;
	}
	
	@RequestMapping(value = "/doctor/appointment/delete", method = RequestMethod.POST, consumes = "application/json")
	public CrudResponse delete(@RequestBody Long id) {
		CrudResponse crudResponse = new CrudResponse();
		try {
			Appointment doctor = appointmentService.getById(id);
			if (doctor == null) {
				crudResponse.getMessages().put("id", MultilanguageService.getMessage(getLanguageCode(), "appointment.appointmentid.notexist"));
				crudResponse.setValid(false);
			} else {
				appointmentService.delete(id);
				crudResponse.setValid(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			crudResponse.setValid(false);
		}
		return crudResponse;
	}

}
