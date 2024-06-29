package com.ftn.PrviMavenVebProjekat.service;

import com.ftn.PrviMavenVebProjekat.model.ZahtevKartice;

import java.util.List;

public interface ZahtevKarticeService {

    List<ZahtevKartice> getAll();

    ZahtevKartice getByKorId(Long id);

    int odbij(Long id);

    ZahtevKartice naruciKarticu(ZahtevKartice zahtevKartice);
}
