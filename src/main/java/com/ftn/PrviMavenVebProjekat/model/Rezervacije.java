package com.ftn.PrviMavenVebProjekat.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rezervacije {
	public Long id;
	public Long korisnikId;
	public Long zeljenoPutovanjeId;
	public Long zeljenaDestinacijaId;
	public Integer brojPutnika;
	public Double cenaRezervacije;
	public LocalDateTime datumRezervisanja;
	
	public Rezervacije(Long id, Long korisnikId, Long zeljenoPutovanjeId, Long zeljenaDestinacijaId,
			Integer brojPutnika, Double cenaRezervacije, LocalDateTime datumRezervisanja) {
		super();
		this.id = id;
		this.korisnikId = korisnikId;
		this.zeljenoPutovanjeId = zeljenoPutovanjeId;
		this.zeljenaDestinacijaId = zeljenaDestinacijaId;
		this.brojPutnika = brojPutnika;
		this.cenaRezervacije = cenaRezervacije;
		this.datumRezervisanja = datumRezervisanja;
	}
	
	
	
	public Rezervacije(Long korisnikId, Long zeljenoPutovanjeId, Long zeljenaDestinacijaId,
			Integer brojPutnika, Double cenaRezervacije, LocalDateTime datumRezervisanja) {
		super();
		this.korisnikId = korisnikId;
		this.zeljenoPutovanjeId = zeljenoPutovanjeId;
		this.zeljenaDestinacijaId = zeljenaDestinacijaId;
		this.brojPutnika = brojPutnika;
		this.cenaRezervacije = cenaRezervacije;
		this.datumRezervisanja = datumRezervisanja;
	}
	
	public Rezervacije(Long korisnikId, Long zeljenoPutovanjeId, Long zeljenaDestinacijaId,
			Integer brojPutnika, Double cenaRezervacije, String formattedDatumRezervisanja) {
		super();
		this.korisnikId = korisnikId;
		this.zeljenoPutovanjeId = zeljenoPutovanjeId;
		this.zeljenaDestinacijaId = zeljenaDestinacijaId;
		this.brojPutnika = brojPutnika;
		this.cenaRezervacije = cenaRezervacije;
		this.datumRezervisanja = datumRezervisanja;
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
	public Long getZeljenoPutovanjeId() {
		return zeljenoPutovanjeId;
	}
	public void setZeljenoPutovanjeId(Long zeljenoPutovanjeId) {
		this.zeljenoPutovanjeId = zeljenoPutovanjeId;
	}
	public Long getZeljenaDestinacijaId() {
		return zeljenaDestinacijaId;
	}
	public void setZeljenaDestinacijaId(Long zeljenaDestinacijaId) {
		this.zeljenaDestinacijaId = zeljenaDestinacijaId;
	}
	public Integer getBrojPutnika() {
		return brojPutnika;
	}
	public void setBrojPutnika(Integer brojPutnika) {
		this.brojPutnika = brojPutnika;
	}
	public Double getCenaRezervacije() {
		return cenaRezervacije;
	}
	public void setCenaRezervacije(Double cenaRezervacije) {
		this.cenaRezervacije = cenaRezervacije;
	}
	public LocalDateTime getDatumRezervisanja() {
		return datumRezervisanja;
	}
	public void setDatumRezervisanja(LocalDateTime datumRezervisanja) {
		this.datumRezervisanja = datumRezervisanja;
	}
	
    public String getFormattedDatumRezervisanja() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return datumRezervisanja.format(formatter);
    }

    // Setter for endDate with custom parsing
    public void setFormattedDatumRezervisanja(String formattedDatumRezervisanja) {
        this.datumRezervisanja = LocalDateTime.parse(formattedDatumRezervisanja, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
	
	
}
