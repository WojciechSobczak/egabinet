package media.controllers.cruds.users;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import media.controllers.BaseController;
import media.controllers.cruds.CrudResponse;
import media.controllers.cruds.users.forms.UserAddForm;
import media.controllers.cruds.users.forms.UserEditForm;
import media.data.model.Doctor;
import media.data.model.Patient;
import media.data.model.User;
import media.service.MultilanguageService;
import media.service.security.ISecurityService;
import media.service.user.IUserService;

@RestController
public class UserCrudController extends BaseController {
	
	@Getter @Setter
	public static class UserCrudResponse extends CrudResponse {
		private Long addedUserId;
	}
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISecurityService securityService;
	
	
	@RequestMapping(value = "/admin/user/add", method = RequestMethod.POST, consumes = "application/json")
	public UserCrudResponse add(@RequestBody UserAddForm userAddForm) {
		if (userAddForm == null) {
			userAddForm = new UserAddForm();
		}
		
		Map<String, String> messages = userAddForm.validate(getLanguageCode()); 
		
		UserCrudResponse crudResponse = new UserCrudResponse();
		crudResponse.setValid(messages.isEmpty());
		crudResponse.setMessages(messages);
		
		if (crudResponse.isValid()) {
			try {
				User oldUser = userService.getByEmail(userAddForm.getEmail());
				if (oldUser != null) {
					crudResponse.getMessages().put("user", MultilanguageService.getMessage(getLanguageCode(), "user.email.exists"));
					throw new IllegalStateException();
				}
				
				User user = userAddForm.toUser();
				user.setPassword(securityService.hashPassword(user.getPassword()));
				Long id = userService.save(user);
				crudResponse.setAddedUserId(id);
			} catch (Exception e) {
				e.printStackTrace();
				crudResponse.setValid(false);
			}
		}
		
		return crudResponse;
	}
	
	@RequestMapping(value = "/admin/user/edit", method = RequestMethod.POST, consumes = "application/json")
	public CrudResponse add(@RequestBody UserEditForm userEditForm) {
		if (userEditForm == null) {
			userEditForm = new UserEditForm();
		}
		Map<String, String> messages = userEditForm.validate(getLanguageCode()); 
		CrudResponse crudResponse = new CrudResponse();
		crudResponse.setValid(messages.isEmpty());
		crudResponse.setMessages(messages);
		if (crudResponse.isValid()) {
			try {
				User newUser = userEditForm.toUser();
				User oldUser = userService.getById(userEditForm.getUserId());
				if (oldUser == null) {
					crudResponse.getMessages().put("user", MultilanguageService.getMessage(getLanguageCode(), "user.id.notexists"));
					throw new IllegalStateException();
				}
				
				if (StringUtils.isNotEmpty(newUser.getPassword())) {
					newUser.setPassword(securityService.hashPassword(newUser.getPassword()));
					if (!oldUser.getPassword().equals(newUser.getPassword())) {
						oldUser.setPassword(newUser.getPassword());
					}
				}
				
				if (StringUtils.isNotEmpty(newUser.getEmail()) && !oldUser.getEmail().equals(newUser.getEmail())) {
					oldUser.setEmail(newUser.getEmail());
				}
				
				if (userEditForm.getActive() != null) {
					oldUser.setActive(newUser.isActive());
				}
				
				userService.update(oldUser);
			} catch (Exception e) {
				crudResponse.setValid(false);
			}
		}
		return crudResponse;
	}
	
	@RequestMapping(value = "/admin/user/delete", method = RequestMethod.POST, consumes = "application/json")
	public CrudResponse delete(@RequestBody Long id) {
		CrudResponse crudResponse = new CrudResponse();
		try {
			User user = userService.getById(id);
			if (user == null) {
				crudResponse.getMessages().put("id", MultilanguageService.getMessage(getLanguageCode(), "user.userid.notexist"));
			}
			
			Doctor doctor = user.getDoctor();
			if (doctor != null) {
				crudResponse.getMessages().put("doctor", MultilanguageService.getMessage(getLanguageCode(), "user.delete.doctorexists"));
			}
			
			Patient patient = user.getPatient();
			if (patient != null) {
				crudResponse.getMessages().put("patient", MultilanguageService.getMessage(getLanguageCode(), "user.delete.patientexists"));
			}
			
			userService.delete(id);
			crudResponse.setValid(crudResponse.getMessages().size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
			crudResponse.setValid(false);
		}
		return crudResponse;
	}
	
	
	
	
}
