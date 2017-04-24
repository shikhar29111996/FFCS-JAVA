

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
import java.sql.*;
public class ConnectDB {
    public Connection connecttoDB() {
        Connection c=null;
        try {
            String host = "jdbc:derby://localhost:1527/Mini";
            String uName = "root";
            String uPass = "root";
            c = DriverManager.getConnection(host, uName, uPass);
        } catch (SQLException ex) {
            System.out.println("Conection to Database not Sucessful !!");
        }
        return c;
    }
}
