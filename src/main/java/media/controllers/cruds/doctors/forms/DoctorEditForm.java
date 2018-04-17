package media.controllers.cruds.doctors.forms;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import media.controllers.Validateable;
import media.service.MultilanguageService;

@Data
public class DoctorEditForm implements Validateable {
	
	protected Long userId;
	protected Long doctorId;

	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = new HashMap<String, String>();
		if (doctorId == null) {
			messages.put("doctorId", MultilanguageService.getMessage(languageCode, "doctor.doctorid.empty"));
		} else if (doctorId <= 0) {
			messages.put("doctorId", MultilanguageService.getMessage(languageCode, "doctor.doctorid.invalid"));
		}
		
		if (userId != null && userId <= 0) {
			messages.put("portalUserId", MultilanguageService.getMessage(languageCode, "doctor.portaluserid.invalid"));
		}
		
		return messages;
	}

}
