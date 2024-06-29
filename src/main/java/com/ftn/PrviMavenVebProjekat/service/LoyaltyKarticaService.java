package com.ftn.PrviMavenVebProjekat.service;

import com.ftn.PrviMavenVebProjekat.model.LoyaltyKartica;

public interface LoyaltyKarticaService {

    LoyaltyKartica getByKorId(Long id);

    LoyaltyKartica save(LoyaltyKartica loyaltyKartica);

    LoyaltyKartica update(LoyaltyKartica loyaltyKartica);

}
