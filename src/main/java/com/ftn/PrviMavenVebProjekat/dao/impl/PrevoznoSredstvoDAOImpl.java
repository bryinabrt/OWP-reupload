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

import com.ftn.PrviMavenVebProjekat.dao.PrevoznoSredstvoDAO;
import com.ftn.PrviMavenVebProjekat.model.Destinacija;
import com.ftn.PrviMavenVebProjekat.model.PrevoznoSredstvo;
import com.ftn.PrviMavenVebProjekat.model.TipJedinice;

@Repository
public class PrevoznoSredstvoDAOImpl implements PrevoznoSredstvoDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class PrevoznoSredstvoRowCallbackHandler implements RowCallbackHandler {
		
		private Map<Long, PrevoznoSredstvo> prevoznaSredstva = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong("id");
			String tipSredstva = resultSet.getString("tipSredstva");
			Integer brojSedista = resultSet.getInt("brojSedista");
			Long krajnjaDestinacija = resultSet.getLong("krajnjaDestinacija");
			String opis = resultSet.getString("opis");
			
			String grad = resultSet.getString("grad");
			String drzava = resultSet.getString("drzava");
			String kontinent = resultSet.getString("kontinent");

			Destinacija destinacija = new Destinacija(grad, drzava, kontinent);
			
			PrevoznoSredstvo prevoznoSredstvo = prevoznaSredstva.get(id);
			if (prevoznoSredstvo == null) {
				List<Destinacija> destinacije = new ArrayList<Destinacija>();
				destinacije.add(destinacija);
				prevoznoSredstvo = new PrevoznoSredstvo(id, tipSredstva, brojSedista, krajnjaDestinacija, opis);
				prevoznoSredstvo.setDestinacije(destinacije);
				prevoznaSredstva.put(prevoznoSredstvo.getId(), prevoznoSredstvo);
			} else {
				boolean found = false;
				for (Destinacija d : prevoznoSredstvo.getDestinacije()) {
					if (d.getId() == krajnjaDestinacija) {
						found = true;
						break;
					}
				}
				// ako ga nemas dodaj!
				if (!found) {
					prevoznoSredstvo.getDestinacije().add(destinacija);
				}
			}
			
		}
		
		public List<PrevoznoSredstvo> getDestinacije() {
			return new ArrayList<>(prevoznaSredstva.values());
		}

	}
	
	
	@Override
	public PrevoznoSredstvo findOne(Long id) {
		String sql = "SELECT "
				+"p.id, p.tipSredstva, p.brojSedista, p.krajnjaDestinacija, p.opis, "
				+"d.id, d.grad, d.drzava, d.kontinent FROM prevoznoSredstvo p "
				+"LEFT JOIN destinacije d on d.id = p.krajnjaDestinacija "
				+"WHERE p.id = ? "
				+"ORDER BY p.id";

		PrevoznoSredstvoRowCallbackHandler rowCallbackHandler = new PrevoznoSredstvoRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getDestinacije().get(0);
	}
	
	@Override
	public List<PrevoznoSredstvo> findOneByDestinacija(Long krajnjaDestinacija) {
		String sql = "SELECT "
				+"p.id, p.tipSredstva, p.brojSedista, p.krajnjaDestinacija, p.opis, "
				+"d.id, d.grad, d.drzava, d.kontinent FROM prevoznoSredstvo p "
				+"LEFT JOIN destinacije d on d.id = p.krajnjaDestinacija "
				+"WHERE p.krajnjaDestinacija = ? "
				+"ORDER BY p.id";

		PrevoznoSredstvoRowCallbackHandler rowCallbackHandler = new PrevoznoSredstvoRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, krajnjaDestinacija);

		return rowCallbackHandler.getDestinacije();
	}
	
	@Override
	public PrevoznoSredstvo findOneByTip(String tipSredstva) {
		String sql = "SELECT "
				+"p.id, p.tipSredstva, p.brojSedista, p.krajnjaDestinacija, p.opis, "
				+"d.id, d.grad, d.drzava, d.kontinent FROM prevoznoSredstvo p "
				+"LEFT JOIN destinacije d on d.id = p.krajnjaDestinacija "
				+"WHERE p.tipSredstva = ? "
				+"ORDER BY p.id";

		PrevoznoSredstvoRowCallbackHandler rowCallbackHandler = new PrevoznoSredstvoRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, tipSredstva);

		return rowCallbackHandler.getDestinacije().get(0);
	}
	
	
	@Override
	public List<PrevoznoSredstvo> findAll() {
		String sql = "SELECT "
				+"p.id, p.tipSredstva, p.brojSedista, p.krajnjaDestinacija, p.opis, "
				+"d.id, d.grad, d.drzava, d.kontinent FROM prevoznoSredstvo p "
				+"LEFT JOIN destinacije d on d.id = p.krajnjaDestinacija "
				+"ORDER BY p.id";

		PrevoznoSredstvoRowCallbackHandler rowCallbackHandler = new PrevoznoSredstvoRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getDestinacije();
	}
	
	@Transactional
	@Override
	public int save(PrevoznoSredstvo prevoznoSredstvo) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO prevoznoSredstvo (tipSredstva, brojSedista, krajnjaDestinacija, "
						+ "opis) VALUES (?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, prevoznoSredstvo.getTipSredstva());
				preparedStatement.setInt(index++, prevoznoSredstvo.getBrojSedista());
				preparedStatement.setLong(index++, prevoznoSredstvo.getKrajnjaDestinacija());
				preparedStatement.setString(index++, prevoznoSredstvo.getOpis());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(PrevoznoSredstvo prevoznoSredstvo) {		
		String sql = "UPDATE prevoznoSredstvo SET tipSredstva = ?, brojSedista = ?, krajnjaDestinacija = ?, opis = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, prevoznoSredstvo.getTipSredstva(), prevoznoSredstvo.getBrojSedista(), 
				prevoznoSredstvo.getKrajnjaDestinacija(), prevoznoSredstvo.getOpis(), prevoznoSredstvo.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM prevoznoSredstvo WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}