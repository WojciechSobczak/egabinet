package media.controllers.cruds.users.forms;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import media.controllers.Validateable;
import media.service.MultilanguageService;

@Data
public class PortalUserAddForm implements Validateable {
	
	private Long userId;
	private Long citizenId;
	private Boolean admin;
	
	
	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = new HashMap<String, String>();
		if (userId == null) {
			messages.put("userId", MultilanguageService.getMessage(languageCode, "portaluser.userid.empty"));
		} else if (userId <= 0) {
			messages.put("userId", MultilanguageService.getMessage(languageCode, "portaluser.userid.invalid"));
		}
		
		if (citizenId == null) {
			messages.put("citizenId", MultilanguageService.getMessage(languageCode, "portaluser.citizen.empty"));
		} else if (citizenId <= 0) {
			messages.put("citizenId", MultilanguageService.getMessage(languageCode, "portaluser.citizen.invalid"));
		}
		return messages;
	}
	
}
