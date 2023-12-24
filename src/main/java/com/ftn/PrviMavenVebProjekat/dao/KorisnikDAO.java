package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;

public interface KorisnikDAO {
	Korisnik findOneById(Long id);
	Korisnik findOne(String email); 
	Korisnik findOne(String email, String sifra);
	List<Korisnik> findAll(); 
	public int save(Korisnik korisnik); 
	public int update(Korisnik korisnik); 
	public int delete(Long id); 
	String trenutnoVreme();
}
