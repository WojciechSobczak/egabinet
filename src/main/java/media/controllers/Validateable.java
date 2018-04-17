package media.controllers;

import java.util.Map;

public interface Validateable {
	public Map<String, String> validate(String languageCode);
}
