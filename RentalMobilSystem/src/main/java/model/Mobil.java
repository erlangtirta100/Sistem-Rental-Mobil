package model;

public class Mobil {
    private int idMobil;
    private String merk;
    private String model;
    private String noPlat;
    private int hargaSewaPerHari;
    private String status;

    public Mobil() {}

    public Mobil(int idMobil, String merk, String model, String noPlat, 
                 int hargaSewaPerHari, String status) {
        this.idMobil = idMobil;
        this.merk = merk;
        this.model = model;
        this.noPlat = noPlat;
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.status = status;
    }

    public int getIdMobil() { return idMobil; }
    public void setIdMobil(int idMobil) { this.idMobil = idMobil; }
    public String getMerk() { return merk; }
    public void setMerk(String merk) { this.merk = merk; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getNoPlat() { return noPlat; }
    public void setNoPlat(String noPlat) { this.noPlat = noPlat; }
    public int getHargaSewaPerHari() { return hargaSewaPerHari; }
    public void setHargaSewaPerHari(int hargaSewaPerHari) { this.hargaSewaPerHari = hargaSewaPerHari; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return idMobil + ". " + merk + " " + model + " [" + noPlat + 
               "] - Rp " + hargaSewaPerHari + "/hari (" + status + ")";
    }
}