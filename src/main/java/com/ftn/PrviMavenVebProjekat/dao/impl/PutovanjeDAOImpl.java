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
import com.ftn.PrviMavenVebProjekat.model.Putovanje;

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
			String prevoznoSredstvo = resultSet.getString(index++);
			String smestajnaJedinica = resultSet.getString(index++);
			Kategorija kategorija = Kategorija.valueOf(resultSet.getString(index++));
			String datumPolaska = resultSet.getString(index++);
			String datumPovratka = resultSet.getString(index++);
			Integer brojNocenja = resultSet.getInt(index++);
			Double cena = resultSet.getDouble(index++);
			
			String grad = resultSet.getString(index++);
			String drzava = resultSet.getString(index++);
			String kontinent = resultSet.getString(index++);
			Destinacija destinacija = new Destinacija(grad, drzava, kontinent);

			Putovanje putovanje = putovanja.get(id);
			if (putovanje == null) {
				List<Destinacija> destinacije = new ArrayList<Destinacija>();
				destinacije.add(destinacija);
				putovanje = new Putovanje(id, sifraPutovanja, idDestinacije, prevoznoSredstvo, smestajnaJedinica, kategorija,
						datumPolaska, datumPovratka, brojNocenja, cena);
				putovanje.setDestinacije(destinacije);
				putovanja.put(putovanje.getId(), putovanje); // dodavanje u kolekciju
			} else {
				// ako putovanje vec postoji u kolekciji samo joj dodaj autora ako ga ona vec nema
				// proveri da li u listi autora imas autora kog trazis
				boolean found = false;
				for (Destinacija d : putovanje.getDestinacije()) {
					if (d.getId() == idDestinacije) {
						found = true;
						break;
					}
				}
				
				// ako ga nemas dodaj!
				if (!found) {
					putovanje.getDestinacije().add(destinacija);
				}
			}
		}

		public List<Putovanje> getDestinacije() {
			return new ArrayList<>(putovanja.values());
		}

	}

	@Override
	public Putovanje findOne(Long id) {
		String sql = 
				"select p.id, p.sifraPutovanja, p.idDestinacije, p.prevoznoSredstvo, p.smestajnaJedinica, p.kategorija, p.datumPolaska, p.datumPovratka, "
				+ "p.brojNocenja, p.cena, d.id, d.grad, d.drzava, d.kontinent from putovanja p "
						+ "left join destinacije d on d.id = p.idDestinacije "
						+ "WHERE p.id = ? " + 
				"ORDER BY p.id";

		PutovanjeRowCallBackHandler rowCallbackHandler = new PutovanjeRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getDestinacije().get(0);
	}

	@Override
	public List<Putovanje> findAll() {
		String sql = 
				"select p.id, p.sifraPutovanja, p.idDestinacije, p.prevoznoSredstvo, p.smestajnaJedinica, p.kategorija, p.datumPolaska, p.datumPovratka, "
				+ "p.brojNocenja, p.cena, d.id, d.grad, d.drzava, d.kontinent from putovanja p "
						+ "left join destinacije d on d.id = p.idDestinacije";

		PutovanjeRowCallBackHandler rowCallbackHandler = new PutovanjeRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getDestinacije();
	}

	@Transactional
	@Override
	public int save(Putovanje putovanje) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO putovanja (id, sifraPutovanja, idDestinacije, prevoznoSredstvo, smestajnaJedinica, kategorija,"
									+ "	datumPolaska, datumPovratka, brojNocenja, cena) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, putovanje.getSifraPutovanja());
				preparedStatement.setLong(index++, putovanje.getIdDestinacije());
				preparedStatement.setString(index++, putovanje.getPrevoznoSredstvo());
				preparedStatement.setString(index++, putovanje.getSmestajnaJedinica());
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
		String sql = "UPDATE putovanja SET naziv = ?, registarskiBrojPrimerka = ?, jezik = ?, brojStranica = ?, izdata = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, putovanje.getId(), putovanje.getSifraPutovanja(), putovanje.getIdDestinacije(),
				putovanje.getPrevoznoSredstvo(), putovanje.getSmestajnaJedinica(), putovanje.getKategorijaString(), putovanje.getDatumPolaska(),
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
