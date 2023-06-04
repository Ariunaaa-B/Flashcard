package dbUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    private static final String DATABASE_PATH = "C:/Users/arrrr/eclipse-workspace/Flashcard/build/classes/dictionary.db";
    private static Connection connection = null;

    private DatabaseConnector() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH);
            //createDatabaseIfNotExists();
        } catch (ClassNotFoundException e) {
            System.err.println("Error: SQLite JDBC driver not found");
        } catch (SQLException e) {
            System.err.println("Error: Failed to connect to SQLite database");
        }
    }

    private void createDatabaseIfNotExists() {
        File databaseFile = new File(DATABASE_PATH);
        if (!databaseFile.exists()) {
            try {
                Statement statement = connection.createStatement();
                
                // Create flashcard table
                statement.execute("CREATE TABLE IF NOT EXISTS flashcard (id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, parentId INTEGER REFERENCES flashcardCollection (id) ON DELETE CASCADE ON UPDATE NO ACTION, front TEXT, back TEXT)");
                
                // Create flashcardCollection table
                statement.execute("CREATE TABLE IF NOT EXISTS flashcardCollection (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, size INTEGER, userId INTEGER REFERENCES user (id) ON DELETE CASCADE)");
                
                // Create user table
                statement.execute("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)");
                
                System.out.println("Database created successfully.");
            } catch (SQLException e) {
                System.err.println("Error: Failed to create database");
            }
        }
    }


    public static Connection getConnection() {
        if (connection == null) {
            new DatabaseConnector();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("Error: Failed to close the database connection");
            }
        }
    }
}
