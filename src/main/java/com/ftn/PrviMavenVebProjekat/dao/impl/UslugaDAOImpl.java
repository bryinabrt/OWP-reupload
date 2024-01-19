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

import com.ftn.PrviMavenVebProjekat.dao.UslugaDAO;
import com.ftn.PrviMavenVebProjekat.model.Usluga;

@Repository
public class UslugaDAOImpl implements UslugaDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class UslugaRowCallbackHandler implements RowCallbackHandler {

		private Map<Long, Usluga> usluge = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String nazivUsluge = resultSet.getString(index++);

			Usluga usluga = usluge.get(id);
			if (usluga == null) {
				usluga = new Usluga(id, nazivUsluge);
				usluge.put(usluga.getId(), usluga); // dodavanje u kolekciju
			}
		}

		public List<Usluga> getUsluge() {
			return new ArrayList<>(usluge.values());
		}

	}

	@Override
	public Usluga findOne(Long id) {
		String sql = 
				"SELECT u.id, u.nazivUsluge FROM usluge u " + 
				"WHERE u.id = ? " + 
				"ORDER BY u.id";

		UslugaRowCallbackHandler rowCallbackHandler = new UslugaRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getUsluge().get(0);
	}

	@Override
	public List<Usluga> findAll() {
		String sql = 
				"SELECT u.id, u.nazivUsluge FROM usluge u " + 
				"ORDER BY u.id";

		UslugaRowCallbackHandler rowCallbackHandler = new UslugaRowCallbackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getUsluge();
	}

	@Transactional
	@Override
	public int save(Usluga usluga) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO Usluge (nazivUsluge) VALUE (?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, usluga.getNazivUsluge());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Usluga usluga) {		
		String sql = "UPDATE usluge SET nazivTipaJedinice = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, usluga.getNazivUsluge(), usluga.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM usluge WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
