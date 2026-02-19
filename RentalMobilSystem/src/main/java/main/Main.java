package main;

import dao.MobilDAO;
import dao.PenyewaDAO;
import dao.TransaksiDAO;
import model.Mobil;
import model.Penyewa;
import model.Transaksi;
import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    
    private static MobilDAO mobilDAO = new MobilDAO();
    private static PenyewaDAO penyewaDAO = new PenyewaDAO();
    private static TransaksiDAO transaksiDAO = new TransaksiDAO();

    public static void main(String[] args) {
        // Set look and feel Windows
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        while (true) {
            String menu = "=== SISTEM PENYEWAAN MOBIL ===\n\n"
                    + "1. Kelola Mobil\n"
                    + "2. Kelola Penyewa\n"
                    + "3. Transaksi Sewa\n"
                    + "4. Transaksi Pengembalian\n"
                    + "5. Lihat Transaksi Berjalan\n"
                    + "0. Keluar\n\n"
                    + "Pilih menu:";
            
            String input = JOptionPane.showInputDialog(null, menu, "Menu Utama", JOptionPane.QUESTION_MESSAGE);
            if (input == null) System.exit(0);
            
            int pilihan;
            try {
                pilihan = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Masukkan angka!", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            
            switch (pilihan) {
                case 1: menuMobil(); break;
                case 2: menuPenyewa(); break;
                case 3: menuSewa(); break;
                case 4: menuKembali(); break;
                case 5: lihatTransaksiBerjalan(); break;
                case 0: System.exit(0);
                default: JOptionPane.showMessageDialog(null, "Pilihan tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void menuMobil() {
        String[] options = {"Lihat Semua", "Tambah Baru", "Hapus", "Kembali"};
        int pilih = JOptionPane.showOptionDialog(null, "Pilih operasi:", "Kelola Mobil",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        try {
            switch (pilih) {
                case 0: // Lihat
                    List<Mobil> list = mobilDAO.getAll();
                    StringBuilder sb = new StringBuilder("Daftar Mobil:\n\n");
                    for (Mobil m : list) sb.append(m).append("\n");
                    JOptionPane.showMessageDialog(null, sb.toString(), "Data Mobil", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 1: // Tambah
                    Mobil m = new Mobil();
                    m.setMerk(JOptionPane.showInputDialog("Merk:"));
                    m.setModel(JOptionPane.showInputDialog("Model:"));
                    m.setNoPlat(JOptionPane.showInputDialog("No Plat:").toUpperCase());
                    m.setHargaSewaPerHari(Integer.parseInt(JOptionPane.showInputDialog("Harga/Hari:")));
                    mobilDAO.tambah(m);
                    JOptionPane.showMessageDialog(null, "Mobil berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 2: // Hapus
                    int id = Integer.parseInt(JOptionPane.showInputDialog("ID Mobil yang dihapus:"));
                    mobilDAO.hapus(id);
                    JOptionPane.showMessageDialog(null, "Mobil berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void menuPenyewa() {
        String[] options = {"Lihat Semua", "Tambah Baru", "Kembali"};
        int pilih = JOptionPane.showOptionDialog(null, "Pilih operasi:", "Kelola Penyewa",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        try {
            switch (pilih) {
                case 0:
                    List<Penyewa> list = penyewaDAO.getAll();
                    StringBuilder sb = new StringBuilder("Daftar Penyewa:\n\n");
                    for (Penyewa p : list) sb.append(p).append("\n");
                    JOptionPane.showMessageDialog(null, sb.toString(), "Data Penyewa", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 1:
                    Penyewa p = new Penyewa();
                    p.setNoKtp(JOptionPane.showInputDialog("No KTP:"));
                    p.setNamaLengkap(JOptionPane.showInputDialog("Nama Lengkap:"));
                    p.setNoTelepon(JOptionPane.showInputDialog("No Telepon:"));
                    p.setAlamat(JOptionPane.showInputDialog("Alamat:"));
                    penyewaDAO.tambah(p);
                    JOptionPane.showMessageDialog(null, "Penyewa berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void menuSewa() {
        try {
            // Pilih mobil
            List<Mobil> mobilList = mobilDAO.getTersedia();
            if (mobilList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tidak ada mobil tersedia!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String[] mobilOptions = new String[mobilList.size()];
            for (int i = 0; i < mobilList.size(); i++) mobilOptions[i] = mobilList.get(i).toString();
            
            String mobilPilih = (String) JOptionPane.showInputDialog(null, "Pilih Mobil:", 
                    "Transaksi Sewa", JOptionPane.QUESTION_MESSAGE, null, mobilOptions, mobilOptions[0]);
            if (mobilPilih == null) return;
            
            Mobil mobilDipilih = null;
            for (Mobil m : mobilList) {
                if (mobilPilih.equals(m.toString())) {
                    mobilDipilih = m;
                    break;
                }
            }
            
            // Pilih penyewa
            List<Penyewa> penyewaList = penyewaDAO.getAll();
            String[] penyewaOptions = new String[penyewaList.size()];
            for (int i = 0; i < penyewaList.size(); i++) penyewaOptions[i] = penyewaList.get(i).toString();
            
            String penyewaPilih = (String) JOptionPane.showInputDialog(null, "Pilih Penyewa:", 
                    "Transaksi Sewa", JOptionPane.QUESTION_MESSAGE, null, penyewaOptions, penyewaOptions[0]);
            if (penyewaPilih == null) return;
            
            Penyewa penyewaDipilih = null;
            for (Penyewa p : penyewaList) {
                if (penyewaPilih.equals(p.toString())) {
                    penyewaDipilih = p;
                    break;
                }
            }
            
            // Input tanggal
            String tglSewaStr = JOptionPane.showInputDialog("Tanggal Sewa (YYYY-MM-DD):");
            String tglKembaliStr = JOptionPane.showInputDialog("Tanggal Kembali (YYYY-MM-DD):");
            
            Date tglSewa = Date.valueOf(tglSewaStr);
            Date tglKembali = Date.valueOf(tglKembaliStr);
            
            // Hitung biaya
            int total = transaksiDAO.hitungTotalBiaya(tglSewa, tglKembali, mobilDipilih.getHargaSewaPerHari());
            
            String konfirmasi = "Rincian:\n"
                    + "Mobil: " + mobilDipilih.getMerk() + " " + mobilDipilih.getModel() + "\n"
                    + "Total: Rp " + total + "\n\n"
                    + "Konfirmasi sewa?";
            
            int yesNo = JOptionPane.showConfirmDialog(null, konfirmasi, "Konfirmasi", JOptionPane.YES_NO_OPTION);
            
            if (yesNo == JOptionPane.YES_OPTION) {
                Transaksi t = new Transaksi();
                t.setIdMobil(mobilDipilih.getIdMobil());
                t.setIdPenyewa(penyewaDipilih.getIdPenyewa());
                t.setTanggalSewa(tglSewa);
                t.setTanggalKembaliRencana(tglKembali);
                t.setTotalBiaya(total);
                
                transaksiDAO.sewa(t);
                JOptionPane.showMessageDialog(null, "Transaksi berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void menuKembali() {
        try {
            List<Transaksi> list = transaksiDAO.getBerjalan();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tidak ada transaksi aktif", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String[] transaksiOptions = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Transaksi t = list.get(i);
                transaksiOptions[i] = "ID: " + t.getIdTransaksi() + " | Mobil: " + t.getIdMobil() 
                        + " | Total: Rp " + t.getTotalBiaya();
            }
            
            String pilih = (String) JOptionPane.showInputDialog(null, "Pilih Transaksi:", 
                    "Pengembalian", JOptionPane.QUESTION_MESSAGE, null, transaksiOptions, transaksiOptions[0]);
            if (pilih == null) return;
            
            int idTransaksi = Integer.parseInt(pilih.substring(4, pilih.indexOf(" |")));
            
            String tglStr = JOptionPane.showInputDialog("Tanggal Kembali Aktual (YYYY-MM-DD):");
            Date tglKembali = Date.valueOf(tglStr);
            
            transaksiDAO.kembali(idTransaksi, tglKembali);
            JOptionPane.showMessageDialog(null, "Pengembalian berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void lihatTransaksiBerjalan() {
        try {
            List<Transaksi> list = transaksiDAO.getBerjalan();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tidak ada transaksi aktif", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            StringBuilder sb = new StringBuilder("Transaksi Berjalan:\n\n");
            for (Transaksi t : list) {
                sb.append("ID: ").append(t.getIdTransaksi())
                  .append(" | Mobil: ").append(t.getIdMobil())
                  .append(" | Penyewa: ").append(t.getIdPenyewa())
                  .append(" | Total: Rp ").append(t.getTotalBiaya())
                  .append("\n");
            }
            
            JOptionPane.showMessageDialog(null, sb.toString(), "Transaksi Aktif", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}