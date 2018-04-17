package media.controllers.portalgets.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import media.service.user.IUserService;

public class UserListController {

	@Autowired
	public IUserService userService;


	@RequestMapping(value = { "/get/front/list/users/" }, method = RequestMethod.POST, consumes = "application/json")
	public List<UserJSON> getList() {
		return null;
	}
	
}