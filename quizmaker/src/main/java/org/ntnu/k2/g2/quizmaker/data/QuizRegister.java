package org.ntnu.k2.g2.quizmaker.data;

import org.ntnu.k2.g2.quizmaker.googlesheets.QuizResultManager;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * A class used to interact with the storage system of quizzes
 */
public class QuizRegister {
    /**
     * @return A complete representation of the database represented by an arraylist of quizzes
     */
    public static ArrayList<QuizModel> getQuizList() {
        return QuizDAO.getAllQuizzes();
    }

    /**
     * Gets all quizzes where the isActive flag is set to true.
     *
     * @return An ArrayList of all active quizzes.
     */
    public static ArrayList<QuizModel> getActiveQuizzes() {
        return new ArrayList<>(getQuizList().stream().filter(QuizModel::isActive).toList());
    }

    /**
     * Gets all quizzes where the isActive flag is set to false.
     *
     * @return An ArrayList of al inactive/archived quizzes.
     */
    public static ArrayList<QuizModel> getArchivedQuizzes() {
        return new ArrayList<>(getQuizList().stream().filter(quiz -> !quiz.isActive()).toList());
    }

    /**
     * Gets a quiz with a specific id.
     *
     * @param id The id of the quiz.
     * @return A quiz object representation of the entry in the database.
     * {@code null} is returned if no quiz matching the query is found in the database.
     */
    public static QuizModel getQuiz(int id) {
        return QuizDAO.getQuizById(id);
    }

    /**
     * Gets a team with a specific id
     *
     * @param id The id of the team.
     * @return A team object representation of the entry in the database.
     * {@code null} is returned if no team matching the query is found in the database.
     */
    public static TeamModel getTeam(int id) {
        return TeamDAO.getTeamById(id);
    }

    /**
     * Gets a question with a specific id
     *
     * @param id The id of the question.
     * @return A question object representation of the entry in the database.
     * {@code null} is returned if no question matching the query is found in the database.
     */
    public static QuestionModel getQuestion(int id) {
        return QuestionDAO.getQuestionById(id);
    }

    /**
     * Saves a quiz and ALL its content to the database.
     *
     * @param quiz The quiz to save to the database.
     * @return A quiz object representation of the entry in the database AFTER it has been updated.
     * {@code null} is returned if something went wrong when the quiz was saved.
     * @throws IOException Throws an exception if something went wrong during the creation of the answer sheet.
     */
    public static QuizModel saveQuiz(QuizModel quiz) throws IOException {
        QuizModel oldQuiz = QuizDAO.getQuizById(quiz.getId());

        // Quiz is not in the database
        if (oldQuiz == null) {
            return null;
        }

        // Quiz name changed. Sheet is null will cause problems, so the sheet name will not be updated.
        if (quiz.getSheetId() != null && !quiz.getName().equals(oldQuiz.getName())) {
            QuizResultManager.changeResultSheetName(quiz);
        }

        return QuizDAO.updateQuiz(quiz);
    }

    /**
     * Saves a team to the database. To use this method, the team object should already be an entry in the database.
     * If it is not, one should instead use the method newTeam to create a new team with a relation to a quiz.
     *
     * @param team The team to save to the database.
     * @return A team object representation of the entry in the database AFTER it has been updated.
     * {@code null} is returned if something went wrong when the team was saved.
     */
    public static TeamModel saveTeam(TeamModel team) {
        return TeamDAO.updateTeam(team);
    }

    /**
     * Saves a question to the database. To use this method, the question object should already be an entry in the database.
     * If it is not, one should instead use the method newQuestion to create a new question with a relation to a quiz.
     *
     * @param question The question to save to the database.
     * @return A question object representation of the entry in the database AFTER it has been updated.
     * {@code null} is returned if something went wrong when the question was saved.
     */
    public static QuestionModel saveQuestion(QuestionModel question) {
        return QuestionDAO.updateQuestion(question);
    }

    /**
     * Create a new quiz.
     * @return The new quiz.
     * @throws IOException Throws an exception if something wrong happened when creating a result-sheet.
     */
    public static QuizModel newQuiz() throws IOException {
        QuizModel quiz = new QuizModel();
        quiz.setName("new quiz");

        // A result sheet is not generated within testing
        if (!Util.isTest()) {
            QuizResultManager.createResultSheet(quiz);
        }

        return QuizDAO.updateQuiz(quiz);
    }

    /**
     * Creates a new team entry in the database and ads an object representation to the supplied quiz.
     * This method must be called when creating a new teams.
     *
     * @param quiz The quiz the team should be part of.
     * @return A team object representation of the entry in the database AFTER it has been added to the database.
     * {@code null} is returned if something went wrong when the team was added to the database.
     */
    public static TeamModel newTeam(QuizModel quiz) {
        TeamModel team = TeamDAO.updateTeam(new TeamModel(), quiz.getId());
        quiz.getTeams().put(team.getId(), team);
        return team;
    }

    /**
     * Creates a new question entry in the database and ads an object representation to the supplied quiz.
     * This method must be called when creating a new questions.
     *
     * @param quiz The quiz the question should be part of.
     * @return A question object representation of the entry in the database AFTER it has been added to the database.
     * {@code null} is returned if something went wrong when the question was added to the database.
     */
    public static QuestionModel newQuestion(QuizModel quiz) {
        QuestionModel question = QuestionDAO.updateQuestion(new QuestionModel(), quiz.getId());
        quiz.getQuestions().put(question.getId(), question);
        return question;
    }

    /**
     * Removes a quiz and all its contents (questions and teams) from the database.
     *
     * @param quiz The quiz to remove from the database.
     * @return True if the operation was successful, false if not.
     */
    public static boolean removeQuiz(QuizModel quiz) {
        return QuizDAO.removeQuizById(quiz.getId());
    }

    /**
     * Removes a team from the database.
     *
     * @param quiz   The quiz the team is a component of.
     * @param teamId The id of the team to remove.
     * @return True if the operation was successful, false if not.
     */
    public static boolean removeTeam(QuizModel quiz, int teamId) {
        quiz.getTeams().remove(teamId);

        return TeamDAO.removeTeamById(teamId);
    }

    /**
     * Removes a team from the database.
     *
     * @param quiz       The quiz the team is a component of.
     * @param questionId The id of the team to remove.
     * @return True if the operation was successful, false if not.
     */
    public static boolean removeQuestion(QuizModel quiz, int questionId) {
        quiz.getQuestions().remove(questionId);

        return QuestionDAO.removeQuestionById(questionId);
    }

    /**
     * Populates the database with a set number of quizzes and a set number of teams and question per quiz.
     *
     * @param quizzes   The number of quizzes to fill that database with.
     * @param teams     The number of teams to fill each quiz with.
     * @param questions The number of questions to fill each quiz with.
     * @return An ArrayList of all the quizzes that were added to the database.
     * @throws IOException Throws an exception if something wrong happened when creating the result sheet.
     */
    public static ArrayList<QuizModel> populateDatabase(int quizzes, int teams, int questions) throws IOException {
        ArrayList<QuizModel> quizArrayList = new ArrayList<>();

        for (int i = 1; i <= quizzes; ++i) {
            QuizModel quiz = newQuiz();
            quiz.setName(String.format("Quiz %d", i));
            for (int j = 1; j <= teams; ++j) {
                TeamModel team = newTeam(quiz);
                team.setTeamName(String.format("Team %d", j));
                team.setScore(j);
            }
            for (int j = 1; j <= questions; ++j) {
                QuestionModel question = newQuestion(quiz);
                question.setQuestion(String.format("Question %d", j));
                question.setAnswer(String.format("Answer %d", j));
            }
            quizArrayList.add(saveQuiz(quiz));
        }

        return quizArrayList;
    }

}
