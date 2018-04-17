package media.controllers.portalgets.patients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import media.controllers.BaseController;
import media.service.patient.IPatientService;

public class PatientListController extends BaseController {

	@Autowired
	public IPatientService patientService;


}