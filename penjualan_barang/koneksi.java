/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package penjualan_barang;

import com.mysql.cj.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ALDIE ACHMAD
 */
public class koneksi {

    private static java.sql.Connection koneksi;

    public static java.sql.Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/penjualan";
                String user = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
            } catch (SQLException t) {
                System.out.println("Error Membuat Koneksi");
            }
        }
        return koneksi;
    }

    public static void eksekusiQuery(String fk) {
        Connection conn = (Connection) getKoneksi();
        if (conn != null) {
            try {
                String query = "SELECT * FROM penjualan WHERE fk = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                    pstmt.setString(1, fk);
                    
                    ResultSet rs = pstmt.executeQuery();
                    
                    if (rs.last()) {
                        int rowCount = rs.getRow();
                        System.out.println("Total Rows: " + rowCount);
                    }
                    
                    // Tampilkan data jika ada
                    rs.beforeFirst(); // Kembali ke awal ResultSet
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getInt("id") + " - Nama: " + rs.getString("name") + " - Jumlah: " + rs.getDouble("amount"));
                    }
                    
                    // Menutup koneksi
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Koneksi tidak tersedia.");
        }
    }
}
