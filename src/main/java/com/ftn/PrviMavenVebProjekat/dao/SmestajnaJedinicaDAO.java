package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;

public interface SmestajnaJedinicaDAO {
	public SmestajnaJedinica findOne(Long id); 
	
	public List<SmestajnaJedinica> findAll(); 
	
	int save(SmestajnaJedinica smestajnaJedinica); 
	
	int update(SmestajnaJedinica smestajnaJedinica); 
	
	int delete(Long id);
}
