package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Usluga;

public interface UslugaService {
	
	Usluga findOne(Long id); 
	
	List<Usluga> findAll(); 
	
	Usluga save(Usluga usluge); 
	
	Usluga update(Usluga usluge); 
	
	Usluga delete(Long id); 

}
