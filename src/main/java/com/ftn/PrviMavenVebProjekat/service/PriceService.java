package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Price;

public interface PriceService {
	Price findOne(Long id);
	Price findOneByDestinationId(Long destinationId);
	Price findOneByPutovanjeId(Long putovanjeId);
	List<Price> findAll();
	List<Price> findAllByDestinationId(Long destinationId); 
	List<Price> findAllByPutovanjeId(Long putovanjeId); 
	Price save(Price price); 
	Price update(Price price); 
	Price delete(Long id); 
}
