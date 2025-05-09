package com.ftn.PrviMavenVebProjekat.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	private Destinacija destinacija;
	private PrevoznoSredstvo prevoznoSredstvo;
	private SmestajnaJedinica smestajnaJedinica;
	private List<Price> prices;


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
		this.destinacija = destinacija;
		this.prevoznoSredstvo = prevoznoSredstvo;
		this.smestajnaJedinica = smestajnaJedinica;
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
		this.destinacija = destinacija;
		this.prevoznoSredstvo = prevoznoSredstvo;
		this.smestajnaJedinica = smestajnaJedinica;
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
		this.destinacija = destinacija;
		this.prevoznoSredstvo = prevoznoSredstvo;
		this.smestajnaJedinica = smestajnaJedinica;
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
		this.destinacija = destinacija;
		this.prevoznoSredstvo = prevoznoSredstvo;
		this.smestajnaJedinica = smestajnaJedinica;
		this.prices = new ArrayList<Price>();	
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Putovanje putovanje = (Putovanje) o;
		return Objects.equals(id, putovanje.id) &&
				Objects.equals(sifraPutovanja, putovanje.sifraPutovanja);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, sifraPutovanja);
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

	public Destinacija getDestinacija() {
		return destinacija;
	}

	public void setDestinacija(Destinacija destinacija) {
		this.destinacija = destinacija;
	}

	public PrevoznoSredstvo getPrevoznoSredstvo() {
		return prevoznoSredstvo;
	}

	public void setPrevoznoSredstvo(PrevoznoSredstvo prevoznoSredstvo) {
		this.prevoznoSredstvo = prevoznoSredstvo;
	}

	public SmestajnaJedinica getSmestajnaJedinica() {
		return smestajnaJedinica;
	}

	public void setSmestajnaJedinica(SmestajnaJedinica smestajnaJedinica) {
		this.smestajnaJedinica = smestajnaJedinica;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	
	
	

}
