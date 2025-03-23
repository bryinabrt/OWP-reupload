package com.ftn.PrviMavenVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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

import com.ftn.PrviMavenVebProjekat.dao.ShoppingCartDAO;
import com.ftn.PrviMavenVebProjekat.model.Destinacija;
import com.ftn.PrviMavenVebProjekat.model.Kategorija;
import com.ftn.PrviMavenVebProjekat.model.Price;
import com.ftn.PrviMavenVebProjekat.model.Putovanje;
import com.ftn.PrviMavenVebProjekat.model.ShoppingCart;
import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;
import com.ftn.PrviMavenVebProjekat.model.TipJedinice;

@Repository
public class ShoppingCartDAOImpl implements ShoppingCartDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class ShoppingCartRowCallBackHandler implements RowCallbackHandler {
		private Map<Long, ShoppingCart> shoppingCarts = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong("id");
			Long korisnikId = resultSet.getLong("korisnikId");
			Long rezervisanoPutovanjeId = resultSet.getLong("rezervisanoPutovanjeId");
			Integer brojPutnika = resultSet.getInt("brojPutnika");
			Double ukupnaCena = resultSet.getDouble("ukupnaCena");
			Long pricesId = resultSet.getLong("pricesId");
			
			String sifraPutovanja = resultSet.getString("sifraPutovanja");
			Long idDestinacije = resultSet.getLong("idDestinacije");
			Long idPrevoznoSredstvo = resultSet.getLong("idPrevoznoSredstvo");
			Long idSmestajnaJedinica = resultSet.getLong("idSmestajnaJedinica");
			Kategorija kategorija = Kategorija.valueOf(resultSet.getString("kategorija"));
			Integer brojNocenja = resultSet.getInt("brojNocenja");
			String slika = resultSet.getString("slika");
			Putovanje putovanje = new Putovanje(sifraPutovanja, idDestinacije, idPrevoznoSredstvo, idSmestajnaJedinica, kategorija, 
					brojNocenja, slika);
			
			Long destinationId = resultSet.getLong("idDestinacije");
			Long putovanjeId = resultSet.getLong("rezervisanoPutovanjeId");
			LocalDateTime startDate = resultSet.getTimestamp("startDate").toLocalDateTime();
			LocalDateTime endDate = resultSet.getTimestamp("startDate").toLocalDateTime();
			Integer numberOfSeats = resultSet.getInt("numberOfSeats");
			Double priceOfTravel = resultSet.getDouble("priceOfTravel");
			Price price = new Price(destinationId, putovanjeId, startDate, endDate, numberOfSeats, priceOfTravel);
			
			ShoppingCart shoppingCart = shoppingCarts.get(id);
			if (shoppingCart == null) {
				List<Putovanje> putovanja = new ArrayList<Putovanje>();
				List<Price> prices = new ArrayList<Price>();
				putovanja.add(putovanje);
				prices.add(price);
				shoppingCart = new ShoppingCart(id, korisnikId, rezervisanoPutovanjeId, brojPutnika, ukupnaCena, pricesId);
				shoppingCart.setPrices(prices);
				shoppingCart.setPutovanja(putovanja);
				shoppingCarts.put(shoppingCart.getId(), shoppingCart);
			} else {
				// ako putovanje vec postoji u kolekciji samo mu dodaj destinaciju ako ga on vec nema
				// proveri da li u listi destinacija imas destinaciju koju trazis
				boolean found = false;
				for (Price pr : shoppingCart.getPrices()) {
					if (pr.getId() == pricesId) {
						found = true;
						break;
					}
				}
				// ako ga nemas dodaj!
				if (!found) {
					shoppingCart.getPrices().add(price);
				}
				
				for (Putovanje pu : shoppingCart.getPutovanja()) {
					if (pu.getId() == rezervisanoPutovanjeId) {
						found = true;
						break;
					}
				}
				
				if (!found) {
					shoppingCart.getPutovanja().add(putovanje);
				}
			}

		}
		public List<ShoppingCart> getSve() {
			System.out.println("Number of users in map: " + shoppingCarts.size());
			return new ArrayList<>(shoppingCarts.values());
		}
	}
	
	@Override
	public ShoppingCart findOne(Long id) {
		String sql = 
				"SELECT sc.id, sc.korisnikId, sc.rezervisanoPutovanjeId, sc.brojPutnika, sc.ukupnaCena, sc.pricesId, "
				+ "put.id, put.sifraPutovanja, put.idDestinacije, put.idPrevoznoSredstvo, put.idSmestajnaJedinica, put.kategorija, put.brojNocenja, put.slika, "
				+ "pr.id, pr.destinationId, pr.putovanjeId, pr.startDate, pr.endDate, pr.priceOfTravel "
				+ "FROM shoppingCart sc "
				+ "LEFT JOIN putovanja put on put.id = sc.rezervisanoPutovanjeId "
				+ "LEFT JOIN prices pr on pr.id = pricesId "
				+ "WHERE sc.id = ? "
				+ "ORDER BY sc.id";

		ShoppingCartRowCallBackHandler rowCallbackHandler = new ShoppingCartRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getSve().get(0);
	}
	
	@Override
	public List<ShoppingCart> findAllByKorisnik(Long korisnikId) {
		String sql = 
				"SELECT sc.id, sc.korisnikId, sc.rezervisanoPutovanjeId, sc.brojPutnika, sc.ukupnaCena, sc.pricesId, "
				+ "put.id, put.sifraPutovanja, put.idDestinacije, put.idPrevoznoSredstvo, put.idSmestajnaJedinica, put.kategorija, put.brojNocenja, put.slika, "
				+ "pr.id, pr.destinationId, pr.putovanjeId, pr.startDate, pr.endDate, pr.priceOfTravel "
				+ "FROM shoppingCart sc "
				+ "LEFT JOIN putovanja put on put.id = sc.rezervisanoPutovanjeId "
				+ "LEFT JOIN prices pr on pr.id = pricesId "
				+ "ORDER BY sc.id";

		ShoppingCartRowCallBackHandler rowCallbackHandler = new ShoppingCartRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getSve();
	}
	
	@Transactional
	@Override
	public int save(ShoppingCart shoppingCart) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO shoppingCart (korisnikId, rezervisanoPutovanjeId, brojPutnika, ukupnaCena, pricesId)"
						+ " VALUES (?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setLong(index++, shoppingCart.getKorisnikId());
				preparedStatement.setLong(index++, shoppingCart.getRezervisanoPutovanjeId());
				preparedStatement.setInt(index++, shoppingCart.getBrojPutnika());
				preparedStatement.setDouble(index++, shoppingCart.getUkupnaCena());
				preparedStatement.setLong(index++, shoppingCart.getPricesId());
				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(ShoppingCart shoppingCart) {        
	    String sql = "UPDATE shoppingCart SET korisnikId = ?, brojPutnika = ?, ukupnaCena = ?, pricesId = ?"
	                + " WHERE id = ?";
	    boolean uspeh = jdbcTemplate.update(sql,
	    		shoppingCart.getKorisnikId(),
	            shoppingCart.getBrojPutnika(),
	            shoppingCart.getUkupnaCena(),
	            shoppingCart.getPricesId(),
	            shoppingCart.getId()) == 1;
	    
	    return uspeh ? 1 : 0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM shoppingCart WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
