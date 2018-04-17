package media.utils;

import java.security.SecureRandom;

import media.data.model.Gender;


public class PeselGenerator {

	private static int PESEL_LENGHT = 11;

	public String generatePesel() {
		SecureRandom secureRandom = new SecureRandom();
		int[] peselCurrent = new int[PESEL_LENGHT];
		char[] peselPrint = new char[PESEL_LENGHT];
		int year = (secureRandom.nextInt() % 75) + 1940;
		int month = (secureRandom.nextInt() % 12) + 1;
		int day = month == 2 ? (secureRandom.nextInt() % 29) + 1 : (secureRandom.nextInt() % 30) + 1;
		
		int gender = secureRandom.nextInt() % 2;
		peselCurrent[0] = year / 10 % 10;
		peselCurrent[1] = year % 10;
		if (year < 1900) {
			peselCurrent[2] = month / 10 + 8;
		} else if (year < 2000) {
			peselCurrent[2] = month / 10;
		} else if (year < 2100) {
			peselCurrent[2] = month / 10 + 2;
		} else if (year < 2200) {
			peselCurrent[2] = month / 10 + 4;
		} else if (year < 2300) {
			peselCurrent[2] = month / 10 + 6;
		}
		
		peselCurrent[3] = month % 10;
		peselCurrent[4] = day / 10;
		peselCurrent[5] = day % 10;

		// generate a validNumber
		int validMonthNumber = peselCurrent[0] + peselCurrent[4] + 3 * (peselCurrent[1] + peselCurrent[5]) + 7 * peselCurrent[2] + 9 * peselCurrent[3];
		for (int l = 0; l < 6; l++) {
			peselPrint[l] = (char) (peselCurrent[l] + 48);
		}

		peselCurrent[6] = gender / 1000;
		int r = gender % 1000;
		peselCurrent[7] = r / 100;
		r %= 100;
		peselCurrent[8] = r / 10;
		peselCurrent[9] = r % 10;

		int validNumber = (peselCurrent[8] + 3 * peselCurrent[9] + 7 * peselCurrent[6] + 9 * peselCurrent[7] + validMonthNumber) % 10;
		if (validNumber == 0) {
			peselPrint[10] = 48;
		} else {
			peselPrint[10] = (char) (58 - validNumber);
		}

		for (int l = 6; l < 10; l++) {
			peselPrint[l] = (char) (peselCurrent[l] + 48);
		}
		
		return String.valueOf(peselPrint);
	}
	
	public static Gender getGenderFromPesel(String pesel) {
		return Integer.parseInt(pesel.split("")[9]) % 2 == 1 ? Gender.MALE : Gender.FEMALE;
	}

}