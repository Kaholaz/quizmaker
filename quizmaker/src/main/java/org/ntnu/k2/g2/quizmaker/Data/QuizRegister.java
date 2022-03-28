package org.ntnu.k2.g2.quizmaker.Data;

import org.ntnu.k2.g2.quizmaker.UserData.QuizResultManager;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * A class used to interact with the storage system of quizzes
 */
public class QuizRegister {
    /**
     * @return A complete representation of the database represented by an arraylist of quizzes
     */
    public ArrayList<Quiz> getQuizList() {
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.getAllQuizzes();
    }

    /**
     * Gets all quizzes where the isActive flag is set to true.
     * @return An ArrayList of all active quizzes.
     */
    public ArrayList<Quiz> getActiveQuizzes() {
        return new ArrayList<>(getQuizList().stream().filter(Quiz::isActive).toList());
    }

    /**
     * Gets all quizzes where the isActive flag is set to false.
     * @return An ArrayList of al inactive/archived quizzes.
     */
    public ArrayList<Quiz> getArchivedQuizzes() {
        return new ArrayList<>(getQuizList().stream().filter(quiz -> !quiz.isActive()).toList());
    }

    /**
     * Gets a quiz with a specific id.
     * @param id The id of the quiz.
     * @return A quiz object representation of the entry in the database.
     *         {@code null} is returned if no quiz matching the query is found in the database.
     */
    public Quiz getQuiz(int id) {
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.getQuizById(id);
    }

    /**
     * Gets a team with a specific id
     * @param id The id of the team.
     * @return A team object representation of the entry in the database.
     *         {@code null} is returned if no team matching the query is found in the database.
     */
    public Team getTeam(int id) {
        TeamDAO teamDAO = new TeamDAO();
        return teamDAO.getTeamById(id);
    }

    /**
     * Gets a question with a specific id
     * @param id The id of the question.
     * @return A question object representation of the entry in the database.
     *         {@code null} is returned if no question matching the query is found in the database.
     */
    public Question getQuestion(int id) {
        QuestionDAO questionDAO = new QuestionDAO();
        return questionDAO.getQuestionById(id);
    }

    /**
     * Saves a quiz and ALL its content to the database.
     * @param quiz The quiz to save to the database.
     * @return A quiz object representation of the entry in the database AFTER it has been updated.
     *         {@code null} is returned if something went wrong when the quiz was saved.
     */
    public Quiz saveQuiz(Quiz quiz) {
        QuizDAO quizDAO = new QuizDAO();
        Quiz outQuiz = quizDAO.updateQuiz(quiz);
        if (!quiz.getName().equals(outQuiz.getName())) {
            QuizResultManager.changeResultSheetName(quiz);
        }
        return quizDAO.updateQuiz(quiz);
    }

    /**
     * Saves an ArrayList of quizzes and all changes to its contents to the database.
     * @param quizzes The quizzes to save to the database.
     * @return An ArrayList of all quizzes that were properly saved.
     */
    public ArrayList<Quiz> saveQuizzes(ArrayList<Quiz> quizzes) {
        // Save all quizzes that were properly saved
        return new ArrayList<>(quizzes.stream().map(this::saveQuiz).filter(Objects::nonNull).toList());
    }

    /**
     * Saves a team to the database. To use this method, the team object should already be an entry in the database.
     * If it is not, one should instead use the method newTeam to create a new team with a relation to a quiz.
     * @param team The team to save to the database.
     * @return A team object representation of the entry in the database AFTER it has been updated.
     *         {@code null} is returned if something went wrong when the team was saved.
     */
    public Team saveTeam(Team team) {
        TeamDAO teamDAO = new TeamDAO();
        return teamDAO.updateTeam(team);
    }

    /**
     * Saves a question to the database. To use this method, the question object should already be an entry in the database.
     * If it is not, one should instead use the method newQuestion to create a new question with a relation to a quiz.
     * @param question The question to save to the database.
     * @return A question object representation of the entry in the database AFTER it has been updated.
     *         {@code null} is returned if something went wrong when the question was saved.
     */
    public Question saveQuestion(Question question) {
        QuestionDAO questionDAO = new QuestionDAO();
        return questionDAO.updateQuestion(question);
    }

    /**
     * Create a new quiz.
     * @return The new quiz.
     */
    public Quiz newQuiz() {
        return newQuiz(false);
    }

    /**
     * Create a new quiz.
     * @param isTest If this flag is set to true, new quizzes are created without a result sheet.
     *               This is to be used in testing.
     * @return The new quiz.
     */
    protected Quiz newQuiz(boolean isTest) {
        Quiz quiz = new Quiz();
        quiz.setName("new quiz");

        if (!isTest) {
            try {
                QuizResultManager.createResultSheet(quiz);
            }
            catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
        }

        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.updateQuiz(quiz);
    }

    /**
     * Creates a new team entry in the database and ads an object representation to the supplied quiz.
     * This method must be called when creating a new teams.
     * @param quiz The quiz the team should be part of.
     * @return A team object representation of the entry in the database AFTER it has been added to the database.
     *         {@code null} is returned if something went wrong when the team was added to the database.
     */
    public Team newTeam(Quiz quiz) {
        TeamDAO teamDAO = new TeamDAO();
        Team team = teamDAO.updateTeam(new Team(), quiz.getId());
        quiz.getTeams().put(team.getId(), team);
        return team;
    }

    /**
     * Creates a new question entry in the database and ads an object representation to the supplied quiz.
     * This method must be called when creating a new questions.
     * @param quiz The quiz the question should be part of.
     * @return A question object representation of the entry in the database AFTER it has been added to the database.
     *         {@code null} is returned if something went wrong when the question was added to the database.
     */
    public Question newQuestion(Quiz quiz) {
        QuestionDAO questionDAO = new QuestionDAO();
        Question question = questionDAO.updateQuestion(new Question(), quiz.getId());
        quiz.getQuestions().put(question.getId(), question);
        return question;
    }

    /**
     * Removes a quiz and all its contents (questions and teams) from the database.
     * @param quiz The quiz to remove from the database.
     * @return True if the operation was successful, false if not.
     */
    public boolean removeQuiz(Quiz quiz) {
        QuizResultManager.deleteResultSheet(quiz);

        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.removeQuizById(quiz.getId());
    }

    /**
     * Removes a team from the database.
     * @param quiz The quiz the team is a component of.
     * @param teamId The id of the team to remove.
     * @return True if the operation was successful, false if not.
     */
    public boolean removeTeam(Quiz quiz, int teamId) {
        quiz.getTeams().remove(teamId);

        TeamDAO teamDAO = new TeamDAO();
        return teamDAO.removeTeamById(teamId);
    }

    /**
     * Removes a team from the database.
     * @param quiz The quiz the team is a component of.
     * @param questionId The id of the team to remove.
     * @return True if the operation was successful, false if not.
     */
    public boolean removeQuestion(Quiz quiz, int questionId) {
        quiz.getQuestions().remove(questionId);

        QuestionDAO questionDAO = new QuestionDAO();
        return questionDAO.removeQuestionById(questionId);
    }

    /**
     * Populates the database with a set number of quizzes and a set number of teams and question per quiz.
     * @param quizzes The number of quizzes to fill that database with.
     * @param teams The number of teams to fill each quiz with.
     * @param questions The number of questions to fill each quiz with.
     * @return An ArrayList of all the quizzes that were added to the database.
     */
    public ArrayList<Quiz> populateDatabase(int quizzes, int teams, int questions) {
        ArrayList<Quiz> quizArrayList = new ArrayList<>();

        for (int i = 1; i <= quizzes; ++i) {
            Quiz quiz = newQuiz();
            quiz.setName(String.format("Quiz %d", i));
            for (int j = 1; j <= teams; ++j) {
                Team team = newTeam(quiz);
                team.setTeamName(String.format("Team %d", j));
                team.setScore(j);
            }
            for (int j = 1; j <= questions; ++j) {
                Question question = newQuestion(quiz);
                question.setQuestion(String.format("Question %d", j));
                question.setAnswer(String.format("Answer %d", j));
            }
            quizArrayList.add(saveQuiz(quiz));
        }

        return quizArrayList;
    }

    /**
     * A class that deals with establishing and closing connections to the database,
     * as well as setting up the necessary tables and columns of the database.
     */
    protected static class DatabaseConnection {
        private static Connection connectionSingleton;

        private static final String DB_URL = "jdbc:sqlite:";

        private static final String DB_PATH = "src/main/resources/";
        private static final String DB_NAME = "quizMaker.data";
        private static final String DB_NAME_TEST_PREFIX = "test_";

        private static final boolean test = isTest();

        /**
         * Creates a new database instance.
         * The constructor establishes a connection to the database and saves it to the connection property.
         *
         */
        private DatabaseConnection() {
            File db_path = getDbPath();
            File root = new File(db_path.getParent());

            root.mkdirs();
            String db_url = DB_URL.concat(db_path.getAbsolutePath());
            try {
                connectionSingleton = DriverManager.getConnection(db_url);
                initTables();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        /**
         * There is only ever one connection to the DB. It is retrieved by using this method.
         * This method initializes a new connection instance if one does not already exist.
         * @return The connection to the database
         */
        public static Connection getConnection() {
            try {
            if (connectionSingleton == null || connectionSingleton.isClosed() || !getDbPath().exists()) {
                    new DatabaseConnection();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return connectionSingleton;
        }

        /**
         * Returns the appropriate file path to the database. This method takes into account whenever or not
         * the database is being run inside a test.
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
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        /**
         * Closes a PreparedStatement, and handles any thrown exceptions.
         * @param preparedStatement The PreparedStatement
         */
        public static void closePreparedStatement(PreparedStatement preparedStatement) {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Closes a ResultSet, and handles any thrown exceptions
         * @param resultSet The ResultSet
         */
        public static void closeResultSet(ResultSet resultSet) {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * A class used to interact with the "quizzes"-table in the database.
     * Instances of this class can be used to save and retrieve quiz-data form the database.
     */
    protected static class QuizDAO {
        /**
         * Constructs a quiz based on the ResultSet of an SQL query. Please note:
         * This method does not fill the teams or questions properties. These are filled in getQuizFromId()
         * @param result The ResultSet of an SQL query.
         * @return A quiz based on the result of the SQL query.
         */
        private Quiz getQuizFromResultSet(ResultSet result) {
            Quiz quiz = null;
            try {
                if (result.next()) {
                    quiz = new Quiz();
                    quiz.setId(result.getInt("id"));
                    quiz.setName(result.getString("name"));
                    quiz.setActive(result.getBoolean("active"));
                    quiz.setSheetId("sheetId");
                    quiz.setLastChanged(LocalDateTime.parse(result.getString("lastChanged")));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closeResultSet(result);
            }
            return quiz;
        }

        /**
         * Constructs an ArrayList of quizzes based on the ResultSet of an SQL query. Please note:
         * This method does not fill the teams or questions properties.
         * These properties are filled in the methods that call this method.
         * @param result The ResultSet of an SQL property
         * @return An ArrayList of all extracted quizzes form the ResultSet.
         */
        private ArrayList<Quiz> getQuizzesFromResultSet(ResultSet result) {
            ArrayList<Quiz> quizzes = new ArrayList<>();
            try {
                while (result.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setId(result.getInt("id"));
                    quiz.setName(result.getString("name"));
                    quiz.setActive(result.getBoolean("active"));
                    quiz.setSheetId("sheetId");
                    quiz.setLastChanged(LocalDateTime.parse(result.getString("lastChanged")));

                    quizzes.add(quiz);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closeResultSet(result);
            }
            return quizzes;
        }

        /**
         * Gets a quiz by an id. This quiz has its teams and questions properties filled.
         * This method returns null if no quiz is found.
         * @param id The id of the quiz.
         * @return The quiz with the id in the database. If no quiz is found, null is returned.
         */
        public Quiz getQuizById(int id) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            Quiz quiz = null;

            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM quizzes WHERE id=?;");
                preparedStatement.setInt(1, id);
                result = preparedStatement.executeQuery();

                quiz = getQuizFromResultSet(result);

                TeamDAO teamDAO = new TeamDAO();
                QuestionDAO questionDAO = new QuestionDAO();
                quiz.setQuestions(questionDAO.getQuestionsByQuizId(id));
                quiz.setTeams(teamDAO.getTeamsByQuizId(id));
            }
            catch (NullPointerException e) {
                return null;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return quiz;
        }

        /**
         * Gets an ArrayList of all quizzes in the database.
         * @return An ArrayList containing all quizzes in the database. Returns an empty list if there are no entries.
         */
        private ArrayList<Quiz> getAllQuizzes() {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            ArrayList<Quiz> quizzes = new ArrayList<>();

            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM quizzes;");
                result = preparedStatement.executeQuery();

                quizzes = getQuizzesFromResultSet(result);

                TeamDAO teamDAO = new TeamDAO();
                QuestionDAO questionDAO = new QuestionDAO();

                quizzes.forEach(quiz -> {
                    quiz.setQuestions(questionDAO.getQuestionsByQuizId(quiz.getId()));
                    quiz.setTeams(teamDAO.getTeamsByQuizId(quiz.getId()));
                });
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return quizzes;
        }

        /**
         * Saves a quiz to the database.
         * @param quiz The quiz to save to the database.
         * @return The quiz as it is saved in the database after the update is done.
         */
        public Quiz updateQuiz(Quiz quiz) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;

            try {
                connection = DatabaseConnection.getConnection();

                // Updates the last changed
                quiz.setLastChanged(LocalDateTime.now());
                // If quiz is not in database
                if (quiz.getId() == -1) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO quizzes (name, sheetId, active, lastChanged) VALUES (?, ?, ?, ?);");
                } else {
                    preparedStatement = connection.prepareStatement(
                            "UPDATE quizzes SET name=?, sheetId=?, active=?, lastChanged=? WHERE id=?;");
                    preparedStatement.setInt(5, quiz.getId());
                }
                preparedStatement.setString(1, quiz.getName());
                preparedStatement.setString(2, quiz.getSheetId());
                preparedStatement.setBoolean(3, quiz.isActive());
                preparedStatement.setString(4, quiz.getLastChanged().toString());

                // New teams and questions
                HashMap<Integer, Team> newTeams = quiz.getTeams();
                HashMap<Integer, Question> newQuestions = quiz.getQuestions();

                // Executes query and gets the new quiz
                int resultRows = preparedStatement.executeUpdate();

                // Quiz was inserted successfully
                if (quiz.getId() == -1 && resultRows == 1) {
                    result = preparedStatement.getGeneratedKeys();
                    quiz.setId(result.getInt(1));
                }
                // Quiz was updated unsuccessfully
                else if (resultRows == 0) {
                    return null;
                }
                final int quizId = quiz.getId();

                // Make updates to the team and question tables to reflect the quiz object
                Quiz oldQuizData = getQuizById(quizId);
                QuestionDAO questionDAO = new QuestionDAO();
                TeamDAO teamDAO = new TeamDAO();

                // Remove removed questions
                Stream<Integer> UniqueOldQuestions = oldQuizData.getQuestions().keySet().stream()
                        .filter(oldId -> !newQuestions.containsKey(oldId));
                UniqueOldQuestions.forEach(questionDAO::removeQuestionById);

                // Remove removed teams
                Stream<Integer> UniqueOldTeams = oldQuizData.getTeams().keySet().stream()
                        .filter(oldId -> !newTeams.containsKey(oldId));
                UniqueOldTeams.forEach(teamDAO::removeTeamById);

                // Update teams
                newTeams.values().forEach(team -> teamDAO.updateTeam(team, quizId));

                // Update questions
                newQuestions.values().forEach(question -> questionDAO.updateQuestion(question, quizId));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e) {
                return null;
            }
            finally {

                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return quiz;
        }

        /**
         * Removes a quiz and ALL its components (questions and teams) from the database.
         * @param id The ID of the quiz to remove.
         * @return True if the operation was successful, false if not.
         */
        public boolean removeQuizById(int id) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            boolean result = false;

            try {
                Quiz quiz = getQuizById(id);

                // remove all questions
                QuestionDAO questionDAO = new QuestionDAO();
                quiz.getQuestions().keySet().forEach(questionDAO::removeQuestionById);


                // remove all teams
                TeamDAO teamDAO= new TeamDAO();
                quiz.getQuestions().keySet().forEach(teamDAO::removeTeamById);


                // removes the quiz itself
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement(
                        "DELETE FROM quizzes WHERE id=?");
                preparedStatement.setInt(1, id);

                result = preparedStatement.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {

                DatabaseConnection.closePreparedStatement(preparedStatement);
            }
            return result;
        }
    }

    /**
     * A class that deals with the "questions"-table in the database.
     * Instances of this class can be used to save and retrieve data from the database.
     */
    protected static class QuestionDAO {
        /**
         * Returns a question from the ResultSet returned by a SQL query.
         * @param result The ResultSet of an SQL query.
         * @return The question
         */
        private Question getQuestionFromResultSet(ResultSet result) {
            Question question = null;
            try {
                if (result.next()) {
                    question = new Question();
                    question.setId(result.getInt("id"));
                    question.setQuestion(result.getString("question"));
                    question.setAnswer(result.getString("answer"));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closeResultSet(result);
            }
            return question;
        }

        /**
         * Gets the quiz id of the ResultSet of a SQL query in the teams table.
         * @param result The ResultSet from an SQL query.
         * @return The quiz id of the team returned by the SQL query.
         */
        private int getQuizIdByResultSet(ResultSet result) {
            int quizId = -1;
            try {
                if (result.next()) {
                    quizId = result.getInt("quizId");
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closeResultSet(result);
            }
            return quizId;
        }

        /**
         * Returns an ArrayList of all questions extracted from a ResultSet of a SQL query.
         * @param result The ResultSet of and SQL query
         * @return An ArrayList of all questions extracted from the ResultSet.
         */
        private HashMap<Integer, Question> getQuestionsFromResultSet(ResultSet result) {
            HashMap<Integer, Question> questions = new HashMap<>();

            try {
                while (result.next()) {
                    Question question = new Question();
                    question.setId(result.getInt("id"));
                    question.setQuestion(result.getString("question"));
                    question.setAnswer(result.getString("answer"));

                    questions.put(question.getId(), question);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closeResultSet(result);
            }
            return questions;
        }

        /**
         * Gets a question form the database based on its ID.
         * @param id The id of the question.
         * @return The question with this ID. If there is no question that matches the ID, a null pointer is returned.
         */
        public Question getQuestionById(int id) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            Question question = null;

            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM questions WHERE id=?;");
                preparedStatement.setInt(1, id);
                result = preparedStatement.executeQuery();

                question = getQuestionFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return question;
        }

        public HashMap<Integer, Question> getQuestionsByQuizId(int quizId) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            HashMap<Integer, Question> questions = null;

            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM questions WHERE quizId=?;");
                preparedStatement.setInt(1, quizId);
                result = preparedStatement.executeQuery();

                questions = getQuestionsFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return questions;
        }

        /**
         * Gets the id of a quiz that contains a question with the supplied id.
         * @param questionId The id of the question.
         * @return The quiz id of the quiz where the question is a component. Returns -1 if the questionId is not found.
         */
        public int getQuizIdByQuestionId(int questionId) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            int quizId = -1;

            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM questions WHERE id=?;");
                preparedStatement.setInt(1, questionId);
                result = preparedStatement.executeQuery();

                quizId = getQuizIdByResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return quizId;
        }

        /**
         * Saves a question to the database.
         * @param question The question to save to the database.
         * @param quizId The id of the quiz this question is part of.
         * @return The question as it is now saved in the database.
         */
        public Question updateQuestion(Question question, int quizId) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;

            try {
                connection = DatabaseConnection.getConnection();
                if (question.getId() == -1) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO questions (question, answer, quizId) VALUES (?, ?, ?);");
                    preparedStatement.setInt(3, quizId);
                } else {
                    preparedStatement = connection.prepareStatement(
                            "UPDATE questions SET question=?, answer=? WHERE id=?");
                    preparedStatement.setInt(3, question.getId());
                }
                preparedStatement.setString(1, question.getQuestion());
                preparedStatement.setString(2, question.getAnswer());

                int resultRows = preparedStatement.executeUpdate();
                if (question.getId() == -1 && resultRows == 1) {
                    result = preparedStatement.getGeneratedKeys();
                    question.setId(result.getInt(1));
                }
                else if (resultRows == 0) {
                    question = null;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return question;
        }

        /**
         * Updates the question is the database by finding its quiz id implicitly.
         * @param question The question to update in the database
         * @return The question as it is now saved in the database.
         */
        public Question updateQuestion(Question question) {
            int quizId = getQuizIdByQuestionId(question.getId());
            if (quizId == -1) return null;
            return updateQuestion(question, quizId);
        }


        /**
         * Removes a question with the given id from the database.
         * @param id The id of the question.
         * @return True if the question was removed successfully, false if not.
         */
        public boolean removeQuestionById(int id) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            boolean result = false;

            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement(
                        "DELETE FROM questions WHERE id=?");
                preparedStatement.setInt(1, id);
                result = preparedStatement.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
            }
            return result;
        }
    }

    /**
     * A class that deals with the "teams"-table in the database.
     * Instances of this class can be used to save and retrieve data about teams from the database.
     */
    protected static class TeamDAO {
        /**
         * Gets a team from a ResultSet of an SQL query.
         * @param result The ResultSet of an SQL query.
         * @return The team based on the ResultSet.
         */
        private Team getTeamFromResultSet(ResultSet result) {
            Team team = null;
            try {
                if (result.next()) {
                    team = new Team();
                    team.setId(result.getInt("id"));
                    team.setTeamName(result.getString("name"));
                    team.setScore(result.getInt("score"));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closeResultSet(result);
            }
            return team;
        }

        /**
         * Returns a map of all teams from the ResultSet of an SQL query.
         * @param result The ResultSet of an SQL query.
         * @return A Map of all teams from the ResultSet of an SQL query. The key is the id of the team,
         *         and the value is the team-object.
         */
        private HashMap<Integer, Team> getTeamsFromResultSet(ResultSet result) {
            HashMap<Integer, Team> teams = new HashMap<>();

            try {
                while (result.next()) {
                    Team team = new Team();
                    team.setId(result.getInt("id"));
                    team.setTeamName(result.getString("name"));
                    team.setScore(result.getInt("score"));

                    teams.put(team.getId(), team);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closeResultSet(result);
            }
            return teams;
        }

        /**
         * Gets the quiz id of the quiz that a team is the component of form the ResultSet of an SQL query.
         * @param result The ResultSet of an SQL query.
         * @return The id of the quiz that the team is a component of. Returns -1 if the ResultSet is empty.
         */
        private int getQuizIdFromResultSet(ResultSet result) {
            int quizId = -1;
            try {
                if (result.next()) {
                    quizId = result.getInt("quizId");
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closeResultSet(result);
            }
            return quizId;
        }

        /**
         * Gets a team from its ID
         * @param id The id of the team
         * @return The team that matches the ID. Returns null if no team was found.
         */
        public Team getTeamById(int id) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            Team team = null;

            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM teams WHERE id=?;");
                preparedStatement.setInt(1, id);
                result = preparedStatement.executeQuery();

                team = getTeamFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {

                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return team;
        }

        /**
         * Gets all teams that are components of a quiz.
         * @param quizId The id of the Quiz
         * @return A map of all the teams that are components of the quiz. The keys in the map are
         *         the ids of the teams, while the keys are the teams objects.
         */
        public HashMap<Integer, Team> getTeamsByQuizId(int quizId) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            HashMap<Integer, Team> teams = null;

            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM teams WHERE quizId=?;");
                preparedStatement.setInt(1, quizId);
                result = preparedStatement.executeQuery();

                teams = getTeamsFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return teams;
        }

        /**
         * Gets the id of a quiz that a team is the component of.
         * @param teamId The id of the team.
         * @return The id of the quiz. Returns -1 if the teamID is not found in the database.
         */
        public int getQuizIdByTeamId(int teamId) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            int quizId = -1;

            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM teams WHERE id=?;");
                preparedStatement.setInt(1, teamId);
                result = preparedStatement.executeQuery();

                quizId = getQuizIdFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return quizId;
        }

        /**
         * Updates the entry of a team in the database to reflect a team object
         * @param team The team object
         * @param quizId The id of the quiz that the team is a component of.
         * @return The team how it is saved in the database.
         */
        public Team updateTeam(Team team, int quizId) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;

            try {
                connection = DatabaseConnection.getConnection();
                if (team.getId() == -1) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO teams (name, score, quizId) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setInt(3, quizId);
                } else {
                    preparedStatement = connection.prepareStatement(
                            "UPDATE teams SET name=?, score=? WHERE id=?");
                    preparedStatement.setInt(3, team.getId());
                }
                preparedStatement.setString(1, team.getTeamName());
                preparedStatement.setInt(2, team.getScore());

                int resultRows = preparedStatement.executeUpdate();
                if (team.getId() == -1 && resultRows == 1) {
                    result = preparedStatement.getGeneratedKeys();
                    if (result.next()) team.setId(result.getInt(1));
                }
                else if (resultRows == 0) {
                    team = null;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
                DatabaseConnection.closeResultSet(result);
            }
            return team;
        }

        /**
         * Updates the database entry of a team by finding the quizId implicitly.
         * @param team The team to save in the database.
         * @return The team how it is saved in the database.
         */
        public Team updateTeam(Team team) {
            int quizId = getQuizIdByTeamId(team.getId());
            if (quizId == -1) return null;
            return updateTeam(team, quizId);
        }

        public boolean removeTeamById(int id) {
            Connection connection;
            PreparedStatement preparedStatement = null;
            boolean result = false;

            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement(
                        "DELETE FROM teams WHERE id=?");
                preparedStatement.setInt(1, id);
                result = preparedStatement.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DatabaseConnection.closePreparedStatement(preparedStatement);
            }
            return result;
        }
    }
}
