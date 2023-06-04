/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Beans;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author votantai
 */
public class ConnectToSQL {
    public static Connection connection;
    private static String DB_URL="jdbc:mysql://localhost:3306/LatHinhDB";
    private static String USER_NAME="root";
    private static String PASSWORD="";
        
    public static Connection getConnection() {
        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("Kết nối thành công!");
        } catch (Exception ex) {
            System.out.println("Kết nối thất bại!");
            ex.printStackTrace();
        }
        return connection;
    }
}
