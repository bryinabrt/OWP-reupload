package com.ftn.PrviMavenVebProjekat.model;

public class ZahtevKartice {
    public Long id;

    public Long idKorisnika;

    public ZahtevKartice(Long id, Long idKorisnika) {
        super();
        this.id = id;
        this.idKorisnika = idKorisnika;
    }

    public ZahtevKartice(Long idKorisnika) {
        super();
        this.idKorisnika = idKorisnika;
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
}
