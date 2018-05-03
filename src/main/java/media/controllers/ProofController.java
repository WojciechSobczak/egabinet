package media.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import media.data.model.Gender;
import media.data.model.User;
import media.data.model.auth.Token;
import media.service.security.ISecurityService;
import media.service.token.ITokenService;
import media.service.user.IUserService;

@RestController
public class ProofController {

	@Autowired 
	private ITokenService tokenService;
	
	@Autowired 
	private IUserService userService;
	
	@Autowired 
	private ISecurityService securityService;
//	
	@RequestMapping(value = "/createAdmin", method = RequestMethod.GET)
	public String createAdmin() {
		String username = "admin@admin.com";
		String password = "admin";
		User oldUser = userService.getByEmailAndHashedPassword(username, securityService.hashPassword(password));
		if (oldUser != null) {
			return "Już istnieje!";
		}
		
		User user = new User();
		user.setName("Mikołaj");
		user.setSurname("Skrzetuski");
		user.setAdmin(true);
//		user.setSecondName("Adam");
//		user.setCity("Zbaraż");
//		user.setGender(Gender.MALE);
//		user.setPesel("80030714179");
//		user.setPostalCode("25-658");
//		user.setPostCity("Zbaraż");
//		user.setStreet("Twierdzowa 25");
		user.setCreationDate(LocalDateTime.now());
		user.setEmail(username);
		user.setPassword(securityService.hashPassword(password));
		user.setActive(true);
		user.setId(userService.save(user));
		
		return "Udało się!";
	}
//	
//	@RequestMapping(value = "/removeAdmin", method = RequestMethod.GET)
//	public String removeAdmin() {
//		String username = "admin@admin.com";
//		String password = "admin";
//		User oldUser = userService.getByEmailAndHashedPassword(username, securityService.hashPassword(password));
//		if (oldUser == null) {
//			return "Nie istnieje";
//		}
//		userService.delete(oldUser);
//		return "Udało się";
//	}
//	
//	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
//	public Token getToken(@RequestParam("id") Long id) {
//		return tokenService.getById(id);
//	}
//	
//	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
//	public User getUser(@RequestParam("id") Long id) {
//		return userService.getById(id);
//	}
	
	
}
