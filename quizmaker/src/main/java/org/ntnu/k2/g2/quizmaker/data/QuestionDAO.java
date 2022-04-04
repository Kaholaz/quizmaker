package org.ntnu.k2.g2.quizmaker.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * A class that deals with the "questions"-table in the database.
 * Instances of this class can be used to save and retrieve data from the database.
 * This method is package-protected. Database interactions should be done using the QuizRegister class.
 */
class QuestionDAO {
    /**
     * Returns a question from the ResultSet returned by a SQL query.
     *
     * @param result The ResultSet of an SQL query.
     * @return The question
     */
    private QuestionModel getQuestionFromResultSet(ResultSet result) {
        QuestionModel question = null;
        try {
            if (result.next()) {
                question = new QuestionModel();
                question.setScore(result.getInt("score"));
                question.setId(result.getInt("id"));
                question.setQuestion(result.getString("question"));
                question.setAnswer(result.getString("answer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(result);
        }
        return question;
    }

    /**
     * Gets the quiz id of the ResultSet of a SQL query in the teams table.
     *
     * @param result The ResultSet from an SQL query.
     * @return The quiz id of the team returned by the SQL query.
     */
    private int getQuizIdByResultSet(ResultSet result) {
        int quizId = -1;
        try {
            if (result.next()) {
                quizId = result.getInt("quizId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(result);
        }
        return quizId;
    }

    /**
     * Returns an ArrayList of all questions extracted from a ResultSet of a SQL query.
     *
     * @param result The ResultSet of and SQL query
     * @return An ArrayList of all questions extracted from the ResultSet.
     */
    private HashMap<Integer, QuestionModel> getQuestionsFromResultSet(ResultSet result) {
        HashMap<Integer, QuestionModel> questions = new HashMap<>();

        try {
            while (result.next()) {
                QuestionModel question = new QuestionModel();
                question.setScore(result.getInt("score"));
                question.setId(result.getInt("id"));
                question.setQuestion(result.getString("question"));
                question.setAnswer(result.getString("answer"));

                questions.put(question.getId(), question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(result);
        }
        return questions;
    }

    /**
     * Gets a question form the database based on its ID.
     *
     * @param id The id of the question.
     * @return The question with this ID. If there is no question that matches the ID, a null pointer is returned.
     */
    public QuestionModel getQuestionById(int id) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        QuestionModel question = null;

        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM questions WHERE id=?;");
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();

            question = getQuestionFromResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return question;
    }

    public HashMap<Integer, QuestionModel> getQuestionsByQuizId(int quizId) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        HashMap<Integer, QuestionModel> questions = null;

        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM questions WHERE quizId=?;");
            preparedStatement.setInt(1, quizId);
            result = preparedStatement.executeQuery();

            questions = getQuestionsFromResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return questions;
    }

    /**
     * Gets the id of a quiz that contains a question with the supplied id.
     *
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return quizId;
    }

    /**
     * Saves a question to the database.
     *
     * @param question The question to save to the database.
     * @param quizId   The id of the quiz this question is part of.
     * @return The question as it is now saved in the database.
     */
    public QuestionModel updateQuestion(QuestionModel question, int quizId) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            connection = DatabaseConnection.getConnection();
            if (question.getId() == -1) {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO questions (question, answer, score, quizId) VALUES (?, ?, ?, ?);");
                preparedStatement.setInt(4, quizId);
            } else {
                preparedStatement = connection.prepareStatement(
                        "UPDATE questions SET question=?, answer=?, score=? WHERE id=?");
                preparedStatement.setInt(4, question.getId());
            }
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setString(2, question.getAnswer());
            preparedStatement.setInt(3, question.getScore());

            int resultRows = preparedStatement.executeUpdate();
            if (question.getId() == -1 && resultRows == 1) {
                result = preparedStatement.getGeneratedKeys();
                question.setId(result.getInt(1));
            } else if (resultRows == 0) {
                question = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return question;
    }

    /**
     * Updates the question is the database by finding its quiz id implicitly.
     *
     * @param question The question to update in the database
     * @return The question as it is now saved in the database.
     */
    public QuestionModel updateQuestion(QuestionModel question) {
        int quizId = getQuizIdByQuestionId(question.getId());
        if (quizId == -1) return null;
        return updateQuestion(question, quizId);
    }


    /**
     * Removes a question with the given id from the database.
     *
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
        }
        return result;
    }
}
