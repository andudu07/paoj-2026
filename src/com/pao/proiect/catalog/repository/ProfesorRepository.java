package com.pao.proiect.catalog.repository;

import com.pao.proiect.catalog.model.db.ProfesorDb;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfesorRepository implements Repository<ProfesorDb, Long> {

    private Connection getConn() throws SQLException {
        try {
            return DatabaseConnection.getInstance().getConnection();
        } catch (IOException e) {
            throw new SQLException("Eroare conexiune DB: " + e.getMessage(), e);
        }
    }

    private ProfesorDb mapRow(ResultSet rs) throws SQLException {
        ProfesorDb p = new ProfesorDb();
        p.setId(rs.getLong("id"));
        p.setNume(rs.getString("nume"));
        p.setPrenume(rs.getString("prenume"));
        p.setEmail(rs.getString("email"));
        p.setTitlu(rs.getString("titlu"));
        p.setDepartament(rs.getString("departament"));
        return p;
    }

    @Override
    public void save(ProfesorDb profesor) throws SQLException {
        String sql = "INSERT INTO profesor (nume, prenume, email, titlu, departament) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, profesor.getNume());
            ps.setString(2, profesor.getPrenume());
            ps.setString(3, profesor.getEmail());
            ps.setString(4, profesor.getTitlu());
            ps.setString(5, profesor.getDepartament());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) profesor.setId(keys.getLong(1));
            }
        }
    }

    @Override
    public Optional<ProfesorDb> findById(Long id) throws SQLException {
        String sql = "SELECT id, nume, prenume, email, titlu, departament "
                   + "FROM profesor WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        }
    }

    @Override
    public List<ProfesorDb> findAll() throws SQLException {
        String sql = "SELECT id, nume, prenume, email, titlu, departament "
                   + "FROM profesor ORDER BY id";
        List<ProfesorDb> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public void update(ProfesorDb profesor) throws SQLException {
        String sql = "UPDATE profesor SET nume=?, prenume=?, email=?, titlu=?, departament=? "
                   + "WHERE id=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, profesor.getNume());
            ps.setString(2, profesor.getPrenume());
            ps.setString(3, profesor.getEmail());
            ps.setString(4, profesor.getTitlu());
            ps.setString(5, profesor.getDepartament());
            ps.setLong(6, profesor.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM profesor WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
