package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.ShoppingCart;

public interface ShoppingCartService {
	ShoppingCart findOne(Long id); 
	
	List<ShoppingCart> findAllByKorisnik(Long korisnikId); 
	
	ShoppingCart save(ShoppingCart shoppingCart); 
	
	ShoppingCart update(ShoppingCart shoppingCart); 
	
	ShoppingCart delete(Long id); 
}
