package com.ftn.PrviMavenVebProjekat.dao.impl;

import com.ftn.PrviMavenVebProjekat.dao.ZahtevKarticeDAO;
import com.ftn.PrviMavenVebProjekat.model.LoyaltyKartica;
import com.ftn.PrviMavenVebProjekat.model.ZahtevKartice;
import com.ftn.PrviMavenVebProjekat.service.ZahtevKarticeService;
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
public class ZahtevKarticeDAOImpl implements ZahtevKarticeDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private class ZahtevKarticeRowCallbackHandler implements RowCallbackHandler {

        private Map<Long, ZahtevKartice> zahteviKartice = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            Long id = resultSet.getLong(index++);
            System.out.println("ID: "+id);
            Long idKorisnika = resultSet.getLong(index++);
            System.out.println("ID KOR: "+idKorisnika);

            ZahtevKartice zahtevKartice = zahteviKartice.get(id);
            if (zahtevKartice == null) {
                zahtevKartice = new ZahtevKartice(id, idKorisnika);
                zahteviKartice.put(zahtevKartice.getId(), zahtevKartice);
            }
        }
        public List<ZahtevKartice> getZahtevi() { return new ArrayList<>(zahteviKartice.values()); }
    }

    @Override
    public List<ZahtevKartice> getAll() {
        String sql = "SELECT z.id, z.idKorisnika " +
                "FROM zahtevikartice z " +
                "ORDER BY z.id";
        ZahtevKarticeRowCallbackHandler rowCallbackHandler = new ZahtevKarticeRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);
        return rowCallbackHandler.getZahtevi();
    }

    @Override
    public ZahtevKartice getByKorId(Long idKorisnika) {
        String sql = "SELECT z.id, z.idKorisnika " +
                "FROM zahtevikartice z " +
                "WHERE z.idKorisnika = ? " +
                "ORDER BY z.idKorisnika";
        ZahtevKarticeRowCallbackHandler rowCallbackHandler = new ZahtevKarticeRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, idKorisnika);
        List<ZahtevKartice> zahtevi = rowCallbackHandler.getZahtevi();
        System.out.println(zahtevi);
        return zahtevi.isEmpty() ? null : zahtevi.get(0);
    }

    @Override
    @Transactional
    public int naruciKarticu(ZahtevKartice zahtevKartice) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO zahtevikartice (idKorisnika) VALUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setLong(index++, zahtevKartice.getIdKorisnika());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh?1:0;
    }

    @Transactional
    @Override
    public int odbij(Long id) {
        String sql = "DELETE FROM zahtevikartice WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
