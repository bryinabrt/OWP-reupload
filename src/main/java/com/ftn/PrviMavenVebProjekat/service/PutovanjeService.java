package com.ftn.PrviMavenVebProjekat.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Putovanje;

public interface PutovanjeService {
	Putovanje findOne(Long id); 
	
	Putovanje findOneBySifra(String sifraPutovanja);
	
	List<Putovanje> findAll(); 
	
	Putovanje save(Putovanje putovanje);

	List<Putovanje> find(String destinacija, String prevoznoSredstvo, String smestajnaJedinica,
						 String kategorijaPutovanja, String datumPolaska, String datumPovratka, Double cenaOd, Double cenaDo,
						 String sortDes, String sortPS, String sortSJ, String sortKat, String sortDatumStart, String sortDatumEnd,
						 String sortCena, Integer brojNocenjaOd, Integer brojNocenjaDo, String sortNoc, Integer brojMesta, Integer putId);
	
	Putovanje update(Putovanje putovanje); 
	
	Putovanje delete(Long id); 
}
