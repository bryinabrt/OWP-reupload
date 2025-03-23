package com.ftn.PrviMavenVebProjekat.model;

public class LoyaltyKartica {
	private Long id;
	private Long idKorisnika;
	private Integer brojBodova;

	private Double potrosenNovac;
	
	public LoyaltyKartica(Long idKorisnika, Integer brojBodova) {
		super();
		this.idKorisnika = idKorisnika;
		this.brojBodova = brojBodova;
	}
	public LoyaltyKartica(Long id, Long idKorisnika, Integer brojBodova) {
		super();
		this.id = id;
		this.idKorisnika = idKorisnika;
		this.brojBodova = brojBodova;
	}

	public LoyaltyKartica(Long idKorisnika, Integer brojBodova, Double potrosenNovac) {
		super();
		this.id = id;
		this.idKorisnika = idKorisnika;
		this.brojBodova = brojBodova;
		this.potrosenNovac = potrosenNovac;
	}

	public LoyaltyKartica(Long id, Long idKorisnika, Integer brojBodova, Double potrosenNovac) {
		super();
		this.id = id;
		this.idKorisnika = idKorisnika;
		this.brojBodova = brojBodova;
		this.potrosenNovac = potrosenNovac;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdKorisnika() {
		return idKorisnika;
	}
	public void setIdKorisnika(Long idKorisnika) {
		this.idKorisnika = idKorisnika;
	}
	public Integer getBrojBodova() {
		return brojBodova;
	}
	public void setBrojBodova(Integer brojBodova) {
		this.brojBodova = brojBodova;
	}
	public Double getPotrosenNovac() {
		return potrosenNovac;
	}
	public void setPotrosenNovac(Double potrosenNovac) {
		this.potrosenNovac = potrosenNovac;
	}
}
