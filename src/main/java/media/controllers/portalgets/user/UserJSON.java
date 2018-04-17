package media.controllers.portalgets.user;

import lombok.Data;
import media.data.model.User;
import media.utils.DateUtils;

@Data
public class UserJSON {
	
	private Long id;
	private String email;
	private long creationDate;
	private boolean active;
	
	public UserJSON(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.creationDate = DateUtils.getMilis(user.getCreationDate());
		this.active = user.isActive();
	}
	
	public UserJSON() {
	}

}
