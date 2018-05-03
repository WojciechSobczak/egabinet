package media.controllers.portalgets.user;

import java.util.List;

import lombok.Data;

@Data
public class UsersListJSON {
	
	private List<UserJSON> patients;
	private List<UserJSON> rehabs;
	private List<UserJSON> admins;
	
	
	public UsersListJSON() {}
	
}
