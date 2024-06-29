package com.ftn.PrviMavenVebProjekat.service.impl;

import com.ftn.PrviMavenVebProjekat.dao.ListaZeljaDAO;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.ListaZelja;
import com.ftn.PrviMavenVebProjekat.service.ListaZeljaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DatabaseListaZeljaServiceImpl implements ListaZeljaService {

    @Autowired
    public ListaZeljaDAO listaZeljaDAO;
    @Override
    public List<ListaZelja> getAll() {
        return listaZeljaDAO.getAll();
    }
    @Override
    public  List<ListaZelja> getAllByKorId(Long id) {
        return listaZeljaDAO.getAllByKorId(id);
    }

    @Override
    public ListaZelja getOneById(Long id) {
        return listaZeljaDAO.getOneById(id);
    }

    @Override
    public ListaZelja delete(Long id) {
        System.out.println("DATABASEIMPL: "+id);
        ListaZelja listaZelja = listaZeljaDAO.getOneById(id);
        listaZeljaDAO.delete(id);
        return listaZelja;
    }

    @Override
    public ListaZelja save(ListaZelja listaZelja) {
        listaZeljaDAO.save(listaZelja);
        return listaZelja;
    }

}
