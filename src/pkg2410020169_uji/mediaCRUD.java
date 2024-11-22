/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg2410020169_uji;
        import java.sql.PreparedStatement;
        import java.sql.Driver;
        import java.sql.DriverManager;
        import java.sql.Connection;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.sql.ResultSet;
        import javax.swing.JOptionPane;
        import javax.swing.JTextField;
/**
 *
 * @author taufi
 */
    public class mediaCRUD {
    String jdbcUrl = "jdbc:mysql://localhost:3306/2410020169_periklanan";
    String username = "root";
    String password = "";
    Connection koneksi;

    public mediaCRUD (){
        try(Connection Koneksi = DriverManager.getConnection(jdbcUrl, username, password)) {
            Driver mysqlDriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);
            
            System.out.println("input berhasil men!");
        } catch (SQLException error) {
            System.err.println(error.toString());
        }
    }    
    public Connection getKoneksi () throws SQLException{
        try {
            Driver mysqlDriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
        return DriverManager.getConnection(this.jdbcUrl, this.username, this.password);
    }

    public boolean duplicateKey(String table, String primaryKey, String value) {
    boolean hasil = false;
    String query = "SELECT * FROM " + table + " WHERE " + primaryKey + " = ?";
    try (Connection koneksi = getKoneksi(); 
         PreparedStatement stmt = koneksi.prepareStatement(query)) {
        stmt.setString(1, value);
        ResultSet rs = stmt.executeQuery();
        hasil = rs.next();
    } catch (SQLException e) {
        System.err.println("Error checking duplicate key: " + e.toString());
    }
    return hasil;
    }
    
    public void SimpanMEDIAStatement(String id_media,String nama_media,String jenis) {
        try{
            if (duplicateKey("media", "id_media", id_media)) {
                JOptionPane.showMessageDialog(null,"ID MEDIA SUDAH TERINPUT");
            } else {
                String SQL = "INSERT INTO media (id_media, nama_media, jenis) VALUE('"+ id_media + "','" + nama_media + "','" + jenis + "')";
                Statement perintah = getKoneksi().createStatement();
                perintah.executeUpdate(SQL);
                perintah.close();
                getKoneksi().close();
                JOptionPane.showMessageDialog(null,"DATA BERHASIL DISIMPAN");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void SimpanMEDIAPreparedstmt(String id_media,String nama_media,String jenis) {
    try {
        if (duplicateKey("media", "id_media", id_media)) {
            JOptionPane.showMessageDialog(null,"ID MEDIA SUDAH TERINPUT");
        } else {
            String SQL = "INSERT INTO media (id_media, nama_media, jenis) VALUES (?, ?, ?)";
            PreparedStatement simpan = getKoneksi().prepareStatement(SQL);
            simpan.setString(1, id_media);
            simpan.setString(2, nama_media);
            simpan.setString(3, jenis);
            simpan.executeUpdate();
            System.out.println("Data Berhasil Disimpan");
            simpan.close();
            getKoneksi().close();
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        }
   }
}
