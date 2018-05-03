package media.controllers.cruds.users.forms;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import lombok.Data;
import media.controllers.Validateable;
import media.data.model.User;
import media.service.MultilanguageService;

@Data
public class UserAddForm implements Validateable {
	
	protected String email;
	protected String password;
	protected String name;
	protected String surname;
	protected Boolean admin;

	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = new HashMap<String, String>();
		if (StringUtils.isEmpty(email)) {
			messages.put("email", MultilanguageService.getMessage(languageCode, "email.empty"));
		} else if (!EmailValidator.getInstance().isValid(email)) {
			messages.put("email", MultilanguageService.getMessage(languageCode, "email.invalid.format"));
		}
		if (StringUtils.isEmpty(password)) {
			messages.put("password", MultilanguageService.getMessage(languageCode, "password.empty"));
		}
		if (StringUtils.isEmpty(name)) {
			messages.put("name", MultilanguageService.getMessage(languageCode, "name.empty"));
		}
		if (StringUtils.isEmpty(surname)) {
			messages.put("surname", MultilanguageService.getMessage(languageCode, "surname.empty"));
		}
		if (admin == null) {
			this.admin = Boolean.FALSE;
		}
		return messages;
	}

	
	public User toUser() {
		User user = new User();
		user.setEmail(email);
		user.setActive(true);
		user.setPassword(password);
		user.setCreationDate(LocalDateTime.now());
		user.setName(name);
		user.setSurname(surname);
		user.setAdmin(admin);
		return user;
	}

}
