package com.ftn.PrviMavenVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.ftn.PrviMavenVebProjekat.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.PrviMavenVebProjekat.model.Destinacija;
import com.ftn.PrviMavenVebProjekat.model.Kategorija;
import com.ftn.PrviMavenVebProjekat.model.PrevoznoSredstvo;
import com.ftn.PrviMavenVebProjekat.model.Price;
import com.ftn.PrviMavenVebProjekat.model.Putovanje;
import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;

@Repository
public class PutovanjeDAOImpl implements PutovanjeDAO{

	@Autowired
	private DestinacijaDAO destinacijaDAO;

	@Autowired
	private SmestajnaJedinicaDAO smestajnaJedinicaDAO;

	@Autowired
	private PriceDAO priceDAO;

	@Autowired
	private PrevoznoSredstvoDAO prevoznoSredstvoDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static List<Putovanje> removeDuplicates(List<Putovanje> putovanja) {
		Set<Putovanje> uniquePutovanja = new HashSet<>(putovanja);
		return new ArrayList<>(uniquePutovanja);
	}

	
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
				putovanje.setDestinacija(destinacija);
				putovanje.setPrevoznoSredstvo(prevoznoSredstvo);
				putovanje.setSmestajnaJedinica(smestajnaJedinica);
				putovanje.setPrices(prices);
				putovanja.put(putovanje.getId(), putovanje);
			} else {
				boolean found = false;
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

	@Override
	public List<Putovanje> find(String destinacijaId, String prevoznoSredstvoId, String smestajnaJedinicaId, String kategorijaPutovanja , String datumPolaska , String datumPovratka , Double cenaOd , Double cenaDo,
								String sortDes, String sortPS, String sortSJ, String sortKat, String sortDatumStart, String sortDatumEnd, String sortCena, Integer brojNocenjaOd, Integer brojNocenjaDo, String sortNoc, Integer brojMesta, Integer putId) {

		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		System.out.println("BRM BRMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA DAO: "+destinacijaId);

		String sql = "SELECT p.id, p.sifraPutovanja, p.idDestinacije, p.idPrevoznoSredstvo, p.idSmestajnaJedinica, p.kategorija, p.brojNocenja, p.slika, d.grad, ps.tipSredstva, ps.brojSedista, s.nazivJedinice, s.kapacitet, " +
				"pr.id, pr.destinationId, pr.putovanjeId, pr.startDate, pr.endDate, pr.priceOfTravel " +
				"from putovanja p left join destinacije d ON p.idDestinacije = d.id left join prevoznoSredstvo ps ON ps.id = p.idPrevoznoSredstvo " +
				"left join smestajnaJedinica s ON p.idSmestajnaJedinica = s.id left join prices pr on p.id = pr.putovanjeId";

		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;

		if(destinacijaId!=null && !destinacijaId.isEmpty()) {
			destinacijaId = "%" + destinacijaId + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.idDestinacije LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(destinacijaId);
		}

		if (prevoznoSredstvoId != null && !prevoznoSredstvoId.isEmpty()) {
			if (imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.idPrevoznoSredstvo LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add("%" + prevoznoSredstvoId + "%");
		}


		if(smestajnaJedinicaId!=null && !smestajnaJedinicaId.isEmpty()) {
			smestajnaJedinicaId = "%" + smestajnaJedinicaId + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.idSmestajnaJedinica LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(smestajnaJedinicaId);
		}

		if(kategorijaPutovanja!=null && !kategorijaPutovanja.isEmpty()) {
			kategorijaPutovanja = "%" + kategorijaPutovanja + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.kategorija LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(kategorijaPutovanja);
		}

		if(datumPolaska!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("pr.startDate >= ?");
			imaArgumenata = true;
			listaArgumenata.add(datumPolaska);
		}

		if(datumPovratka!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("pr.endDate <= ?");
			imaArgumenata = true;
			listaArgumenata.add(datumPovratka);
		}

		if(cenaOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("pr.priceOfTravel >= ?");
			imaArgumenata = true;
			listaArgumenata.add(cenaOd);
		}

		if(cenaDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("pr.priceOfTravel <= ?");
			imaArgumenata = true;
			listaArgumenata.add(cenaDo);
		}

		if(brojNocenjaOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.brojNocenja >= ?");
			imaArgumenata = true;
			listaArgumenata.add(brojNocenjaOd);
		}

		if(brojNocenjaDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.brojNocenja <= ?");
			imaArgumenata = true;
			listaArgumenata.add(brojNocenjaDo);
		}

		if(brojMesta!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("ps.brojSedista >= ?");
			imaArgumenata = true;
			listaArgumenata.add(brojMesta);
		}

		if(brojMesta!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("s.kapacitet >= ?");
			imaArgumenata = true;
			listaArgumenata.add(brojMesta);
		}

		if(putId != null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.id = ?");
			imaArgumenata = true;
			listaArgumenata.add(putId);
		}

		boolean imaSort = false;

		StringBuffer orderSQL = new StringBuffer(" ORDER BY ");

		if(sortDes != null) {
			orderSQL.append("d.id " + sortDes);
			imaSort = true;
		}

		if(sortPS != null) {
			if(imaSort) {
				orderSQL.append(",ps.tipSredstva " + sortPS);
			}else {
				orderSQL.append("ps.tipSredstva " + sortPS);
				imaSort = true;
			}
		}

		if(sortSJ != null) {
			if(imaSort) {
				orderSQL.append(",s.nazivJedinice " + sortSJ);
			}else {
				orderSQL.append("s.nazivJedinice " + sortSJ);
				imaSort = true;
			}
		}

		if(sortKat != null) {
			if(imaSort) {
				orderSQL.append(",p.kategorija " + sortKat);
			}else {
				orderSQL.append("p.kategorija " + sortKat);
				imaSort = true;
			}
		}

		if(sortDatumStart  != null) {
			if(imaSort) {
				orderSQL.append(",pr.startDate " + sortDatumStart);
			}else {
				orderSQL.append("pr.startDate " + sortDatumStart);
				imaSort = true;
			}
		}

		if(sortDatumEnd != null) {
			if(imaSort) {
				orderSQL.append(",pr.endDate " + sortDatumEnd);
			}else {
				orderSQL.append("pr.endDate " + sortDatumEnd);
				imaSort = true;
			}
		}

		if(sortCena != null) {
			if(imaSort) {
				orderSQL.append(",pr.priceOfTravel " + sortCena);
			}else {
				orderSQL.append("pr.priceOfTravel " + sortCena);
				imaSort = true;
			}
		}

		if(sortNoc != null) {
			if(imaSort) {
				orderSQL.append(",p.brojNocenja " + sortNoc);
			}else {
				orderSQL.append("p.brojNocenja " + sortNoc);
				imaSort = true;
			}
		}


		StringBuffer orderId = new StringBuffer(" ORDER BY p.id");

		if(imaArgumenata) {
			if(imaSort) {
				sql=sql + whereSql.toString()+orderSQL;
			}else {
				sql=sql + whereSql.toString()+orderId;
			}
		}else {
			if(imaSort) {
				sql=sql + orderSQL;
			}else {
				sql=sql + orderId;
			}
		}
		List<Putovanje> putovanja = jdbcTemplate.query(sql, listaArgumenata.toArray(), new PutovanjeRowMapper());
		List<Putovanje> filtrp = removeDuplicates(putovanja);
		return filtrp;
	}

	private class PutovanjeRowMapper implements RowMapper<Putovanje> {

		@Override
		public Putovanje mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String sifraPutovanja = resultSet.getString(index++);
			Long destinacijaId = resultSet.getLong(index++);
			Destinacija destinacija = destinacijaDAO.findOne(destinacijaId);
			Long prevoznoSredstvoId = resultSet.getLong(index++);
			PrevoznoSredstvo prevoznoSredstvo = prevoznoSredstvoDAO.findOne(prevoznoSredstvoId);
			Long smestajnaJedinicaId = resultSet.getLong(index++);
			SmestajnaJedinica smestajnaJedinica = smestajnaJedinicaDAO.findOne(smestajnaJedinicaId);
			Kategorija kategorija = Kategorija.valueOf(resultSet.getString(index++));
			int brojNocenja = resultSet.getInt(index++);
			String slika = resultSet.getString(index++);
			List<Price> prices = priceDAO.findAllByPutovanjeId(id);


			Putovanje putovanje = new Putovanje(id, sifraPutovanja, destinacijaId, prevoznoSredstvoId, smestajnaJedinicaId, kategorija, brojNocenja, slika);
			putovanje.setDestinacija(destinacija);
			putovanje.setPrevoznoSredstvo(prevoznoSredstvo);
			putovanje.setSmestajnaJedinica(smestajnaJedinica);
			putovanje.setPrices(prices);
			for (Price price : prices){
				System.out.println("cena"+price.getPriceOfTravel());
			}
			return putovanje;
		}
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
