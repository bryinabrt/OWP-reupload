package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.UslugaDAO;
import com.ftn.PrviMavenVebProjekat.model.Usluga;
import com.ftn.PrviMavenVebProjekat.service.UslugaService;

@Service
public class DatabaseUslugaServiceImpl implements UslugaService{
	
	@Autowired
	private UslugaDAO uslugaDAO;
	
	@Override
	public Usluga findOne(Long id) {
		return uslugaDAO.findOne(id);
	}

	@Override
	public List<Usluga> findAll() {
		return uslugaDAO.findAll();
	}

	@Override
	public Usluga save(Usluga usluga) {
		uslugaDAO.save(usluga);
		return usluga;
	}

	@Override
	public Usluga update(Usluga usluga) {
		uslugaDAO.update(usluga);
		return usluga;
	}

	@Override
	public Usluga delete(Long id) {
		Usluga usluga = uslugaDAO.findOne(id);
		uslugaDAO.delete(id);
		return usluga;
	}

}
