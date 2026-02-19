package model;

public class Penyewa {
    private int idPenyewa;
    private String noKtp;
    private String namaLengkap;
    private String noTelepon;
    private String alamat;

    public Penyewa() {}

    public Penyewa(int idPenyewa, String noKtp, String namaLengkap, 
                   String noTelepon, String alamat) {
        this.idPenyewa = idPenyewa;
        this.noKtp = noKtp;
        this.namaLengkap = namaLengkap;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
    }

    public int getIdPenyewa() { return idPenyewa; }
    public void setIdPenyewa(int idPenyewa) { this.idPenyewa = idPenyewa; }
    public String getNoKtp() { return noKtp; }
    public void setNoKtp(String noKtp) { this.noKtp = noKtp; }
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    @Override
    public String toString() {
        return idPenyewa + ". " + namaLengkap + " (KTP: " + noKtp + ")";
    }
}