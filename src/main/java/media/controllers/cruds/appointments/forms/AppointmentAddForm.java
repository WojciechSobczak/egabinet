package media.controllers.cruds.appointments.forms;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import media.controllers.Validateable;
import media.data.model.Appointment;
import media.service.MultilanguageService;

@Data
public class AppointmentAddForm implements Validateable {
	
	protected Long doctorId;
	protected Long patientId;
	
	protected LocalDateTime start;
	protected LocalDateTime end;

	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = new HashMap<String, String>();
		if (doctorId == null) {
			messages.put("doctorId", MultilanguageService.getMessage(languageCode, "appointment.doctorId.empty"));
		} else if (doctorId <= 0) {
			messages.put("doctorId", MultilanguageService.getMessage(languageCode, "appointment.doctorId.invalid"));
		}
		
		if (doctorId == null) {
			messages.put("patientId", MultilanguageService.getMessage(languageCode, "appointment.patientId.empty"));
		} else if (doctorId <= 0) {
			messages.put("patientId", MultilanguageService.getMessage(languageCode, "appointment.patientId.invalid"));
		}
		
		if (start == null) {
			messages.put("start", MultilanguageService.getMessage(languageCode, "appointment.start.empty"));
		}
		if (end == null) {
			messages.put("end", MultilanguageService.getMessage(languageCode, "appointment.end.empty"));
		}
		
		if (start != null && end != null) {
			if (start.isAfter(end)) {
				messages.put("start", MultilanguageService.getMessage(languageCode, "appointment.start.afterend"));
				messages.put("end", MultilanguageService.getMessage(languageCode, "appointment.end.beforestart"));
			}
		}
		return messages;
	}
	

}
