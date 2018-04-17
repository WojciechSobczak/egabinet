package media.data.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.ToString;

public class Parser {
	
	@ToString
	static class DrugLord {
		String name;
		String commonName;
		HashSet<String> forms = new HashSet<>();
		HashSet<String> doses = new HashSet<>();
		String ean;
		HashSet<String> packages = new HashSet<>();
	}
	
	public static void main(String[] args) throws IOException {
		
		HashMap<String, DrugLord> drugLords = new HashMap<>();
		List<String> list = Files.readAllLines(Paths.get("C:/Users/Lama/Desktop/leks.txt"));
		HashSet<String> filter = new HashSet<>();
		for (String string : list) {
			String[] lineChunks = string.split(";", -1);
			for (int i = 0; i < lineChunks.length; i++) {
				lineChunks[i] = lineChunks[i].trim();
			}
			DrugLord lord = drugLords.get(lineChunks[0]);
			if (lord == null) {
				lord = new DrugLord();
				lord.name = lineChunks[0];
				lord.commonName = lineChunks[1];
				lord.ean = lineChunks[5];
			}
			lord.forms.add(lineChunks[2]);
			lord.doses.add(lineChunks[3]);
			lord.packages.add(lineChunks[6]);
			
			drugLords.put(lord.name, lord);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		for (Entry<String, DrugLord> lord : drugLords.entrySet()) {
			System.out.println(lord);
		}
		
		
		
	}

}
