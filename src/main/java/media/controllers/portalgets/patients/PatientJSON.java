package media.controllers.portalgets.patients;

import lombok.Data;
import media.data.model.Patient;

@Data
public class PatientJSON {
	
	private Long id;
	
	public PatientJSON(Patient patient) {
		this.id = patient.getId();
	}
	
	public PatientJSON() {
	}

}
