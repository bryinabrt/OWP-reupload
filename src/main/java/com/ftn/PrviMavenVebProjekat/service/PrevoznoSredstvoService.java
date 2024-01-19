package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.PrevoznoSredstvo;

public interface PrevoznoSredstvoService {
	PrevoznoSredstvo findOne(Long id); 
	
	List<PrevoznoSredstvo> findAll(); 
	
	PrevoznoSredstvo save(PrevoznoSredstvo prevoznaSredstva); 
	
	PrevoznoSredstvo update(PrevoznoSredstvo prevoznaSredstva); 
	
	PrevoznoSredstvo delete(Long id); 
}
