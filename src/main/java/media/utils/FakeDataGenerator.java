/*package media.utils;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import media.data.dao.citizen.Gender;
import media.data.model.Citizen;
import media.data.model.Drug;
import media.data.model.DrugRecommendation;
import media.data.model.Pharmacy;
import media.data.model.User;
import media.data.model.Vovoidship;
import media.service.security.SecurityService;

public class FakeDataGenerator {
	
	public static String[] maleSecondNames = new String[]{"Jan","Stanisław","Andrzej","Józef","Tadeusz","Jerzy","Zbigniew","Krzysztof","Henryk","Ryszard","Kazimierz","Marek","Marian","Piotr","Janusz","Władysław","Adam","Wiesław","Zdzisław","Edward","Mieczysław","Roman","Mirosław","Grzegorz","Czesław","Dariusz","Wojciech","Jacek","Eugeniusz","Tomasz","Stefan","Zygmunt","Leszek","Bogdan","Antoni","Paweł","Franciszek","Sławomir","Waldemar","Jarosław","Robert","Mariusz","Włodzimierz","Michał","Zenon","Bogusław","Witold","Aleksander","Bronisław","Wacław","Bolesław","Ireneusz","Maciej","Artur","Edmund","Marcin","Lech","Karol","Rafał","Arkadiusz","Leon","Sylwester","Lucjan","Julian","Wiktor","Romuald","Bernard","Ludwik","Feliks","Alfred","Alojzy","Przemysław","Cezary","Daniel","Mikołaj","Ignacy","Lesław","Radosław","Konrad"};
	public static String[] femaleSecondNames = new String[]{"Maria","Krystyna","Anna","Barbara","Teresa","Elżbieta","Janina","Zofia","Jadwiga","Danuta","Halina","Irena","Ewa","Małgorzata","Helena","Grażyna","Bożena","Stanisława","Jolanta","Marianna","Urszula","Wanda","Alicja","Dorota","Agnieszka","Beata","Katarzyna","Joanna","Wiesława","Renata","Iwona","Genowefa","Kazimiera","Stefania","Hanna","Lucyna","Józefa","Alina","Mirosława","Aleksandra","Władysława","Henryka","Czesława","Lidia","Regina","Monika","Magdalena","Bogumiła","Marta","Marzena","Leokadia","Mariola","Bronisława","Aniela","Bogusława","Eugenia","Izabela","Cecylia","Emilia","Łucja","Gabriela","Sabina","Zdzisława","Agata","Edyta","Aneta","Daniela","Wioletta","Sylwia","Weronika","Antonina","Justyna","Gertruda","Mieczysława","Franciszka","Rozalia","Zuzanna","Natalia","Celina","Ilona","Alfreda","Wiktoria","Olga","Wacława","Róża","Karolina","Bernadeta","Julia","Michalina","Adela","Ludwika","Honorata","Aldona","Eleonora","Violetta","Felicja","Walentyna","Pelagia","Apolonia","Brygida","Zenona","Izabella","Romana","Zenobia","Waleria","Anita","Bożenna","Romualda","Marzanna","Anastazja","Kamila","Aurelia","Ewelina","Ludmiła","Hildegarda","Teodozja","Feliksa","Nina","Paulina","Longina","Julianna","Klara","Marlena","Teodora","Leonarda","Ryszarda","Liliana","Kinga","Lilianna","Albina","Elwira","Gizela","Bolesława","Otylia","Karina","Arleta","Marzenna","Melania","Kornelia","Salomea","Adelajda","Eryka","Dominika","Sławomira","Donata","Eliza","Tamara","Zyta","Bernadetta","Nadzieja","Bernarda","Irmina","Julita","Wiera","Dagmara","Wioleta","Matylda","Edwarda","Lilla","Klaudia","Żaneta","Tatiana","Elfryda","Patrycja","Anetta","Lilia","Teofila","Daria","Maryla","Rita","Amelia"};
	public static String[] cities = new String[] {"Warszawa","Kraków","Łódź","Wrocław","Poznań","Gdańsk","Szczecin","Bydgoszcz","Lublin","Katowice","Białystok","Gdynia","Częstochowa","Radom","Sosnowiec","Toruń","Kielce","Rzeszów","Gliwice","Zabrze","Olsztyn","Bielsko","Bytom","Ruda","Rybnik","Zielona","Tychy","Gorzów","Dąbrowa","Płock"};
	public static String[] drugsNames = new String[]{"Dampiryna x 10 tabl (Amara)","Coffepirine x 6 tabletek","Paracetamol 500 mg x 10 tabl (biofarm)","Calcium 300 mg + vit.c x 20 tabl musujących - smak cytrynowy","Ambrosan 30 mg x 20 tabl ","Zdrovit calcium w folii z witaminą c  x 12 tabl musujących","Calcium c x 12 tabl musujących o smaku pomarańczowym (pliva kraków)","Coffepirine x 12 tabletek","Calcium c forte x 20 tabl musujących o smaku pomarańczowym (Biotter)","Sirupus althae - syrop prawoślazowy 125 g (gemi) kartonik","Syrop prawoślazowy 125 g (Hasco-Lek)","Maść majerankowa 10 g (amara)","Ibuprofen aflofarm 200 mg x 10 tabl drażowanych","Cukierki verbena dzika róża 60 g","Verbena żelki aloes i winogrona  60 g ","Syrop prawoślazowy 125 g (Amara)","Codipar 500 mg x 12 tabl","Paracetamol 500 mg x 20 tabl (blister - Filofarm)","Zdrovit calcium 300 mg + vit.c x 20 tabl musujących","Syrop Tussipini D 125 g","Syrop z sulfogwajakolem 125 g (prolab)","Rutinostrong x 60 tabl ","Paracetamol 125 mg x 10 czopków (Hasco)","Ibupar forte 400 mg x 10 tabl powlekanych","Rodzina Zdrowia Nagietek x 30 sasz","Rodzina Zdrowia Szałwia x 30 sasz","Syrop althagem 125 g","Rutinoscorbin x 30 tabl","Vitaminum c 200 mg x 50 tabl powlekanych (teva)","Calcium alergino buzzujące x 24 tabl musujące ","Witamina C 200 mg x 50 tabl (Biotter)","Omnipap (Paracetamol Actavis) 500 mg x 12 tabl powlekanych","Syrop prawoślazowy alti-sir 125 g ","Calcium c x 16 tabl musujących o smaku maliny (polfa łódź)","Liść podbiału 50 g (Kawon)","Xylometazolin VP 0,05% krople 10 ml (ICN)","Syrop tymiankowy złożony 125 g (Amara)","Ibum x 10 kaps","Witamina c forte 1000 mg x 20 tabl musujących (Biotter)","Panadol 500 mg x 12 tabl powlekanych","Ibuprofen Teva Max 400 mg x 10 tabl powlekanych","Paracetamol 500 mg x 15 tabl powlekanych (Hasco)","Aromactiv sztyft do nosa","Apap noc x 6 tabl powlekanych","Rhin-bac fresh sztyft do nosa","Ibupar 200 mg x 10 tabl drażowanych","Fix szałwia 1,5 g x 30 sasz (Kawon)","Ibuprofen Teva 200 mg x 10 tabl powlekanych ","Syrop tymiankowy złożony 125 g (hasco-lek)","Rodzina Zdrowia Czystek x 30 sasz","Nurofen 200 mg x 12 tabl","Syrop z sulfogwajakolem 125 g (Amara)","Sirupus kalii guajacolosulfonici 125 g (VIS)","Laciflorstrong x 10 kaps","Ibuprofen aflofarm 200 mg x 20 tabl drażowanych","Syrop z sulfogwajakolem 6% 125 g (hasco-lek)","Rutinacea max x 60 tabl","Calcium c cytrynowe x 16 tabl musujących (polfa łódź) ","Calcium c pomarańczowe x 16 tabl musujących (polfa łódź)","Rutinacea complete x 90 tabl + 30 tabl","Vitaminum C 1000 mg x 20 tabl musujących (Polfa Łódź)","Sirupus verbasci 125 g (hasco-lek)","PlantagoPharm syrop  100 ml","Sirupus plantaginis - syrop z babki lancetowatej 125 g (Hasco-lek)","Syrop tymiankowy złożony 125 g (Prolab)","Lancetan - syrop z babki lancetowatej 125 g","Olimp Ulgard herbal  x 16 pastylek do ssania o smaku ziołowo - cytrynowym","Rodzina Zdrowia Lipa x 30 sasz","Envil Gardło (Envix) x 10 tabl do ssania","Paracetamol 500 mg x 20 tabl (biofarm)","Pyralgina 500 mg x 6 tabl","Rutinacea junior x 20 tabletek do ssania","Vitamina c 1000 mg x 20 tabl musujących","Cynk organiczny  x 30 tabl powlekanych (Avet)","Ibuprom 200 mg x 10 tabl powlekanych","Flucontrol max x 10 tabl","Fix salviae - szałwia 1,2 g x 30 sasz","Paracetamol 125 mg x 10 czopków (Aflofarm)","Calcium o smaku neutralnym x 20 tabl musujących (Polski Lek) ","Syrop z babki lancetowatej 125 g (Amara)","Domowa apteczka immunorutin x 90 tabl","Ibufen Baby  60 mg x 5 czopków ","Ultralac rich x 10 kaps","Vitamina c 1000 mg x 10 tabl musujących" };

	private static SecureRandom random = new SecureRandom();
	
	
	public static Pair<Citizen, User> getCitizenAndUser() throws IOException {
		
		Document doc = Jsoup.connect("http://www.fakenamegenerator.com/gen-random-pl-pl.php").get();
		Element dataContainer = doc.select("#details").first();
		dataContainer = dataContainer.select(".content").first();
		dataContainer = dataContainer.select(".content").first();

		Citizen citizen = new Citizen();
		citizen.setName(dataContainer.select(".address h3").html().split(" ")[0].trim());
		citizen.setSurname(dataContainer.select(".address h3").html().split(" ")[1].trim());
		
		Element addressContainer = dataContainer.select(".adr").first();
		String address = addressContainer.html().trim();
		String[] splittedAddress = address.split("\\s");
		for (int i = 0; i < splittedAddress.length; i++) {
			splittedAddress[i] = splittedAddress[i].trim();
		}
		
		citizen.setCity(splittedAddress[splittedAddress.length - 1].trim());
		citizen.setPostCity(splittedAddress[splittedAddress.length - 1].trim());
		citizen.setPostalCode(splittedAddress[splittedAddress.length - 2].replaceAll("<br>", "").trim());
		citizen.setStreet(address.substring(4, address.indexOf("<br>")).trim());
		citizen.setVovoidship(Vovoidship.values()[random.nextInt(16)]);
		
		Element peselContainer = dataContainer.select(".extra").first();
		peselContainer = peselContainer.select(".dl-horizontal").get(1);
		peselContainer = peselContainer.select("dd").first();
		
		String pesel = peselContainer.html().substring(0, peselContainer.html().indexOf("<")).trim();
		Gender gender = Integer.parseInt(pesel.split("")[9]) % 2 == 1 ? Gender.MALE : Gender.FEMALE;
		citizen.setSecondName(gender == Gender.FEMALE ? femaleSecondNames[random.nextInt(femaleSecondNames.length)] : maleSecondNames[random.nextInt(maleSecondNames.length)]);
		citizen.setPesel(pesel);
		citizen.setGender(gender);
		
		User user = new User();
		user.setActive(true);
		user.setCreationDate(LocalDateTime.now());
		
		Element emailContainer = dataContainer.select(".extra").first();;
		emailContainer = emailContainer.select(".dl-horizontal").get(8);
		emailContainer = emailContainer.select("dd").first();
		String email =  emailContainer.html().substring(0, emailContainer.html().indexOf("<")).trim();
		user.setEmail(email);
		user.setPassword(new SecurityService().hashPassword(citizen.getName()+citizen.getSurname()));
		user.setActive(true);
		
		return new ImmutablePair<Citizen, User>(citizen, user);
	}
	
	public static Pharmacy getRandomPharmacy(Citizen citizen) {
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setCity(cities[random.nextInt(cities.length)]);
		pharmacy.setName("Apteka " + citizen.getName() + " " + citizen.getSurname());
		pharmacy.setPostCity(citizen.getCity());
		pharmacy.setPostalCode(citizen.getPostalCode());
		pharmacy.setStreet(citizen.getStreet());
		pharmacy.setVovoidship(Vovoidship.values()[random.nextInt(16)]);
		
		return pharmacy;
	}
	
	public static Drug getRandomDrug() {
		Drug drug = new Drug();
		String randomName = drugsNames[random.nextInt(drugsNames.length)];
		drug.setName(randomName);
		drug.setCommonName(randomName);
		
		char[] ean = new char[10];
		for (int i = 0; i < ean.length; i++) {
			ean[i] = (char) (random.nextInt(9) + 48);
		}
		drug.setEan(new String(ean));
		drug.setRefundPercentage((long) random.nextInt(101));
		
		return drug;
	}
	
	public static DrugRecommendation getRandomDrugRecommendation(Drug drug) {
		DrugRecommendation drugRecommendation = new DrugRecommendation();
		drugRecommendation.setDrug(drug);
		drugRecommendation.setComments(random.nextBoolean() ? "Brak komentarza" : "MÄ…dry komentarz");
		drugRecommendation.setDose(random.nextInt(10) + " tabletek");
		drugRecommendation.setInterval(random.nextBoolean() ? "dziennie" : "tygodniowo");
		drugRecommendation.setRefundPercentage(drug.getRefundPercentage());
		return drugRecommendation;
	}
	
}
*/