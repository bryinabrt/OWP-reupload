package com.ftn.PrviMavenVebProjekat.dao.impl;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Destinacija;
import com.ftn.PrviMavenVebProjekat.service.DestinacijaService;

@Service
@Qualifier("fajloviDestinacija")
public class DestinacijaDAOImpl implements DestinacijaService {
	
	@Value("${destinacije.pathToFile}")
	private String pathToFile;
	
	private Long nextId = 1L;
	
    private Map<Long, Destinacija> readFromFile() {

    	Map<Long, Destinacija> destinacije = new HashMap<>();
    	
    	try {
			Path path = Paths.get(pathToFile);
			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

			for (String line : lines) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				
				String[] tokens = line.split(";");
				Long id = Long.parseLong(tokens[0]);
				String grad = tokens[1];
				String drzava = tokens[2];
				String kontinent = tokens[3];
				
				Destinacija destinacija = new Destinacija(id, grad, drzava, kontinent);
				
				destinacije.put(id, destinacija);
				
				if(nextId<id)
					nextId=id;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return destinacije;
    }
    
    private Map<Long, Destinacija> saveToFile(Map<Long, Destinacija> destinacije) {
    	
    	Map<Long, Destinacija> ret = new HashMap<>();
    	
    	try {
			Path path = Paths.get(pathToFile);
			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = new ArrayList<>();
			
			for (Destinacija destinacija : destinacije.values()) {
					String line = destinacija.getId() + ";"
					+ destinacija.getGrad() + ";" 
					+ destinacija.getDrzava() + ";"
					+ destinacija.getKontinent();
				lines.add(line);
				ret.put(destinacija.getId(), destinacija);
			}
			Files.write(path, lines, Charset.forName("UTF-8"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ret;
    }

	@Override
	public Destinacija findOne(Long id) {
		Map<Long, Destinacija> destinacije = readFromFile();
		return destinacije.get(id);
	}

	@Override
	public List<Destinacija> findAll() {
		Map<Long, Destinacija> destinacije = readFromFile();
		return new ArrayList<Destinacija>(destinacije.values());
	}

	@Override
	public Destinacija save(Destinacija destinacija) {
		Map<Long, Destinacija> destinacije = readFromFile();
		if (destinacija.getId() == null) {
			destinacija.setId(++nextId);
		}
		destinacije.put(destinacija.getId(), destinacija);
		saveToFile(destinacije);
		return destinacija;
	}
	

	@Override
	public Destinacija update(Destinacija destinacija) {
		Map<Long, Destinacija> destinacije = readFromFile();
		destinacije.put(destinacija.getId(), destinacija);
		saveToFile(destinacije);
		return destinacija;
	}

	@Override
	public Destinacija delete(Long id) {
		Map<Long, Destinacija> destinacije = readFromFile();
		if (!destinacije.containsKey(id)) {
			throw new IllegalArgumentException("tried to remove non existing destination");
		}
		Destinacija destinacija = destinacije.get(id);
		if (destinacija != null) {
			destinacije.remove(id);
		}
		saveToFile(destinacije);
		return destinacija;
	}

}

