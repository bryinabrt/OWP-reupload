package com.ftn.PrviMavenVebProjekat.dao;

import com.ftn.PrviMavenVebProjekat.model.LoyaltyKartica;
import com.ftn.PrviMavenVebProjekat.model.ZahtevKartice;

import java.util.List;

public interface ZahtevKarticeDAO {

    List<ZahtevKartice> getAll();

    ZahtevKartice getByKorId(Long id);

    int odbij(Long id);

    int naruciKarticu(ZahtevKartice zahtevKartice);
}
