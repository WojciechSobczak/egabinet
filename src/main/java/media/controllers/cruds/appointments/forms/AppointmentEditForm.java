package media.controllers.cruds.appointments.forms;

import java.util.Map;

import lombok.Data;
import media.service.MultilanguageService;

@Data
public class AppointmentEditForm extends AppointmentAddForm {
	
	private Long appointmentId;
	
	
	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = super.validate(languageCode);
		if (appointmentId == null) {
			messages.put("appointmentId", MultilanguageService.getMessage(languageCode, "appointment.appointmentId.empty"));
		} else if (appointmentId <= 0) {
			messages.put("appointmentId", MultilanguageService.getMessage(languageCode, "appointment.appointmentId.invalid"));
		}
		
		return messages;
	}

}
