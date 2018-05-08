package media.controllers.cruds.appointments.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import media.controllers.Validateable;
import media.data.model.Appointment;
import media.service.MultilanguageService;
import media.utils.DateUtils;

@Data
public class AppointmentAddForm implements Validateable {
	
	private static final String DATE_FORMAT_STRING = "dd.MM.yyyy HH:mm:ss";
	protected Long doctorId;
	protected Long patientId;
	
	protected String start;
	protected String end;

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
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
		LocalDateTime startDate = null;
		LocalDateTime endDate = null;
		try {
			startDate = DateUtils.fromDate(simpleDateFormat.parse(start));
		} catch (Exception e) {
			messages.put("start", MultilanguageService.getMessage(languageCode, "appointment.start.invalidformat"));
		}
		try {
			endDate = DateUtils.fromDate(simpleDateFormat.parse(end));
		} catch (Exception e) {
			messages.put("end", MultilanguageService.getMessage(languageCode, "appointment.end.invalidformat"));
		}
		
		if (startDate != null && endDate != null) {
			if (startDate.isAfter(endDate)) {
				messages.put("start", MultilanguageService.getMessage(languageCode, "appointment.start.afterend"));
				messages.put("end", MultilanguageService.getMessage(languageCode, "appointment.end.beforestart"));
			}
		}
		return messages;
	}
	
	public LocalDateTime convertStartDate() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
		return DateUtils.fromDate(simpleDateFormat.parse(start));
	}
	
	public LocalDateTime convertEndDate() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
		return DateUtils.fromDate(simpleDateFormat.parse(end));
	}

}
