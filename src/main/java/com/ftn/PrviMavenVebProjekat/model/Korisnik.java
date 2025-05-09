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
	private String datumRegistracije;
	private Uloga uloga;
	private boolean blokiran = false;
	private boolean ulogovan = false;
	
	public Korisnik(Long id, String korisnickoIme, String lozinka, String email, String ime,
			String prezime, String datumRodjenja, String adresa, String brojTelefona, String datumRegistracije, String uloga) {}
	


	public Korisnik(Long id, String korisnickoIme, String lozinka, String email, String ime, String prezime,
			String datumRodjenja, String adresa, String brojTelefona, String datumRegistracije, Uloga uloga) {
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
		this.datumRegistracije = datumRegistracije;
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

	public Korisnik(Long id, String korisnickoIme, String lozinka, String email, String ime, String prezime,
					String datumRodjenja, String adresa, String brojTelefona, String datumRegistracije, Uloga uloga, Boolean blokiran) {
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
		this.datumRegistracije = datumRegistracije;
		this.uloga = uloga;
		this.blokiran = blokiran;
	}

	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime,
					String datumRodjenja, String adresa, String brojTelefona, Uloga uloga, Boolean blokiran) {
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
		this.blokiran = blokiran;
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
	

	public String getDatumRegistracije() { return datumRegistracije; }

	public void setDatumRegistracije(String datumRegistracije) { this.datumRegistracije = datumRegistracije; }
	

	//public Uloga getUloga() { return uloga; }

	//public void setUloga(Uloga uloga) { this.uloga = uloga; }
	

	public String getUloga() {
	    return this.uloga.name();
	}

	public void setUloga(String uloga) {
	    this.uloga = Uloga.valueOf(uloga);
	}
	
	public boolean isPrijavljen() {
		return ulogovan;
	}
	
	public boolean isAdmin() {
	    return uloga == Uloga.administrator;
	}
	
	public boolean isOrganizator() {
	    return uloga == Uloga.organizator;
	}
	
	public boolean isPutnik() {
	    return uloga == Uloga.putnik;
	}

	public void setUlogovan(boolean ulogovan) {
		this.ulogovan = ulogovan;
	}
	
	public boolean isBlokiran() {
		return blokiran;
	}
	
	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}
	
	
}
