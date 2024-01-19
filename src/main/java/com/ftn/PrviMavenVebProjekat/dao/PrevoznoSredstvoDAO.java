package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.PrevoznoSredstvo;

public interface PrevoznoSredstvoDAO {
	public PrevoznoSredstvo findOne(Long id); 
	
	public List<PrevoznoSredstvo> findAll(); 
	
	int save(PrevoznoSredstvo prevoznaSredstva); 
	
	int update(PrevoznoSredstvo prevoznaSredstva); 
	
	int delete(Long id);
}
