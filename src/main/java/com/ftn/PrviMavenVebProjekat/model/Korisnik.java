package com.ftn.PrviMavenVebProjekat.model;

public class Korisnik {
	private Long id;
	private String korisnickoIme;
	private String lozinka;
	private String email;
	private String ime;
	private String prezime;
	private String datumRodjenja;
	private String adresa;
	private String brojTelefona;
	private String trenutnoVreme;
	private Uloga uloga;
	
	public Korisnik(Long id, String korisnickoIme, String lozinka, String email, String ime, String prezime, String datumRodjenja, String adresa, String brojTelefona, String trenutnoVreme, String uloga) {}
	


	public Korisnik(Long id, String korisnickoIme, String lozinka, String email, String ime, String prezime,
			String datumRodjenja, String adresa, String brojTelefona, String trenutnoVreme, Uloga uloga) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.trenutnoVreme = trenutnoVreme;
		this.uloga = uloga;
	}
	
	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime,
			String datumRodjenja, String adresa, String brojTelefona, Uloga uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.uloga = uloga;
	}
	


	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }
	

	public String getKorisnickoIme() { return korisnickoIme; }

	public void setKorisnickoIme(String korisnickoIme) { this.korisnickoIme = korisnickoIme; }
	

	public String getLozinka() { return lozinka; }

	public void setLozinka(String lozinka) { this.lozinka = lozinka; }
	

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }
	

	public String getIme() { return ime; }

	public void setIme(String ime) { this.ime = ime; }
	

	public String getPrezime() { return prezime; }

	public void setPrezime(String prezime) { this.prezime = prezime; }
	

	public String getDatumRodjenja() { return datumRodjenja; }

	public void setDatumRodjenja(String datumRodjenja) { this.datumRodjenja = datumRodjenja; }
	

	public String getAdresa() { return adresa; }

	public void setAdresa(String adresa) { this.adresa = adresa; }
	

	public String getBrojTelefona() { return brojTelefona; }

	public void setBrojTelefona(String brojTelefona) { this.brojTelefona = brojTelefona; }
	

	public String getTrenutnoVreme() { return trenutnoVreme; }

	public void setTrenutnoVreme(String trenutnoVreme) { this.trenutnoVreme = trenutnoVreme; }
	

	//public Uloga getUloga() { return uloga; }

	//public void setUloga(Uloga uloga) { this.uloga = uloga; }
	

	public String getUloga() {
	    return this.uloga.name();
	}

	public void setUloga(String uloga) {
	    this.uloga = Uloga.valueOf(uloga);
	}
	

	public String toFileString() {
		return this.getId() + ";" + this.getKorisnickoIme() + ";" + this.getLozinka() + ";" +
				this.getEmail() + ";" + this.getIme() + ";" + this.getPrezime() + ";" +
				this.getDatumRodjenja() + ";" + this.getAdresa() + ";" + this.getBrojTelefona() + ";" +
				this.getTrenutnoVreme() + ";" + this.getUloga();
	}
	
	
}
