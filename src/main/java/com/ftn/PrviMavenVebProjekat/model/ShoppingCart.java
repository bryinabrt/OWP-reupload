package com.ftn.PrviMavenVebProjekat.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	private Long id;
	private Long korisnikId;
	private Long rezervisanoPutovanjeId;
	private Integer brojPutnika;
	private Double ukupnaCena;
	private Long pricesId;
	private List<Putovanje> putovanja;
	private List<Price> prices;
	
	public ShoppingCart() {
		this.putovanja = new ArrayList<Putovanje>();
		this.prices = new ArrayList<Price>();
	}
	
	public ShoppingCart(Long id, Long korisnikId, Long rezervisanoPutovanjeId, Integer brojPutnika, Double ukupnaCena, Long pricesId) {
		super();
		this.id = id;
		this.korisnikId = korisnikId;
		this.rezervisanoPutovanjeId = rezervisanoPutovanjeId;
		this.brojPutnika = brojPutnika;
		this.ukupnaCena = ukupnaCena;
		this.pricesId = pricesId;
		this.putovanja = new ArrayList<Putovanje>();
		this.prices = new ArrayList<Price>();
	}
	
	
	public ShoppingCart(Long korisnikId, Long rezervisanoPutovanjeId, Integer brojPutnika, Double ukupnaCena, Long pricesId) {
		super();
		this.korisnikId = korisnikId;
		this.rezervisanoPutovanjeId = rezervisanoPutovanjeId;
		this.brojPutnika = brojPutnika;
		this.ukupnaCena = ukupnaCena;
		this.pricesId = pricesId;
		this.putovanja = new ArrayList<Putovanje>();
		this.prices = new ArrayList<Price>();
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getKorisnikId() {
		return korisnikId;
	}
	public void setKorisnikId(Long korisnikId) {
		this.korisnikId = korisnikId;
	}
	public Long getRezervisanoPutovanjeId() {
		return rezervisanoPutovanjeId;
	}
	public void setRezervisanoPutovanjeId(Long rezervisanoPutovanjeId) {
		this.rezervisanoPutovanjeId = rezervisanoPutovanjeId;
	}
	public Integer getBrojPutnika() {
		return brojPutnika;
	}
	public void setBrojPutnika(Integer brojPutnika) {
		this.brojPutnika = brojPutnika;
	}
	public Double getUkupnaCena() {
		return ukupnaCena;
	}
	public void setUkupnaCena(Double ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}
	
	public Long getPricesId() {
		return pricesId;
	}
	public void setPricesId(Long pricesId) {
		this.pricesId = pricesId;
	}

	public List<Putovanje> getPutovanja() {
		return putovanja;
	}

	public void setPutovanja(List<Putovanje> putovanja) {
		this.putovanja = putovanja;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	
	
	
}


