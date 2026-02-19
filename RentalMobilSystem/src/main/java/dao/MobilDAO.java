package dao;

import database.DatabaseConnection;
import model.Mobil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MobilDAO {
    
    public void tambah(Mobil m) throws SQLException {
        String sql = "INSERT INTO mobil (merk, model, no_plat, harga_sewa_per_hari) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getMerk());
            ps.setString(2, m.getModel());
            ps.setString(3, m.getNoPlat());
            ps.setInt(4, m.getHargaSewaPerHari());
            ps.executeUpdate();
        }
    }

    public List<Mobil> getAll() throws SQLException {
        List<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil ORDER BY merk, model";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractFromResultSet(rs));
            }
        }
        return list;
    }

    public List<Mobil> getTersedia() throws SQLException {
        List<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil WHERE status = 'tersedia' ORDER BY merk, model";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractFromResultSet(rs));
            }
        }
        return list;
    }

    public void updateStatus(int idMobil, String status) throws SQLException {
        String sql = "UPDATE mobil SET status = ? WHERE id_mobil = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, idMobil);
            ps.executeUpdate();
        }
    }

    public void hapus(int idMobil) throws SQLException {
        String sql = "DELETE FROM mobil WHERE id_mobil = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMobil);
            ps.executeUpdate();
        }
    }

    private Mobil extractFromResultSet(ResultSet rs) throws SQLException {
        Mobil m = new Mobil();
        m.setIdMobil(rs.getInt("id_mobil"));
        m.setMerk(rs.getString("merk"));
        m.setModel(rs.getString("model"));
        m.setNoPlat(rs.getString("no_plat"));
        m.setHargaSewaPerHari(rs.getInt("harga_sewa_per_hari"));
        m.setStatus(rs.getString("status"));
        return m;
    }
}