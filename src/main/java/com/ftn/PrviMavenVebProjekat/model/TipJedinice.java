package com.ftn.PrviMavenVebProjekat.model;

public class TipJedinice {
	private Long id;
	private String nazivTipaJedinice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getNazivTipaJedinice() {
		return nazivTipaJedinice;
	}
	public void setNazivTipaJedinice(String nazivTipaJedinice) {
		this.nazivTipaJedinice = nazivTipaJedinice;
	}
	
	
	public TipJedinice(Long id, String nazivTipaJedinice) {
		super();
		this.id = id;
		this.nazivTipaJedinice = nazivTipaJedinice;
	}
	
	public TipJedinice(String nazivTipaJedinice) {
		super();
		this.nazivTipaJedinice = nazivTipaJedinice;
	}
}
