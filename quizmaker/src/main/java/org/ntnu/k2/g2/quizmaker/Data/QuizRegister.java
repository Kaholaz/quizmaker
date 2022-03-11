package org.ntnu.k2.g2.quizmaker.Data;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class QuizRegister {
    private class DataBase {
        private static DataBase dataBaseInstance;

        private static final String DB_URL = "jdbc:sqlite:";
        private static final String USER = "admin";
        private static final String PASS = "password";

        private static final String DB_PATH = "src/main/resources/";
        private static final String DB_NAME = "quizMaker.data";
        private static final String DB_NAME_TEST_PREFIX = "test_";

        private static boolean test = true;

        private DataBase () {
            File root = new File(".");
            File db_path = new File(root, String.format("%s%s%s", DB_PATH, test ? DB_NAME_TEST_PREFIX : "", DB_NAME));
            String db_url = DB_URL.concat(db_path.getAbsolutePath());

            try {
                Connection connection = DriverManager.getConnection(db_url);
                if (connection != null) {
                    DatabaseMetaData meta = connection.getMetaData();
                }
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private class QuizDAO {

    }

    private class QuestionDAO {

    }

    private class TeamDAO {

    }

    private List<Quiz> quizList;

    public void addQuiz(Quiz quizName) {}

    public Quiz removeQuiz(int index) throws IndexOutOfBoundsException {return null;}
}
