package mx.ceined.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD { // Clase singleton

    private static String url="jdbc:mysql://localhost:3306/cafeteria?serverTimezone=UTC";
    private static String username="root";
    private static String password="toor";
    private static Connection connection;

    public static Connection getInstance() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(url,username,password);
        }
        return connection;
    }

}
