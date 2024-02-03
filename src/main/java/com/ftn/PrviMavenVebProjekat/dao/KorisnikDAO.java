package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Uloga;

public interface KorisnikDAO {
	public Korisnik findOneById(Long id);
	public Korisnik findOne(String email); 
	public Korisnik findOne(String email, String sifra);
	public List<Korisnik> findOneForSort(String korisnickoIme, String uloga);
	public List<Korisnik> findAll(); 
	public int save(Korisnik korisnik); 
	public int update(Korisnik korisnik); 
	public int delete(Long id); 
	String datumRegistracije();
}
