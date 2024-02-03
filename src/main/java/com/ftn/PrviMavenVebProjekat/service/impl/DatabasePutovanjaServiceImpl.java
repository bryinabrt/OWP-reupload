package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.PutovanjeDAO;
import com.ftn.PrviMavenVebProjekat.model.Putovanje;
import com.ftn.PrviMavenVebProjekat.service.PutovanjeService;

@Service
public class DatabasePutovanjaServiceImpl implements PutovanjeService{
	
	@Autowired
	private PutovanjeDAO putovanjeDAO;
	
	@Override
	public Putovanje findOne(Long id) {
		return putovanjeDAO.findOne(id);
	}
	
	@Override
	public Putovanje findOneBySifra(String sifraPutovanja) {
		return putovanjeDAO.findOneBySifra(sifraPutovanja);
	}

	@Override
	public List<Putovanje> findAll() {
		return putovanjeDAO.findAll();
	}

	@Override
	public Putovanje save(Putovanje putovanje) {
		putovanjeDAO.save(putovanje);
		return putovanje;
	}

	@Override
	public Putovanje update(Putovanje putovanje) {
		putovanjeDAO.update(putovanje);
		return putovanje;
	}

	@Override
	public Putovanje delete(Long id) {
		Putovanje putovanje = putovanjeDAO.findOne(id);
		putovanjeDAO.delete(id);
		return putovanje;
	}
}
