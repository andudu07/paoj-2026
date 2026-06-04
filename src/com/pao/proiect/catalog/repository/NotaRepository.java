package com.pao.proiect.catalog.repository;

import com.pao.proiect.catalog.model.db.NotaDb;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotaRepository implements Repository<NotaDb, Long> {

    private Connection getConn() throws SQLException {
        try {
            return DatabaseConnection.getInstance().getConnection();
        } catch (IOException e) {
            throw new SQLException("Eroare conexiune DB: " + e.getMessage(), e);
        }
    }

    private NotaDb mapRow(ResultSet rs) throws SQLException {
        NotaDb n = new NotaDb();
        n.setId(rs.getLong("id"));
        n.setStudentId(rs.getLong("student_id"));
        n.setMaterieId(rs.getLong("materie_id"));
        n.setValoare(rs.getDouble("valoare"));
        n.setDataNota(rs.getString("data_nota"));
        return n;
    }

    @Override
    public void save(NotaDb nota) throws SQLException {
        String sql = "INSERT INTO nota (student_id, materie_id, valoare, data_nota) "
                   + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, nota.getStudentId());
            ps.setLong(2, nota.getMaterieId());
            ps.setDouble(3, nota.getValoare());
            ps.setString(4, nota.getDataNota());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) nota.setId(keys.getLong(1));
            }
        }
    }

    @Override
    public Optional<NotaDb> findById(Long id) throws SQLException {
        String sql = "SELECT id, student_id, materie_id, valoare, data_nota "
                   + "FROM nota WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        }
    }

    @Override
    public List<NotaDb> findAll() throws SQLException {
        String sql = "SELECT id, student_id, materie_id, valoare, data_nota "
                   + "FROM nota ORDER BY id";
        List<NotaDb> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public void update(NotaDb nota) throws SQLException {
        String sql = "UPDATE nota SET student_id=?, materie_id=?, valoare=?, data_nota=? "
                   + "WHERE id=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, nota.getStudentId());
            ps.setLong(2, nota.getMaterieId());
            ps.setDouble(3, nota.getValoare());
            ps.setString(4, nota.getDataNota());
            ps.setLong(5, nota.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM nota WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * JOIN #2 — notele unui student cu denumirea materiei si profesorul.
     * Returneaza String[] cu: [prenume+nume student, denumire materie,
     *                          prenume+nume profesor, valoare, data]
     */
    public List<String[]> findNoteCuDetalii(long studentId) throws SQLException {
        String sql =
            "SELECT s.prenume, s.nume, m.denumire, " +
            "       p.prenume AS prof_prenume, p.nume AS prof_nume, " +
            "       n.valoare, n.data_nota " +
            "FROM nota n " +
            "JOIN student s  ON n.student_id  = s.id " +
            "JOIN materie m  ON n.materie_id  = m.id " +
            "JOIN profesor p ON m.profesor_id = p.id " +
            "WHERE n.student_id = ? " +
            "ORDER BY n.data_nota";
        List<String[]> result = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new String[]{
                        rs.getString("prenume") + " " + rs.getString("nume"),
                        rs.getString("denumire"),
                        rs.getString("prof_prenume") + " " + rs.getString("prof_nume"),
                        String.valueOf(rs.getDouble("valoare")),
                        rs.getString("data_nota")
                    });
                }
            }
        }
        return result;
    }

    /**
     * JOIN #3 - media fiecarui student la o materie data.
     * Returneaza String[] cu: [prenume+nume student, grupa, medie]
     */
    public List<String[]> mediePeStudentiLaMaterie(long materieId) throws SQLException {
        String sql =
            "SELECT s.prenume, s.nume, s.grupa, AVG(n.valoare) AS medie " +
            "FROM nota n " +
            "JOIN student s ON n.student_id = s.id " +
            "JOIN materie m ON n.materie_id = m.id " +
            "WHERE n.materie_id = ? " +
            "GROUP BY s.id, s.prenume, s.nume, s.grupa " +
            "ORDER BY medie DESC";
        List<String[]> result = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, materieId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new String[]{
                        rs.getString("prenume") + " " + rs.getString("nume"),
                        rs.getString("grupa"),
                        String.format("%.2f", rs.getDouble("medie"))
                    });
                }
            }
        }
        return result;
    }

    /** Sterge toate notele unui student (util inainte de DELETE student). */
    public void deleteByStudent(long studentId) throws SQLException {
        String sql = "DELETE FROM nota WHERE student_id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, studentId);
            ps.executeUpdate();
        }
    }
}
