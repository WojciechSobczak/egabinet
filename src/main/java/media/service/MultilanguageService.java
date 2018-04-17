package media.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MultilanguageService {
	
	public static final String DEFAULT_LANGUAGE = "en";
	
	public static final Map<String, Map<String, String>> MESSAGES;
	
	static {
		MESSAGES = new HashMap<>();
		try {
			URL pathUrl = MultilanguageService.class.getClassLoader().getResource("languages/");
			String[] filesNames = new File(pathUrl.toURI()).list();
			for (int i = 0; i < filesNames.length; i++) {
				String[] tokens = filesNames[i].split("\\.");
				if (tokens.length != 2) {
					continue;
				}
				
				String extension = tokens[1];
				if (!"lang".equals(extension)) {
					continue;
				}
				
				String languageCode = tokens[0];
				Map<String, String> languageMessages = new HashMap<>();
				MESSAGES.put(languageCode, languageMessages);
				
				File file = new File(MultilanguageService.class.getClassLoader().getResource("languages/" + filesNames[i]).getFile());
				try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
					String buffer = null;
					while ((buffer = bufferedReader.readLine()) != null) {
						if (buffer.startsWith("#")) {
							continue;
						}
						
						String[] messageTokens = buffer.split("\\=");
						String messageCode = null;
						String messageText = null;
						
						if (messageTokens.length == 0) {
							continue;
						} else if (messageTokens.length == 1) {
							messageCode = messageTokens[0];
							messageText = "";
						} else {
							messageCode = messageTokens[0];
							messageText = messageTokens[1];
						}
						
						languageMessages.put(messageCode.trim(), messageText.trim());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-126);
		}
	}
	
	
	public static String getMessage(String code) {
		return getMessage(DEFAULT_LANGUAGE, code);
	}
	
	public static String getMessage(String languageCode, String code) {
		if (languageCode == null) {
			languageCode = DEFAULT_LANGUAGE;
		}
		Map<String, String> messages = MESSAGES.get(languageCode);
		if (messages == null) {
			return code;
		}
		String message = messages.get(code);
		if (message == null) {
			return code;
		}
		
		return message;
	}

}
