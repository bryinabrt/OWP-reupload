package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.PrviMavenVebProjekat.dao.PrevoznoSredstvoDAO;
import com.ftn.PrviMavenVebProjekat.model.PrevoznoSredstvo;
import com.ftn.PrviMavenVebProjekat.service.PrevoznoSredstvoService;

public class DatabasePrevoznoSredstvoServiceImpl implements PrevoznoSredstvoService{
	@Autowired
	private PrevoznoSredstvoDAO prevoznoSredstvoDAO;
	
	@Override
	public PrevoznoSredstvo findOne(Long id) {
		return prevoznoSredstvoDAO.findOne(id);
	}

	@Override
	public List<PrevoznoSredstvo> findAll() {
		return prevoznoSredstvoDAO.findAll();
	}

	@Override
	public PrevoznoSredstvo save(PrevoznoSredstvo prevoznaSredstva) {
		prevoznoSredstvoDAO.save(prevoznaSredstva);
		return prevoznaSredstva;
	}

	@Override
	public PrevoznoSredstvo update(PrevoznoSredstvo prevoznaSredstva) {
		prevoznoSredstvoDAO.update(prevoznaSredstva);
		return prevoznaSredstva;
	}

	@Override
	public PrevoznoSredstvo delete(Long id) {
		PrevoznoSredstvo prevoznaSredstva = prevoznoSredstvoDAO.findOne(id);
		prevoznoSredstvoDAO.delete(id);
		return prevoznaSredstva;
	}
}
