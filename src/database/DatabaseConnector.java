package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnector is used to create a connection to the SQLite database used by the
 * Vehicle Management System. It handles the connection to the database.
 */
public class DatabaseConnector {
    private final String DBURL = "jdbc:sqlite:./vehicle_maintenance.db";
    private Connection connection;

    /**
     * This constructor attempts to make a connection with the database. If the connection is
     * successful, it will be used through  getConnection(). If not, the error will
     * be printed.
     */

    public DatabaseConnector() {
        try {
            this.connection = DriverManager.getConnection(DBURL);
        }
        catch (SQLException e) {
            System.err.println("An error has occurred: " + e.getMessage());
        }
    }

    /**
     * Returns the active connection
     */

    public Connection getConnection() {
        return connection;
    }

}
