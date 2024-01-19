package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.TipJedinice;

public interface TipJediniceDAO {
	
	public TipJedinice findOne(Long id); 
	
	public List<TipJedinice> findAll(); 
	
	int save(TipJedinice tipJedinice); 
	
	int update(TipJedinice tipJedinice); 
	
	int delete(Long id);
	
}