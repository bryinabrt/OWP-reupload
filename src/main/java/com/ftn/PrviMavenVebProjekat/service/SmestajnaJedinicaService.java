package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;

public interface SmestajnaJedinicaService {
	
	SmestajnaJedinica findOne(Long id); 
	
	List<SmestajnaJedinica> findAll(); 
	
	SmestajnaJedinica save(SmestajnaJedinica smestajneJedinice); 
	
	SmestajnaJedinica update(SmestajnaJedinica smestajneJedinice); 
	
	SmestajnaJedinica delete(Long id); 

}
