package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.ShoppingCart;

import javax.servlet.http.HttpSession;

public interface ShoppingCartService {
	ShoppingCart findOne(Long id); 
	
	List<ShoppingCart> findAllByKorisnik(Long korisnikId); 
	
	ShoppingCart save(ShoppingCart shoppingCart); 
	
	ShoppingCart update(ShoppingCart shoppingCart); 
	
	ShoppingCart delete(Long id);

	ShoppingCart remove(Long cartId, Long korisnikId, HttpSession session);
}
