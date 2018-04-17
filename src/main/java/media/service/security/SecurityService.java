package media.service.security;

import java.security.SecureRandom;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import media.data.model.auth.Token;

@Service
public class SecurityService implements ISecurityService {
	
	private static final String SALT = "IFplOMQoc6VttWISHNVOOp0JELPkZCyhSGIjHYSBeJIuEiiU2qT3w3CwKgvunMRIIR58gPJAt4";
	private ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
	private SecureRandom random = new SecureRandom();
	
	@Override
	public String hashPassword(String password) {
		return shaPasswordEncoder.encodePassword(password, SALT);
	}

	@Override
	public Token generateToken() {
		byte[] accessBytes = new byte[256];
		byte[] refreshBytes = new byte[256];
		random.nextBytes(accessBytes);
		random.nextBytes(refreshBytes);
		
		Token token = new Token(
			shaPasswordEncoder.encodePassword(new String(accessBytes), SALT),
			shaPasswordEncoder.encodePassword(new String(refreshBytes), SALT)
		);
		
		return token;
	}

	
}
