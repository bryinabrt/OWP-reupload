package com.ftn.PrviMavenVebProjekat.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Putovanje;

public interface PutovanjeDAO {
	
	Putovanje findOne(Long id);
	
	Putovanje findOneBySifra(String sifraPutovanja);
	
	List<Putovanje> findAll();

	List<Putovanje> find(String destinacija, String prevoznoSredstvo, String smestajnaJedinica,
						 String kategorijaPutovanja, String datumPolaska, String datumPovratka, Double cenaOd, Double cenaDo,
						 String sortDes, String sortPS, String sortSJ, String sortKat, String sortDatumStart, String sortDatumEnd,
						 String sortCena, Integer brojNocenjaOd, Integer brojNocenjaDo, String sortNoc, Integer brojMesta, Integer putId);

	int save(Putovanje putovanje); 
	
	int update(Putovanje putovanje); 
	
	int delete(Long id); 

}
