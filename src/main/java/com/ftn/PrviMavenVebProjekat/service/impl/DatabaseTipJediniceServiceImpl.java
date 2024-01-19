package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.TipJediniceDAO;
import com.ftn.PrviMavenVebProjekat.model.TipJedinice;
import com.ftn.PrviMavenVebProjekat.service.TipJediniceService;

@Service
public class DatabaseTipJediniceServiceImpl implements TipJediniceService {
	
	@Autowired
	private TipJediniceDAO tipJediniceDAO;
	
	@Override
	public TipJedinice findOne(Long id) {
		return tipJediniceDAO.findOne(id);
	}

	@Override
	public List<TipJedinice> findAll() {
		return tipJediniceDAO.findAll();
	}

	@Override
	public TipJedinice save(TipJedinice tipJedinice) {
		tipJediniceDAO.save(tipJedinice);
		return tipJedinice;
	}

	@Override
	public TipJedinice update(TipJedinice tipJedinice) {
		tipJediniceDAO.update(tipJedinice);
		return tipJedinice;
	}

	@Override
	public TipJedinice delete(Long id) {
		TipJedinice tipJedinice = tipJediniceDAO.findOne(id);
		tipJediniceDAO.delete(id);
		return tipJedinice;
	}

}
