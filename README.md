
# Aplikasi Inventori Barang
UTS - Muhammad Raihan Fadhillah 2210010404

## Daftar Isi
- [Deskripsi](#deskripsi)
- [Prerequisites](#prerequisites)
- [Fitur](#fitur)
- [Cara Menjalankan](#cara-menjalankan)
- [Dokumentasi](#dokumentasi)
- [Screenshots](#screenshots)
- [Contoh Penggunaan](#contoh-penggunaan)
- [Pembuat](#pembuat)

## Deskripsi
Aplikasi ini adalah sistem manajemen inventori yang dirancang untuk membantu pengguna dalam mengelola stok barang dan transaksi.

Aplikasi ini memungkinkan pengguna untuk menambah, mengubah, dan menghapus data barang serta mengelola transaksi masuk dan keluar. 

Dengan antarmuka yang sederhana dan intuitif, pengguna dapat dengan mudah mengakses informasi stok dan melakukan pengelolaan barang secara efisien.

## Prerequisites
Sebelum menjalankan aplikasi ini, pastikan Anda telah memenuhi syarat berikut:
1. Java JDK: Pastikan Anda telah menginstal Java Development Kit (JDK) versi 8 atau yang lebih baru.
2. Database SQLite: Aplikasi ini menggunakan SQLite sebagai database. Pastikan Anda memiliki akses ke SQLite.
3. IDE: Disarankan untuk menggunakan Integrated Development Environment (IDE) seperti IntelliJ IDEA, Eclipse, atau NetBeans untuk menjalankan dan mengembangkan aplikasi.
4. Koneksi Internet: Diperlukan untuk mengunduh dependensi jika menggunakan build tools seperti Maven atau Gradle.


## Fitur   


1. Manajemen Barang:
- **Tambah Barang**: Pengguna dapat menambahkan barang baru ke dalam sistem dengan menyertakan nama dan jenis barang.
- **Ubah Barang**: Pengguna dapat memperbarui informasi barang yang sudah ada, termasuk nama dan jenis barang.
- **Hapus Barang**: Pengguna dapat menghapus barang dari sistem jika barang tersebut tidak lagi diperlukan.
- **Tampilkan Daftar Barang**: Menampilkan semua barang yang terdaftar dalam sistem beserta detailnya.

2. Manajemen Stok:
- **Tampilkan Stok**: Menampilkan jumlah stok yang tersedia untuk setiap barang.
- **Integrasi dengan Barang**: Stok barang terhubung dengan data barang, sehingga pengguna dapat melihat informasi stok secara langsung.

3. Manajemen Transaksi:
- **Tambah Transaksi**: Pengguna dapat mencatat transaksi masuk (penambahan stok) dan keluar (pengurangan stok) dengan menyertakan jenis transaksi, jumlah barang, dan tanggal transaksi.
- **Ubah Transaksi**: Memungkinkan pengguna untuk memperbarui informasi transaksi yang telah dicatat.
- **Hapus Transaksi**: Pengguna dapat menghapus transaksi yang tidak lagi relevan.
- **Tampilkan Daftar Transaksi**: Menampilkan semua transaksi yang telah dicatat, termasuk detail seperti nama barang, jenis transaksi, jumlah, dan tanggal.

4. Export dan Import data
- **Export** : Pengguna dapat mengexport data stok ke dalam file berformat.csv.
- **Import** : Pengguna dapat mengimport data dari stok.csv yang ada.


## Cara Menjalankan
1. **Clone atau Download Repository** :
    - Clone repository ini atau download sebagai ZIP dan ekstrak.

2. **Buka Proyek di IDE** :
    - Buka IDE Anda dan import proyek Java yang telah diunduh.

3. **Jalankan Aplikasi**:
    - Jalankan kelas MenuUtama dari IDE Anda.

  
## Dokumentasi
1. Model
    - Model Barang
    ``` java
        /*
         * To change this license header, choose License Headers in Project Properties.
         * To change this template file, choose Tools | Templates
         * and open the template in the editor.
         */
        
        package Model;
        
        // Import library untuk koneksi ke database
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;
        
        /**
         *
         * @author rhnfa
         */
        
        public class BarangHelper {
            private DatabaseConnection conn; // Objek koneksi ke database
            
            // Inisialisasi koneksi
            public BarangHelper() {
                conn = new DatabaseConnection();
            }
            
            // Method untuk menambah data barang
            public void tambahBarang(String namaBarang, String jenisBarang) {
                String query = "INSERT INTO barang(nama_barang, jenis_barang) VALUES(?, ?)";
                try (Connection connection = conn.getConnection();  // Membuka koneksi database
                    PreparedStatement pstmt = connection.prepareStatement(query)) { // Menyiapkan query
                        pstmt.setString(1, namaBarang); // Mengisi parameter pertama dengan nama barang
                        pstmt.setString(2, jenisBarang); // Mengisi parameter kedua dengan jenis barang
                        pstmt.executeUpdate(); // Menjalankan query
                        System.out.println("Barang berhasil ditambahkan."); // Notifikasi sukses
                } catch (SQLException e) {
                    System.err.println("Error saat menambahkan barang: " + e.getMessage()); // Penanganan error
                }
            }
        
            // Method untuk menampilkan data barang
            public List<String[]> tampilData() {
                List<String[]> daftarBarang = new ArrayList<>(); // List untuk menyimpan hasil
                String query = "SELECT * FROM barang"; // Query untuk mengambil semua data
                try (Connection connection = conn.getConnection(); // Membuka koneksi database
                    PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery()) { // Eksekusi query
                        while (resultSet.next()) {
                            int idBarang = resultSet.getInt("id_barang"); // Mengambil ID barang
                            String namaBarang = resultSet.getString("nama_barang"); // Mengambil nama
                            String jenisBarang = resultSet.getString("jenis_barang"); // Mengambil jenis barang
                            daftarBarang.add(new String[]{String.valueOf(idBarang), namaBarang, jenisBarang}); // Menambah ke daftar
                        }
                } catch (SQLException e) {
                    System.err.println("Error saat menampilkan data: " + e.getMessage()); // Penanganan error
                }
                return daftarBarang; // Mengembalikan daftar barang
            }
            
            //Method untuk mengupdate data barang
            public void updateBarang(int idBarang, String namaBarangBaru, String jenisBarangBaru) {
                String query = "UPDATE barang SET nama_barang = ?, jenis_barang = ? WHERE id_barang = ?";
                try (Connection connection = conn.getConnection(); // Perbaikan di sini
                    PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setString(1, namaBarangBaru); // Mengisi parameter pertama dengan namaBarangBaru
                        statement.setString(2, jenisBarangBaru); // Mengisi parameter kedua dengan jenisBarangBaru
                        statement.setInt(3, idBarang); // Mengisi parameter ketiga dengan idBarang
                        statement.executeUpdate(); // Menjalankan query
                        System.out.println("Barang berhasil diperbarui."); // Menampilkan pesan berhasil
                } catch (SQLException e) {
                    System.err.println("Error saat memperbarui barang: " + e.getMessage()); //Penanganan Error
                }
            }
            
            // Method untuk menghapus barang
            public void hapusBarang(int idBarang) {
                String query = "DELETE FROM barang WHERE id_barang = ?";
                try (Connection connection = conn.getConnection(); // Membuka koneksi ke database
                    PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setInt(1, idBarang); // Mengisi parameter pertama dengan idbarang
                        statement.executeUpdate(); // Menjalankan query
                        System.out.println("Barang berhasil dihapus."); // Menampilkan pesan berhasil
                } catch (SQLException e) {
                    System.err.println("Error saat menghapus barang: " + e.getMessage()); // Penanganan Error
                }
            }
            
            //Melakukan join antara stok dan barang untuk mengambil nama barang dan id_stok dari barang yang dipilih
             public List<String[]> getNamaDanId() {
                List<String[]> daftarBarang = new ArrayList<>(); // List untuk menyimpan hasil
                String query = "SELECT s.id_stok, b.nama_barang FROM stok s JOIN barang b ON s.id_barang = b.id_barang"; // Query join
        
                 try (Connection connection = conn.getConnection(); // Membuka koneksi database
                      PreparedStatement pstmt = connection.prepareStatement(query);
                      ResultSet rs = pstmt.executeQuery()) { // Eksekusi query
                        while (rs.next()) {
                            String[] barang = new String[2];
                            barang[0] = String.valueOf(rs.getInt("id_stok")); // Mengambil id_stok
                            barang[1] = rs.getString("nama_barang"); // Mengambil nama barang
                            daftarBarang.add(barang); // Menambah ke daftar
                        }
                 } catch (SQLException e) {
                     System.err.println("Error saat mengambil daftar barang: " + e.getMessage()); // Penanganan Error
                 }
                 return daftarBarang; // Mengembalikan daftar barang
             }
          public boolean barangExists(String namaBarang) {
                String sql = "SELECT COUNT(*) FROM barang WHERE nama_barang = ?";
                try (Connection connection = conn.getConnection();
                    PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, namaBarang);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) { // Pastikan ada hasil
                        return rs.getInt(1) > 0; // Kembalikan true jika barang ada
                    }   
                } catch (SQLException e) {
                    System.err.println("Error saat melakukan cek barang: " + e.getMessage());
                }
                 return false;
            }
             
             public void cekDanTambahBarang(String namaBarang, int stok) {
                String jenisBarang = "Barang Baru";
                if (!barangExists(namaBarang)) { // Cek apakah barang sudah ada
                    // Tambahkan barang terlebih dahulu
                    String insertBarangQuery = "INSERT INTO barang(nama_barang, jenis_barang) VALUES(?, ?)";
                    try (Connection connection = conn.getConnection(); 
                        PreparedStatement pstmt = connection.prepareStatement(insertBarangQuery, Statement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, namaBarang);
                        pstmt.setString(2, jenisBarang);
                        pstmt.executeUpdate(); // Menjalankan query untuk menambah barang
                        // Ambil ID barang yang baru ditambahkan
                        ResultSet generatedKeys = pstmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int idBarangBaru = generatedKeys.getInt(1); // Ambil id_barang yang baru ditambahkan
                            // Perbarui stok menggunakan id_barang
                            String updateStokQuery = "UPDATE stok SET stok = stok + ? WHERE id_barang = ?";
                            try (PreparedStatement pstmtUpdate = connection.prepareStatement(updateStokQuery)) {
                                pstmtUpdate.setInt(1, stok); // Tambahkan jumlah stok
                                pstmtUpdate.setInt(2, idBarangBaru); // ID barang yang baru
                                int rowsAffected = pstmtUpdate.executeUpdate(); // Menjalankan query untuk memperbarui stok               
                                if (rowsAffected > 0) {
                                    System.out.println("Jumlah stok berhasil diperbarui untuk barang dengan ID: " + idBarangBaru);
                                } else {
                                    System.out.println("Tidak ada stok yang diperbarui. Pastikan ID barang ada di tabel stok.");
                                }
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println("Error saat menambahkan barang dan memperbarui stok: " + e.getMessage());
                    }
                } else {
                    System.out.println("Barang sudah ada: " + namaBarang); // Notifikasi jika barang sudah ada
                }
            }
            
        }    
    ```
    - Model Transaksi
    ``` java
                 /*
         * To change this license header, choose License Headers in Project Properties.
         * To change this template file, choose Tools | Templates
         * and open the template in the editor.
         */
        
        package Model;
        
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.time.LocalDate;
        import java.time.format.DateTimeFormatter;
        import java.util.ArrayList;
        import java.util.List;
        
        
        /**
         *
         * @author rhnfa
         */
        
        
        public class TransaksiHelper {
             private DatabaseConnection conn; // Objek untuk mengelola koneksi ke database.
        
            // Konstruktor untuk menginisialisasi objek DatabaseConnection.
            public TransaksiHelper() {
                conn = new DatabaseConnection();
            }
            
            // Method untuk menambah transaksi
           public void tambahTransaksi(int id_stok, String jenis_transaksi, int jumlah_barang) {
                LocalDate today = LocalDate.now(); // Mendapatkan tanggal saat ini.
                String tanggal_transaksi = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Format tanggal.
                String query = "INSERT INTO transaksi(id_stok, jenis_transaksi, jumlah_barang, tanggal_transaksi) VALUES(?, ?, ?, ?)";
                try (Connection connection = conn.getConnection(); 
                    PreparedStatement pstmt = connection.prepareStatement(query)) {
                    // Mengisi parameter query dengan data.
                        pstmt.setInt(1, id_stok);
                        pstmt.setString(2, jenis_transaksi);
                        pstmt.setInt(3, jumlah_barang);
                        pstmt.setString(4, tanggal_transaksi);
                        pstmt.executeUpdate(); // Menjalankan query.
                        System.out.println("Transaksi berhasil ditambahkan."); // Pesan sukses.
                } catch (SQLException e) {
                    System.err.println("Error saat menambahkan transaksi: " + e.getMessage()); // Pesan error.
                }
            }
           
           // Method untuk mengubah data transaksi
           public void updateTransaksi(int id_transaksi, int id_stok, String jenis_transaksi, int jumlah_barang) {
                LocalDate today = LocalDate.now();  // Mendapatkan tanggal saat ini.
                String tanggal_transaksi = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Format tanggal.
                String query = "UPDATE transaksi SET id_stok = ?, jenis_transaksi = ?, jumlah_barang = ?, tanggal_transaksi = ? WHERE id_transaksi = ?";
                try (Connection connection = conn.getConnection();
                    PreparedStatement pstmt = connection.prepareStatement(query)) {
                     // Mengisi parameter query dengan data.
                        pstmt.setInt(1, id_stok);
                        pstmt.setString(2, jenis_transaksi);
                        pstmt.setInt(3, jumlah_barang);
                        pstmt.setString(4, tanggal_transaksi);
                        pstmt.setInt(5, id_transaksi); // Menetapkan ID transaksi yang akan diperbarui
                        pstmt.executeUpdate(); // Menjalankan query.
                        System.out.println("Transaksi berhasil diperbarui."); // Pesan sukses.
                } catch (SQLException e) {
                    System.err.println("Error saat memperbarui transaksi: " + e.getMessage()); // Pesan error.
                }
            }
           
           // Method untuk menghapus data transaksi
           public void hapusTransaksi(int id_Transaksi) {
                String query = "DELETE FROM transaksi WHERE id_transaksi = ?";
                try (Connection connection = conn.getConnection(); // Perbaikan di sini
                    PreparedStatement statement = connection.prepareStatement(query)) {
                      // Mengisi parameter query dengan ID transaksi.
                        statement.setInt(1, id_Transaksi);
                        statement.executeUpdate(); // Menjalankan query.
                        System.out.println("Transaksi berhasil dihapus."); // Pesan sukses.
                } catch (SQLException e) {
                    System.err.println("Error saat menghapus transaksi: " + e.getMessage()); // Pesan error.
                }
            }
           
           // Method untuk menampilkan data transaksi dan nama barang dengan melakuka join antara tabel stok dan tabel barang
            public List<String[]> tampilData() {
              List<String[]> daftarTransaksi = new ArrayList<>();  // List untuk menyimpan daftar transaksi.
        
              // Query untuk mengambil data transaksi dan melakukan join dengan tabel stok dan barang.
              String query = "SELECT t.id_transaksi, b.nama_barang, t.jenis_transaksi, t.jumlah_barang, t.tanggal_transaksi " +
                             "FROM transaksi t " +
                             "JOIN stok s ON t.id_stok = s.id_stok " + // Menyambungkan dengan tabel stok
                             "JOIN barang b ON s.id_barang = b.id_barang"; // Menyambungkan dengan tabel barang
        
              try (Connection connection = conn.getConnection();
                  Statement stmt = connection.createStatement();
                  ResultSet rs = stmt.executeQuery(query)) {
                  // Iterasi melalui setiap baris hasil query.
                      while (rs.next()) {
                          // Menyimpan data transaksi ke dalam array String.
                          String[] transaksi = new String[5];
                          transaksi[0] = rs.getString("id_transaksi"); // ID transaksi.
                          transaksi[1] = rs.getString("nama_barang"); // Nama barang.
                          transaksi[2] = rs.getString("jenis_transaksi");  // Jenis transaksi.
                          transaksi[3] = String.valueOf(rs.getInt("jumlah_barang")); // Jumlah barang.
                          transaksi[4] = rs.getString("tanggal_transaksi"); // Tanggal transaksi.
                          daftarTransaksi.add(transaksi); // Menambahkan ke list.
                      }
              } catch (SQLException e) {
                  System.err.println("Error saat mengambil daftar transaksi: " + e.getMessage());  // Pesan error.
              }
              return daftarTransaksi; // Mengembalikan list transaksi.
          }
        }
        ```
        - Model Stok
        ``` java
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    
    package Model;
    
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;
    
    /**
     *
     * @author rhnfa
     */
    
    
    public class StokHelper {
        
        // Mengambil semua data stok dengan nama barang
        public List<Object[]> tampilData() {
            List<Object[]> dataStok = new ArrayList<>(); // List untuk menyimpan data stok.
            
            // Membuat instance dari DatabaseConnection untuk mengatur koneksi.
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();  // Membuka koneksi
    
            // Query untuk mengambil data stok dan nama barang
            String query = "SELECT b.nama_barang, s.stok FROM stok s JOIN barang b ON s.id_barang = b.id_barang"; 
            try (PreparedStatement statement = connection.prepareStatement(query);  // Menggunakan PreparedStatement untuk menyiapkan dan mengeksekusi query.
                ResultSet resultSet = statement.executeQuery()) {  // Menyimpan hasil eksekusi query ke dalam ResultSet.
                // Iterasi melalui setiap baris dalam hasil query.
                    while (resultSet.next()) {
                        // Mengambil data dari kolom "nama_barang" dan "stok".
                        String namaBarang = resultSet.getString("nama_barang");
                        int stok = resultSet.getInt("stok");
                        dataStok.add(new Object[]{namaBarang, stok}); // Menambahkan data ke dalam list sebagai array objek.
                    }
            } catch (SQLException e) {
                e.printStackTrace(); // Menangkap dan mencetak error jika terjadi masalah dengan SQL.
            } finally {
                dbConnection.closeConnection(); // Memastikan koneksi ke database ditutup untuk mencegah kebocoran sumber daya.
            }
            return dataStok; // Mengembalikan list yang berisi data stok.
        }
    }
    ```
    - Model Connection
    ``` java
        /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    
    
    package Model;
    
    // Mengimpor kelas-kelas yang diperlukan untuk koneksi database
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.SQLException;
    
    
    /**
     *
     * @author rhnfa
     */
    
    
    public class DatabaseConnection { 
        private static final String URL = "jdbc:sqlite:inventoriDB";  // URL database SQLite. Lokasi file database bernama "inventoriDB".
        
        // Variabel untuk menyimpan koneksi ke database.
        private Connection connection;
        
        
        // Method untuk membuka koneksi
        public Connection getConnection() {
            Connection connection = null; // Variabel lokal untuk menyimpan koneksi sementara
            try {
                connection = DriverManager.getConnection(URL);   // Membuka koneksi menggunakan DriverManager dan URL yang ditentukan.
            } catch (SQLException e) {
                e.printStackTrace();   // Menangkap dan mencetak error jika koneksi gagal.
            }
    
            return connection; // Mengembalikan objek koneksi (null jika gagal).
    
        }
        
        // Method untuk menutup koneksi
        public void closeConnection() {
            if (connection != null) {    // Memeriksa apakah koneksi tidak null sebelum mencoba menutup.
                try {
                    connection.close(); // Menutup koneksi ke database.
                    System.out.println("Koneksi ke database ditutup.");
                } catch (SQLException e) {
                    System.err.println("Gagal menutup koneksi.");   // Menangkap error jika terjadi kegagalan saat menutup koneksi.
                    e.printStackTrace();
                }
            }
        }
    }
    ```

2. Controller
   - Controller Barang
   ``` java
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package Controller;
    
    // Import kelas yang dibutuhkan
    import Model.BarangHelper; // Kelas model untuk berinteraksi dengan data barang
    import View.MenuBarang;  // Kelas view untuk mengelola tampilan barang
    
    import java.util.List; // Import List untuk manipulasi koleksi data
    
    /**
     *
     * @author rhnfa
     */
    
    
    public class BarangController {
        private BarangHelper barangHelper; // Objek untuk berinteraksi dengan model (data barang)
        private MenuBarang menuBarangView; // Objek untuk berinteraksi dengan view (tampilan)
    
         // Konstruktor untuk inisialisasi objek controller
        public BarangController(MenuBarang menuBarangView) {
            this.menuBarangView = menuBarangView; // Inisialisasi view
            this.barangHelper = new BarangHelper(); // Inisialisasi helper model
        }
    
        // Mengambil semua barang dan mengupdate tampilan
        public void loadBarangData() {
            List<String[]> daftarBarang = barangHelper.tampilData(); // Mengambil data barang dari model
            menuBarangView.updateBarangTable(daftarBarang); // Mengirim data ke view untuk ditampilkan
        }
    
        // Menambahkan barang baru
        public void tambahBarang(String namaBarang, String jenisBarang) {
            barangHelper.tambahBarang(namaBarang, jenisBarang);  // Menambahkan barang melalui model
            loadBarangData(); // Memperbarui data tampilan setelah penambahan
            
        }
    
        // Mengupdate barang yang ada
        public void updateBarang(int idBarang, String namaBarangBaru, String jenisBarangBaru) {
            barangHelper.updateBarang(idBarang, namaBarangBaru, jenisBarangBaru); // Mengupdate data barang di model
            loadBarangData();  // Memperbarui data tampilan setelah pengubahan
        }
    
        // Menghapus barang
        public void hapusBarang(int idBarang) {
            barangHelper.hapusBarang(idBarang); // Menghapus barang melalui model
            loadBarangData(); // Memperbarui data tampilan setelah penghapusan
        }
    }
   ```
   - Controller Stok
   ``` java
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package Controller;
    
    // Import kelas yang dibutuhkan
    import Model.StokHelper; // Kelas model untuk mengelola data stok
    import View.MenuStok;  // Kelas view untuk menampilkan data stok ke pengguna
    import java.util.List; // Import List untuk manipulasi koleksi data
    
    /**
     *
     * @author rhnfa
     */
    
    public class StokController {
        private MenuStok menuStok; // Objek view untuk berinteraksi dengan tampilan stok
        private StokHelper stokHelper;  // Objek model untuk berinteraksi dengan data stok
        
        public StokController(MenuStok menuStok) {
            this.menuStok = menuStok;  // Menghubungkan controller dengan view
            this.stokHelper = new StokHelper(); // Membuat instance helper untuk akses data stok
        }
        
        //Menampilkan data stok
        public void loadStokData() {
            List<Object[]> daftarStok = stokHelper.tampilData(); // Mengambil data stok dari model
            menuStok.updateStokTable(daftarStok);   // Mengupdate tampilan tabel stok di view
        }
    }
   ```
   - Controller Transaksi
   ``` java
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package Controller;
    
    // Import kelas model dan view yang dibutuhkan
    import Model.BarangHelper; // Kelas model untuk berinteraksi dengan data barang
    import Model.TransaksiHelper;// Kelas model untuk berinteraksi dengan data transaksi
    import View.MenuTransaksi; // Kelas view untuk menampilkan menu transaksi
    import java.util.List; // Import List untuk manipulasi koleksi data
    
    /**
     *
     * @author rhnfa
     */
    public class TransaksiController {
        private TransaksiHelper transaksiHelper;  // Objek model untuk mengelola data transaksi
        private MenuTransaksi transaksiView; // Objek view untuk berinteraksi dengan tampilan transaksi
        private BarangHelper barangHelper; // Objek model untuk mengambil data barang
        
        public TransaksiController(MenuTransaksi transaksiView){
            this.transaksiView = transaksiView; // Inisialisasi view transaksi
            this.transaksiHelper = new TransaksiHelper(); // Inisialisasi model transaksi
            barangHelper = new BarangHelper();  // Inisialisasi model barang
        }
        
        // Menampilkan data Transaksi
        public void loadTransaksiData(){
            List<String[]> daftarTransaksi = transaksiHelper.tampilData(); // Mengambil data transaksi dari model
            transaksiView.updateTransaksiTabel(daftarTransaksi); // Mengupdate tampilan tabel transaksi di view
        }
        
        //Menambahkan data Transaksi
        public void tambahTransaksi(int id_stok, String jenis_transaksi, int jumlah_barang) {
           transaksiHelper.tambahTransaksi(id_stok, jenis_transaksi, jumlah_barang); // Menambahkan data transaksi melalui model
            loadTransaksiData(); // Memuat ulang data transaksi untuk memperbarui tampilan
            
        }
        
        //Mengubah data Transaksi
        public void updateTransaksi(int id_transaksi, int id_stok, String jenis_transaksi, int jumlah_barang) {
           transaksiHelper.updateTransaksi(id_transaksi, id_stok, jenis_transaksi, jumlah_barang); // Mengubah data transaksi melalui model
            loadTransaksiData(); // Memuat ulang data transaksi untuk memperbarui tampilan
        }
        
        //Menghapus data Transaksi
        public void hapusTransaksi(int id_transaksi){
            transaksiHelper.hapusTransaksi(id_transaksi); // Menghapus data transaksi melalui model
            loadTransaksiData(); // Memuat ulang data transaksi untuk memperbarui tampilan
        }
        
        //Mengambil nama dan id_stok barang
        public List<String[]> getNamaDanId() {
            return barangHelper.getNamaDanId(); // Mengambil data nama dan id_stok dari model barang
        }
    }
   ```
3. View
   - View Barang
   ``` java
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package View;
    
    
    import Controller.BarangController;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.util.List;
    import javax.swing.JOptionPane;
    import javax.swing.table.DefaultTableModel;
    
    /**
     *
     * @author rhnfa
     */
    public class MenuBarang extends javax.swing.JFrame {
    private BarangController barangController; // Deklarasi variabel barangController untuk mengelola data barang
        /**
         * Creates new form MenuBarang
         */
        public MenuBarang() {
            initComponents();
            
            // Memanggil controller untuk mengatur data barang.
            barangController = new BarangController(this);
            
            // Memuat data barang ke tabel saat menu barang dibuat.
            barangController.loadBarangData();
                
            tableBarang.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    int selectedRow = tableBarang.getSelectedRow(); // Mendapatkan indeks baris yang diklik.
                    if (selectedRow != -1) { // Jika baris valid dipilih.
                    // Mengambil data dari baris yang dipilih berdasarkan indeks kolom.
                    String idBarangString = (String) tableBarang.getValueAt(selectedRow, 0); 
                    int idBarang = Integer.parseInt(idBarangString); // Mengubah ID barang menjadi integer.
                    String namaBarang = (String) tableBarang.getValueAt(selectedRow, 1);
                    String jenisBarang = (String) tableBarang.getValueAt(selectedRow, 2);
                    
                    // Mengisi field nama dan jenis barang di GUI berdasarkan data tabel.
                    txtNama.setText(namaBarang);
                    comboJenis.setSelectedItem(jenisBarang);
                    }
                }
            });
        }
        
        // Method untuk melakukan update tabel barang
       public void updateBarangTable(List<String[]> daftarBarang) {
            DefaultTableModel model = (DefaultTableModel) tableBarang.getModel(); // Mendapatkan model tabel.
            model.setRowCount(0); // Menghapus semua baris di tabel sebelum memuat data baru.
    
            for (String[] barang : daftarBarang) {
                model.addRow(barang);  // Menambahkan setiap baris data barang ke model tabel.
            }
        }
       
       // Method untuk menampilkan pesan error
       public void showError(String message) {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
       
       
       
        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {
    
            jPanel1 = new javax.swing.JPanel();
            jPanel2 = new javax.swing.JPanel();
            jLabel1 = new javax.swing.JLabel();
            jLabel2 = new javax.swing.JLabel();
            jScrollPane1 = new javax.swing.JScrollPane();
            tableBarang = new javax.swing.JTable();
            txtNama = new javax.swing.JTextField();
            comboJenis = new javax.swing.JComboBox<>();
            btnSimpan = new javax.swing.JButton();
            btnUbah = new javax.swing.JButton();
            btnHapus = new javax.swing.JButton();
            jMenuBar1 = new javax.swing.JMenuBar();
            menuHome = new javax.swing.JMenu();
            menuStok = new javax.swing.JMenu();
            menuTransaksi = new javax.swing.JMenu();
    
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Aplikasi Inventori Barang || Barang");
    
            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Barang", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24))); // NOI18N
    
            jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Barang", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
    
            jLabel1.setText("Nama Barang");
    
            jLabel2.setText("Jenis Barang");
    
            tableBarang.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String [] {
                    "ID", "Nama Barang", "Jenis Barang"
                }
            ));
            jScrollPane1.setViewportView(tableBarang);
    
            comboJenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Jenis Barang", "Packaging", "Bahan Baku", "Alat Makan", "Perlengkapan" }));
    
            btnSimpan.setText("SIMPAN");
            btnSimpan.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnSimpanActionPerformed(evt);
                }
            });
    
            btnUbah.setText("UBAH");
            btnUbah.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnUbahActionPerformed(evt);
                }
            });
    
            btnHapus.setText("HAPUS");
            btnHapus.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnHapusActionPerformed(evt);
                }
            });
    
            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addGap(35, 35, 35)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(comboJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnSimpan)
                            .addGap(18, 18, 18)
                            .addComponent(btnUbah)
                            .addGap(18, 18, 18)
                            .addComponent(btnHapus)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(comboJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(34, 34, 34)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSimpan)
                        .addComponent(btnUbah)
                        .addComponent(btnHapus))
                    .addGap(29, 29, 29)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(54, Short.MAX_VALUE))
            );
    
            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
    
            menuHome.setText("Home");
            menuHome.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    menuHomeMouseClicked(evt);
                }
            });
            jMenuBar1.add(menuHome);
    
            menuStok.setText("Stok");
            menuStok.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    menuStokMouseClicked(evt);
                }
            });
            jMenuBar1.add(menuStok);
    
            menuTransaksi.setText("Transaksi");
            menuTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    menuTransaksiMouseClicked(evt);
                }
            });
            jMenuBar1.add(menuTransaksi);
    
            setJMenuBar(jMenuBar1);
    
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            );
    
            pack();
        }// </editor-fold>                        
    
        private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
            // TODO add your handling code here:
            String namaBarang = txtNama.getText(); // Mengambil input nama barang dari field.
            String jenisBarang = (String) comboJenis.getSelectedItem(); // Mengambil input jenis barang dari combo box.
    
            // Validasi apakah nama barang dan jenis barang sudah diisi.
            if (!namaBarang.isEmpty() && !jenisBarang.equals("Pilih Jenis Barang")) {
                try {
                    // Menyimpan barang baru melalui controller.
                    barangController.tambahBarang(namaBarang, jenisBarang);
                    JOptionPane.showMessageDialog(this, "Barang berhasil disimpan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Mengosongkan field input setelah menyimpan.
                    txtNama.setText("");
                    comboJenis.setSelectedIndex(0);
                } catch (Exception e) {
                    showError("Gagal menyimpan barang: " + e.getMessage());  // Menampilkan pesan error jika gagal.
                }
            } else {
                showError("Nama barang dan jenis barang harus diisi!"); // Menampilkan error jika validasi gagal.
            }
        }                                         
    
        private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {                                        
            // TODO add your handling code here:
            int selectedRow = tableBarang.getSelectedRow();  // Mendapatkan baris yang dipilih.
    
            if (selectedRow != -1) { // Jika ada baris yang dipilih.
                
                // Mengambil data baru dari field input.
                String namaBarangBaru = txtNama.getText();
                String jenisBarangBaru = (String) comboJenis.getSelectedItem();
                
                // Mengambil ID barang dari baris yang dipilih.
                String idBarangString = (String) tableBarang.getValueAt(selectedRow, 0);
                int idBarang = Integer.parseInt(idBarangString);
                // Validasi apakah input baru sudah diisi.
                if (!namaBarangBaru.isEmpty() && !jenisBarangBaru.equals("Pilih Jenis Barang")) {
                    try {
                        // Mengupdate barang melalui controller.
                        barangController.updateBarang(idBarang, namaBarangBaru, jenisBarangBaru);
                        JOptionPane.showMessageDialog(this, "Barang berhasil diubah.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        // Mengosongkan field input setelah mengubah.
                        txtNama.setText("");
                        comboJenis.setSelectedIndex(0);
                    } catch (Exception e) {
                        showError("Gagal mengubah barang: " + e.getMessage()); // Menampilkan pesan error jika gagal.
                    }
                } else {
                    showError("Nama barang dan jenis barang harus diisi!"); // Menampilkan error jika validasi gagal.
                }
            } else {
                showError("Silakan pilih barang yang ingin diubah."); // Menampilkan error jika tidak ada baris yang dipilih.
            }
        }                                       
    
        private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
            // TODO add your handling code here:
            int selectedRow = tableBarang.getSelectedRow();  // Mendapatkan baris yang dipilih.
    
            if (selectedRow != -1) { // Jika ada baris yang dipilih.
                
                // Mengambil ID barang dari baris yang dipilih.
                String idBarangString = (String) tableBarang.getValueAt(selectedRow, 0);
                int idBarang = Integer.parseInt(idBarangString);
                // Konfirmasi penghapusan.
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Apakah Anda yakin ingin menghapus barang ini?", 
                    "Konfirmasi Hapus", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {   // Jika pengguna mengonfirmasi.
                    try {
                        // Menghapus barang melalui controller.
                        barangController.hapusBarang(idBarang);
                        JOptionPane.showMessageDialog(this, "Barang berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Mengosongkan field input setelah menghapus.
                        txtNama.setText("");
                        comboJenis.setSelectedIndex(0);
                    } catch (Exception e) {
                        showError("Gagal menghapus barang: " + e.getMessage());  // Menampilkan pesan error jika gagal.
                    }
                }
            } else {
                showError("Silakan pilih barang yang ingin dihapus.");  // Menampilkan error jika tidak ada baris yang dipilih.
            }
        }                                        
    
        private void menuStokMouseClicked(java.awt.event.MouseEvent evt) {                                      
            // TODO add your handling code here:
            // Membuka form MenuStok dan menutup form saat ini.
            new MenuStok().setVisible(true);
            dispose();
        }                                     
    
        private void menuTransaksiMouseClicked(java.awt.event.MouseEvent evt) {                                           
            // TODO add your handling code here:
            // Membuka form MenuTransaksi dan menutup form saat ini.
            new MenuTransaksi().setVisible(true);
            dispose();
        }                                          
    
        private void menuHomeMouseClicked(java.awt.event.MouseEvent evt) {                                      
            // TODO add your handling code here:
            // Membuka form MenuHome dan menutup form saat ini.
            new MenuUtama().setVisible(true);
            dispose();
        }                                     
    
        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MenuBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MenuBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MenuBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MenuBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
    
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new MenuBarang().setVisible(true);
                }
            });
        }
    
        // Variables declaration - do not modify                     
        private javax.swing.JButton btnHapus;
        private javax.swing.JButton btnSimpan;
        private javax.swing.JButton btnUbah;
        private javax.swing.JComboBox<String> comboJenis;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JMenuBar jMenuBar1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JMenu menuHome;
        private javax.swing.JMenu menuStok;
        private javax.swing.JMenu menuTransaksi;
        private javax.swing.JTable tableBarang;
        private javax.swing.JTextField txtNama;
        // End of variables declaration                   
    }

   ```
   - View Stok
   ``` java
  
        package View;
        
        import Controller.StokController;
        import Model.BarangHelper;
        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileReader;
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.util.List;
        import javax.swing.JOptionPane;
        import javax.swing.table.DefaultTableModel;
        
        public class MenuStok extends javax.swing.JFrame {
        private StokController stokController;
            public MenuStok() {
                initComponents();
                // Memanggil controller untuk mengatur data barang.
                stokController = new StokController(this);
                
                // Memuat data barang ke tabel saat menu barang dibuat.
                stokController.loadStokData();
            }
            
            public void updateStokTable(List<Object[]> daftarStok) {
                DefaultTableModel model = (DefaultTableModel) tableStok.getModel(); // Mendapatkan model tabel.
                model.setRowCount(0); // Menghapus semua baris di tabel sebelum memuat data baru
                for (Object[] stok : daftarStok) {
                    model.addRow(stok); // Menambahkan setiap baris data stok ke model tabel.
                }
            }
        
            /**
             * This method is called from within the constructor to initialize the form.
             * WARNING: Do NOT modify this code. The content of this method is always
             * regenerated by the Form Editor.
             */
            @SuppressWarnings("unchecked")
            // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
            private void initComponents() {
        
                jPanel1 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                tableStok = new javax.swing.JTable();
                btnImport = new javax.swing.JButton();
                btnExport = new javax.swing.JButton();
                jMenuBar1 = new javax.swing.JMenuBar();
                menuHome = new javax.swing.JMenu();
                menuTransaksi = new javax.swing.JMenu();
                menuBarang = new javax.swing.JMenu();
        
                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setTitle("Aplikasi Inventori Barang || Stok");
        
                jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Stok Barang", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24))); // NOI18N
        
                tableStok.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null}
                    },
                    new String [] {
                        "Nama Barang", "Stok"
                    }
                ));
                jScrollPane1.setViewportView(tableStok);
        
                btnImport.setText("Import");
                btnImport.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnImportActionPerformed(evt);
                    }
                });
        
                btnExport.setText("Export");
                btnExport.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnExportActionPerformed(evt);
                    }
                });
        
                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(176, Short.MAX_VALUE)
                        .addComponent(btnExport)
                        .addGap(150, 150, 150)
                        .addComponent(btnImport)
                        .addGap(182, 182, 182))
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnImport)
                            .addComponent(btnExport))
                        .addContainerGap(47, Short.MAX_VALUE))
                );
        
                menuHome.setText("Home");
                menuHome.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        menuHomeMouseClicked(evt);
                    }
                });
                jMenuBar1.add(menuHome);
        
                menuTransaksi.setText("Transaksi");
                menuTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        menuTransaksiMouseClicked(evt);
                    }
                });
                jMenuBar1.add(menuTransaksi);
        
                menuBarang.setText("Barang");
                menuBarang.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        menuBarangMouseClicked(evt);
                    }
                });
                jMenuBar1.add(menuBarang);
        
                setJMenuBar(jMenuBar1);
        
                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                );
        
                pack();
            }// </editor-fold>                        
        
            private void menuBarangMouseClicked(java.awt.event.MouseEvent evt) {                                        
                // TODO add your handling code here:
                // Membuka form MenuBarang dan menutup form saat ini.
                new MenuBarang().setVisible(true);
                dispose();
            }                                       
        
            private void menuTransaksiMouseClicked(java.awt.event.MouseEvent evt) {                                           
                // TODO add your handling code here:
                // Membuka form MenuTransaksi dan menutup form saat ini.
                new MenuTransaksi().setVisible(true);
                dispose();
            }                                          
        
            private void menuHomeMouseClicked(java.awt.event.MouseEvent evt) {                                      
                // TODO add your handling code here:
                // Membuka form MenuHome dan menutup form saat ini.
                new MenuUtama().setVisible(true);
                dispose();
            }                                     
        
            private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {                                          
                // TODO add your handling code here:
                // Mendapatkan model dari tabel
                DefaultTableModel model = (DefaultTableModel) tableStok.getModel();
                // Menentukan jalur untuk file CSV
                String filePath = "stok.csv"; // Anda bisa mengubah jalur ini sesuai kebutuhan
                try (PrintWriter writer = new PrintWriter(new File(filePath))) {
                    // Menulis header
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        writer.print(model.getColumnName(i));
                        if (i < model.getColumnCount() - 1) {
                            writer.print(",");
                        }
                    }
                    writer.println();
                    // Menulis data baris
                    for (int i = 0; i < model.getRowCount(); i++) {
                        for (int j = 0; j < model.getColumnCount(); j++) {
                            writer.print(model.getValueAt(i, j));
                            if (j < model.getColumnCount() - 1) {
                                writer.print(",");
                            }
                        }
                        writer.println();
                    }
                    JOptionPane.showMessageDialog(this, "Data berhasil diekspor ke " + filePath);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengekspor data: " + e.getMessage());
                }
            }                                         
        
            private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {                                          
                // TODO add your handling code here:
                // Menentukan jalur untuk file CSV
                String filePath = "stok.csv"; // Anda bisa mengubah jalur ini sesuai kebutuhan
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                reader.readLine(); // Lewati baris header
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // Memisahkan data berdasarkan koma
                    if (data.length == 2) { // Pastikan ada dua kolom (Nama Barang dan Stok)
                        String namaBarang = data[0].trim(); // Menghapus spasi di awal dan akhir
                        String jumlah_stok = data[1].trim(); // Menghapus spasi di awal dan akhir
                        int stok = Integer.parseInt(jumlah_stok);
                        // Memeriksa apakah barang sudah ada
                        BarangHelper barangHelper = new BarangHelper();
                        if (barangHelper.barangExists(namaBarang)) {
                            JOptionPane.showMessageDialog(this, "Barang dengan nama '" + namaBarang + "' sudah ada. Barang ini akan diabaikan.", "Duplikat Ditemukan", JOptionPane.WARNING_MESSAGE);
                        } else {
                            // Menambahkan barang jika belum ada
                            barangHelper.cekDanTambahBarang(namaBarang, stok);
                        }
                    }
                }
                // Setelah import, muat ulang data dari database ke tabel
                stokController.loadStokData();
                JOptionPane.showMessageDialog(this, "Data berhasil diimpor dari " + filePath, "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengimpor data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }                                         
        
            /**
             * @param args the command line arguments
             */
            public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
                /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
                 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
                 */
                try {
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (ClassNotFoundException ex) {
                    java.util.logging.Logger.getLogger(MenuStok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    java.util.logging.Logger.getLogger(MenuStok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    java.util.logging.Logger.getLogger(MenuStok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                    java.util.logging.Logger.getLogger(MenuStok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
        
                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new MenuStok().setVisible(true);
                    }
                });
            }
        
            // Variables declaration - do not modify                     
            private javax.swing.JButton btnExport;
            private javax.swing.JButton btnImport;
            private javax.swing.JMenuBar jMenuBar1;
            private javax.swing.JPanel jPanel1;
            private javax.swing.JScrollPane jScrollPane1;
            private javax.swing.JMenu menuBarang;
            private javax.swing.JMenu menuHome;
            private javax.swing.JMenu menuTransaksi;
            private javax.swing.JTable tableStok;
            // End of variables declaration                   
        }


   ```
   - View Transaksi
   ``` java
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package View;
    
    import Controller.TransaksiController;
    import java.util.List;
    import javax.swing.JOptionPane;
    import javax.swing.table.DefaultTableModel;
    
    /**
     *
     * @author rhnfa
     */
    public class MenuTransaksi extends javax.swing.JFrame {
    private TransaksiController transaksiController; // Deklarasi variabel untuk mengelola data transaksi
    private List<String[]> daftarBarang; // Deklarasi list untuk menyimpan data barang
        /**
         * Creates new form MenuTransaksi
         */
        public MenuTransaksi() {
            initComponents();
            populateComboBarang(); // Mengisi combo box dengan daftar barang
            transaksiController = new TransaksiController(this); // Membuat TransaksiController dan menghubungkannya dengan MenuTransaksi
            transaksiController.loadTransaksiData(); // Memuat data transaksi saat menu transaksi ditampilkan
        }
          public void showError(String message) {
            // Menampilkan pesan kesalahan dalam dialog
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
        public void updateTransaksiTabel(List<String[]> daftarTransaksi) {
            DefaultTableModel model = (DefaultTableModel) tabelTransaksi.getModel(); // Mendapatkan model tabel transaksi
            model.setRowCount(0); // Menghapus semua baris yang ada di tabel
    
            for (String[] transaksi : daftarTransaksi) {
                model.addRow(transaksi);  // Menambahkan baris baru untuk setiap transaksi
            }
        }
        
        // Mengisi comboBarang dari transaksiController
        private void populateComboBarang() {
            transaksiController = new TransaksiController(this); // Membuat instance baru dari TransaksiController
            List<String[]> daftarBarang = transaksiController.getNamaDanId(); // Mengambil daftar nama dan ID barang dari transaksiController
            comboBarang.removeAllItems();  // Menghapus semua item yang ada di combo box
            comboBarang.addItem("-- Pilih Barang --");  // Menambahkan item default ke combo box
            for (String[] barang : daftarBarang) {
                comboBarang.addItem(barang[1]);   // Menambahkan nama barang ke combo box
            }
            
            this.daftarBarang = daftarBarang;  // Menyimpan daftar barang ke variabel instance
        }
    
        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {
    
            buttonGroup1 = new javax.swing.ButtonGroup();
            jPanel1 = new javax.swing.JPanel();
            jPanel2 = new javax.swing.JPanel();
            jLabel1 = new javax.swing.JLabel();
            comboBarang = new javax.swing.JComboBox<>();
            jLabel2 = new javax.swing.JLabel();
            jLabel3 = new javax.swing.JLabel();
            jPanel3 = new javax.swing.JPanel();
            radioKeluar = new javax.swing.JRadioButton();
            radioMasuk = new javax.swing.JRadioButton();
            spinnerJumlah = new javax.swing.JSpinner();
            jScrollPane1 = new javax.swing.JScrollPane();
            tabelTransaksi = new javax.swing.JTable();
            btnSimpan = new javax.swing.JButton();
            btnUbah = new javax.swing.JButton();
            btnHapus = new javax.swing.JButton();
            jMenuBar1 = new javax.swing.JMenuBar();
            menuHome = new javax.swing.JMenu();
            menuStok = new javax.swing.JMenu();
            menuBarang = new javax.swing.JMenu();
    
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Aplikasi Inventori || Transaksi");
    
            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24))); // NOI18N
    
            jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
    
            jLabel1.setText("Barang");
    
            comboBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Barang --" }));
    
            jLabel2.setText("Jenis Transaksi");
    
            jLabel3.setText("Jumlah Barang");
    
            buttonGroup1.add(radioKeluar);
            radioKeluar.setText("Keluar");
    
            buttonGroup1.add(radioMasuk);
            radioMasuk.setText("Masuk");
    
            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(radioMasuk)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                    .addComponent(radioKeluar)
                    .addContainerGap())
            );
            jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioKeluar)
                        .addComponent(radioMasuk))
                    .addGap(0, 9, Short.MAX_VALUE))
            );
    
            spinnerJumlah.setModel(new javax.swing.SpinnerNumberModel(1, 1, 9999, 1));
    
            tabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null}
                },
                new String [] {
                    "ID", "Nama Barang", "Jenis Transaksi", "Jumlah Barang", "Tanggal Transaksi"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
                };
    
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            tabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tabelTransaksiMouseClicked(evt);
                }
            });
            jScrollPane1.setViewportView(tabelTransaksi);
    
            btnSimpan.setText("SIMPAN");
            btnSimpan.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnSimpanActionPerformed(evt);
                }
            });
    
            btnUbah.setText("UBAH");
            btnUbah.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnUbahActionPerformed(evt);
                }
            });
    
            btnHapus.setText("HAPUS");
            btnHapus.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnHapusActionPerformed(evt);
                }
            });
    
            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                            .addGap(58, 58, 58)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(comboBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(spinnerJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnSimpan)
                            .addGap(18, 18, 18)
                            .addComponent(btnUbah)
                            .addGap(18, 18, 18)
                            .addComponent(btnHapus)))
                    .addGap(0, 0, Short.MAX_VALUE))
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(comboBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel2)))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(spinnerJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSimpan)
                        .addComponent(btnUbah)
                        .addComponent(btnHapus))
                    .addGap(35, 35, 35)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
    
            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
    
            menuHome.setText("Home");
            menuHome.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    menuHomeMouseClicked(evt);
                }
            });
            jMenuBar1.add(menuHome);
    
            menuStok.setText("Stok");
            menuStok.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    menuStokMouseClicked(evt);
                }
            });
            jMenuBar1.add(menuStok);
    
            menuBarang.setText("Barang");
            menuBarang.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    menuBarangMouseClicked(evt);
                }
            });
            jMenuBar1.add(menuBarang);
    
            setJMenuBar(jMenuBar1);
    
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 42, Short.MAX_VALUE))
            );
    
            pack();
        }// </editor-fold>                        
    
        private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
            // TODO add your handling code here:
            int id_stok = -1; // Inisialisasi ID stok dengan nilai tidak valid
            String namaBarang = (String) comboBarang.getSelectedItem(); // Mengambil nama barang yang dipilih dari combo box
            // Mencari ID stok berdasarkan nama barang
            for (String[] barang : daftarBarang) {
                if (barang[1].equals(namaBarang)) {
                    id_stok = Integer.parseInt(barang[0]); // Mengambil ID stok
                    break;
                }
            }
            // Mendapatkan jenis transaksi dari radio button
            String jenis_transaksi = radioKeluar.isSelected() ? "Keluar" : "Masuk";
            // Mendapatkan jumlah barang dari spinner
            int jumlah_barang = (Integer) spinnerJumlah.getValue();
            // Memanggil metode untuk menambah transaksi
            if (id_stok != -1) { // Pastikan ID stok valid
                transaksiController.tambahTransaksi(id_stok, jenis_transaksi, jumlah_barang);
                // Reset form setelah menyimpan
                comboBarang.setSelectedIndex(0); // Mengatur combo box ke item default
                spinnerJumlah.setValue(1); // Mengatur spinner ke nilai default
                buttonGroup1.clearSelection();
            } else {
                // Menampilkan pesan kesalahan jika tidak ada barang yang dipilih
               JOptionPane.showMessageDialog(this, "Silakan pilih barang terlebih dahulu.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }                                         
    
        private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {                                        
            // TODO add your handling code here:
            int selectedRow = tabelTransaksi.getSelectedRow();
            if (selectedRow >= 0) { // Pastikan ada baris yang dipilih
                // Mengambil ID transaksi dari tabel
                String idTransaksiString = (String) tabelTransaksi.getValueAt(selectedRow, 0); // ID transaksi berada di kolom pertama
                int id_transaksi = Integer.parseInt(idTransaksiString);
                // Mendapatkan ID barang yang dipilih dari combo box
                int id_stok = -1; // Inisialisasi dengan nilai yang tidak valid
                String namaBarang = (String) comboBarang.getSelectedItem();
                // Mencari ID stok berdasarkan nama barang
                for (String[] barang : daftarBarang) {
                    if (barang[1].equals(namaBarang)) {
                        id_stok = Integer.parseInt(barang[0]); // Mengambil ID stok
                        break;
                    }
                }
                // Mendapatkan jenis transaksi dari radio button
                String jenis_transaksi = radioKeluar.isSelected() ? "Keluar" : "Masuk";
                // Mendapatkan jumlah barang dari spinner
                int jumlah_barang = (Integer) spinnerJumlah.getValue();
                // Memanggil metode untuk memperbarui transaksi
                if (id_stok != -1) { // Pastikan ID stok valid
                    transaksiController.updateTransaksi(id_transaksi, id_stok, jenis_transaksi, jumlah_barang); // Memanggil metode untuk update 
                    // Reset form setelah mengubah
                    comboBarang.setSelectedIndex(0); // Mengatur combo box ke item default
                    spinnerJumlah.setValue(1); // Mengatur spinner ke nilai default
                    buttonGroup1.clearSelection(); // Menghapus pilihan radio button
                } else {
                    // Menampilkan pesan kesalahan jika tidak ada barang yang dipilih
                  JOptionPane.showMessageDialog(this, "Silakan pilih barang terlebih dahulu.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Menampilkan pesan kesalahan jika tidak ada baris yang dipilih
               JOptionPane.showMessageDialog(this, "Silakan pilih transaksi yang ingin diubah.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }                                       
    
        private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
            // TODO add your handling code here:
            int selectedRow = tabelTransaksi.getSelectedRow(); // Mengambil baris yang dipilih dari tabel transaksi
            if (selectedRow != -1) { // Pastikan ada baris yang dipilih
                String idTransaksiString = (String) tabelTransaksi.getValueAt(selectedRow, 0);
                int idTransaksi = Integer.parseInt(idTransaksiString);
                // Menampilkan dialog konfirmasi
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Apakah Anda yakin ingin menghapus transaksi ini?", 
                    "Konfirmasi Hapus", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE);
                // Jika pengguna memilih "Ya"
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        transaksiController.hapusTransaksi(idTransaksi); // Memanggil metode untuk menghapus 
                        JOptionPane.showMessageDialog(this, "Transaksi berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        comboBarang.setSelectedIndex(0);  // Mengatur combo box ke item default
                        spinnerJumlah.setValue(1);  // Mengatur spinner ke nilai default
                        buttonGroup1.clearSelection(); // Menghapus pilihan radio button
                    } catch (Exception e) {
                        showError("Gagal menghapus barang: " + e.getMessage());
                    }
                }
            } else {
                showError("Silakan pilih barang yang ingin dihapus.");
            }
        }                                        
    
        private void tabelTransaksiMouseClicked(java.awt.event.MouseEvent evt) {                                            
            // TODO add your handling code here:
            int selectedRow = tabelTransaksi.getSelectedRow();
            if (selectedRow >= 0) { // Pastikan ada baris yang dipilih
                // Mengambil data dari baris yang dipilih
                String idTransaksiString = (String) tabelTransaksi.getValueAt(selectedRow, 0); // ID transaksi berada di kolom pertama
                String namaBarang = (String) tabelTransaksi.getValueAt(selectedRow, 1); // Nama barang berada di kolom kedua
                String jenisTransaksi = (String) tabelTransaksi.getValueAt(selectedRow, 2); // Jenis transaksi berada di kolom ketiga
                String jumlahBarangString = (String) tabelTransaksi.getValueAt(selectedRow, 3); // Jumlah barang berada di kolom keempat
    
                // Mengisi form dengan data yang dipilih
                for (String[] barang : daftarBarang) {
                    if (barang[1].equals(namaBarang)) {
                        comboBarang.setSelectedItem(namaBarang); // Mengatur combo box ke nama barang yang dipilih
                        break;
                    }
                }
                spinnerJumlah.setValue(Integer.parseInt(jumlahBarangString)); // Mengatur spinner ke jumlah barang yang dipilih
                // Mengatur radio button berdasarkan jenis transaksi
                if ("Keluar".equals(jenisTransaksi)) {
                    radioKeluar.setSelected(true);
                } else {
                    radioMasuk.setSelected(true);
                }
            }   
        }                                           
    
        private void menuStokMouseClicked(java.awt.event.MouseEvent evt) {                                      
            // TODO add your handling code here:
            // Membuka form MenuStok dan menutup form saat ini.
            new MenuStok().setVisible(true);
            dispose();
        }                                     
    
        private void menuBarangMouseClicked(java.awt.event.MouseEvent evt) {                                        
            // TODO add your handling code here:
            // Membuka form MenuBarang dan menutup form saat ini.
            new MenuBarang().setVisible(true);
            dispose();
        }                                       
    
        private void menuHomeMouseClicked(java.awt.event.MouseEvent evt) {                                      
            // TODO add your handling code here:
            // Membuka form MenuHome dan menutup form saat ini.
            new MenuUtama().setVisible(true);
            dispose();
        }                                     
    
        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
    
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new MenuTransaksi().setVisible(true);
                }
            });
        }
    
        // Variables declaration - do not modify                     
        private javax.swing.JButton btnHapus;
        private javax.swing.JButton btnSimpan;
        private javax.swing.JButton btnUbah;
        private javax.swing.ButtonGroup buttonGroup1;
        private javax.swing.JComboBox<String> comboBarang;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JMenuBar jMenuBar1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JMenu menuBarang;
        private javax.swing.JMenu menuHome;
        private javax.swing.JMenu menuStok;
        private javax.swing.JRadioButton radioKeluar;
        private javax.swing.JRadioButton radioMasuk;
        private javax.swing.JSpinner spinnerJumlah;
        private javax.swing.JTable tabelTransaksi;
        // End of variables declaration                   
    }
   ```
   - View Menu Utama
   ``` java
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package View;
    
    /**
     *
     * @author rhnfa
     */
    public class MenuUtama extends javax.swing.JFrame {
    
        /**
         * Creates new form MenuUtama
         */
        public MenuUtama() {
            initComponents();
        }
    
        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {
    
            jPanel1 = new javax.swing.JPanel();
            jPanel2 = new javax.swing.JPanel();
            jLabel1 = new javax.swing.JLabel();
            jPanel3 = new javax.swing.JPanel();
            btnStok = new javax.swing.JButton();
            btnBarang = new javax.swing.JButton();
            btnTransaksi = new javax.swing.JButton();
    
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    
            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MENU UTAMA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 36))); // NOI18N
    
            jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
            jLabel1.setText("PILIH MENU");
    
            btnStok.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
            btnStok.setText("STOK");
            btnStok.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnStokActionPerformed(evt);
                }
            });
    
            btnBarang.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
            btnBarang.setText("BARANG");
            btnBarang.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnBarangActionPerformed(evt);
                }
            });
    
            btnTransaksi.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
            btnTransaksi.setText("TRANSAKSI");
            btnTransaksi.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnTransaksiActionPerformed(evt);
                }
            });
    
            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(43, 43, 43)
                    .addComponent(btnStok)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                    .addComponent(btnBarang)
                    .addGap(67, 67, 67)
                    .addComponent(btnTransaksi))
            );
            jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(39, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnStok)
                        .addComponent(btnBarang)
                        .addComponent(btnTransaksi))
                    .addGap(36, 36, 36))
            );
    
            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(285, 285, 285)
                            .addComponent(jLabel1))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(63, 63, 63)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(98, Short.MAX_VALUE))
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addGap(36, 36, 36)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(94, Short.MAX_VALUE))
            );
    
            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(59, 59, 59)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(52, Short.MAX_VALUE))
            );
    
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
    
            pack();
        }// </editor-fold>                        
    
        private void btnStokActionPerformed(java.awt.event.ActionEvent evt) {                                        
            // TODO add your handling code here:
            // Membuka form MenuStok dan menutup form saat ini.
            new MenuStok().setVisible(true);
            dispose();
        }                                       
    
        private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {                                          
            // TODO add your handling code here:
            // Membuka form MenuBarang dan menutup form saat ini.
            new MenuBarang().setVisible(true);
            dispose();
        }                                         
    
        private void btnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {                                             
            // TODO add your handling code here:
            // Membuka form MenuTransaksi dan menutup form saat ini.
            new MenuTransaksi().setVisible(true);
            dispose();
        }                                            
    
        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
    
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new MenuUtama().setVisible(true);
                }
            });
        }
    
        // Variables declaration - do not modify                     
        private javax.swing.JButton btnBarang;
        private javax.swing.JButton btnStok;
        private javax.swing.JButton btnTransaksi;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        // End of variables declaration                   
    }
   ```
## Screenshots
1. Menu Utama

![Screenshot 2024-11-23 155058](https://github.com/user-attachments/assets/7f83c089-c199-4f48-b090-1b128f4ccf80)

2. Menu Stok

![Screenshot 2024-11-24 003825](https://github.com/user-attachments/assets/9fe7c8ba-ee29-48d3-abdb-3cae63ed1133)


3. Menu Barang

![Screenshot 2024-11-23 155114](https://github.com/user-attachments/assets/15ad2869-f7a3-4298-9c68-3729fdfdfc2b)

4. Menu Transaksi

![Screenshot 2024-11-23 155119](https://github.com/user-attachments/assets/05114f81-1401-4ef0-a32f-80c234936a83)



## Contoh Penggunaan

1. Mengelola Stok dan Barang:
- Untuk menambah barang, pilih menu "Barang" dan gunakan tombol "SIMPAN".
- Untuk mengelola stok, pilih menu "Stok" dan lihat daftar barang yang tersedia.

2. Mengelola Transaksi:
- Pilih menu "Transaksi" untuk mencatat transaksi masuk atau keluar.
- Isi informasi yang diperlukan dan simpan transaksi.

## Pembuat

- Nama : Muhammad Raihan Fadhillah
- NPM : 2210010404

