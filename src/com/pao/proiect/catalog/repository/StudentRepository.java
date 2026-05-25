package com.pao.proiect.catalog.repository;

import com.pao.proiect.catalog.model.db.StudentDb;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements Repository<StudentDb, Long> {

    private Connection getConn() throws SQLException {
        try {
            return DatabaseConnection.getInstance().getConnection();
        } catch (IOException e) {
            throw new SQLException("Eroare conexiune DB: " + e.getMessage(), e);
        }
    }

    private StudentDb mapRow(ResultSet rs) throws SQLException {
        StudentDb s = new StudentDb();
        s.setId(rs.getLong("id"));
        s.setNume(rs.getString("nume"));
        s.setPrenume(rs.getString("prenume"));
        s.setEmail(rs.getString("email"));
        s.setGrupa(rs.getString("grupa"));
        s.setAnStudiu(rs.getInt("an_studiu"));
        return s;
    }

    @Override
    public void save(StudentDb student) throws SQLException {
        String sql = "INSERT INTO student (nume, prenume, email, grupa, an_studiu) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getNume());
            ps.setString(2, student.getPrenume());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getGrupa());
            ps.setInt(5, student.getAnStudiu());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) student.setId(keys.getLong(1));
            }
        }
    }

    @Override
    public Optional<StudentDb> findById(Long id) throws SQLException {
        String sql = "SELECT id, nume, prenume, email, grupa, an_studiu "
                   + "FROM student WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        }
    }

    @Override
    public List<StudentDb> findAll() throws SQLException {
        String sql = "SELECT id, nume, prenume, email, grupa, an_studiu "
                   + "FROM student ORDER BY id";
        List<StudentDb> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public void update(StudentDb student) throws SQLException {
        String sql = "UPDATE student SET nume=?, prenume=?, email=?, grupa=?, an_studiu=? "
                   + "WHERE id=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, student.getNume());
            ps.setString(2, student.getPrenume());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getGrupa());
            ps.setInt(5, student.getAnStudiu());
            ps.setLong(6, student.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    /** Returneaza toti studentii dintr-o grupa. */
    public List<StudentDb> findByGrupa(String grupa) throws SQLException {
        String sql = "SELECT id, nume, prenume, email, grupa, an_studiu "
                   + "FROM student WHERE grupa = ? ORDER BY nume";
        List<StudentDb> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, grupa);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }
}
