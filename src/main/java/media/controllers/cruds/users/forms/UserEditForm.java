package media.controllers.cruds.users.forms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import lombok.Data;
import media.data.model.User;
import media.service.MultilanguageService;

@Data
public class UserEditForm extends UserAddForm {

	private Long userId;
	
	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = new HashMap<>();
		if (userId == null || userId < 0) {
			messages.put("userId", MultilanguageService.getMessage(languageCode, "userid.empty"));
		}
		if (!StringUtils.isEmpty(email) && !EmailValidator.getInstance().isValid(email)) {
			messages.put("email", MultilanguageService.getMessage(languageCode, "email.invalid.format"));
		}
		return messages;
	}
	
	@Override
	public User toUser() {
		User user = super.toUser();
		user.setId(userId);
		user.setActive(active == null ? true : active);
		user.setPassword(password);
		user.setEmail(email);
		return user;
	}

}

