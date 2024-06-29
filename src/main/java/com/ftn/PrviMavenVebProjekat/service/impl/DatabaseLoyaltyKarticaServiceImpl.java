package com.ftn.PrviMavenVebProjekat.service.impl;

import com.ftn.PrviMavenVebProjekat.dao.LoyaltyKarticaDAO;
import com.ftn.PrviMavenVebProjekat.model.LoyaltyKartica;
import com.ftn.PrviMavenVebProjekat.service.LoyaltyKarticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseLoyaltyKarticaServiceImpl implements LoyaltyKarticaService {

    @Autowired
    private LoyaltyKarticaDAO loyaltyKarticaDAO;

    @Override
    public LoyaltyKartica getByKorId(Long id) {
        return loyaltyKarticaDAO.getByKorId(id);
    }

    @Override
    public LoyaltyKartica save(LoyaltyKartica loyaltyKartica) {
        loyaltyKarticaDAO.save(loyaltyKartica);
        return loyaltyKartica;
    }

    @Override
    public  LoyaltyKartica update(LoyaltyKartica loyaltyKartica) {
        loyaltyKarticaDAO.update(loyaltyKartica);
        return loyaltyKartica;
    }

}
