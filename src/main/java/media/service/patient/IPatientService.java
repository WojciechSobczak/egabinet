package media.service.patient;

import media.data.model.Patient;
import media.service.base.IBaseService;

public interface IPatientService extends IBaseService<Long, Patient> {

	public boolean isPatientOf(Long patientId, Long doctorId);

}
