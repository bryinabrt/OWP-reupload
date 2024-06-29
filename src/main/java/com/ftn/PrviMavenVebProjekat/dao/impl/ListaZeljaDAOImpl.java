package com.ftn.PrviMavenVebProjekat.dao.impl;

import com.ftn.PrviMavenVebProjekat.dao.ListaZeljaDAO;
import com.ftn.PrviMavenVebProjekat.model.ListaZelja;
import com.ftn.PrviMavenVebProjekat.model.Rezervacije;
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
public class ListaZeljaDAOImpl implements ListaZeljaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ListaZeljaRowCallBackHandler implements RowCallbackHandler {
        private Map<Long, ListaZelja> listeZelja = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            Long id = resultSet.getLong(index++);
            Long idKorisnika = resultSet.getLong(index++);
            Long idPutovanja = resultSet.getLong(index++);

            ListaZelja listaZelja = listeZelja.get(id);
            if (listaZelja == null) {
                listaZelja = new ListaZelja(id, idKorisnika, idPutovanja);
                listeZelja.put(listaZelja.getId(), listaZelja); // dodavanje u kolekciju
            }
        }

        public List<ListaZelja> getListeZelja() {
            return new ArrayList<>(listeZelja.values());
        }
    }

    @Override
    public List<ListaZelja> getAll() {
        String sql =
                "SELECT l.id, l.idKorisnika, l.idPutovanja "+
                        "FROM listazelja l " +
                        "ORDER BY l.id";

        ListaZeljaDAOImpl.ListaZeljaRowCallBackHandler rowCallbackHandler = new ListaZeljaDAOImpl.ListaZeljaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);

        return rowCallbackHandler.getListeZelja();
    }

    @Override
    public List<ListaZelja> getAllByKorId(Long idKorisnika) {
        String sql =
                "SELECT l.id, l.idKorisnika, l.idPutovanja "+
                        "FROM listazelja l "+
                        "WHERE l.idKorisnika = ? "+
                        "ORDER BY l.id";
        ListaZeljaDAOImpl.ListaZeljaRowCallBackHandler rowCallbackHandler = new ListaZeljaDAOImpl.ListaZeljaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, idKorisnika);

        return rowCallbackHandler.getListeZelja();
    }

    @Override
    public ListaZelja getOneById(Long id) {
        String sql =
                "SELECT l.id, l.idKorisnika, l.idPutovanja " +
                        "FROM listazelja l " +
                        "WHERE l.id = ? " +
                        "ORDER BY l.id";

        ListaZeljaDAOImpl.ListaZeljaRowCallBackHandler rowCallbackHandler = new ListaZeljaDAOImpl.ListaZeljaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, id);

        return rowCallbackHandler.getListeZelja().get(0);
    }

    @Transactional
    @Override
    public int save(ListaZelja listaZelja) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO listazelja (idKorisnika, idPutovanja)"
                        + " VALUES (?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setLong(index++, listaZelja.getIdKorisnika());
                preparedStatement.setLong(index++, listaZelja.getIdPutovanja());

                return preparedStatement;
            }

        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh?1:0;
    }

    @Transactional
    @Override
    public int delete(Long id) {
        String sql =
                "DELETE FROM listazelja WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }



}
