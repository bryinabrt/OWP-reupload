package com.ftn.PrviMavenVebProjekat.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.ftn.PrviMavenVebProjekat.dao.KorisnikDAO;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Uloga;

@Repository
public class KorisnikDAOImpl implements KorisnikDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class KorisnikRowCallBackHandler implements RowCallbackHandler {
		
		private Map<Long, Korisnik> korisnici = new LinkedHashMap<>();
    	
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String korisnickoIme = resultSet.getString(index++);
			String lozinka = resultSet.getString(index++);
			String email = resultSet.getString(index++);
			String ime = resultSet.getString(index++);
			String prezime = resultSet.getString(index++);
			String datumRodjenja = resultSet.getString(index++);
			String adresa = resultSet.getString(index++);
			String brojTelefona = resultSet.getString(index++);
			String datumRegistracije = resultSet.getString(index++);
			Uloga uloga = Uloga.valueOf(resultSet.getString(index++));

			Korisnik korisnik = korisnici.get(id);
			if (korisnik == null) {
				korisnik = new Korisnik(id, korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, uloga);
				korisnici.put(korisnik.getId(), korisnik);
			}

		}
		public List<Korisnik> getKorisnik() {
			System.out.println("Number of users in map: " + korisnici.size());
			return new ArrayList<>(korisnici.values());
		}
    }

	
	@Override
	public List<Korisnik> findAll() {
		String sql = 
				"SELECT kor.id, kor.korisnickoIme, kor.lozinka, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, "
				+ "kor.adresa, kor.brojTelefona, kor.datumRegistracije, kor.uloga "
				+ "FROM korisnici kor "
				+ "ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKorisnik();
	}
    
	@Override
	public Korisnik findOneById(Long id) {
		String sql = 
				"SELECT kor.id, kor.korisnickoIme, kor.lozinka, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona,"
				+ " kor.datumRegistracije, kor.uloga"
				+ " FROM korisnici kor " + 
				"WHERE kor.id = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getKorisnik().get(0);
	}
	
	
	@Override
	public Korisnik findOne(String email) {
		String sql = 
				"SELECT kor.id, kor.korisnickoIme, kor.lozinka, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona,"
				+ " kor.datumRegistracije, kor.uloga"
				+ "FROM korisnici kor " + 
				"WHERE kor.email = ? " + 
				"ORDER BY kor.email";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, email);

		return rowCallbackHandler.getKorisnik().get(0);
	}
	
	
	@Override
	public Korisnik findOne(String email, String lozinka) {
		String sql = 
				"SELECT kor.id, kor.korisnickoIme, kor.lozinka, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona,"
				+ " kor.datumRegistracije, kor.uloga"
				+ " FROM korisnici kor" 
				+ " WHERE kor.email = ? and kor.lozinka = ?" 
				+ " ORDER BY kor.email and kor.lozinka";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, email, lozinka);

		return rowCallbackHandler.getKorisnik().get(0);
	}
	
	@Override
	public List<Korisnik> findOneForSort(String korisnickoIme, String uloga) {
		
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT korisnickoIme, uloga FROM korisnici ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(korisnickoIme!=null) {
			korisnickoIme = "%" + korisnickoIme + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("korisnickoIme LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(korisnickoIme);
		}
		
		if(uloga!=null) {	
			uloga = "%" + uloga + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("korisnickoIme LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(uloga);
		}
		
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY korisnickoIme";
		else
			sql=sql + " ORDER BY korisnickoIme";
		System.out.println(sql);
		
		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKorisnik();
	}
	



	@Transactional
	@Override
	public int save(Korisnik korisnik) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO korisnici (korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona"
						+ ", datumRegistracije, uloga) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, korisnik.getKorisnickoIme());
				preparedStatement.setString(index++, korisnik.getLozinka());
				preparedStatement.setString(index++, korisnik.getEmail());
				preparedStatement.setString(index++, korisnik.getIme());
				preparedStatement.setString(index++, korisnik.getPrezime());
				preparedStatement.setString(index++, korisnik.getDatumRodjenja());
				preparedStatement.setString(index++, korisnik.getAdresa());
				preparedStatement.setString(index++, korisnik.getBrojTelefona());
				preparedStatement.setString(index++, korisnik.getDatumRegistracije());
				preparedStatement.setString(index++, korisnik.getUloga());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}

	@Transactional
	@Override
	public int update(Korisnik korisnik) {
		String sql = "UPDATE korisnici SET korisnickoIme = ?, lozinka = ?, email = ?, ime = ?, prezime  = ?, datumRodjenja = ?,"
				+ " adresa = ?, brojTelefona = ?, datumRegistracije = ?, uloga = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, korisnik.getKorisnickoIme(), korisnik.getLozinka(), korisnik.getEmail(), korisnik.getIme(),
				korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getBrojTelefona(),
				korisnik.getDatumRegistracije(), korisnik.getUloga()) == 1;
		
		return uspeh?1:0;
	}

	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM korisnici WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	public String datumRegistracije() {
		/*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date datumRegistracije = new Date();
		String strTrenutnoVreme = formatter.format(datumRegistracije);
		return strTrenutnoVreme;*/
		
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);  
		return strDate;
	}
	
}
