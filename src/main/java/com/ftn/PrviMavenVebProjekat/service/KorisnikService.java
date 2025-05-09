package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;

public interface KorisnikService {
	Korisnik findOneById(Long id);
	Korisnik findOne(String email); 
	Korisnik findOne(String email, String sifra);
	List<Korisnik> findAll(); 
	Korisnik save(Korisnik korisnik); 
	Korisnik update(Korisnik korisnik); 
	Korisnik delete(Long id);
	Korisnik deblokiraj(Long id);
	List<Korisnik> find(String korisnickoIme,String uloga,String sortKI, String SortU);
	Korisnik blokiraj(Long id);
	String datumRegistracije();
}
