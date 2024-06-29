package com.ftn.PrviMavenVebProjekat.dao;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.ListaZelja;

import java.util.List;

public interface ListaZeljaDAO {
    List<ListaZelja> getAll();

    int delete(Long id);

    int save(ListaZelja listaZelja);

    ListaZelja getOneById(Long id);

    List<ListaZelja> getAllByKorId(Long id);
}
