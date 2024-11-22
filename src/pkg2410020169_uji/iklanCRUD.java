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
public class iklanCRUD {
    String jdbcUrl = "jdbc:mysql://localhost:3306/2410020169_periklanan";
    String username = "root";
    String password = "";
    Connection koneksi;
    
    public iklanCRUD (){
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
    
//    public boolean duplicateKey(String table, String PrimaryKey,String value) {
//        boolean hasil = false;
//        try {
//            Statement sts = getKoneksi().createStatement();
//            ResultSet rs = sts.executeQuery("SELECT*FROM" +table+"WHERE"+PrimaryKey+"='"+value+"'");
//            hasil = rs.next();
//            rs.close();
//            sts.close();
//            getKoneksi().close();
//        } catch (Exception e) {
//            System.err.println(e.toString());
//        }
//        return hasil;
//    }
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
    
    public void SimpanIKLANStatement(String id_iklan,String judul,String tayang,String waktu,String harga) {
        try{
            if (duplicateKey("iklan", "id_iklan", id_iklan)) {
                JOptionPane.showMessageDialog(null,"ID IKLAN SUDAH TERINPUT");
            } else {
                String SQL = "INSERT INTO iklan (id_iklan, judul, tayang, waktu, harga) VALUE('"+ id_iklan + "','" + judul + "','" + tayang + "','" + waktu + "','" + harga + "')";
                Statement perintah = getKoneksi().createStatement();
                perintah.executeUpdate(SQL);
                perintah.close();
                getKoneksi().close();
                JOptionPane.showMessageDialog(null,"DATTA BERHASIL DISIMPAN");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void SimpanIKLANPreparedstmt(String id_iklan,String judul,String tayang,String waktu,String harga) {
    try {
        if (duplicateKey("iklan", "id_iklan", id_iklan)) {
            JOptionPane.showMessageDialog(null,"ID IKLAN SUDAH TERINPUT");
        } else {
            String SQL = "INSERT INTO iklan (id_iklan, judul, tayang, waktu, harga) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement simpan = getKoneksi().prepareStatement(SQL);
            simpan.setString(1, id_iklan);
            simpan.setString(2, judul);
            simpan.setString(3, tayang);
            simpan.setString(4, waktu);
            simpan.setString(5, harga);
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
