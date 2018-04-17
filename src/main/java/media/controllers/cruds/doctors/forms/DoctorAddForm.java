package media.controllers.cruds.doctors.forms;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import media.controllers.Validateable;
import media.service.MultilanguageService;

@Data
public class DoctorAddForm implements Validateable {
	
	protected Long userId;

	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = new HashMap<String, String>();
		if (userId == null) {
			messages.put("userId", MultilanguageService.getMessage(languageCode, "doctor.portaluserid.empty"));
		} else if (userId <= 0) {
			messages.put("userId", MultilanguageService.getMessage(languageCode, "doctor.portaluserid.invalid"));
		}
		
		return messages;
	}

}
