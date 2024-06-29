package com.ftn.PrviMavenVebProjekat.dao;

import com.ftn.PrviMavenVebProjekat.model.LoyaltyKartica;

public interface LoyaltyKarticaDAO {

    LoyaltyKartica getByKorId(Long id);

    int save(LoyaltyKartica loyaltyKartica);

    int update(LoyaltyKartica loyaltyKartica);

}
