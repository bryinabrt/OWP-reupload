package com.ftn.PrviMavenVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.ftn.PrviMavenVebProjekat.model.Price;
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
			Long id = resultSet.getLong("id");
			String sifraPutovanja = resultSet.getString("sifraPutovanja");
			Long idDestinacije = resultSet.getLong("idDestinacije");
			Long idPrevoznoSredstvo = resultSet.getLong("idPrevoznoSredstvo");
			Long idSmestajnaJedinica = resultSet.getLong("idSmestajnaJedinica");
			Kategorija kategorija = Kategorija.valueOf(resultSet.getString("kategorija"));
			Integer brojNocenja = resultSet.getInt("brojNocenja");
			String slika = resultSet.getString("slika");
		
			
			String grad = resultSet.getString("grad");
			String drzava = resultSet.getString("drzava");
			String kontinent = resultSet.getString("kontinent");
			Destinacija destinacija = new Destinacija(grad, drzava, kontinent);
			
			String tipSredstva = resultSet.getString("tipSredstva");
			Integer brojSedista = resultSet.getInt("brojSedista");
			Long krajnjaDestinacija = resultSet.getLong("krajnjaDestinacija");
			String opis = resultSet.getString("opis");
			PrevoznoSredstvo prevoznoSredstvo = new PrevoznoSredstvo(tipSredstva, brojSedista, krajnjaDestinacija, opis);

			
			String nazivJedinice = resultSet.getString("nazivJedinice");
			Long idTipJedinice = resultSet.getLong("idTipJedinice");
			Integer kapacitet = resultSet.getInt("kapacitet");
			Long idDestinacijeSmestaja = resultSet.getLong("idDestinacijeSmestaja");
			Double recenzija = resultSet.getDouble("recenzija");
			Boolean uslugaWifi = resultSet.getBoolean("uslugaWifi");
		    Boolean uslugaKupatilo = resultSet.getBoolean("uslugaKupatilo");
		    Boolean uslugaTv = resultSet.getBoolean("uslugaTv");
		    SmestajnaJedinica smestajnaJedinica = new SmestajnaJedinica(nazivJedinice, idTipJedinice, kapacitet, idDestinacijeSmestaja, 
		    		recenzija, uslugaWifi, uslugaKupatilo, uslugaTv);
		    

		    Long destinationId = resultSet.getLong("destinationId");
		    Long putovanjeId = resultSet.getLong("putovanjeId");
			LocalDateTime startDate = resultSet.getTimestamp("startDate").toLocalDateTime();
			LocalDateTime endDate = resultSet.getTimestamp("endDate").toLocalDateTime();
			Double priceOfTravel = resultSet.getDouble("priceOfTravel");
			
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        String formattedStartDate = startDate.format(formatter);
	        String formattedEndDate = endDate.format(formatter);

	        Price price = new Price(destinationId, putovanjeId, startDate, endDate, priceOfTravel);
			

			Putovanje putovanje = putovanja.get(id);
			if (putovanje == null) {
				List<Destinacija> destinacije = new ArrayList<Destinacija>();
				destinacije.add(destinacija);
				List<PrevoznoSredstvo> prevoznaSredstva = new ArrayList<PrevoznoSredstvo>();
				prevoznaSredstva.add(prevoznoSredstvo);
				List<SmestajnaJedinica> smestajneJedinice = new ArrayList<SmestajnaJedinica>();
				smestajneJedinice.add(smestajnaJedinica);
				List<Price> prices = new ArrayList<Price>();
				
				putovanje = new Putovanje(id, sifraPutovanja, idDestinacije, idPrevoznoSredstvo, idSmestajnaJedinica, kategorija, brojNocenja, slika);
				putovanje.setDestinacije(destinacije);
				putovanje.setPrevoznaSredstva(prevoznaSredstva);
				putovanje.setSmestajneJedinice(smestajneJedinice);
				putovanje.setPrices(prices);
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
				
				for (Price p : putovanje.getPrices()) {
					if (p.getDestinationId() == idDestinacije) {
						found = true;
						break;
					}
				}
				
				if (!found) {
					putovanje.getPrices().add(price);
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
				"select p.id, p.sifraPutovanja, p.idDestinacije, p.idPrevoznoSredstvo, p.idSmestajnaJedinica, p.kategorija, "
				+ "p.brojNocenja, p.slika, "
				+ "d.id, d.grad, d.drzava, d.kontinent, "
				+ "ps.id, ps.tipSredstva, ps.brojSedista, ps.krajnjaDestinacija, ps.opis, "
				+ "s.id, s.nazivJedinice, s.idTipJedinice, s.kapacitet, s.idDestinacijeSmestaja, s.recenzija, s.uslugaWifi, s.uslugaKupatilo, "
				+ "s.uslugaTv, s.opis, "
				+ "pr.id, pr.destinationId, pr.putovanjeId, pr.startDate, pr.endDate, pr.priceOfTravel from putovanja p "
						+ "left join destinacije d on d.id = p.idDestinacije "
						+ "left join prevoznosredstvo ps on ps.id = p.idPrevoznoSredstvo "
						+ "left join smestajnaJedinica s on s.id = p.idSmestajnaJedinica "
						+ "left join prices pr on pr.putovanjeId = p.id "
						+ "WHERE p.id = ? " 
						+ "ORDER BY p.id";

		PutovanjeRowCallBackHandler rowCallbackHandler = new PutovanjeRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getSve().get(0);
	}
	
	private class PutovanjeSifraRowCallBackHandler implements RowCallbackHandler {
		
		private Map<Long, Putovanje> putovanja = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong("id");
			String sifraPutovanja = resultSet.getString("sifraPutovanja");
			Long idDestinacije = resultSet.getLong("idDestinacije");
			Long idPrevoznoSredstvo = resultSet.getLong("idPrevoznoSredstvo");
			Long idSmestajnaJedinica = resultSet.getLong("idSmestajnaJedinica");
			Kategorija kategorija = Kategorija.valueOf(resultSet.getString("kategorija"));
			Integer brojNocenja = resultSet.getInt("brojNocenja");
			String slika = resultSet.getString("slika");
			
			Putovanje putovanje = new Putovanje(id, sifraPutovanja, idDestinacije, idPrevoznoSredstvo, idSmestajnaJedinica, kategorija, brojNocenja, slika);
			putovanja.put(putovanje.getId(), putovanje);
		}
		public List<Putovanje> getSve() {
			return new ArrayList<>(putovanja.values());
		}
	}
	
	@Override
	public Putovanje findOneBySifra(String sifraPutovanja) {
		String sql = 
				"select p.id, p.sifraPutovanja, p.idDestinacije, p.idPrevoznoSredstvo, p.idSmestajnaJedinica, p.kategorija, "
				+ "p.brojNocenja, p.slika "
				+ "from putovanja p "
						+ "WHERE p.sifraPutovanja = ? " 
						+ "ORDER BY p.sifraPutovanja";

		PutovanjeSifraRowCallBackHandler rowCallbackHandler = new PutovanjeSifraRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, sifraPutovanja);

		return rowCallbackHandler.getSve().get(0);
	}

	@Override
	public List<Putovanje> findAll() {
		String sql = 
				"select p.id, p.sifraPutovanja, p.idDestinacije, p.idPrevoznoSredstvo, p.idSmestajnaJedinica, p.kategorija, "
				+ "p.brojNocenja, p.slika, "
				+ "d.id, d.grad, d.drzava, d.kontinent, "
				+ "ps.id, ps.tipSredstva, ps.brojSedista, ps.krajnjaDestinacija, ps.opis, "
				+ "s.id, s.nazivJedinice, s.idTipJedinice, s.kapacitet, s.idDestinacijeSmestaja, s.recenzija, s.uslugaWifi, s.uslugaKupatilo, "
				+ "s.uslugaTv, s.opis, "
				+ "pr.id, pr.destinationId, pr.putovanjeId, pr.startDate, pr.endDate, pr.priceOfTravel from putovanja p "
						+ "left join destinacije d on d.id = p.idDestinacije "
						+ "left join prevoznoSredstvo ps on ps.id = p.idPrevoznoSredstvo "
						+ "left join smestajnaJedinica s on s.id = p.idSmestajnaJedinica "
						+ "left join prices pr on pr.putovanjeId = p.id "
						+ "ORDER BY p.id";

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
				String sql = "INSERT INTO putovanja (sifraPutovanja, idDestinacije, idPrevoznoSredstvo, idSmestajnaJedinica, kategorija,"
									+ " brojNocenja, slika) VALUES (?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, putovanje.getSifraPutovanja());
				preparedStatement.setLong(index++, putovanje.getIdDestinacije());
				preparedStatement.setLong(index++, putovanje.getIdPrevoznoSredstvo());
				preparedStatement.setLong(index++, putovanje.getIdSmestajnaJedinica());
				preparedStatement.setString(index++, putovanje.getKategorijaString());
				preparedStatement.setInt(index++, putovanje.getBrojNocenja());
				preparedStatement.setString(index++, putovanje.getSlika());
				
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
				+ "kategorija = ?, brojNocenja = ?, cena = ?, slika = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, putovanje.getId(), putovanje.getSifraPutovanja(), putovanje.getIdDestinacije(),
				putovanje.getIdPrevoznoSredstvo(), putovanje.getIdSmestajnaJedinica(), putovanje.getKategorijaString(),
				putovanje.getBrojNocenja(), putovanje.getSlika()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM putovanja WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
