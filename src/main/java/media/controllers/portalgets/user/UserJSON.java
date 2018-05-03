package media.controllers.portalgets.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import media.data.model.User;
import media.utils.DateUtils;

@Data
public class UserJSON {
	
	private Long id;
	private String email;
	private String name;
	private String surname;
	private Long doctorId;
	private Long patientId;
	private long creationDate;
	private Integer[] roles;
	
	public UserJSON(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.creationDate = DateUtils.getMilis(user.getCreationDate());
		this.name = user.getName();
		this.surname = user.getSurname();
		
		if (user.getDoctor() != null) {
			this.doctorId = user.getDoctor().getId();
		}
		if (user.getPatient() != null) {
			this.patientId = user.getPatient().getId();
		}
		
		ArrayList<Integer> roles= new ArrayList<>();
		if (user.isAdmin()) {
			roles.add(2);
		}
		if (user.hasDoctor()) {
			roles.add(1);
		}
		if (user.hasPatient()) {
			roles.add(0);
		}
		this.roles = roles.toArray(new Integer[0]);
	}
	
	public UserJSON() {
	}
	
	
	public static List<UserJSON> getList(List<User> users) {
		ArrayList<UserJSON> output = new ArrayList<>(users.size());
		for (User user : users) {
			output.add(new UserJSON(user));
		}
		return output;
	}

}
