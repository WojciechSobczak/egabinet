package media.controllers.portalgets.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import media.data.model.Appointment;
import media.data.model.User;
import media.utils.DateUtils;

@Data
public class AppointmentJSON {
	
	private Long id;
	private UserJSON rehab;
	private UserJSON patient;
	private long start;
	private long end;
	
	public AppointmentJSON() {
	}
	
	public AppointmentJSON(Appointment appointment, User rehab, User patient) {
		this.id = appointment.getId();
		this.rehab = new UserJSON(rehab);
		this.patient = new UserJSON(patient);
		this.start = DateUtils.getMilis(appointment.getStart());
		this.end = DateUtils.getMilis(appointment.getEnd());
	}
	
	public AppointmentJSON(Appointment appointment) {
		this.id = appointment.getId();
		this.rehab = new UserJSON(appointment.getDoctor().getUser());
		this.patient = new UserJSON(appointment.getPatient().getUser());
		this.start = DateUtils.getMilis(appointment.getStart());
		this.end = DateUtils.getMilis(appointment.getEnd());
	}
		
	public static List<AppointmentJSON> getList(List<Appointment> appointments) {
		ArrayList<AppointmentJSON> output = new ArrayList<>(appointments.size());
		for (Appointment appointment : appointments) {
			output.add(new AppointmentJSON(appointment));
		}
		return output;
	}
	

}
