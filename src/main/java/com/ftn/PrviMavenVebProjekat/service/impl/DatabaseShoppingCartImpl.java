package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.controller.ShoppingCartController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.ShoppingCartDAO;
import com.ftn.PrviMavenVebProjekat.model.ShoppingCart;
import com.ftn.PrviMavenVebProjekat.service.ShoppingCartService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

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

	@Override
	public ShoppingCart remove(Long cartId, Long korisnikId, HttpSession session) {
		List<ShoppingCart> cartinjo = (List<ShoppingCart>) session.getAttribute(ShoppingCartController.KOLICA_KEY);
		if (cartinjo != null) {
			// Find the shopping cart item to remove
			ShoppingCart cartToRemove = null;
			for (ShoppingCart cart : cartinjo) {
				if (cart.getId().equals(cartId)) {
					cartToRemove = cart;
					break;
				}
			}
			// Remove the item from the list if it exists
			if (cartToRemove != null) {
				cartinjo.remove(cartToRemove);
			}

			// Update the session attribute
			session.setAttribute(ShoppingCartController.KOLICA_KEY, cartinjo);
		}
		return null;
	}
}
