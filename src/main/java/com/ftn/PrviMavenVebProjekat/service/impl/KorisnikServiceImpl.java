/*package com.ftn.PrviMavenVebProjekat.service.impl;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Uloga;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;

@Service
@Qualifier("fajloviKorisnik")
public class KorisnikServiceImpl implements KorisnikService{
	
	@Value("${korisnici.pathToFile}")
	private String pathToFile;
	
	Long nextId = 1L;
	
	private Map<Long, Korisnik> readFromFile() {

    	Map<Long, Korisnik> korisnici = new HashMap<>();
    	
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
				String korisnickoIme = tokens[1];
				String lozinka = tokens[2];
				String email = tokens[3];
				String ime = tokens[4];
				String prezime = tokens[5];
				String datumRodjenja = tokens[6];
				String adresa = tokens[7];
				String brojTelefona = tokens[8];
				String trenutnoVreme = tokens[9];
				Uloga uloga = Uloga.valueOf(tokens[10]);
				

				korisnici.put(id, new Korisnik(id,korisnickoIme,lozinka,email,ime,prezime,datumRodjenja,
						adresa,brojTelefona,trenutnoVreme, uloga));

				if(nextId<id)
					nextId=id;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return korisnici;
    }
    
    private Map<Long, Korisnik> saveToFile(Map<Long, Korisnik> korisnici) {
    	
    	Map<Long, Korisnik> ret = new HashMap<>();
    	
    	try {
			Path path = Paths.get(pathToFile);
			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = new ArrayList<>();
			
			for (Korisnik k : korisnici.values()) {
				lines.add(k.toFileString());
				ret.put(k.getId(), k);
			}
			//pisanje svih redova za clanske karte
			Files.write(path, lines, Charset.forName("UTF-8"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ret;
    }
    
    private Long nextId(Map<Long, Korisnik> korisnici) {
    	for (Long id : korisnici.keySet()) {
    		if(nextId<id)
				nextId=id;
		}
    	return ++nextId;
    }
	
	@Override
	public Korisnik findOneById(Long id) {
		Map<Long, Korisnik> korisnici = readFromFile();
		return korisnici.get(id);
	}

	@Override
	public Korisnik findOne(String email) {
		Map<Long, Korisnik> korisnici = readFromFile();
		Korisnik found = null;
		for (Korisnik korisnik : korisnici.values()) {
			if (korisnik.getEmail().equals(email)) {
				found = korisnik;
				break;
			}
		}
		
		return found;
	}

	@Override
	public Korisnik findOne(String email, String sifra) {
		Map<Long, Korisnik> korisnici = readFromFile();
		Korisnik found = null;
		for (Korisnik korisnik : korisnici.values()) {
			if (korisnik.getEmail().equals(email) && korisnik.getLozinka().equals(sifra)) {
				found = korisnik;
				break;
			}
		}
		
		return found;
	}

	@Override
	public List<Korisnik> findAll() {
		Map<Long, Korisnik> korisnici = readFromFile();
		return new ArrayList<>(korisnici.values());
	}

	@Override
	public Korisnik save(Korisnik korisnik) {
		Map<Long, Korisnik> korisnici = readFromFile();	
		Long nextId = nextId(korisnici); 
		
		if (korisnik.getId() == null) {
			korisnik.setId(nextId++);
			
		}
		korisnici.put(korisnik.getId(), korisnik);
		saveToFile(korisnici);
		return korisnik;
	}

	@Override
	public Korisnik update(Korisnik korisnik) {
		Map<Long, Korisnik> korisnici = readFromFile();	
		
		korisnici.put(korisnik.getId(), korisnik);
		saveToFile(korisnici);
		return korisnik;
	}

	@Override
	public Korisnik delete(Long id) {
		Map<Long, Korisnik> korisnici = readFromFile();	
		
		if (!korisnici.containsKey(id)) {
			throw new IllegalArgumentException("tried to remove non existing user");
		}
		
		Korisnik korisnik = korisnici.get(id);
		if (korisnik != null) {
			korisnici.remove(id);
		}
		saveToFile(korisnici);
		return korisnik;
	}
	public String trenutnoVreme() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date trenutnoVreme = new Date();
		String strTrenutnoVreme = formatter.format(trenutnoVreme);
		return strTrenutnoVreme;
		
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);  
		return strDate;
	}
	
}*/