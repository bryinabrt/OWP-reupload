package com.ftn.PrviMavenVebProjekat.dao.impl;

import com.ftn.PrviMavenVebProjekat.dao.LoyaltyKarticaDAO;
import com.ftn.PrviMavenVebProjekat.model.LoyaltyKartica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LoyaltyKarticaDAOImpl implements LoyaltyKarticaDAO {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    private class LoyaltyKarticaRowCallBackHandler implements RowCallbackHandler {
        private Map<Long, LoyaltyKartica> loyaltyKartice = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            Long id = resultSet.getLong(index++);
            Long idKorisnika = resultSet.getLong(index++);
            int brojBodova = resultSet.getInt(index++);
            int potrosenNovac = resultSet.getInt(index++);

            LoyaltyKartica loyaltyKartica = loyaltyKartice.get(id);
            if (loyaltyKartica == null) {
                loyaltyKartica = new LoyaltyKartica(id, idKorisnika, brojBodova, potrosenNovac);
                System.out.println("Kartica: "+loyaltyKartica);
                loyaltyKartice.put(loyaltyKartica.getId(), loyaltyKartica); // dodavanje u kolekciju
                System.out.println("Kartice: "+loyaltyKartice);
            }
        }

        public List<LoyaltyKartica> getLoyaltyKartice() {
            return new ArrayList<>(loyaltyKartice.values());
        }
    }

    @Override
    public LoyaltyKartica getByKorId(Long id) {
        String sql = "SELECT l.id, l.idKorisnika, l.brojBodova, l.potrosenNovac " +
                "FROM loyaltykartica l " +
                "WHERE l.idKorisnika = ? " +
                "ORDER BY l.id";
        LoyaltyKarticaDAOImpl.LoyaltyKarticaRowCallBackHandler rowCallbackHandler = new LoyaltyKarticaDAOImpl.LoyaltyKarticaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, id);

        List<LoyaltyKartica> loyaltyKartice = rowCallbackHandler.getLoyaltyKartice();

        if (loyaltyKartice.isEmpty()) {
            return null; // or handle the empty case as needed
        }

        return loyaltyKartice.get(0);
    }

    @Transactional
    @Override
    public int save(LoyaltyKartica loyaltyKartica) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO loyaltykartica (idKorisnika, brojBodova, potrosenNovac) "
                        + " VALUES (?, 5, 0)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setLong(index++, loyaltyKartica.getIdKorisnika());

                return preparedStatement;
            }

        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh?1:0;
    }

    @Transactional
    @Override
    public int update(LoyaltyKartica loyaltyKartica) {
        String sql = "UPDATE loyaltykartica SET brojBodova = ?, potrosenNovac = ? " +
                "WHERE id = ?";
        boolean uspeh = jdbcTemplate.update(sql, loyaltyKartica.getBrojBodova(), loyaltyKartica.getPotrosenNovac(), loyaltyKartica.getId()) == 1;
        return uspeh?1:0;
    }

}
