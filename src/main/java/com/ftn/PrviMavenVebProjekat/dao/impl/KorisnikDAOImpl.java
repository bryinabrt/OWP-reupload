package com.ftn.PrviMavenVebProjekat.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.jdbc.core.RowMapper;
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
			Boolean blokiran = resultSet.getBoolean(index++);

			Korisnik korisnik = korisnici.get(id);
			if (korisnik == null) {
				korisnik = new Korisnik(id, korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, uloga, blokiran);
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
				+ "kor.adresa, kor.brojTelefona, kor.datumRegistracije, kor.uloga, kor.blokiran "
				+ "FROM korisnici kor "
				+ "WHERE kor.uloga != 'administrator'"
				+ "ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKorisnik();
	}
    
	@Override
	public Korisnik findOneById(Long id) {
		String sql = 
				"SELECT kor.id, kor.korisnickoIme, kor.lozinka, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona,"
				+ " kor.datumRegistracije, kor.uloga, kor.blokiran"
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
				+ " kor.datumRegistracije, kor.uloga, kor.blokiran "
				+ "FROM korisnici kor " + 
				"WHERE kor.email = ? " + 
				"ORDER BY kor.email";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, email);

		return rowCallbackHandler.getKorisnik().get(0);
	}
	
	
	@Override
	public Korisnik findOne(String email, String lozinka) {
		try {
			String sql =
					"SELECT kor.id, kor.korisnickoIme, kor.lozinka, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona,"
							+ " kor.datumRegistracije, kor.uloga, kor.blokiran"
							+ " FROM korisnici kor"
							+ " WHERE kor.email = ? and kor.lozinka = ?"
							+ " ORDER BY kor.email and kor.lozinka";

			KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
			jdbcTemplate.query(sql, rowCallbackHandler, email, lozinka);

			return rowCallbackHandler.getKorisnik().get(0);

		} catch(Exception e) {
			return null;
		}
	}

	private class KorisnikRowMapper implements RowMapper<Korisnik> {

		@Override
		public Korisnik mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			String korisnickoIme = rs.getString(index++);
			String lozinka = rs.getString(index++);
			String email = rs.getString(index++);
			String ime = rs.getString(index++);
			String prezime = rs.getString(index++);
			String datumRodjenja = rs.getString(index++);
			String adresa = rs.getString(index++);
			String brojTelefona = rs.getString(index++);
			String datumRegistracije = rs.getString(index++);
			Uloga uloga = Uloga.valueOf(rs.getString(index++));
			boolean blokiran = rs.getBoolean(index++);


			Korisnik kor = new Korisnik (id,korisnickoIme,lozinka,email,ime,prezime,
					datumRodjenja,adresa,brojTelefona,datumRegistracije,uloga,blokiran);
			return kor;
		}
	}


	@Override
	public List<Korisnik> find(String korisnickoIme,String uloga,String sortKI, String sortU) {

		ArrayList<Object> listaArgumenata = new ArrayList<Object>();

		String sql = "SELECT k.id, k.korisnickoIme, k.lozinka, k.email, k.ime, k.prezime,"
				+ " k.datumRodjenja, k.adresa, k.brojTelefona, k.datumRegistracije, k.uloga,k.blokiran"
				+ " from korisnici k";

		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;

		if(korisnickoIme != null && !korisnickoIme.isEmpty()) {
			korisnickoIme = "%" + korisnickoIme + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("k.korisnickoIme LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(korisnickoIme);
		}

		if (uloga != null && !uloga.isEmpty()) {
			if (imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("k.uloga LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add("%" + uloga + "%");
		}


		boolean imaSort = false;

		StringBuffer orderSQL = new StringBuffer(" ORDER BY ");

		if(sortKI != null && !sortKI.isEmpty()) {
			orderSQL.append("k.korisnickoIme " + sortKI);
			imaSort = true;
		}

		if(sortU != null && !sortU.isEmpty()) {
			if(imaSort) {
				orderSQL.append(",k.uloga " + sortU);
			}else {
				orderSQL.append("k.uloga " + sortU);
				imaSort = true;
			}
		}

		StringBuffer orderId = new StringBuffer(" ORDER BY k.id");

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
		List<Korisnik> korisnici = jdbcTemplate.query(sql, listaArgumenata.toArray(), new KorisnikRowMapper());


		return korisnici;
	}
	



	@Transactional
	@Override
	public int save(Korisnik korisnik) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO korisnici (korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona"
						+ ", datumRegistracije, uloga, blokiran) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, false)";

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
				korisnik.getDatumRegistracije(), korisnik.getUloga(), korisnik.getId()) == 1;
		
		return uspeh?1:0;
	}

	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM korisnici WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	@Transactional
	@Override
	public int blokiraj(Long id) {
		String sql = "UPDATE korisnici SET blokiran = true " +
				"WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	@Transactional
	@Override
	public int deblokiraj(Long id) {
		String sql = "UPDATE korisnici SET blokiran = false " +
				"WHERE id = ?";
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
