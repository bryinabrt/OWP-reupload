package com.ftn.PrviMavenVebProjekat.model;

public class Usluga {
	private Long id;
	private String nazivUsluge;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getNazivUsluge() {
		return nazivUsluge;
	}
	public void setNazivUsluge(String nazivUsluge) {
		this.nazivUsluge = nazivUsluge;
	}
	
	
	public Usluga(Long id, String nazivUsluge) {
		super();
		this.id = id;
		this.nazivUsluge = nazivUsluge;
	}
	
	public Usluga(String nazivUsluge) {
		super();
		this.nazivUsluge = nazivUsluge;
	}
}
