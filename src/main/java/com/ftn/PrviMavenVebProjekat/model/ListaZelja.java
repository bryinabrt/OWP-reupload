package com.ftn.PrviMavenVebProjekat.model;

public class ListaZelja {
	private Long id;
	private Long idKorisnika;
	private Long idPutovanja;
	
	
	public ListaZelja(Long idKorisnika, Long idPutovanja) {
		super();
		this.idKorisnika = idKorisnika;
		this.idPutovanja = idPutovanja;
	}
	
	
	public ListaZelja(Long id, Long idKorisnika, Long idPutovanja) {
		super();
		this.id = id;
		this.idKorisnika = idKorisnika;
		this.idPutovanja = idPutovanja;
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
	public Long getIdPutovanja() {
		return idPutovanja;
	}
	public void setIdPutovanja(Long idPutovanja) {
		this.idPutovanja = idPutovanja;
	}
	
}