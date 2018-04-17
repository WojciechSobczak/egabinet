package media.controllers.cruds;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CrudResponse {
	private boolean valid = false;
	private Map<String, String> messages = new HashMap<>();
}