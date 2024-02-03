package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.ShoppingCart;

public interface ShoppingCartDAO {
	public ShoppingCart findOne(Long id);
	public List<ShoppingCart> findAllByKorisnik(Long korisnikId); 
	public int save(ShoppingCart shoppingCart); 
	public int update(ShoppingCart shoppingCart); 
	public int delete(Long id); 
}
