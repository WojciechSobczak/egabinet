package media.utils;

public class UserDataUtils {
	
	public static final boolean isPostalCodeValid(String postalCode) {
		if (postalCode == null) {
			return false;
		}
		if (postalCode.length() != 6) {
			return false;
		}
		
		String[] tokens = postalCode.split("\\-");
		if (tokens.length != 2) {
			return false;
		}
		
		try {
			Integer first = Integer.parseInt(tokens[0]);
			Integer second = Integer.parseInt(tokens[1]);
			if (first < 0 && second < 0) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	public static final boolean isPeselValid(String pesel) {
		int peselSize = pesel.length();
		if (peselSize != 11) {
			return false;
		}
		int[] weights = { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3 };
		int j = 0;
		int sum = 0;
		int control = 0;
		int controlSum = Integer.valueOf(pesel.substring(peselSize - 1));
		for (int i = 0; i < peselSize - 1; i++) {
			char c = pesel.charAt(i);
			j = Integer.valueOf(String.valueOf(c));
			sum += j * weights[i];
		}
		
		control = 10 - (sum % 10);
		if (control == 10) {
			control = 0;
		}
		
		return (control == controlSum);
	}

}
