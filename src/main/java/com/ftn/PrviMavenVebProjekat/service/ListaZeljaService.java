package com.ftn.PrviMavenVebProjekat.service;

import com.ftn.PrviMavenVebProjekat.model.ListaZelja;

import java.util.List;

public interface ListaZeljaService {
    List<ListaZelja> getAll();

    ListaZelja delete(Long id);

    ListaZelja save(ListaZelja listaZelja);

    ListaZelja getOneById(Long id);

    List<ListaZelja> getAllByKorId(Long id);
}
