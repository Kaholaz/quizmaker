package org.ntnu.k2.g2.quizmaker.data;

import junit.framework.TestCase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizRegisterTest extends TestCase {
    /**
     * Runs before each testcase
     */
    public void setUp() throws IOException {
        deleteDatabase();
        populateDatabase();
    }

    public void tearDown() {
        deleteDatabase();
    }

    private static void deleteDatabase() {
        ArrayList<QuizModel> quizzes = QuizRegister.getQuizList();
        for (QuizModel quiz : quizzes) {
            QuizRegister.removeQuiz(quiz);
        }
        try {
            DatabaseConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConnection.getDbPath().delete();
    }

    private static void populateDatabase() throws IOException {
        QuizModel testQuiz = QuizRegister.newQuiz();

        testQuiz.setName("Test Quiz");

        for (int i = 1; i <= 5; ++i) {
            QuestionModel question = QuizRegister.newQuestion(testQuiz);
            question.setScore(i);
            question.setQuestion(String.format("Q%d", i));
            question.setAnswer(String.format("A%d", i));
        }

        for (int i = 1; i <= 3; ++i) {
            TeamModel team = QuizRegister.newTeam(testQuiz);
            team.setTeamName(String.format("T%d", i));
            team.setScore(i);
        }

        QuizRegister.saveQuiz(testQuiz);
    }

    public void testGetQuizList() {
        ArrayList<QuizModel> quizzes = QuizRegister.getQuizList();
        assertEquals(1, quizzes.size());
    }

    public void testGetQuiz() {
        QuizModel testQuiz = QuizRegister.getQuiz(1);

        assertEquals( "Test Quiz", testQuiz.getName());
        assertEquals(5, testQuiz.getQuestions().size());
        assertEquals(3, testQuiz.getTeams().size());
        assertEquals(1, testQuiz.getId());
        assertTrue(testQuiz.isActive());
    }

    public void testGetQuizReturnsNullOnInvalidId() {
        QuizModel testQuiz = QuizRegister.getQuiz(100);

        assertNull(testQuiz);
    }

    public void testGetTeam() {
        TeamModel team = QuizRegister.getTeam(2);

        assertEquals("T2", team.getTeamName());
        assertEquals(2d, team.getScore());
        assertEquals(2, team.getId());
    }

    public void testGetTeamWithNonIntegerScore() {
        TeamModel team = QuizRegister.getTeam(2);
        team.setScore(2.3);
        QuizRegister.saveTeam(team);
        team = QuizRegister.getTeam(2);

        assertEquals(2.3, team.getScore());
    }

    public void testGetTeamReturnsNullOnInvalidId() {
        TeamModel team = QuizRegister.getTeam(100);

        assertNull(team);
    }

    public void testGetQuestion() {
        QuestionModel question = QuizRegister.getQuestion(5);

        assertEquals("Q5", question.getQuestion());
        assertEquals("A5", question.getAnswer());
        assertEquals(5, question.getScore());
        assertEquals(5, question.getId());
    }

    public void testGetQuestionReturnsNullOnInvalidId() {
        QuestionModel question = QuizRegister.getQuestion(100);

        assertNull(question);
    }

    public void testSaveQuiz() throws IOException {
        QuizModel quiz = QuizRegister.getQuiz(1);
        quiz.setActive(false);
        quiz.setName("Altered name");

        QuizModel returnQuiz = QuizRegister.saveQuiz(quiz);
        QuizModel savedQuiz = QuizRegister.getQuiz(1);

        assertEquals(quiz, returnQuiz);
        assertEquals(quiz, savedQuiz);
    }

    public void testSaveTeam() {
        TeamModel team = QuizRegister.getTeam(3);
        team.setScore(69);
        team.setTeamName("Altered team name");

        TeamModel returnTeam = QuizRegister.saveTeam(team);
        TeamModel savedTeam = QuizRegister.getTeam(3);

        assertEquals(team, returnTeam);
        assertEquals(team, savedTeam);
    }

    public void testSaveQuestion() {
        QuestionModel question = QuizRegister.getQuestion(4);
        question.setQuestion("New Question");
        question.setAnswer("New Answer");
        question.setScore(69);

        QuestionModel returnQuestion = QuizRegister.saveQuestion(question);
        QuestionModel savedQuestion = QuizRegister.getQuestion(4);

        assertEquals(question, returnQuestion);
        assertEquals(question, savedQuestion);
    }

    public void testSaveQuizAltersTeams() throws IOException {
        QuizModel quiz = QuizRegister.getQuiz(1);

        quiz.getTeams().remove(2);
        quiz.getTeams().get(3).setTeamName("Test");

        quiz = QuizRegister.saveQuiz(quiz);

        assertFalse(quiz.getTeams().containsKey(2));
        assertNull(QuizRegister.getTeam(2));
        assertEquals(QuizRegister.getTeam(3), quiz.getTeams().get(3));
    }

    public void testSaveQuizAltersQuestions() throws IOException {
        QuizModel quiz = QuizRegister.getQuiz(1);

        quiz.getQuestions().remove(3);
        quiz.getQuestions().get(4).setQuestion("Test");

        quiz = QuizRegister.saveQuiz(quiz);

        assertFalse(quiz.getQuestions().containsKey(3));
        assertNull(QuizRegister.getQuestion(3));
        assertEquals(QuizRegister.getQuestion(4), quiz.getQuestions().get(4));
    }

    public void testRemoveQuiz() {
        QuizModel quiz = QuizRegister.getQuiz(1);
        assertTrue(QuizRegister.removeQuiz(quiz));

        ArrayList<QuizModel> quizzes = QuizRegister.getQuizList();
        assertEquals(0, quizzes.size());
        assertNull(QuizRegister.getQuestion(1));
        assertNull(QuizRegister.getTeam(1));
    }

    public void testRemoveTeam() {
        QuizModel quiz = QuizRegister.getQuiz(1);
        QuizRegister.removeTeam(quiz, 1);

        assertEquals(2, quiz.getTeams().size());
        assertNull(QuizRegister.getTeam(1));
        assertEquals(quiz.getTeams().get(2), QuizRegister.getTeam(2));
    }

    public void testRemoveQuestion() {
        QuizModel quiz = QuizRegister.getQuiz(1);
        QuizRegister.removeQuestion(quiz, 2);

        assertEquals(4, quiz.getQuestions().size());
        assertNull(QuizRegister.getQuestion(2));
        assertEquals(quiz.getQuestions().get(1), QuizRegister.getQuestion(1));
    }

    public void testRemoveTeamNotInDatabaseReturnsFalse() throws IOException {
        QuizModel quiz = QuizRegister.newQuiz();

        TeamModel team = QuizRegister.newTeam(quiz);
        QuizRegister.removeTeam(quiz, team.getId());

        assertFalse(QuizRegister.removeTeam(quiz, team.getId()));
    }

    public void testRemoveQuestionNotInDatabaseReturnsFalse() throws IOException {
        QuizModel quiz = QuizRegister.newQuiz();

        QuestionModel question = QuizRegister.newQuestion(quiz);
        QuizRegister.removeTeam(quiz, question.getId());

        assertFalse(QuizRegister.removeTeam(quiz, question.getId()));
    }

    public void testSaveTeamNotInDataBaseReturnsNull() throws IOException {
        QuizModel quiz = QuizRegister.newQuiz();

        TeamModel team = QuizRegister.newTeam(quiz);
        QuizRegister.removeTeam(quiz, team.getId());

        assertNull(QuizRegister.saveTeam(team));
    }

    public void testSaveQuestionNotInDataBaseReturnsNull() throws IOException {
        QuizModel quiz = QuizRegister.newQuiz();

        QuestionModel question = QuizRegister.newQuestion(quiz);
        QuizRegister.removeQuestion(quiz, question.getId());

        assertNull(QuizRegister.saveQuestion(question));
    }

    public void testGetArchivedQuizzes() throws IOException {
        QuizRegister.populateDatabase(1,2,3);

        QuizModel quiz = QuizRegister.getQuiz(2);
        quiz.setActive(false);

        QuizRegister.saveQuiz(quiz);

        assertEquals(List.of(QuizRegister.getQuiz(1)),QuizRegister.getActiveQuizzes());
        assertEquals(List.of(QuizRegister.getQuiz(2)), QuizRegister.getArchivedQuizzes());
    }

    public void testPopulateDatabase() throws IOException {
        QuizRegister.populateDatabase(1, 2, 3);

        ArrayList<QuizModel> quizzes = QuizRegister.getQuizList();

        assertEquals(2, quizzes.size());
        assertEquals(2, quizzes.get(1).getTeams().size());
        assertEquals(3, quizzes.get(1).getQuestions().size());

        assertEquals("Quiz 1", quizzes.get(1).getName());

        assertEquals("Question 2", quizzes.get(1).getQuestions().get(7).getQuestion());
        assertEquals("Answer 3", quizzes.get(1).getQuestions().get(8).getAnswer());

        assertEquals("Team 2", quizzes.get(1).getTeams().get(5).getTeamName());
        assertEquals(2d, quizzes.get(1).getTeams().get(5).getScore());
    }
}