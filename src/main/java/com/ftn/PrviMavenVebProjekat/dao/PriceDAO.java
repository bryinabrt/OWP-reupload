package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Price;

public interface PriceDAO {
	
	public Price findOne(Long id);
	public Price findOneByDestinationId(Long destinationId);
	public Price findOneByPutovanjeId(Long putovanjeId);
	public List<Price> findAll();
	public List<Price> findAllByDestinationId(Long destinationId);
	public List<Price> findAllByPutovanjeId(Long putovanjeId);
	public int save(Price price); 
	public int update(Price price); 
	public int delete(Long id); 

}
