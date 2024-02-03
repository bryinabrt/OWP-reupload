package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.PriceDAO;
import com.ftn.PrviMavenVebProjekat.model.Price;
import com.ftn.PrviMavenVebProjekat.service.PriceService;

@Service
public class DatabasePriceServiceImpl implements PriceService{
	
	@Autowired
	private PriceDAO priceDAO;
	
	@Override
	public Price findOne(Long id) {
		return priceDAO.findOne(id);
	}
	
	@Override
	public Price findOneByDestinationId(Long destinationId) {
		return priceDAO.findOneByDestinationId(destinationId);
	}
	
	@Override
	public Price findOneByPutovanjeId(Long putovanjeId) {
		return priceDAO.findOneByPutovanjeId(putovanjeId);
	}

	@Override
	public List<Price> findAll() {
		return priceDAO.findAll();
	}
	
	@Override
	public List<Price> findAllByDestinationId(Long destinationId) {
		return priceDAO.findAllByDestinationId(destinationId);
	}
	
	@Override
	public List<Price> findAllByPutovanjeId(Long putovanjeId) {
		return priceDAO.findAllByPutovanjeId(putovanjeId);
	}

	@Override
	public Price save(Price price) {
		priceDAO.save(price);
		return price;
	}

	@Override
	public Price update(Price price) {
		priceDAO.update(price);
		return price;
	}

	@Override
	public Price delete(Long id) {
		Price price = priceDAO.findOne(id);
		priceDAO.delete(id);
		return price;
	}

}
