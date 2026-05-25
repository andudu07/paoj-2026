package com.pao.proiect.catalog.repository;

import com.pao.proiect.catalog.model.db.MaterieDb;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaterieRepository implements Repository<MaterieDb, Long> {

    private Connection getConn() throws SQLException {
        try {
            return DatabaseConnection.getInstance().getConnection();
        } catch (IOException e) {
            throw new SQLException("Eroare conexiune DB: " + e.getMessage(), e);
        }
    }

    private MaterieDb mapRow(ResultSet rs) throws SQLException {
        MaterieDb m = new MaterieDb();
        m.setId(rs.getLong("id"));
        m.setCod(rs.getString("cod"));
        m.setDenumire(rs.getString("denumire"));
        m.setCredite(rs.getInt("credite"));
        m.setProfesorId(rs.getLong("profesor_id"));
        return m;
    }

    @Override
    public void save(MaterieDb materie) throws SQLException {
        String sql = "INSERT INTO materie (cod, denumire, credite, profesor_id) "
                   + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, materie.getCod());
            ps.setString(2, materie.getDenumire());
            ps.setInt(3, materie.getCredite());
            ps.setLong(4, materie.getProfesorId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) materie.setId(keys.getLong(1));
            }
        }
    }

    @Override
    public Optional<MaterieDb> findById(Long id) throws SQLException {
        String sql = "SELECT id, cod, denumire, credite, profesor_id "
                   + "FROM materie WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        }
    }

    @Override
    public List<MaterieDb> findAll() throws SQLException {
        String sql = "SELECT id, cod, denumire, credite, profesor_id "
                   + "FROM materie ORDER BY id";
        List<MaterieDb> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public void update(MaterieDb materie) throws SQLException {
        String sql = "UPDATE materie SET cod=?, denumire=?, credite=?, profesor_id=? "
                   + "WHERE id=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, materie.getCod());
            ps.setString(2, materie.getDenumire());
            ps.setInt(3, materie.getCredite());
            ps.setLong(4, materie.getProfesorId());
            ps.setLong(5, materie.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM materie WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    /** Materiile predate de un profesor (JOIN #1). */
    public List<MaterieDb> findByProfesor(long profesorId) throws SQLException {
        String sql = "SELECT m.id, m.cod, m.denumire, m.credite, m.profesor_id "
                   + "FROM materie m "
                   + "JOIN profesor p ON m.profesor_id = p.id "
                   + "WHERE p.id = ? ORDER BY m.cod";
        List<MaterieDb> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, profesorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }
}
