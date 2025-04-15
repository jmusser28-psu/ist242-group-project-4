package database;

import java.sql.Connection;

public class DatabaseManager {
    Connection connection;

    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    
}
