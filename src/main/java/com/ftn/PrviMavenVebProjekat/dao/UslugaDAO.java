package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Usluga;

public interface UslugaDAO {
	
	public Usluga findOne(Long id); 
	
	public List<Usluga> findAll(); 
	
	int save(Usluga usluga); 
	
	int update(Usluga usluga); 
	
	int delete(Long id);

}
