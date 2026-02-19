package dao;

import database.DatabaseConnection;
import model.Penyewa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PenyewaDAO {
    
    public void tambah(Penyewa p) throws SQLException {
        String sql = "INSERT INTO penyewa (no_ktp, nama_lengkap, no_telepon, alamat) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNoKtp());
            ps.setString(2, p.getNamaLengkap());
            ps.setString(3, p.getNoTelepon());
            ps.setString(4, p.getAlamat());
            ps.executeUpdate();
        }
    }

    public List<Penyewa> getAll() throws SQLException {
        List<Penyewa> list = new ArrayList<>();
        String sql = "SELECT * FROM penyewa ORDER BY nama_lengkap";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractFromResultSet(rs));
            }
        }
        return list;
    }

    private Penyewa extractFromResultSet(ResultSet rs) throws SQLException {
        Penyewa p = new Penyewa();
        p.setIdPenyewa(rs.getInt("id_penyewa"));
        p.setNoKtp(rs.getString("no_ktp"));
        p.setNamaLengkap(rs.getString("nama_lengkap"));
        p.setNoTelepon(rs.getString("no_telepon"));
        p.setAlamat(rs.getString("alamat"));
        return p;
    }
}