package media.controllers.portalgets.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import media.controllers.BaseController;
import media.service.doctor.IDoctorService;

public class DoctorListController extends BaseController {

	@Autowired
	public IDoctorService doctorService;


}