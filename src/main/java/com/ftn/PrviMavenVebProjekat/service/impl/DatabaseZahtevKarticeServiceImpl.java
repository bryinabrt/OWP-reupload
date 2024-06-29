package com.ftn.PrviMavenVebProjekat.service.impl;

import com.ftn.PrviMavenVebProjekat.dao.ZahtevKarticeDAO;
import com.ftn.PrviMavenVebProjekat.model.ZahtevKartice;
import com.ftn.PrviMavenVebProjekat.service.ZahtevKarticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseZahtevKarticeServiceImpl implements ZahtevKarticeService {

    @Autowired
    ZahtevKarticeDAO zahtevKarticeDAO;

    @Override
    public List<ZahtevKartice> getAll() {
        return zahtevKarticeDAO.getAll();
    }

    @Override
    public int odbij(Long id) {
        return zahtevKarticeDAO.odbij(id);
    }

    @Override
    public ZahtevKartice getByKorId(Long id) {
        return zahtevKarticeDAO.getByKorId(id);
    }

    @Override
    public ZahtevKartice naruciKarticu(ZahtevKartice zahtevKartice) {
        zahtevKarticeDAO.naruciKarticu(zahtevKartice);
        return zahtevKartice;
    }

}
