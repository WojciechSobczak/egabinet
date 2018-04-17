package media.controllers.cruds.patients.forms;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import media.service.MultilanguageService;

@Data
public class PatientEditForm extends PatientAddForm {
	
	private Long patientId;
	
	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = new HashMap<String, String>();
		if (patientId == null) {
			messages.put("patientId", MultilanguageService.getMessage(languageCode, "patient.id.empty"));
		} else if (patientId <= 0) {
			messages.put("patientId", MultilanguageService.getMessage(languageCode, "patient.id.invalid"));
		}
		if (userId != null && userId <= 0) {
			messages.put("userId", MultilanguageService.getMessage(languageCode, "patient.userId.invalid"));
		}
		return messages;
	}

}
