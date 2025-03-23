package com.ftn.PrviMavenVebProjekat.service.impl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.KorisnikDAO;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;

@Service
public class DatabaseKorisnikServiceImpl implements KorisnikService{

	@Autowired
	private KorisnikDAO korisnikDAO;
	
	@Override
	public Korisnik findOneById(Long id) {
		return korisnikDAO.findOneById(id);
	}

	@Override
	public Korisnik findOne(String email) {
		return korisnikDAO.findOne(email);
	}

	@Override
	public Korisnik findOne(String email, String sifra) {
		return korisnikDAO.findOne(email, sifra);
	}

	@Override
	public List<Korisnik> findAll() {
		return korisnikDAO.findAll();
	}

	@Override
	public List<Korisnik> find(String korisnickoIme,String uloga,String sortKI, String SortU) {
		return korisnikDAO.find(korisnickoIme, uloga, sortKI, SortU);
	}

	@Override
	public Korisnik save(Korisnik korisnik) {
		korisnikDAO.save(korisnik);
		return korisnik;
	}

	@Override
	public Korisnik update(Korisnik korisnik) {
		korisnikDAO.update(korisnik);
		return korisnik;
	}

	@Override
	public Korisnik delete(Long id) {
		Korisnik korisnik = korisnikDAO.findOneById(id);
		korisnikDAO.delete(id);
		return korisnik;
	}

	@Override
	public  Korisnik blokiraj(Long id) {
		Korisnik korisnik = korisnikDAO.findOneById(id);
		korisnikDAO.blokiraj(id);
		return korisnik;
	}

	@Override
	public  Korisnik deblokiraj(Long id) {
		Korisnik korisnik = korisnikDAO.findOneById(id);
		korisnikDAO.deblokiraj(id);
		return korisnik;
	}
	
	@Override
	public String datumRegistracije() {
		/*
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date trenutnoVreme = new Date().getTime();
		String strTrenutnoVreme = formatter.format(trenutnoVreme);
		return strTrenutnoVreme;
		
		*/
        Date date = (Date) Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);  
		return strDate;
	}


}
