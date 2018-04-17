package media.controllers.cruds.patients;

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
import media.controllers.cruds.patients.forms.PatientAddForm;
import media.controllers.cruds.patients.forms.PatientEditForm;
import media.data.model.Doctor;
import media.data.model.Patient;
import media.data.model.User;
import media.service.MultilanguageService;
import media.service.doctor.IDoctorService;
import media.service.patient.IPatientService;
import media.service.user.IUserService;

@RestController
public class PatientCrudController extends BaseController {
	
	@Autowired
	private IPatientService patientService;
	
	@Autowired
	private IUserService userService;
	
	@Getter @Setter
	public static class PatientCrudResponse extends CrudResponse {
		private Long addedPatientId;
	}
	
	@RequestMapping(value = "/admin/patient/add", method = RequestMethod.POST, consumes = "application/json")
	public PatientCrudResponse add(@RequestBody PatientAddForm patientAddForm) {
		if (patientAddForm == null) {
			patientAddForm = new PatientAddForm();
		}
		
		Map<String, String> messages = patientAddForm.validate(getLanguageCode()); 
		PatientCrudResponse crudResponse = new PatientCrudResponse();
		crudResponse.setValid(messages.isEmpty());
		crudResponse.setMessages(messages);
		
		if (crudResponse.isValid()) {
			try {
				User user = userService.getById(patientAddForm.getUserId());
				if (user == null) {
					messages.put("portalUser", MultilanguageService.getMessage(getLanguageCode(), "patient.portaluser.notexists"));
					throw new IllegalArgumentException();
				}
				
				if (user.hasPatient()) {
					messages.put("portalUser", MultilanguageService.getMessage(getLanguageCode(), "patient.portaluser.haspatient"));
					throw new IllegalArgumentException();
				}
				
				Patient patient = new Patient();
				Long patientId = patientService.save(patient);
				patient.setId(patientId);
				
				user.setPatient(patient);
				userService.update(user);
				crudResponse.setAddedPatientId(patientId);
			} catch (Exception e) {
				e.printStackTrace();
				crudResponse.setValid(false);
			}
		}
		
		return crudResponse;
	}
	
	@RequestMapping(value = "/admin/patient/edit", method = RequestMethod.POST, consumes = "application/json")
	public CrudResponse add(@RequestBody PatientEditForm patientEditForm) {
		if (patientEditForm == null) {
			patientEditForm = new PatientEditForm();
		}
		Map<String, String> messages = patientEditForm.validate(getLanguageCode()); 
		CrudResponse crudResponse = new CrudResponse();
		crudResponse.setValid(messages.isEmpty());
		crudResponse.setMessages(messages);
		if (crudResponse.isValid()) {
			try {
				Patient oldPatient = patientService.getById(patientEditForm.getPatientId());
				if (oldPatient == null) {
					messages.put("patient", MultilanguageService.getMessage(getLanguageCode(), "patient.notexists"));
				}
				
				Long userId = patientEditForm.getUserId();
				User user = null;
				if (userId != null) {
					user = userService.getById(patientEditForm.getUserId());
					if (user == null) {
						messages.put("user", MultilanguageService.getMessage(getLanguageCode(), "patient.user.notexists"));
						throw new IllegalArgumentException();
					}
					
					if (user.getPatient().getId() != null) {
						messages.put("user", MultilanguageService.getMessage(getLanguageCode(), "patient.user.haspatient"));
						throw new IllegalArgumentException();
					}
					
					User patientOwner = userService.getByDoctorId(patientEditForm.getPatientId());
					if (patientOwner != null) {
						patientOwner.setPatient(null);
						userService.update(patientOwner);
					}
					user.setPatient(oldPatient);
					userService.update(user);
				} 
			} catch (Exception e) {
				crudResponse.setValid(false);
			}
		}
		return crudResponse;
	}
	
	@RequestMapping(value = "/admin/patient/delete", method = RequestMethod.POST, consumes = "application/json")
	public CrudResponse delete(@RequestBody Long id) {
		CrudResponse crudResponse = new CrudResponse();
		try {
			Patient patient = patientService.getById(id);
			if (patient == null) {
				crudResponse.getMessages().put("id", MultilanguageService.getMessage(getLanguageCode(), "patient.userid.notexist"));
				crudResponse.setValid(false);
			} else {
				patientService.delete(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			crudResponse.setValid(false);
		}
		return crudResponse;
	}
	
	
}
