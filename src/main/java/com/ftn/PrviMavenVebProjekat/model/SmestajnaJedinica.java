package com.ftn.PrviMavenVebProjekat.model;

import java.util.ArrayList;
import java.util.List;
import com.ftn.PrviMavenVebProjekat.model.TipJedinice;
import com.ftn.PrviMavenVebProjekat.model.Destinacija;

public class SmestajnaJedinica {
	private Long id;
	private String nazivJedinice;
	private Long idTipJedinice;
	private Integer kapacitet;
	private Long idDestinacijeSmestaja;
	private Double recenzija;
	private Boolean uslugaWifi;
	private Boolean uslugaKupatilo;
	private Boolean uslugaTv;
	private String opis;
	private List<Destinacija> destinacije;
	private List<TipJedinice> tipJedinica;
	
	public SmestajnaJedinica() {
		this.tipJedinica = new ArrayList<TipJedinice>();
		this.destinacije = new ArrayList<Destinacija>();
	}
	
	
	

	public SmestajnaJedinica(String nazivJedinice, Long idTipJedinice, Integer kapacitet, Long idDestinacijeSmestaja, Double recenzija,
			Boolean uslugaWifi, Boolean uslugaKupatilo, Boolean uslugaTv, String opis) {
		super();
		this.nazivJedinice = nazivJedinice;
		this.idTipJedinice = idTipJedinice;
		this.kapacitet = kapacitet;
		this.idDestinacijeSmestaja = idDestinacijeSmestaja;
		this.recenzija = recenzija;
		this.uslugaWifi = uslugaWifi;
		this.uslugaKupatilo = uslugaKupatilo;
		this.uslugaTv = uslugaTv;
		this.opis = opis;
		this.destinacije = new ArrayList<Destinacija>();
		this.tipJedinica = new ArrayList<TipJedinice>();
	}
	
	
	public SmestajnaJedinica(String nazivJedinice, Long idTipJedinice, Integer kapacitet, Long idDestinacijeSmestaja, Double recenzija,
			Boolean uslugaWifi, Boolean uslugaKupatilo, Boolean uslugaTv) {
		super();
		this.nazivJedinice = nazivJedinice;
		this.idTipJedinice = idTipJedinice;
		this.kapacitet = kapacitet;
		this.idDestinacijeSmestaja = idDestinacijeSmestaja;
		this.recenzija = recenzija;
		this.uslugaWifi = uslugaWifi;
		this.uslugaKupatilo = uslugaKupatilo;
		this.uslugaTv = uslugaTv;
		this.destinacije = new ArrayList<Destinacija>();
		this.tipJedinica = new ArrayList<TipJedinice>();
	}




	public SmestajnaJedinica(Long id, String nazivJedinice, Long idTipJedinice, Integer kapacitet, Long idDestinacijeSmestaja,
			Double recenzija, Boolean uslugaWifi, Boolean uslugaKupatilo, Boolean uslugaTv, String opis) {
		super();
		this.id = id;
		this.nazivJedinice = nazivJedinice;
		this.idTipJedinice = idTipJedinice;
		this.kapacitet = kapacitet;
		this.idDestinacijeSmestaja = idDestinacijeSmestaja;
		this.recenzija = recenzija;
		this.uslugaWifi = uslugaWifi;
		this.uslugaKupatilo = uslugaKupatilo;
		this.uslugaTv = uslugaTv;
		this.opis = opis;
		this.destinacije = new ArrayList<Destinacija>();
		this.tipJedinica = new ArrayList<TipJedinice>();
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getNazivJedinice() {
		return nazivJedinice;
	}




	public void setNazivJedinice(String nazivJedinice) {
		this.nazivJedinice = nazivJedinice;
	}




	public Long getIdTipJedinice() {
		return idTipJedinice;
	}




	public void setIdTipJedinice(Long idTipJedinice) {
		this.idTipJedinice = idTipJedinice;
	}




	public Integer getKapacitet() {
		return kapacitet;
	}




	public void setKapacitet(Integer kapacitet) {
		this.kapacitet = kapacitet;
	}




	public Long getIdDestinacijeSmestaja() {
		return idDestinacijeSmestaja;
	}




	public void setIdDestinacijeSmestaja(Long idDestinacijeSmestaja) {
		this.idDestinacijeSmestaja = idDestinacijeSmestaja;
	}




	public Double getRecenzija() {
		return recenzija;
	}




	public void setRecenzija(Double recenzija) {
		this.recenzija = recenzija;
	}

	
	


	public Boolean getUslugaWifi() {
		return uslugaWifi;
	}




	public void setUslugaWifi(Boolean uslugaWifi) {
		this.uslugaWifi = uslugaWifi;
	}




	public Boolean getUslugaKupatilo() {
		return uslugaKupatilo;
	}




	public void setUslugaKupatilo(Boolean uslugaKupatilo) {
		this.uslugaKupatilo = uslugaKupatilo;
	}




	public Boolean getUslugaTv() {
		return uslugaTv;
	}




	public void setUslugaTv(Boolean uslugaTv) {
		this.uslugaTv = uslugaTv;
	}




	public String getOpis() {
		return opis;
	}




	public void setOpis(String opis) {
		this.opis = opis;
	}




	public List<Destinacija> getDestinacije() {
		return destinacije;
	}




	public void setDestinacije(List<Destinacija> destinacije) {
		this.destinacije = destinacije;
	}




	public List<TipJedinice> getTipJedinica() {
		return tipJedinica;
	}




	public void setTipJedinica(List<TipJedinice> tipJedinica) {
		this.tipJedinica = tipJedinica;
	}

	
	
	public void getSve(List<TipJedinice> tipJedinica, List<Destinacija> destinacije) {
		this.tipJedinica = tipJedinica;
		this.destinacije = destinacije;
	}

	
}
