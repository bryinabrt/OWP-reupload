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

import com.ftn.PrviMavenVebProjekat.dao.TipJediniceDAO;
import com.ftn.PrviMavenVebProjekat.model.TipJedinice;

@Repository
public class TipJediniceDAOImpl implements TipJediniceDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class TipJediniceRowCallbackHandler implements RowCallbackHandler {

		private Map<Long, TipJedinice> tipJedinica = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String nazivTipaJedinice = resultSet.getString(index++);

			TipJedinice tipJedinice = tipJedinica.get(id);
			if (tipJedinice == null) {
				tipJedinice = new TipJedinice(id, nazivTipaJedinice);
				tipJedinica.put(tipJedinice.getId(), tipJedinice); // dodavanje u kolekciju
			}
		}

		public List<TipJedinice> getTipJedinica() {
			return new ArrayList<>(tipJedinica.values());
		}

	}

	@Override
	public TipJedinice findOne(Long id) {
		String sql = 
				"SELECT t.id, t.nazivTipaJedinice FROM tipJedinice t " + 
				"WHERE t.id = ? " + 
				"ORDER BY t.id";

		TipJediniceRowCallbackHandler rowCallbackHandler = new TipJediniceRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getTipJedinica().get(0);
	}
	
	@Override
	public TipJedinice findOneByNaziv(String nazivTipaJedinice) {
		String sql = 
				"SELECT t.id, t.nazivTipaJedinice FROM tipJedinice t " + 
				"WHERE t.nazivTipajedinice = ? " + 
				"ORDER BY t.nazivTipaJedinice";

		TipJediniceRowCallbackHandler rowCallbackHandler = new TipJediniceRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, nazivTipaJedinice);

		return rowCallbackHandler.getTipJedinica().get(0);
	}

	@Override
	public List<TipJedinice> findAll() {
		String sql = 
				"SELECT t.id, t.nazivTipaJedinice FROM tipJedinice t " + 
				"ORDER BY t.id";

		TipJediniceRowCallbackHandler rowCallbackHandler = new TipJediniceRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getTipJedinica();
	}

	@Transactional
	@Override
	public int save(TipJedinice tipJedinice) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO tipJedinice (nazivTipaJedinice) VALUE (?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, tipJedinice.getNazivTipaJedinice());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(TipJedinice tipJedinice) {		
		String sql = "UPDATE tipJedinice SET nazivTipaJedinice = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, tipJedinice.getNazivTipaJedinice(), tipJedinice.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM tipJedinice WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
