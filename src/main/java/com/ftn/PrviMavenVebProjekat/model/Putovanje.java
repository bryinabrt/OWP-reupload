package com.ftn.PrviMavenVebProjekat.model;

import java.util.ArrayList;
import java.util.List;

public class Putovanje {
	
	private Long id;
	private String sifraPutovanja;
	private Long idDestinacije;
	private Long idPrevoznoSredstvo;
	private Long idSmestajnaJedinica;
	private Kategorija kategorija;
	private String datumPolaska;
	private String datumPovratka;
	private int brojNocenja;
	private String slika;
	private List<Destinacija> destinacije;
	private List<PrevoznoSredstvo> prevoznaSredstva;
	private List<SmestajnaJedinica> smestajneJedinice;
	private List<Price> prices;
	
	public Putovanje() {
		this.destinacije = new ArrayList<Destinacija>();
		this.prevoznaSredstva = new ArrayList<PrevoznoSredstvo>();
		this.smestajneJedinice = new ArrayList<SmestajnaJedinica>();
		this.prices = new ArrayList<Price>();
	}

	public Putovanje(Long id, String sifraPutovanja, Long idDestinacije, Long idPrevoznoSredstvo,
			Long idSmestajnaJedinica, Kategorija kategorija, String datumPolaska, String datumPovratka, int brojNocenja, String slika) {
		super();
		this.id = id;
		this.sifraPutovanja = sifraPutovanja;
		this.idDestinacije = idDestinacije;
		this.idPrevoznoSredstvo = idPrevoznoSredstvo;
		this.idSmestajnaJedinica = idSmestajnaJedinica;
		this.kategorija = kategorija;
		this.datumPolaska = datumPolaska;
		this.datumPovratka = datumPovratka;
		this.brojNocenja = brojNocenja;
		this.slika = slika;
		this.destinacije = new ArrayList<Destinacija>();
		this.prevoznaSredstva = new ArrayList<PrevoznoSredstvo>();
		this.smestajneJedinice = new ArrayList<SmestajnaJedinica>();
		this.prices = new ArrayList<Price>();
	}
	
	public Putovanje(String sifraPutovanja, Long idDestinacije, Long idPrevoznoSredstvo,
			Long idSmestajnaJedinica, Kategorija kategorija, String datumPolaska, String datumPovratka, int brojNocenja, String slika) {
		super();
		this.sifraPutovanja = sifraPutovanja;
		this.idDestinacije = idDestinacije;
		this.idPrevoznoSredstvo = idPrevoznoSredstvo;
		this.idSmestajnaJedinica = idSmestajnaJedinica;
		this.kategorija = kategorija;
		this.datumPolaska = datumPolaska;
		this.datumPovratka = datumPovratka;
		this.brojNocenja = brojNocenja;
		this.slika = slika;
		this.destinacije = new ArrayList<Destinacija>();
		this.prevoznaSredstva = new ArrayList<PrevoznoSredstvo>();
		this.smestajneJedinice = new ArrayList<SmestajnaJedinica>();
		this.prices = new ArrayList<Price>();
	}

	public Putovanje(String sifraPutovanja, Long idDestinacije, Long idPrevoznoSredstvo, Long idSmestajnaJedinica,
			Kategorija kategorija, int brojNocenja, String slika) {
		super();
		this.sifraPutovanja = sifraPutovanja;
		this.idDestinacije = idDestinacije;
		this.idPrevoznoSredstvo = idPrevoznoSredstvo;
		this.idSmestajnaJedinica = idSmestajnaJedinica;
		this.kategorija = kategorija;
		this.brojNocenja = brojNocenja;
		this.slika = slika;
		this.destinacije = new ArrayList<Destinacija>();
		this.prevoznaSredstva = new ArrayList<PrevoznoSredstvo>();
		this.smestajneJedinice = new ArrayList<SmestajnaJedinica>();
		this.prices = new ArrayList<Price>();	
	}
	
	public Putovanje(Long id, String sifraPutovanja, Long idDestinacije, Long idPrevoznoSredstvo, Long idSmestajnaJedinica,
			Kategorija kategorija, int brojNocenja, String slika) {
		super();
		this.id = id;
		this.sifraPutovanja = sifraPutovanja;
		this.idDestinacije = idDestinacije;
		this.idPrevoznoSredstvo = idPrevoznoSredstvo;
		this.idSmestajnaJedinica = idSmestajnaJedinica;
		this.kategorija = kategorija;
		this.brojNocenja = brojNocenja;
		this.slika = slika;
		this.destinacije = new ArrayList<Destinacija>();
		this.prevoznaSredstva = new ArrayList<PrevoznoSredstvo>();
		this.smestajneJedinice = new ArrayList<SmestajnaJedinica>();
		this.prices = new ArrayList<Price>();	
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

	public Long getIdPrevoznoSredstvo() {
		return idPrevoznoSredstvo;
	}

	public void setIdPrevoznoSredstvo(Long idPrevoznoSredstvo) {
		this.idPrevoznoSredstvo = idPrevoznoSredstvo;
	}

	public Long getIdSmestajnaJedinica() {
		return idSmestajnaJedinica;
	}

	public void setIdSmestajnaJedinica(Long idSmestajnaJedinica) {
		this.idSmestajnaJedinica = idSmestajnaJedinica;
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

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public List<Destinacija> getDestinacije() {
		return destinacije;
	}

	public void setDestinacije(List<Destinacija> destinacije) {
		this.destinacije = destinacije;
	}

	public List<PrevoznoSredstvo> getPrevoznaSredstva() {
		return prevoznaSredstva;
	}

	public void setPrevoznaSredstva(List<PrevoznoSredstvo> prevoznaSredstva) {
		this.prevoznaSredstva = prevoznaSredstva;
	}

	public List<SmestajnaJedinica> getSmestajneJedinice() {
		return smestajneJedinice;
	}

	public void setSmestajneJedinice(List<SmestajnaJedinica> smestajneJedinice) {
		this.smestajneJedinice = smestajneJedinice;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	
	
	

}
