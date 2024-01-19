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

import com.ftn.PrviMavenVebProjekat.dao.PutovanjeDAO;
import com.ftn.PrviMavenVebProjekat.model.Destinacija;
import com.ftn.PrviMavenVebProjekat.model.Kategorija;
import com.ftn.PrviMavenVebProjekat.model.PrevoznoSredstvo;
import com.ftn.PrviMavenVebProjekat.model.Putovanje;
import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;

@Repository
public class PutovanjeDAOImpl implements PutovanjeDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class PutovanjeRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Putovanje> putovanja = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String sifraPutovanja = resultSet.getString(index++);
			Long idDestinacije = resultSet.getLong(index++);
			Long idPrevoznoSredstvo = resultSet.getLong(index++);
			Long idSmestajnaJedinica = resultSet.getLong(index++);
			Kategorija kategorija = Kategorija.valueOf(resultSet.getString(index++));
			String datumPolaska = resultSet.getString(index++);
			String datumPovratka = resultSet.getString(index++);
			Integer brojNocenja = resultSet.getInt(index++);
			Double cena = resultSet.getDouble(index++);
			
			String grad = resultSet.getString(index++);
			String drzava = resultSet.getString(index++);
			String kontinent = resultSet.getString(index++);
			Destinacija destinacija = new Destinacija(grad, drzava, kontinent);
			
			String tipSredstva = resultSet.getString(index++);
			Integer brojSedista = resultSet.getInt(index++);
			Long krajnjaDestinacija = resultSet.getLong(index++);
			String opis = resultSet.getString(index++);
			PrevoznoSredstvo prevoznoSredstvo = new PrevoznoSredstvo(tipSredstva, brojSedista, krajnjaDestinacija, opis);
			
			String nazivJedinice = resultSet.getString(index++);
			Long idTipJedinice = resultSet.getLong(index++);
			Integer kapacitet = resultSet.getInt(index++);
			Long idDestinacijeSmestaja = resultSet.getLong(index++);
			Double recenzija = resultSet.getDouble(index++);
			Boolean uslugaWifi = resultSet.getBoolean(index++);
		    Boolean uslugaKupatilo = resultSet.getBoolean(index++);
		    Boolean uslugaTv = resultSet.getBoolean(index++);
		    SmestajnaJedinica smestajnaJedinica = new SmestajnaJedinica(nazivJedinice, idTipJedinice, kapacitet, idDestinacijeSmestaja, 
		    		recenzija, uslugaWifi, uslugaKupatilo, uslugaTv);
			

			Putovanje putovanje = putovanja.get(id);
			if (putovanje == null) {
				List<Destinacija> destinacije = new ArrayList<Destinacija>();
				destinacije.add(destinacija);
				List<PrevoznoSredstvo> prevoznaSredstva = new ArrayList<PrevoznoSredstvo>();
				prevoznaSredstva.add(prevoznoSredstvo);
				List<SmestajnaJedinica> smestajneJedinice = new ArrayList<SmestajnaJedinica>();
				smestajneJedinice.add(smestajnaJedinica);
				
				putovanje = new Putovanje(id, sifraPutovanja, idDestinacije, idPrevoznoSredstvo, idSmestajnaJedinica, kategorija,
						datumPolaska, datumPovratka, brojNocenja, cena);
				putovanje.setDestinacije(destinacije);
				putovanja.put(putovanje.getId(), putovanje);
			} else {
				boolean found = false;
				for (Destinacija d : putovanje.getDestinacije()) {
					if (d.getId() == idDestinacije) {
						found = true;
						break;
					}
				}
				
				if (!found) {
					putovanje.getDestinacije().add(destinacija);
				}
				
				for (PrevoznoSredstvo p : putovanje.getPrevoznaSredstva()) {
					if (p.getId() == idPrevoznoSredstvo) {
						found = true;
						break;
					}
				}
				
				if (!found) {
					putovanje.getPrevoznaSredstva().add(prevoznoSredstvo);
				}
				
				for (SmestajnaJedinica s : putovanje.getSmestajneJedinice()) {
					if (s.getId() == idSmestajnaJedinica) {
						found = true;
						break;
					}
				}
				
				if (!found) {
					putovanje.getSmestajneJedinice().add(smestajnaJedinica);
				}
				
				
			}
		}

		public List<Putovanje> getSve() {
			return new ArrayList<>(putovanja.values());
		}

	}

	@Override
	public Putovanje findOne(Long id) {
		String sql = 
				"select p.id, p.sifraPutovanja, p.idDestinacije, p.idPrevoznoSredstvo, p.idSmestajnaJedinica, p.kategorija, p.datumPolaska, p.datumPovratka, "
				+ "p.brojNocenja, p.cena, "
				+ "d.id, d.grad, d.drzava, d.kontinent, "
				+ "ps.id, ps.tipSredstva, ps.brojSedista, ps.krajnjaDestinacija, ps.opis, "
				+ "s.id, s.nazivJedinice, s.idTipJedinice, s.kapacitet, s.idDestinacijeSmestaja, s.recenzija, s.uslugaWifi, s.uslugaKupatilo, "
				+ "s.uslugaTv, s.opis from putovanja p "
						+ "left join destinacije d on d.id = p.idDestinacije "
						+ "left join prevoznoSredstvo ps on ps.id = p.idPrevoznoSredstvo "
						+ "left join smestajnaJedinica s on s.id = s.idSmestajnaJedinica"
						+ "WHERE p.id = ? " + 
				"ORDER BY p.id";

		PutovanjeRowCallBackHandler rowCallbackHandler = new PutovanjeRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getSve().get(0);
	}

	@Override
	public List<Putovanje> findAll() {
		String sql = 
				"select p.id, p.sifraPutovanja, p.idDestinacije, p.idPrevoznoSredstvo, p.idSmestajnaJedinica, p.kategorija, p.datumPolaska, p.datumPovratka, "
				+ "p.brojNocenja, p.cena, "
				+ "d.id, d.grad, d.drzava, d.kontinent, "
				+ "ps.id, ps.tipSredstva, ps.brojSedista, ps.krajnjaDestinacija, ps.opis, "
				+ "s.id, s.nazivJedinice, s.idTipJedinice, s.kapacitet, s.idDestinacijeSmestaja, s.recenzija, s.uslugaWifi, s.uslugaKupatilo, "
				+ "s.uslugaTv, s.opis from putovanja p "
						+ "left join destinacije d on d.id = p.idDestinacije "
						+ "left join prevoznoSredstvo ps on ps.id = p.idPrevoznoSredstvo "
						+ "left join smestajnaJedinica s on s.id = s.idSmestajnaJedinica";

		PutovanjeRowCallBackHandler rowCallbackHandler = new PutovanjeRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getSve();
	}

	@Transactional
	@Override
	public int save(Putovanje putovanje) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO putovanja (id, sifraPutovanja, idDestinacije, idPrevoznoSredstvo, idSmestajnaJedinica, kategorija,"
									+ "	datumPolaska, datumPovratka, brojNocenja, cena) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, putovanje.getSifraPutovanja());
				preparedStatement.setLong(index++, putovanje.getIdDestinacije());
				preparedStatement.setLong(index++, putovanje.getIdPrevoznoSredstvo());
				preparedStatement.setLong(index++, putovanje.getIdSmestajnaJedinica());
				preparedStatement.setString(index++, putovanje.getKategorijaString());
				preparedStatement.setString(index++, putovanje.getDatumPolaska());
				preparedStatement.setString(index++, putovanje.getDatumPovratka());
				preparedStatement.setInt(index++, putovanje.getBrojNocenja());
				preparedStatement.setDouble(index++, putovanje.getCena());
				
				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Putovanje putovanje) {		
		String sql = "UPDATE putovanja SET sifraPutovanja = ?, idDestinacijeSmestaja = ?, idPrevoznoSredstvo = ?, idSmestajnajedinica = ?, "
				+ "kategorija = ?, datumPolaska = ?, datumPovratka = ?, brojNocenja = ?, cena = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, putovanje.getId(), putovanje.getSifraPutovanja(), putovanje.getIdDestinacije(),
				putovanje.getIdPrevoznoSredstvo(), putovanje.getIdSmestajnaJedinica(), putovanje.getKategorijaString(), putovanje.getDatumPolaska(),
				putovanje.getDatumPovratka(), putovanje.getBrojNocenja(), putovanje.getCena()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM putovanja WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
