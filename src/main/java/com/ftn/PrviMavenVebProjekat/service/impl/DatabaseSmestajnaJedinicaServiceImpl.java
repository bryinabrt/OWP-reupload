package com.ftn.PrviMavenVebProjekat.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.SmestajnaJedinicaDAO;
import com.ftn.PrviMavenVebProjekat.model.PrevoznoSredstvo;
import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;
import com.ftn.PrviMavenVebProjekat.service.SmestajnaJedinicaService;

@Service
public class DatabaseSmestajnaJedinicaServiceImpl implements SmestajnaJedinicaService {

	@Autowired
	private SmestajnaJedinicaDAO smestajnaJedinicaDAO;
	
	@Override
	public SmestajnaJedinica findOne(Long id) {
		return smestajnaJedinicaDAO.findOne(id);
	}
	
	@Override
	public List<SmestajnaJedinica> findOneByDestinacija(Long idDestinacijeSmestaja) {
		return smestajnaJedinicaDAO.findOneByDestinacija(idDestinacijeSmestaja);
	}
	
	@Override
	public SmestajnaJedinica findOneByNaziv(String nazivJedinice) {
		return smestajnaJedinicaDAO.findOneByNaziv(nazivJedinice);
	}

	@Override
	public List<SmestajnaJedinica> findAll() {
		return smestajnaJedinicaDAO.findAll();
	}

	@Override
	public SmestajnaJedinica save(SmestajnaJedinica smestajnaJedinica) {
		smestajnaJedinicaDAO.save(smestajnaJedinica);
		return smestajnaJedinica;
	}

	@Override
	public SmestajnaJedinica update(SmestajnaJedinica smestajnaJedinica) {
		smestajnaJedinicaDAO.update(smestajnaJedinica);
		return smestajnaJedinica;
	}

	@Override
	public SmestajnaJedinica delete(Long id) {
		SmestajnaJedinica smestajnaJedinica = smestajnaJedinicaDAO.findOne(id);
		smestajnaJedinicaDAO.delete(id);
		return smestajnaJedinica;
	}
}
