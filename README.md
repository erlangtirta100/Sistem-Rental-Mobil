# 🚗 RentalMobilSystem

<div align="center">

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![NetBeans](https://img.shields.io/badge/NetBeans-IDE-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

**Sistem Penyewaan Mobil berbasis Java dengan arsitektur Layered (Model-DAO-View)**  
Studi kasus implementasi OOP: Enkapsulasi · Pewarisan · Polimorfisme

</div>

---

## 📋 Daftar Isi

- [Tentang Proyek](#-tentang-proyek)
- [Fitur Utama](#-fitur-utama)
- [Struktur Proyek](#-struktur-proyek)
- [Prasyarat](#-prasyarat)
- [Instalasi & Konfigurasi](#-instalasi--konfigurasi)
- [Penggunaan](#-penggunaan)
- [Diagram UML](#-diagram-uml)
- [Skema Database](#-skema-database)
- [Kontribusi](#-kontribusi)

---

## 📖 Tentang Proyek

**RentalMobilSystem** adalah aplikasi manajemen penyewaan mobil berbasis Java Console yang dikembangkan sebagai studi kasus penerapan konsep **Pemrograman Berorientasi Objek (OOP)**. Proyek ini dirancang untuk membantu pemula memahami arsitektur perangkat lunak berlapis (*layered architecture*) yang memisahkan logika bisnis, akses data, dan antarmuka pengguna.

Proyek ini mencakup:
- Pengelolaan armada mobil dan data pelanggan secara penuh (CRUD)
- Pemrosesan transaksi sewa dan pengembalian dengan perhitungan denda otomatis
- Koneksi ke database MySQL menggunakan JDBC dengan pola Singleton
- Validasi input dan penanganan error yang terstruktur

---

## ✨ Fitur Utama

| Fitur | Deskripsi | Aktor |
|-------|-----------|-------|
| 🚘 **Kelola Data Mobil** | Tambah, edit, hapus, lihat armada kendaraan | Admin |
| 👤 **Kelola Data Penyewa** | Manajemen data pelanggan lengkap | Admin |
| 📊 **Laporan Transaksi** | Lihat seluruh riwayat penyewaan | Admin |
| 🔍 **Cari Mobil** | Filter berdasarkan merk, model, atau status | Petugas |
| 📝 **Proses Penyewaan** | Cek ketersediaan + hitung biaya otomatis | Petugas |
| 🔄 **Proses Pengembalian** | Hitung denda keterlambatan otomatis | Petugas |
| 📈 **Tracking Status** | Pantau status armada secara real-time | Admin |

---

## 📁 Struktur Proyek

```
RentalMobilSystem/
├── 📄 README.md                        # Dokumentasi utama proyek
├── 📄 .gitignore                       # File yang diabaikan Git
├── 📄 LICENSE                          # Lisensi MIT
│
├── 📁 src/
│   └── main/java/com/rentalmobil/
│       ├── 📄 Main.java                # Entry point aplikasi
│       │
│       ├── 📁 model/                   # Kelas entitas domain (POJO)
│       │   ├── 📄 Mobil.java
│       │   ├── 📄 Penyewa.java
│       │   └── 📄 Transaksi.java
│       │
│       ├── 📁 dao/                     # Data Access Object (CRUD ke DB)
│       │   ├── 📄 MobilDAO.java
│       │   ├── 📄 PenyewaDAO.java
│       │   └── 📄 TransaksiDAO.java
│       │
│       ├── 📁 database/               # Manajemen koneksi database
│       │   └── 📄 DatabaseConnection.java
│       │
│       ├── 📁 view/                   # Antarmuka pengguna (Console)
│       │   ├── 📄 MenuMobil.java
│       │   ├── 📄 MenuPenyewa.java
│       │   └── 📄 MenuTransaksi.java
│       │
│       └── 📁 utils/                  # Kelas utilitas
│           ├── 📄 DateUtils.java
│           └── 📄 ValidationUtils.java
│
├── 📁 lib/                            # Library eksternal
│   └── 📄 mysql-connector-j-8.x.jar
│
├── 📁 docs/                           # Dokumentasi & diagram
│   ├── 📄 ARSITEKTUR.md
│   ├── 📁 diagrams/
│   │   ├── 🖼️ class_diagram.png
│   │   ├── 🖼️ erd_diagram.png
│   │   ├── 🖼️ usecase_diagram.png
│   │   ├── 🖼️ sequence_diagram.png
│   │   └── 🖼️ activity_diagram.png
│   └── 📁 sql/
│       ├── 📄 schema.sql              # DDL: buat tabel & relasi
│       └── 📄 sample_data.sql         # DML: data contoh
│
└── 📁 test/                           # Unit test
    └── java/com/rentalmobil/
        ├── 📄 MobilDAOTest.java
        └── 📄 TransaksiDAOTest.java
```

---

## 🔧 Prasyarat

Pastikan perangkat Anda telah terpasang:

- **Java JDK 17** atau lebih baru → [Download](https://www.oracle.com/java/technologies/downloads/)
- **Apache NetBeans IDE 19+** → [Download](https://netbeans.apache.org/front/main/download/)
- **MySQL Server 8.0+** → [Download](https://dev.mysql.com/downloads/mysql/)
- **HeidiSQL** (opsional, untuk manajemen DB visual) → [Download](https://www.heidisql.com/download.php)
- **MySQL Connector/J 8.x** (JDBC Driver) → [Download](https://dev.mysql.com/downloads/connector/j/)

---

## 🚀 Instalasi & Konfigurasi

### 1. Clone Repositori

```bash
git clone https://github.com/username/RentalMobilSystem.git
cd RentalMobilSystem
```

### 2. Setup Database

Buka HeidiSQL atau MySQL Workbench, lalu jalankan:

```bash
mysql -u root -p < docs/sql/schema.sql
mysql -u root -p rental_mobil < docs/sql/sample_data.sql
```

### 3. Konfigurasi Koneksi Database

Edit file `src/main/java/com/rentalmobil/database/DatabaseConnection.java`:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/rental_mobil";
private static final String USER     = "root";       // sesuaikan username Anda
private static final String PASSWORD = "password";   // sesuaikan password Anda
```

### 4. Tambahkan Library JDBC

Di NetBeans:
1. Klik kanan proyek → **Properties** → **Libraries**
2. Klik **Add JAR/Folder**
3. Pilih `lib/mysql-connector-j-8.x.jar`

### 5. Jalankan Aplikasi

```bash
# Compile
javac -cp lib/mysql-connector-j-8.x.jar -d out src/main/java/com/rentalmobil/**/*.java

# Run
java -cp out:lib/mysql-connector-j-8.x.jar com.rentalmobil.Main
```

Atau tekan **F6** di NetBeans IDE.

---

## 💻 Penggunaan

Setelah aplikasi berjalan, navigasi menu menggunakan angka:

```
========================================
     SISTEM PENYEWAAN MOBIL v1.0
========================================
 1. Kelola Data Mobil
 2. Kelola Data Penyewa
 3. Proses Penyewaan
 4. Proses Pengembalian
 5. Lihat Transaksi Berjalan
 6. Lihat Laporan Transaksi
 0. Keluar
========================================
Pilihan Anda: _
```

---

## 📐 Diagram UML

Semua diagram tersedia di folder [`docs/diagrams/`](docs/diagrams/):

| Diagram | Preview |
|---------|---------|
| Class Diagram | [`class_diagram.png`](docs/diagrams/class_diagram.png) |
| ERD | [`erd_diagram.png`](docs/diagrams/erd_diagram.png) |
| Use Case Diagram | [`usecase_diagram.png`](docs/diagrams/usecase_diagram.png) |
| Sequence Diagram | [`sequence_diagram.png`](docs/diagrams/sequence_diagram.png) |
| Activity Diagram | [`activity_diagram.png`](docs/diagrams/activity_diagram.png) |

---

## 🗄️ Skema Database

```sql
-- Tiga tabel utama dengan relasi One-to-Many
mobil       (id_mobil PK, merk, model, no_plat UNIQUE, harga_sewa_per_hari, status)
penyewa     (id_penyewa PK, no_ktp UNIQUE, nama_lengkap, no_telepon, alamat)
transaksi   (id_transaksi PK, id_mobil FK, id_penyewa FK, tanggal_sewa,
             tanggal_kembali_rencana, tanggal_kembali_aktual, total_biaya, denda, status)
```

> Detail lengkap lihat [`docs/sql/schema.sql`](docs/sql/schema.sql)

---

## 🤝 Kontribusi

Kontribusi sangat diterima! Berikut cara berkontribusi:

1. **Fork** repositori ini
2. Buat branch fitur baru: `git checkout -b fitur/nama-fitur`
3. Commit perubahan: `git commit -m "feat: menambahkan fitur X"`
4. Push ke branch: `git push origin fitur/nama-fitur`
5. Buat **Pull Request**

Lihat [CONTRIBUTING.md](CONTRIBUTING.md) untuk panduan lebih lengkap.

---

## 📄 Penyusun

Nama : Erlang Tirta Ramadhan
NIM : 24131310037
Jurusan : Teknologi Informasi
Universitas : Universitas Tangerang Raya

Link Ebook : 
https://ebook.webiot.id/ebooks/panduan-praktis-implementasi-oop-sistem-penyewaan-mobil

---
