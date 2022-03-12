package org.ntnu.k2.g2.quizmaker.Data;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class QuizRegister {
    public List<Quiz> getQuizList() {
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.getAllQuizzes();
    }

    public Quiz getQuiz(int id) {
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.getQuizById(id);
    }

    public Team getTeam(int id) {
        TeamDAO teamDAO = new TeamDAO();
        return teamDAO.getTeamById(id);
    }

    public Question getQuestions(int id) {
        QuestionDAO questionDAO = new QuestionDAO();
        return questionDAO.getQuestionById(id);
    }

    public Quiz saveQuiz(Quiz quiz) {
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.updateQuiz(quiz);
    }

    public Team saveTeam(Team team) {
        TeamDAO teamDAO = new TeamDAO();
        return teamDAO.updateTeam(team);
    }

    public Question saveQuestion(Question question) {
        QuestionDAO questionDAO = new QuestionDAO();
        return questionDAO.updateQuestion(question);
    }

    public Team newTeam(Quiz quiz) {
        TeamDAO teamDAO = new TeamDAO();
        Team team = teamDAO.updateTeam(new Team(), quiz.getId());
        quiz.getTeams().put(team.getId(), team);
        return team;
    }

    public Question newQuestion(Quiz quiz) {
        QuestionDAO questionDAO = new QuestionDAO();
        Question question = questionDAO.updateQuestion(new Question(), quiz.getId());
        quiz.getQuestions().put(question.getId(), question);
        return question;
    }

    public boolean removeTeam(Quiz quiz, int teamId) {
        quiz.getTeams().remove(teamId);

        TeamDAO teamDAO = new TeamDAO();
        return teamDAO.removeTeamById(teamId);
    }

    public boolean removeQuestion(Quiz quiz, int questionId) {
        quiz.getQuestions().remove(questionId);

        QuestionDAO questionDAO = new QuestionDAO();
        return questionDAO.removeQuestionById(questionId);
    }

    private static class DataBase {
        private static Connection connection;

        private static final String DB_URL = "jdbc:sqlite:";

        private static final String DB_PATH = "src/main/resources/";
        private static final String DB_NAME = "quizMaker.data";
        private static final String DB_NAME_TEST_PREFIX = "test_";

        private static boolean test = true;

        private DataBase () {
            File root = new File(DB_PATH);
            File db_path = new File(root, String.format("%s%s", test ? DB_NAME_TEST_PREFIX : "", DB_NAME));

            root.mkdirs();
            String db_url = DB_URL.concat(db_path.getAbsolutePath());
            try {
                connection = DriverManager.getConnection(db_url);
                initTables();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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
         * There is only ever one connection to the DB. It is retrieved by using this method.
         * This method initializes a new connection instance if one does not already exist.
         * @return The connection to the database
         */
        public static Connection getConnection() {
            if (connection == null) {
                new DataBase();
            }
            return connection;
        }

        /**
         * Closes a connection to a database, and handles any thrown exceptions
         * @param connection The connection to the database
         */
        public static void closeConnection(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
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

    private static class QuizDAO {
        private Quiz getQuizFromResultSet(ResultSet result) {
            Quiz quiz = null;
            try {
                if (result.next()) {
                    quiz = new Quiz();
                    quiz.setId(result.getInt("id"));
                    quiz.setName(result.getString("quiz"));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeResultSet(result);
            }
            return quiz;
        }

        private ArrayList<Quiz> getQuizzesFromResultSet(ResultSet result) {
            ArrayList<Quiz> quizzes = new ArrayList<>();
            try {
                while (result.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setId(result.getInt("id"));
                    quiz.setName(result.getString("quiz"));

                    quizzes.add(quiz);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeResultSet(result);
            }
            return quizzes;
        }

        public Quiz getQuizById(int id) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            Quiz quiz = null;

            try {
                connection = DataBase.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM quizzes WHERE id=?;");
                preparedStatement.setInt(1, id);
                result = preparedStatement.executeQuery();

                quiz = getQuizFromResultSet(result);

                TeamDAO teamDAO = new TeamDAO();
                QuestionDAO questionDAO = new QuestionDAO();
                quiz.setQuestions(questionDAO.getQuestionsByQuizId(id));
                quiz.setTeams(teamDAO.getTeamsByQuizId(id));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return quiz;
        }

        private ArrayList<Quiz> getAllQuizzes() {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            ArrayList<Quiz> quizzes = new ArrayList<>();

            try {
                connection = DataBase.getConnection();
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
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return quizzes;
        }

        public Quiz updateQuiz(Quiz quiz) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;

            try {
                connection = DataBase.getConnection();

                // If quiz is not in database
                if (quiz.getId() == -1) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO quizzes (name, lastChanged) VALUES (?, datetime('now'));");
                    preparedStatement.setString(1, quiz.getName());
                } else {
                    preparedStatement = connection.prepareStatement(
                            "UPDATE quizzes SET name=?, lastChanged=datetime('now') WHERE id=?;");
                    preparedStatement.setString(1, quiz.getName());
                    preparedStatement.setInt(2, quiz.getId());
                }

                // New teams and questions
                HashMap<Integer, Team> newTeams = quiz.getTeams();
                HashMap<Integer, Question> newQuestions = quiz.getQuestions();

                // Executes query and gets the new quiz
                result = preparedStatement.executeQuery();
                quiz = getQuizFromResultSet(result);
                final int quizId = quiz.getId();

                // Make updates to the team and question tables to reflect the quiz object
                Quiz oldQuizData = getQuizById(quizId);
                QuestionDAO questionDAO = new QuestionDAO();
                TeamDAO teamDAO = new TeamDAO();

                // Remove removed questions
                Stream<Integer> UniqueOldQuestions = oldQuizData.getQuestions().keySet().stream()
                        .filter(oldId -> !newQuestions.keySet().contains(oldId));
                UniqueOldQuestions.forEach(questionDAO::removeQuestionById);

                // Remove removed teams
                Stream<Integer> UniqueOldTeams = oldQuizData.getTeams().keySet().stream()
                        .filter(oldId -> !newTeams.keySet().contains(oldId));
                UniqueOldTeams.forEach(questionDAO::removeQuestionById);

                // Update teams
                newTeams.values().forEach(team -> teamDAO.updateTeam(team, quizId));

                // Update questions
                newQuestions.values().forEach(question -> questionDAO.updateQuestion(question, quizId));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return quiz;
        }

        public boolean removeQuizById(int id) {
            Connection connection = null;
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

                connection = DataBase.getConnection();
                preparedStatement = connection.prepareStatement(
                        "DELETE FROM quizzes WHERE id=?");
                preparedStatement.setInt(1, id);

                result = preparedStatement.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
            }
            return result;
        }
    }

    private static class QuestionDAO {
        public QuestionDAO(){}

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
                DataBase.closeResultSet(result);
            }
            return question;
        }

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
                DataBase.closeResultSet(result);
            }
            return quizId;
        }

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
                DataBase.closeResultSet(result);
            }
            return questions;
        }

        public Question getQuestionById(int id) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            Question question = null;

            try {
                connection = DataBase.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM questions WHERE id=?;");
                preparedStatement.setInt(1, id);
                result = preparedStatement.executeQuery();

                question = getQuestionFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return question;
        }

        public HashMap<Integer, Question> getQuestionsByQuizId(int quizId) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            HashMap<Integer, Question> questions = null;

            try {
                connection = DataBase.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM questions WHERE quizId=?;");
                preparedStatement.setInt(1, quizId);
                result = preparedStatement.executeQuery();

                questions = getQuestionsFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return questions;
        }

        public int getQuizIdByQuestionId(int questionId) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            int quizId = -1;

            try {
                connection = DataBase.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM questions WHERE id=?;");
                preparedStatement.setInt(1, questionId);
                result = preparedStatement.executeQuery();

                quizId = getQuizIdByResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return quizId;
        }

        public Question updateQuestion(Question question, int quizId) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;

            try {
                connection = DataBase.getConnection();
                if (question.getId() != -1) {
                    preparedStatement = connection.prepareStatement(
                            "UPDATE questions SET question=?, answer=? WHERE id=?");
                    preparedStatement.setInt(3, question.getId());
                }
                else {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO questions (question, answer, quizId) VALUES (?, ?, ?);");
                    preparedStatement.setInt(3, quizId);
                }
                preparedStatement.setString(1, question.getQuestion());
                preparedStatement.setString(2, question.getAnswer());

                result = preparedStatement.executeQuery();

                question = getQuestionFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return question;
        }

        public Question updateQuestion(Question question) {
            if (question.getId() == -1) {
                throw new IllegalArgumentException("Question id must be set to update question implicitly");
            }

            int quizId = getQuizIdByQuestionId(question.getId());
            return updateQuestion(question, quizId);
        }


        public boolean removeQuestionById(int id) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            boolean result = false;

            try {
                connection = DataBase.getConnection();
                preparedStatement = connection.prepareStatement(
                        "DELETE FROM questions WHERE id=?");
                preparedStatement.setInt(1, id);
                result = preparedStatement.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
            }
            return result;
        }
    }

    private static class TeamDAO {
        public TeamDAO(){}

        private Team getTeamFromResultSet(ResultSet result) {
            Team team = null;
            try {
                if (result.next()) {
                    team = new Team();
                    team.setId(result.getInt("id"));
                    team.setTeamName(result.getString("name"));
                    team.setScore(result.getInt("answer"));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeResultSet(result);
            }
            return team;
        }

        private HashMap<Integer, Team> getTeamsFromResultSet(ResultSet result) {
            HashMap<Integer, Team> teams = new HashMap<>();

            try {
                while (result.next()) {
                    Team team = new Team();
                    team.setId(result.getInt("id"));
                    team.setTeamName(result.getString("name"));
                    team.setScore(result.getInt("answer"));

                    teams.put(team.getId(), team);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeResultSet(result);
            }
            return teams;
        }

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
                DataBase.closeResultSet(result);
            }
            return quizId;
        }

        public Team getTeamById(int id) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            Team team = null;

            try {
                connection = DataBase.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM teams WHERE id=?;");
                preparedStatement.setInt(1, id);
                result = preparedStatement.executeQuery();

                team = getTeamFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return team;
        }

        public HashMap<Integer, Team> getTeamsByQuizId(int quizId) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            HashMap<Integer, Team> teams = null;

            try {
                connection = DataBase.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM teams WHERE quizId=?;");
                preparedStatement.setInt(1, quizId);
                result = preparedStatement.executeQuery();

                teams = getTeamsFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return teams;
        }

        public int getQuizIdByTeamId(int teamId) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            int quizId = -1;

            try {
                connection = DataBase.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM teams WHERE id=?;");
                preparedStatement.setInt(1, quizId);
                result = preparedStatement.executeQuery();

                quizId = getQuizIdFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return quizId;
        }

        public Team updateTeam(Team team, int quizId) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;

            try {
                connection = DataBase.getConnection();
                if (team.getId() != -1) {
                    preparedStatement = connection.prepareStatement(
                            "UPDATE teams SET team=?, answer=? WHERE id=?");
                    preparedStatement.setInt(3, team.getId());
                }
                else {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO teams (team, answer, quizId) VALUES (?, ?, ?);");
                    preparedStatement.setInt(3, quizId);
                }
                preparedStatement.setString(1, team.getTeamName());
                preparedStatement.setInt(2, team.getScore());

                result = preparedStatement.executeQuery();

                team = getTeamFromResultSet(result);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
                DataBase.closeResultSet(result);
            }
            return team;
        }

        public Team updateTeam(Team team) {
            if (team.getId() == -1) {
                throw new IllegalArgumentException("Team id must be set to update team implicitly");
            }

            int quizId = getQuizIdByTeamId(team.getId());
            return updateTeam(team, quizId);
        }

        public boolean removeTeamById(int id) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            boolean result = false;

            try {
                connection = DataBase.getConnection();
                preparedStatement = connection.prepareStatement(
                        "DELETE FROM teams WHERE id=?");
                preparedStatement.setInt(1, id);
                result = preparedStatement.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DataBase.closeConnection(connection);
                DataBase.closePreparedStatement(preparedStatement);
            }
            return result;
        }
    }
}
