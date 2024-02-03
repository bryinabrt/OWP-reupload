package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Destinacija;

public interface DestinacijaDAO {

	public Destinacija findOne(Long id); 
	public Destinacija findOneByGrad(String grad);
	public List<Destinacija> findAll(); 
	public int save(Destinacija destinacija); 
	public int update(Destinacija destinacija); 
	public int delete(Long id); 
}