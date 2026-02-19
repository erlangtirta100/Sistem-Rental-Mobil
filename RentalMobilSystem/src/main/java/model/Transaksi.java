package model;

import java.sql.Date;

public class Transaksi {
    private int idTransaksi;
    private int idMobil;
    private int idPenyewa;
    private Date tanggalSewa;
    private Date tanggalKembaliRencana;
    private Date tanggalKembaliAktual;
    private int totalBiaya;
    private int denda;
    private String status;

    public Transaksi() {}

    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }
    public int getIdMobil() { return idMobil; }
    public void setIdMobil(int idMobil) { this.idMobil = idMobil; }
    public int getIdPenyewa() { return idPenyewa; }
    public void setIdPenyewa(int idPenyewa) { this.idPenyewa = idPenyewa; }
    public Date getTanggalSewa() { return tanggalSewa; }
    public void setTanggalSewa(Date tanggalSewa) { this.tanggalSewa = tanggalSewa; }
    public Date getTanggalKembaliRencana() { return tanggalKembaliRencana; }
    public void setTanggalKembaliRencana(Date tanggalKembaliRencana) { this.tanggalKembaliRencana = tanggalKembaliRencana; }
    public Date getTanggalKembaliAktual() { return tanggalKembaliAktual; }
    public void setTanggalKembaliAktual(Date tanggalKembaliAktual) { this.tanggalKembaliAktual = tanggalKembaliAktual; }
    public int getTotalBiaya() { return totalBiaya; }
    public void setTotalBiaya(int totalBiaya) { this.totalBiaya = totalBiaya; }
    public int getDenda() { return denda; }
    public void setDenda(int denda) { this.denda = denda; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}