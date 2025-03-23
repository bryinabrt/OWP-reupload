package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Uloga;

public interface KorisnikDAO {
	Korisnik findOneById(Long id);
	Korisnik findOne(String email);
	Korisnik findOne(String email, String sifra);
	List<Korisnik> findAll();
	List<Korisnik> find(String korisnickoIme,String uloga,String sortKI, String SortU);
	int save(Korisnik korisnik);
	int update(Korisnik korisnik);
	int delete(Long id);
	int deblokiraj(Long id);
	int blokiraj(Long id);
	String datumRegistracije();
}
