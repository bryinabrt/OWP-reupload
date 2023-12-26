package com.ftn.PrviMavenVebProjekat.model;

import java.util.ArrayList;
import java.util.List;

public class Putovanje {
	
	private Long id;
	private String sifraPutovanja;
	private Long idDestinacije;
	private String prevoznoSredstvo;
	private String smestajnaJedinica;
	private Kategorija kategorija;
	private String datumPolaska;
	private String datumPovratka;
	private int brojNocenja;
	private double cena;
	private List<Destinacija> destinacije;
	
	public Putovanje() {
		this.destinacije = new ArrayList<Destinacija>();
	}

	

	public Putovanje(Long id, String sifraPutovanja, Long idDestinacije, String prevoznoSredstvo, String smestajnaJedinica, Kategorija kategorija,
			String datumPolaska, String datumPovratka, int brojNocenja, double cena) {
		super();
		this.id = id;
		this.sifraPutovanja = sifraPutovanja;
		this.idDestinacije = idDestinacije;
		this.prevoznoSredstvo = prevoznoSredstvo;
		this.smestajnaJedinica = smestajnaJedinica;
		this.kategorija = kategorija;
		this.datumPolaska = datumPolaska;
		this.datumPovratka = datumPovratka;
		this.brojNocenja = brojNocenja;
		this.cena = cena;
		this.destinacije = new ArrayList<Destinacija>();
	}

	public Putovanje(String sifraPutovanja, Long idDestinacije, String prevoznoSredstvo, String smestajnaJedinica, Kategorija kategorija,
			String datumPolaska, String datumPovratka, int brojNocenja, double cena) {
		super();
		this.sifraPutovanja = sifraPutovanja;
		this.idDestinacije = idDestinacije;
		this.prevoznoSredstvo = prevoznoSredstvo;
		this.smestajnaJedinica = smestajnaJedinica;
		this.kategorija = kategorija;
		this.datumPolaska = datumPolaska;
		this.datumPovratka = datumPovratka;
		this.brojNocenja = brojNocenja;
		this.cena = cena;
		this.destinacije = new ArrayList<Destinacija>();
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getSifraPutovanja() {
		return sifraPutovanja;
	}

	public void setSifraPutovanja(String sifraPutovanja) {
		this.sifraPutovanja = sifraPutovanja;
	}



	public Long getIdDestinacije() {
		return idDestinacije;
	}

	public void setIdDestinacije(Long idDestinacije) {
		this.idDestinacije = idDestinacije;
	}



	public String getPrevoznoSredstvo() {
		return prevoznoSredstvo;
	}

	public void setPrevoznoSredstvo(String prevoznoSredstvo) {
		this.prevoznoSredstvo = prevoznoSredstvo;
	}
	
	
	
	public String getSmestajnaJedinica() {
		return smestajnaJedinica;
	}

	public void setSmestajnaJedinica(String smestajnaJedinica) {
		this.smestajnaJedinica = smestajnaJedinica;
	}



	public Kategorija getKategorija() {
		return kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}
	
	public String getKategorijaString() {
	    return this.kategorija.name();
	}

	public void setKategorija(String kategorija) {
	    this.kategorija = Kategorija.valueOf(kategorija);
	}



	public String getDatumPolaska() {
		return datumPolaska;
	}

	public void setDatumPolaska(String datumPolaska) {
		this.datumPolaska = datumPolaska;
	}



	public String getDatumPovratka() {
		return datumPovratka;
	}

	public void setDatumPovratka(String datumPovratka) {
		this.datumPovratka = datumPovratka;
	}



	public int getBrojNocenja() {
		return brojNocenja;
	}

	public void setBrojNocenja(int brojNocenja) {
		this.brojNocenja = brojNocenja;
	}



	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}



	public List<Destinacija> getDestinacije() {
		return destinacije;
	}

	public void setDestinacije(List<Destinacija> destinacije) {
		this.destinacije = destinacije;
	}

}
