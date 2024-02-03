package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.TipJedinice;

public interface TipJediniceService {
	
	TipJedinice findOne(Long id); 
	
	TipJedinice findOneByNaziv(String naziv);
	
	List<TipJedinice> findAll(); 
	
	TipJedinice save(TipJedinice tipJedinica); 
	
	TipJedinice update(TipJedinice tipJedinica); 
	
	TipJedinice delete(Long id); 

}
