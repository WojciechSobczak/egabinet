package media.service.security;

import media.data.model.auth.Token;

public interface ISecurityService {
	
	public String hashPassword(String password);
	public Token generateToken();
	
	
}
