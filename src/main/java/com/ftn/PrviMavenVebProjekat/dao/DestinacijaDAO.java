package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Destinacija;

public interface DestinacijaDAO {

	Destinacija findOne(Long id); 
	List<Destinacija> findAll(); 
	Destinacija save(Destinacija destinacija); 
	Destinacija update(Destinacija destinacija); 
	Destinacija delete(Long id); 
}