package org.ntnu.k2.g2.quizmaker.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * A class used to interact with the "quizzes"-table in the database.
 * Instances of this class can be used to save and retrieve quiz-data form the database.
 * This method is package-protected. Database interactions should be done using the QuizRegister class.
 */
class QuizDAO {
    /**
     * Constructs a quiz based on the ResultSet of an SQL query. Please note:
     * This method does not fill the teams or questions properties. These are filled in getQuizFromId()
     *
     * @param result The ResultSet of an SQL query.
     * @return A quiz based on the result of the SQL query.
     */
    private QuizModel getQuizFromResultSet(ResultSet result) {
        QuizModel quiz = null;
        try {
            if (result.next()) {
                quiz = new QuizModel();
                quiz.setId(result.getInt("id"));
                quiz.setName(result.getString("name"));
                quiz.setActive(result.getBoolean("active"));
                quiz.setSheetId(result.getString("sheetId"));
                quiz.setLastChanged(LocalDateTime.parse(result.getString("lastChanged")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(result);
        }
        return quiz;
    }

    /**
     * Constructs an ArrayList of quizzes based on the ResultSet of an SQL query. Please note:
     * This method does not fill the teams or questions properties.
     * These properties are filled in the methods that call this method.
     *
     * @param result The ResultSet of an SQL property
     * @return An ArrayList of all extracted quizzes form the ResultSet.
     */
    private ArrayList<QuizModel> getQuizzesFromResultSet(ResultSet result) {
        ArrayList<QuizModel> quizzes = new ArrayList<>();
        try {
            while (result.next()) {
                QuizModel quiz = new QuizModel();
                quiz.setId(result.getInt("id"));
                quiz.setName(result.getString("name"));
                quiz.setActive(result.getBoolean("active"));
                quiz.setSheetId(result.getString("sheetId"));
                quiz.setLastChanged(LocalDateTime.parse(result.getString("lastChanged")));

                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(result);
        }
        return quizzes;
    }

    /**
     * Gets a quiz by an id. This quiz has its teams and questions properties filled.
     * This method returns null if no quiz is found.
     *
     * @param id The id of the quiz.
     * @return The quiz with the id in the database. If no quiz is found, null is returned.
     */
    public QuizModel getQuizById(int id) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        QuizModel quiz = null;

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
        } catch (NullPointerException e) {
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return quiz;
    }

    /**
     * Gets an ArrayList of all quizzes in the database.
     *
     * @return An ArrayList containing all quizzes in the database. Returns an empty list if there are no entries.
     */
    public ArrayList<QuizModel> getAllQuizzes() {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        ArrayList<QuizModel> quizzes = new ArrayList<>();

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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return quizzes;
    }

    /**
     * Saves a quiz to the database.
     *
     * @param quiz The quiz to save to the database.
     * @return The quiz as it is saved in the database after the update is done.
     */
    public QuizModel updateQuiz(QuizModel quiz) {
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
            HashMap<Integer, TeamModel> newTeams = quiz.getTeams();
            HashMap<Integer, QuestionModel> newQuestions = quiz.getQuestions();

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
            QuizModel oldQuizData = getQuizById(quizId);
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return null;
        } finally {

            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return quiz;
    }

    /**
     * Removes a quiz and ALL its components (questions and teams) from the database.
     *
     * @param id The ID of the quiz to remove.
     * @return True if the operation was successful, false if not.
     */
    public boolean removeQuizById(int id) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        boolean result = false;

        try {
            QuizModel quiz = getQuizById(id);

            // remove all questions
            QuestionDAO questionDAO = new QuestionDAO();
            quiz.getQuestions().keySet().forEach(questionDAO::removeQuestionById);


            // remove all teams
            TeamDAO teamDAO = new TeamDAO();
            quiz.getQuestions().keySet().forEach(teamDAO::removeTeamById);


            // removes the quiz itself
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM quizzes WHERE id=?");
            preparedStatement.setInt(1, id);

            result = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            DatabaseConnection.closePreparedStatement(preparedStatement);
        }
        return result;
    }
}
