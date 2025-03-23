package com.ftn.PrviMavenVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import com.ftn.PrviMavenVebProjekat.dao.PriceDAO;
import com.ftn.PrviMavenVebProjekat.model.Price;

@Repository
public class PriceDAOImpl implements PriceDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class PriceRowCallBackHandler implements RowCallbackHandler {
		private Map<Long, Price> prices = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			Long destinationId = resultSet.getLong(index++);
			Long putovanjeId = resultSet.getLong(index++);
			LocalDateTime startDate = resultSet.getTimestamp(index++).toLocalDateTime();
			LocalDateTime endDate = resultSet.getTimestamp(index++).toLocalDateTime();
			Integer numberOfSeats = resultSet.getInt(index++);
			Double priceOfTravel = resultSet.getDouble(index++);

			/*
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formattedStartDate = startDate.format(formatter);
	        String formattedEndDate = endDate.format(formatter);
	        
	        System.out.println("ovo je iz price" + formattedStartDate);
	        System.out.println("ovo je iz price" + formattedEndDate);
			*/
			Price price = prices.get(id);
			if (price == null) {
				price = new Price(id, destinationId, putovanjeId, startDate, endDate, numberOfSeats, priceOfTravel);
				prices.put(price.getId(), price);
			}

		}
		public List<Price> getPrice() {
			return new ArrayList<>(prices.values());
		}

	}
	
	@Override
	public List<Price> findAll() {
		String sql = 
				"SELECT p.id, p.destinationId, p.putovanjeId, p.startDate, p.endDate, p.numberOfSeats, p.priceOfTravel "
				+ "FROM prices p "
				+ "ORDER BY p.id";

		PriceRowCallBackHandler rowCallbackHandler = new PriceRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getPrice();
	}
    
	@Override
	public Price findOne(Long id) {
		String sql = 
				"SELECT p.id, p.destinationId, p.putovanjeId, p.startDate, p.endDate, p.numberOfSeats, p.priceOfTravel "
				+ "FROM prices p "
				+ "WHERE p.id = ? "
				+ "ORDER BY p.id";

		PriceRowCallBackHandler rowCallbackHandler = new PriceRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getPrice().get(0);
	}
	
	@Override
	public Price findOneByDestinationId(Long destinationId) {
		String sql = 
				"SELECT p.id, p.destinationId, p.putovanjeId, p.startDate, p.endDate, p.numberOfSeats, p.priceOfTravel "
				+ "FROM prices p "
				+ "WHERE p.destinationId = ? "
				+ "ORDER BY p.destinationId";

		PriceRowCallBackHandler rowCallbackHandler = new PriceRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, destinationId);

		return rowCallbackHandler.getPrice().get(0);
	}
	
	@Override
	public Price findOneByPutovanjeId(Long putovanjeId) {
		String sql = 
				"SELECT p.id, p.destinationId, p.putovanjeId, p.startDate, p.endDate, p.priceOfTravel, p.numberOfSeats, put.id "
				+ "FROM prices p "
				+ "LEFT JOIN putovanja put on put.id = p.putovanjeId "
				+ "WHERE p.putovanjeId = ? "
				+ "ORDER BY p.putovanjeId";

		PriceRowCallBackHandler rowCallbackHandler = new PriceRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, putovanjeId);

		return rowCallbackHandler.getPrice().get(0);
	}
	
	@Override
	public List<Price> findAllByDestinationId(Long destinationId) {
		String sql = 
				"SELECT p.id, p.destinationId, p.putovanjeId, p.startDate, p.endDate, p.numberOfSeats, p.priceOfTravel "
				+ "FROM prices p "
				+ "WHERE p.destinationId = ? "
				+ "ORDER BY p.destinationId";

		PriceRowCallBackHandler rowCallbackHandler = new PriceRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, destinationId);

		return rowCallbackHandler.getPrice();
	}
	
	@Override
	public List<Price> findAllByPutovanjeId(Long putovanjeId) {
		String sql = 
				"SELECT p.id, p.destinationId, p.putovanjeId, p.startDate, p.endDate, p.numberOfSeats, p.priceOfTravel, put.id "
				+ "FROM prices p "
				+ "LEFT JOIN putovanja put on put.id = p.putovanjeId "		
				+ "WHERE p.putovanjeId = ? "
				+ "ORDER BY p.putovanjeId";

		PriceRowCallBackHandler rowCallbackHandler = new PriceRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, putovanjeId);

		return rowCallbackHandler.getPrice();
	}

	@Transactional
	@Override
	public int save(Price price) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO prices (destinationId, putovanjeId, startDate, endDate, numberOfSeats, priceOfTravel)"
						+ " VALUES (?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setLong(index++, price.getDestinationId());
				preparedStatement.setLong(index++, price.getPutovanjeId());
				preparedStatement.setString(index++, price.getFormattedStartDate());
				preparedStatement.setString(index++, price.getFormattedEndDate());
				preparedStatement.setInt(index++, price.getNumberOfSeats());
				preparedStatement.setDouble(index++, price.getPriceOfTravel());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}

	@Transactional
	@Override
	public int update(Price price) {
		String sql = "UPDATE prices SET destinationId = ?, putovanjeId = ?, startDate = ?, endDate = ?, numberOfSeats = ?, priceOfTravel = ? WHERE id = ?";
		boolean uspeh = jdbcTemplate.update(sql, price.getDestinationId(), price.getPutovanjeId(), price.getFormattedStartDate(),
				price.getFormattedEndDate(), price.getNumberOfSeats(), price.getPriceOfTravel(), price.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM prices WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}
