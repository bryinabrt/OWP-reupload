package com.ftn.PrviMavenVebProjekat.model;

import java.util.ArrayList;
import java.util.List;

public class PrevoznoSredstvo {
	private Long id;
	private String tipSredstva;
	private Integer brojSedista;
	private Long krajnjaDestinacija;
	private String opis;
	private List<Destinacija> destinacije;
	
	public PrevoznoSredstvo() {
		this.destinacije = new ArrayList<Destinacija>();
	}
	
	public PrevoznoSredstvo(Long id, String tipSredstva, Integer brojSedista, Long krajnjaDestinacija, String opis) {
		super();
		this.id = id;
		this.tipSredstva = tipSredstva;
		this.brojSedista = brojSedista;
		this.krajnjaDestinacija = krajnjaDestinacija;
		this.opis = opis;
		this.destinacije = new ArrayList<Destinacija>();
	}
	
	public PrevoznoSredstvo(String tipSredstva, Integer brojSedista, Long krajnjaDestinacija, String opis) {
		super();
		this.tipSredstva = tipSredstva;
		this.brojSedista = brojSedista;
		this.krajnjaDestinacija = krajnjaDestinacija;
		this.opis = opis;
		this.destinacije = new ArrayList<Destinacija>();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	public String getTipSredstva() {
		return tipSredstva;
	}

	public void setTipSredstva(String tipSredstva) {
		this.tipSredstva = tipSredstva;
	}
	

	public Integer getBrojSedista() {
		return brojSedista;
	}

	public void setBrojSedista(Integer brojSedista) {
		this.brojSedista = brojSedista;
	}

	
	public Long getKrajnjaDestinacija() {
		return krajnjaDestinacija;
	}

	public void setKrajnjaDestinacija(Long krajnjaDestinacija) {
		this.krajnjaDestinacija = krajnjaDestinacija;
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
	
	
	
}
