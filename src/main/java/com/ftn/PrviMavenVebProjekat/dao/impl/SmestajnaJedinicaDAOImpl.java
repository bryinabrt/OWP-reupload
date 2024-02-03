package com.ftn.PrviMavenVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.PrviMavenVebProjekat.dao.SmestajnaJedinicaDAO;
import com.ftn.PrviMavenVebProjekat.model.Destinacija;
import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;
import com.ftn.PrviMavenVebProjekat.model.TipJedinice;

@Repository
public class SmestajnaJedinicaDAOImpl implements SmestajnaJedinicaDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class SmestajnaJedinicaRowCallbackHandler implements RowCallbackHandler {
		
		private Map<Long, SmestajnaJedinica> smestajneJedinice = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String nazivJedinice = resultSet.getString(index++);
			Long idTipJedinice = resultSet.getLong(index++);
			Integer kapacitet = resultSet.getInt(index++);
			Long idDestinacijeSmestaja = resultSet.getLong(index++);
			Double recenzija = resultSet.getDouble(index++);
			Boolean uslugaWifi = resultSet.getBoolean(index++);
		    Boolean uslugaKupatilo = resultSet.getBoolean(index++);
		    Boolean uslugaTv = resultSet.getBoolean(index++);
			String opis = resultSet.getString(index++);
			System.out.println("wifi: " + uslugaWifi + " kupatilo: " + uslugaKupatilo + " tv: " + uslugaTv);
			
			
			String nazivTipaJedinice = resultSet.getString(index++);
			TipJedinice tipJedinice = new TipJedinice(nazivTipaJedinice);
			
			
			String grad = resultSet.getString(index++);
			String drzava = resultSet.getString(index++);
			String kontinent = resultSet.getString(index++);
			Destinacija destinacija = new Destinacija(grad, drzava, kontinent);
			
			
			SmestajnaJedinica smestajnaJedinica = smestajneJedinice.get(id);
			if (smestajnaJedinica == null) {
				List<TipJedinice> tipJedinica = new ArrayList<TipJedinice>();
				List<Destinacija> destinacije = new ArrayList<Destinacija>();
				destinacije.add(destinacija);
				tipJedinica.add(tipJedinice);
				smestajnaJedinica = new SmestajnaJedinica(id, nazivJedinice, idTipJedinice, kapacitet, idDestinacijeSmestaja, recenzija,
						uslugaWifi, uslugaKupatilo, uslugaTv, opis);
				smestajnaJedinica.setTipJedinica(tipJedinica);
				smestajnaJedinica.setDestinacije(destinacije);
				smestajneJedinice.put(smestajnaJedinica.getId(), smestajnaJedinica); // dodavanje u kolekciju
			} else {
				// ako putovanje vec postoji u kolekciji samo mu dodaj destinaciju ako ga on vec nema
				// proveri da li u listi destinacija imas destinaciju koju trazis
				boolean found = false;
				for (Destinacija d : smestajnaJedinica.getDestinacije()) {
					if (d.getId() == idDestinacijeSmestaja) {
						found = true;
						break;
					}
				}
				// ako ga nemas dodaj!
				if (!found) {
					smestajnaJedinica.getDestinacije().add(destinacija);
				}
				
				for (TipJedinice t : smestajnaJedinica.getTipJedinica()) {
					if (t.getId() == idTipJedinice) {
						found = true;
						break;
					}
				}
				
				if (!found) {
					smestajnaJedinica.getTipJedinica().add(tipJedinice);
				}
			}
		}
		
		public List<SmestajnaJedinica> getSve() {
			return new ArrayList<>(smestajneJedinice.values());
		}
		
		/*
		public List<SmestajnaJedinica> getTipJedinice() {
			return new ArrayList<>(smestajneJedinice.values());
		}
		
		public List<SmestajnaJedinica> getDestinacije() {
			return new ArrayList<>(smestajneJedinice.values());
		}
		
		public List<SmestajnaJedinica> getUsluge() {
			return new ArrayList<>(smestajneJedinice.values());
		}*/

	}

	@Override
	public SmestajnaJedinica findOne(Long id) {
		String sql = 
				"select s.id, s.nazivJedinice, s.idTipJedinice, s.kapacitet, s.idDestinacijeSmestaja, s.recenzija, s.uslugaWifi, s.uslugaKupatilo, "
				+ "s.uslugaTv, s.opis, "
				+ "t.id, t.nazivTipaJedinice, d.id, d.grad, d.drzava, d.kontinent "
				+ "from smestajnajedinica s "
						+ "left join tipjedinice t on t.id = s.idTipJedinice "
						+ "left join destinacije d on d.id = s.idDestinacijeSmestaja "
						+ "WHERE s.id = ? " + 
				"ORDER BY s.id";

		SmestajnaJedinicaRowCallbackHandler rowCallbackHandler = new SmestajnaJedinicaRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getSve().get(0);
	}
	
	@Override
	public List<SmestajnaJedinica> findOneByDestinacija(Long idDestinacijeSmestaja) {
		String sql = 
				"select s.id, s.nazivJedinice, s.idTipJedinice, s.kapacitet, s.idDestinacijeSmestaja, s.recenzija, s.uslugaWifi, s.uslugaKupatilo, "
				+ "s.uslugaTv, s.opis, "
				+ "t.id, t.nazivTipaJedinice, d.id, d.grad, d.drzava, d.kontinent "
				+ "from smestajnajedinica s "
						+ "left join tipjedinice t on t.id = s.idTipJedinice "
						+ "left join destinacije d on d.id = s.idDestinacijeSmestaja "
						+ "WHERE s.idDestinacijeSmestaja = ? " + 
				"ORDER BY s.id";

		SmestajnaJedinicaRowCallbackHandler rowCallbackHandler = new SmestajnaJedinicaRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, idDestinacijeSmestaja);

		return rowCallbackHandler.getSve();
	}
	
	@Override
	public SmestajnaJedinica findOneByNaziv(String nazivJedinice) {
		String sql = 
				"select s.id, s.nazivJedinice, s.idTipJedinice, s.kapacitet, s.idDestinacijeSmestaja, s.recenzija, s.uslugaWifi, s.uslugaKupatilo, "
				+ "s.uslugaTv, s.opis, "
				+ "t.id, t.nazivTipaJedinice, d.id, d.grad, d.drzava, d.kontinent "
				+ "from smestajnajedinica s "
						+ "left join tipjedinice t on t.id = s.idTipJedinice "
						+ "left join destinacije d on d.id = s.idDestinacijeSmestaja "
						+ "WHERE s.nazivJedinice = ? " + 
				"ORDER BY s.id";

		SmestajnaJedinicaRowCallbackHandler rowCallbackHandler = new SmestajnaJedinicaRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, nazivJedinice);

		return rowCallbackHandler.getSve().get(0);
	}

	@Override
	public List<SmestajnaJedinica> findAll() {
		String sql = 
				"select s.id, s.nazivJedinice, s.idTipJedinice, "
				+"s.kapacitet, s.idDestinacijeSmestaja, s.recenzija, s.uslugaWifi, s.uslugaKupatilo, s.uslugaTv, s.opis, "
				+"t.id, t.nazivTipaJedinice, "
				+"d.id, d.grad, d.drzava, d.kontinent "
				+"from smestajnajedinica s "
				+"left join destinacije d on d.id = s.idDestinacijeSmestaja "
				+"LEFT JOIN tipjedinice t ON t.id = s.idTipJedinice";

		SmestajnaJedinicaRowCallbackHandler rowCallbackHandler = new SmestajnaJedinicaRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getSve();
	}

	@Transactional
	@Override
	public int save(SmestajnaJedinica smestajnaJedinica) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO smestajnaJedinica (nazivJedinice, idTipJedinice, kapacitet, idDestinacijeSmestaja, recenzija,"
									+ "	uslugaWifi, uslugaKupatilo, uslugaTv, opis) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, smestajnaJedinica.getNazivJedinice());
				preparedStatement.setLong(index++, smestajnaJedinica.getIdTipJedinice());
				preparedStatement.setInt(index++, smestajnaJedinica.getKapacitet());
				preparedStatement.setLong(index++, smestajnaJedinica.getIdDestinacijeSmestaja());
				preparedStatement.setDouble(index++, smestajnaJedinica.getRecenzija());
				preparedStatement.setBoolean(index++, smestajnaJedinica.getUslugaWifi());
				preparedStatement.setBoolean(index++, smestajnaJedinica.getUslugaKupatilo());
				preparedStatement.setBoolean(index++, smestajnaJedinica.getUslugaTv());
				preparedStatement.setString(index++, smestajnaJedinica.getOpis());
				
				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(SmestajnaJedinica smestajnaJedinica) {        
	    String sql = "UPDATE smestajnaJedinica SET nazivJedinice = ?, kapacitet = ?, opis = ?"
	                + " WHERE id = ?";
	    boolean uspeh = jdbcTemplate.update(sql, 
	            smestajnaJedinica.getNazivJedinice(),
	            smestajnaJedinica.getKapacitet(), 
	            smestajnaJedinica.getOpis(),
	            smestajnaJedinica.getId()) == 1;
	    
	    return uspeh ? 1 : 0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM smestajnaJedinica WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
