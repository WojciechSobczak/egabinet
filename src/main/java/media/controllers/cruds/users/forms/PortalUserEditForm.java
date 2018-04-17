package media.controllers.cruds.users.forms;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import media.controllers.Validateable;
import media.service.MultilanguageService;

@Data
public class PortalUserEditForm implements Validateable {
	
	private Long portalUserId;
	private Long userId;
	private Long citizenId;
	private Boolean admin;
	
	
	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = new HashMap<String, String>();
		if (userId != null && userId <= 0) {
			messages.put("userId", MultilanguageService.getMessage(languageCode, "portaluser.userid.invalid"));
		}
		if (citizenId != null && citizenId <= 0) {
			messages.put("citizenId", MultilanguageService.getMessage(languageCode, "portaluser.citizen.invalid"));
		}
		if (portalUserId == null) {
			messages.put("portalUserId", MultilanguageService.getMessage(languageCode, "portaluser.portaluserid.empty"));
		} else if (portalUserId <= 0) {
			messages.put("portalUserId", MultilanguageService.getMessage(languageCode, "portaluser.portaluserid.invalid"));
		}
		return messages;
	}
	
}
