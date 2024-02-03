package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.ShoppingCartDAO;
import com.ftn.PrviMavenVebProjekat.model.ShoppingCart;
import com.ftn.PrviMavenVebProjekat.service.ShoppingCartService;

@Service
public class DatabaseShoppingCartImpl implements ShoppingCartService{
	
	@Autowired
	private ShoppingCartDAO shoppingCartDAO;
	
	@Override
	public ShoppingCart findOne(Long id) {
		return shoppingCartDAO.findOne(id);
	}
	

	@Override
	public List<ShoppingCart> findAllByKorisnik(Long korisnikId) {
		return shoppingCartDAO.findAllByKorisnik(korisnikId);
	}

	@Override
	public ShoppingCart save(ShoppingCart shoppingCart) {
		shoppingCartDAO.save(shoppingCart);
		return shoppingCart;
	}

	@Override
	public ShoppingCart update(ShoppingCart shoppingCart) {
		shoppingCartDAO.update(shoppingCart);
		return shoppingCart;
	}

	@Override
	public ShoppingCart delete(Long id) {
		ShoppingCart shoppingCart = shoppingCartDAO.findOne(id);
		shoppingCartDAO.delete(id);
		return shoppingCart;
	}
}
