package dao;

import database.DatabaseConnection;
import model.Transaksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TransaksiDAO {
    private MobilDAO mobilDAO = new MobilDAO();
    private static final int DENDA_PER_HARI = 50000;

    public void sewa(Transaksi t) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String sql = "INSERT INTO transaksi (id_mobil, id_penyewa, tanggal_sewa, tanggal_kembali_rencana, total_biaya) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, t.getIdMobil());
            ps.setInt(2, t.getIdPenyewa());
            ps.setDate(3, t.getTanggalSewa());
            ps.setDate(4, t.getTanggalKembaliRencana());
            ps.setInt(5, t.getTotalBiaya());
            ps.executeUpdate();

            mobilDAO.updateStatus(t.getIdMobil(), "disewa");
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public void kembali(int idTransaksi, Date tglKembaliAktual) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            Transaksi t = getById(idTransaksi);
            long diffMillis = tglKembaliAktual.getTime() - t.getTanggalKembaliRencana().getTime();
            long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
            int denda = diffDays > 0 ? (int) diffDays * DENDA_PER_HARI : 0;

            String sql = "UPDATE transaksi SET tanggal_kembali_aktual=?, denda=?, status=? WHERE id_transaksi=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, tglKembaliAktual);
            ps.setInt(2, denda);
            ps.setString(3, "selesai");
            ps.setInt(4, idTransaksi);
            ps.executeUpdate();

            mobilDAO.updateStatus(t.getIdMobil(), "tersedia");
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public Transaksi getById(int id) throws SQLException {
        String sql = "SELECT * FROM transaksi WHERE id_transaksi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractFromResultSet(rs);
            }
        }
        return null;
    }

    public List<Transaksi> getBerjalan() throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi WHERE status = 'berjalan'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractFromResultSet(rs));
            }
        }
        return list;
    }

    public int hitungTotalBiaya(Date tglSewa, Date tglKembali, int hargaPerHari) {
        long diffMillis = tglKembali.getTime() - tglSewa.getTime();
        long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
        if (diffDays == 0) diffDays = 1;
        return (int) diffDays * hargaPerHari;
    }

    private Transaksi extractFromResultSet(ResultSet rs) throws SQLException {
        Transaksi t = new Transaksi();
        t.setIdTransaksi(rs.getInt("id_transaksi"));
        t.setIdMobil(rs.getInt("id_mobil"));
        t.setIdPenyewa(rs.getInt("id_penyewa"));
        t.setTanggalSewa(rs.getDate("tanggal_sewa"));
        t.setTanggalKembaliRencana(rs.getDate("tanggal_kembali_rencana"));
        t.setTanggalKembaliAktual(rs.getDate("tanggal_kembali_aktual"));
        t.setTotalBiaya(rs.getInt("total_biaya"));
        t.setDenda(rs.getInt("denda"));
        t.setStatus(rs.getString("status"));
        return t;
    }
}