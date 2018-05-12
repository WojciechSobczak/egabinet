package media.controllers.portalgets.calls;

import java.util.HashMap;

import lombok.Data;

@Data
public class CallRequestResponseJSON {
	
	private HashMap<String, String> errors;
	private boolean success = false;
	private CallEntryJSON call;

	public CallRequestResponseJSON() {
		this.errors = new HashMap<>();
	}
}