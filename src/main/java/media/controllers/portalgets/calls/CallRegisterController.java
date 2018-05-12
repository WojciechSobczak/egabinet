package media.controllers.portalgets.calls;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import media.controllers.BaseController;
import media.data.model.Appointment;
import media.service.appointment.IAppointmentService;

@RestController
public class CallRegisterController extends BaseController {
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@RequestMapping(value = { "/user/call/register" }, method = RequestMethod.POST)
	public CallRequestResponseJSON registerCallOffer(@RequestBody Long callId) {
		CallRequestResponseJSON response = new CallRequestResponseJSON();
		if (callId == null) {
			response.getErrors().put("callId", "callId.nullValue");
		} else {
			checkCallRequestValidity(callId, response);
			if (response.getErrors().isEmpty()) {
				CallEntry existingEntry = CallsRegisterHandler.getEntry(callId);
				if (existingEntry != null) {
					CallsRegisterHandler.register(new CallEntry(callId, getLoggedUser().getId()));
				} else {
					response.setCall(new CallEntryJSON(existingEntry));
				}
			}
		}
		response.setSuccess(response.getErrors().isEmpty());
		return response;
	}

	private void checkCallRequestValidity(Long callId, CallRequestResponseJSON response) {
		Appointment appointment = appointmentService.getByIdWithEverything(callId);
		Long loggedUserId = getLoggedUser().getId();
		if (appointment == null) {
			response.getErrors().put("callId", "callId.noSuchAppointment");
		} else if (LocalDateTime.now().isAfter(appointment.getEnd())) {
			response.getErrors().put("callId", "callId.appointmentEnded");
		} else if (LocalDateTime.now().isBefore(appointment.getStart())) {
			response.getErrors().put("callId", "callId.appointmentHaveNotStarted");
		} else if (appointment != null) {
			Long patientUserId = appointment.getPatient().getUser().getId();
			Long doctorUserId = appointment.getDoctor().getUser().getId();
			if (!patientUserId.equals(loggedUserId) && !doctorUserId.equals(loggedUserId) ) {
				response.getErrors().put("userId", "userId.loggedUserNotLinkedWithAppointment");
			}
		}
	}
	
	@RequestMapping(value = { "/user/call/unregister" }, method = RequestMethod.POST)
	public CallRequestResponseJSON unregisterCallOffer(@RequestBody Long callId) {
		CallRequestResponseJSON response = new CallRequestResponseJSON();
		if (callId == null) {
			response.getErrors().put("callId", "callId.nullValue");
		} else {
			checkCallRequestValidity(callId, response);
			if (response.getErrors().isEmpty()) {
				CallsRegisterHandler.unregister(callId);
			}
		}
		response.setSuccess(response.getErrors().isEmpty());
		return response;
	}
	
}
