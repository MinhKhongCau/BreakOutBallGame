/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author minhn
 */
public class ConnectionConfig {

    /**
     * Function getConnection will be return Connection with database SQL SERVER
     * @return
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            //1. Create link connect with sql server
            String url = "jdbc:sqlserver://localhost:1433;databaseName=BreakOutBall;encrypt=true;trustServerCertificate=true;";
            //2. Create username & password
            String username = "sa";
            String password = "123456";
            //3. Execute link
            con = (Connection) DriverManager.getConnection(url, username, password);
            if (con != null) {
                System.out.println("Connect to database sucessfully!!!");
            }
        } catch (SQLException ex) {
                Logger.getLogger(ConnectionConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
}
