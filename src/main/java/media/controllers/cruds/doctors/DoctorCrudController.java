package media.controllers.cruds.doctors;

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
import media.controllers.cruds.doctors.forms.DoctorAddForm;
import media.controllers.cruds.doctors.forms.DoctorEditForm;
import media.data.model.Doctor;
import media.data.model.User;
import media.service.MultilanguageService;
import media.service.doctor.IDoctorService;
import media.service.user.IUserService;

@RestController
public class DoctorCrudController extends BaseController {
	
	@Getter @Setter
	public static class DoctorCrudResponse extends CrudResponse {
		private Long addedDoctorId;
	}
	
	@Autowired
	private IDoctorService doctorService;
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/admin/doctor/add", method = RequestMethod.POST, consumes = "application/json")
	public DoctorCrudResponse add(@RequestBody DoctorAddForm doctorAddForm) {
		if (doctorAddForm == null) {
			doctorAddForm = new DoctorAddForm();
		}
		
		Map<String, String> messages = doctorAddForm.validate(getLanguageCode()); 
		
		DoctorCrudResponse crudResponse = new DoctorCrudResponse();
		crudResponse.setValid(messages.isEmpty());
		crudResponse.setMessages(messages);
		
		if (crudResponse.isValid()) {
			try {
				User user = userService.getById(doctorAddForm.getUserId());
				if (user == null) {
					crudResponse.getMessages().put("doctor", MultilanguageService.getMessage(getLanguageCode(), "doctor.userid.notexist"));
					throw new IllegalStateException();
				}
				
				if (user.getDoctor() != null && user.getDoctor().getId() != null) {
					crudResponse.getMessages().put("doctor", MultilanguageService.getMessage(getLanguageCode(), "doctor.portaluser.taken"));
					throw new IllegalStateException();
				}
				
				Doctor newDoctor = new Doctor();
				Long doctorId = doctorService.save(newDoctor);
				crudResponse.setAddedDoctorId(doctorId);
				newDoctor.setId(doctorId);
				user.setDoctor(newDoctor);
				userService.update(user);
			} catch (Exception e) {
				e.printStackTrace();
				crudResponse.setValid(false);
			}
		}
		return crudResponse;
	}
	
	@RequestMapping(value = "/admin/doctor/edit", method = RequestMethod.POST, consumes = "application/json")
	public CrudResponse edit(@RequestBody DoctorEditForm doctorEditForm) {
		if (doctorEditForm == null) {
			doctorEditForm = new DoctorEditForm();
		}
		Map<String, String> messages = doctorEditForm.validate(getLanguageCode()); 
		CrudResponse crudResponse = new CrudResponse();
		crudResponse.setValid(messages.isEmpty());
		crudResponse.setMessages(messages);
		
		if (crudResponse.isValid()) {
			try {
				Doctor oldDoctor = doctorService.getById(doctorEditForm.getDoctorId());
				if (oldDoctor == null) {
					crudResponse.getMessages().put("doctor", MultilanguageService.getMessage(getLanguageCode(), "doctor.notexist"));
					throw new IllegalStateException();
				}
				User user = null;
				if (doctorEditForm.getUserId() != null) {
					user = userService.getById(doctorEditForm.getUserId());
					if (user == null) {
						crudResponse.getMessages().put("doctor", MultilanguageService.getMessage(getLanguageCode(), "doctor.userid.notexist"));
						throw new IllegalStateException();
					} else {
						User doctorOwner = userService.getByDoctorId(doctorEditForm.getDoctorId());
						if (doctorOwner != null) {
							doctorOwner.setDoctor(null);
							userService.update(doctorOwner);
						}
						user.setDoctor(oldDoctor);
						userService.update(user);
					}
				}
			} catch (Exception e) {
				crudResponse.setValid(false);
			}
		}
		return crudResponse;
	}
	
	@RequestMapping(value = "/admin/doctor/delete", method = RequestMethod.POST, consumes = "application/json")
	public CrudResponse delete(@RequestBody Long id) {
		CrudResponse crudResponse = new CrudResponse();
		try {
			Doctor doctor = doctorService.getById(id);
			if (doctor == null) {
				crudResponse.getMessages().put("id", MultilanguageService.getMessage(getLanguageCode(), "doctor.userid.notexist"));
				crudResponse.setValid(false);
			} else {
				doctorService.delete(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			crudResponse.setValid(false);
		}
		return crudResponse;
	}
	
	
}
