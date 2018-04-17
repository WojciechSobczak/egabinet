package media.controllers.portalgets.doctor;

import lombok.Data;
import media.data.model.Doctor;

@Data
public class DoctorJSON {
	
	private Long id;
	
	public DoctorJSON(Doctor doctor) {
		this.id = doctor.getId();
	}
	
}
