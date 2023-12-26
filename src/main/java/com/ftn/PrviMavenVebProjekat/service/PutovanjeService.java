package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Putovanje;

public interface PutovanjeService {
	Putovanje findOne(Long id); 
	
	List<Putovanje> findAll(); 
	
	Putovanje save(Putovanje putovanje); 
	
	Putovanje update(Putovanje putovanje); 
	
	Putovanje delete(Long id); 
}
