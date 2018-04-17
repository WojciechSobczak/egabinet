package media.controllers.cruds.users.forms;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import lombok.Data;
import media.controllers.Validateable;
import media.data.model.Gender;
import media.data.model.User;
import media.service.MultilanguageService;
import media.utils.UserDataUtils;

@Data
public class UserAddForm implements Validateable {
	
	protected String email;
	protected String password;
	protected Boolean active;
	protected String pesel;
	protected String name;
	protected String secondName;
	protected String surname;
	protected Gender gender;
	protected String city;
	protected String street;
	protected String postalCode;
	protected String postCity;

	@Override
	public Map<String, String> validate(String languageCode) {
		Map<String, String> messages = new HashMap<String, String>();
		if (StringUtils.isEmpty(email)) {
			messages.put("email", MultilanguageService.getMessage(languageCode, "email.empty"));
		} else if (!EmailValidator.getInstance().isValid(email)) {
			messages.put("email", MultilanguageService.getMessage(languageCode, "email.invalid.format"));
		}
		if (StringUtils.isEmpty(password)) {
			messages.put("password", MultilanguageService.getMessage(languageCode, "password.empty"));
		}
		
		if (StringUtils.isEmpty(pesel)) {
			messages.put("pesel", MultilanguageService.getMessage(languageCode, "pesel.empty"));
		} else if (!UserDataUtils.isPeselValid(pesel)) {
			messages.put("pesel", MultilanguageService.getMessage(languageCode, "pesel.invalid.format"));
		}
		
		if (StringUtils.isEmpty(name)) {
			messages.put("name", MultilanguageService.getMessage(languageCode, "name.empty"));
		}
		
		if (StringUtils.isEmpty(surname)) {
			messages.put("surname", MultilanguageService.getMessage(languageCode, "surname.empty"));
		}
		
		if (gender == null) {
			messages.put("gender", MultilanguageService.getMessage(languageCode, "gender.empty"));
		}
		
		if (StringUtils.isEmpty(city)) {
			messages.put("city", MultilanguageService.getMessage(languageCode, "city.empty"));
		}
		
		if (StringUtils.isEmpty(street)) {
			messages.put("street", MultilanguageService.getMessage(languageCode, "street.empty"));
		}
		
		if (StringUtils.isEmpty(postalCode)) {
			messages.put("postalCode", MultilanguageService.getMessage(languageCode, "postalCode.empty"));
		} else if (!UserDataUtils.isPostalCodeValid(postalCode)) {
			messages.put("postalCode", MultilanguageService.getMessage(languageCode, "postalCode.invalid.format"));
		}
		
		if (StringUtils.isEmpty(postCity)) {
			messages.put("postCity", MultilanguageService.getMessage(languageCode, "postCity.empty"));
		} 
		
		return messages;
	}

	
	public User toUser() {
		User user = new User();
		user.setEmail(email);
		user.setActive(active == null ? true : active);
		user.setPassword(password);
		user.setCreationDate(LocalDateTime.now());
		user.setPesel(pesel);
		user.setName(name);
		user.setSecondName(secondName);
		user.setSurname(surname);
		user.setGender(gender);
		user.setCity(city);
		user.setStreet(street);
		user.setPostalCode(postalCode);
		user.setPostCity(postCity);
		return user;
	}

}
