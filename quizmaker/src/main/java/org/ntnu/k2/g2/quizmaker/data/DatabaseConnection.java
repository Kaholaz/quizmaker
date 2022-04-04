package org.ntnu.k2.g2.quizmaker.Data;

import java.io.File;
import java.sql.*;

/**
 * A class that deals with establishing and closing connections to the database,
 * as well as setting up the necessary tables and columns of the database.
 */
class DatabaseConnection {
    private static Connection connectionSingleton;

    private static final String DB_URL = "jdbc:sqlite:";

    private static final String DB_PATH = "src/main/resources/";
    private static final String DB_NAME = "quizMaker.data";
    private static final String DB_NAME_TEST_PREFIX = "test_";

    private static final boolean test = isTest();

    /**
     * Creates a new database instance.
     * The constructor establishes a connection to the database and saves it to the connection property.
     */
    private DatabaseConnection() {
        File db_path = getDbPath();
        File root = new File(db_path.getParent());

        root.mkdirs();
        String db_url = DB_URL.concat(db_path.getAbsolutePath());
        try {
            connectionSingleton = DriverManager.getConnection(db_url);
            initTables();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * There is only ever one connection to the DB. It is retrieved by using this method.
     * This method initializes a new connection instance if one does not already exist.
     *
     * @return The connection to the database
     */
    public static Connection getConnection() {
        try {
            if (connectionSingleton == null || connectionSingleton.isClosed() || !getDbPath().exists()) {
                new DatabaseConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionSingleton;
    }

    /**
     * Returns the appropriate file path to the database. This method takes into account whenever or not
     * the database is being run inside a test.
     *
     * @return The path to the database
     */
    public static File getDbPath() {
        File path = new File(DB_PATH);
        String db_name = (test ? DB_NAME_TEST_PREFIX : "").concat(DB_NAME);
        return new File(path, db_name);
    }

    /**
     * Checks whenever or not the database is running inside a test by searching through the stack trace.
     * This method is used to determine what database URL to use.
     *
     * @return True if the database is running inside a test, false if not.
     */
    private static boolean isTest() {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraces) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a new database with the appropriate fields
     */
    private void initTables() {
        Connection con;
        Statement statement;

        try {
            con = getConnection();
            statement = con.createStatement();

            // quizzes table
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS quizzes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    sheetId TEXT,
                    active INTEGER,
                    lastChanged TEXT
                    );
                    """);

            // teams table
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS teams (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      name TEXT,
                      score INTEGER,
                      quizId INTEGER,
                      FOREIGN KEY (quizId) REFERENCES quiz(id)
                    );
                    """);

            // questions table
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS questions (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      question TEXT,
                      answer TEXT,
                      quizId INTEGER,
                      FOREIGN KEY (quizId) REFERENCES quiz(id)
                    );
                    """);

            // indexed columns
            statement.execute("CREATE INDEX IF NOT EXISTS inx_teamQuizId ON teams(quizId);");
            statement.execute("CREATE INDEX IF NOT EXISTS inx_questionQuizId ON questions(quizId);");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Closes a PreparedStatement, and handles any thrown exceptions.
     *
     * @param preparedStatement The PreparedStatement
     */
    public static void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes a ResultSet, and handles any thrown exceptions
     *
     * @param resultSet The ResultSet
     */
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
