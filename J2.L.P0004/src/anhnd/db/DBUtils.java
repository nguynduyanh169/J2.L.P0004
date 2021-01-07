/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhnd.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author anhnd
 */
public class DBUtils {
   
    public static Connection getMyConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=BookManagement", "sa", "1234567");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
