package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final String DBURL = "jdbc:sqlite:./vehicle_maintenance.db";
    private Connection connection;

    public DatabaseConnector() {
        try {
            this.connection = DriverManager.getConnection(DBURL);
        }
        catch (SQLException e) {
            System.err.println("An error has occurred: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
