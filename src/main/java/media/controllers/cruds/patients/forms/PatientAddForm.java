package media.controllers.cruds.patients.forms;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import media.controllers.Validateable;
import media.service.MultilanguageService;

@Getter @Setter
public class PatientAddForm implements Validateable {
	
	protected Long userId;

	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = new HashMap<String, String>();
		if (userId == null) {
			messages.put("userId", MultilanguageService.getMessage(languageCode, "patient.userId.empty"));
		} else if (userId <= 0) {
			messages.put("userId", MultilanguageService.getMessage(languageCode, "patient.userId.invalid"));
		}
		
		return messages;
	}

}
