package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Putovanje;

public interface PutovanjeDAO {
	
	public Putovanje findOne(Long id); 
	
	public List<Putovanje> findAll(); 
	
	int save(Putovanje putovanje); 
	
	int update(Putovanje putovanje); 
	
	int delete(Long id); 

}
