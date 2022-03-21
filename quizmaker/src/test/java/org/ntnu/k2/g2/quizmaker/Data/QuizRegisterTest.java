package org.ntnu.k2.g2.quizmaker.Data;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class QuizRegisterTest extends TestCase {
    /**
     * Runs before each testcase
     */
    public void setUp() {
        deleteDatabase();
        populateDatabase();
    }

    public void tearDown() {
        deleteDatabase();
    }

    private static void deleteDatabase() {
        QuizRegister.DataBase.getDbPath().delete();
    }

    private static void populateDatabase() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz testQuiz = quizRegister.newQuiz();

        testQuiz.setName("Test Quiz");

        for (int i = 1; i <= 5; ++i) {
            Question question = quizRegister.newQuestion(testQuiz);
            question.setQuestion(String.format("Q%d", i));
            question.setAnswer(String.format("A%d", i));
        }

        for (int i = 1; i <= 3; ++i) {
            Team team = quizRegister.newTeam(testQuiz);
            team.setTeamName(String.format("T%d", i));
            team.setScore(i);
        }

        quizRegister.saveQuiz(testQuiz);
    }

    public void testGetQuizList() {
        QuizRegister quizRegister = new QuizRegister();
        ArrayList<Quiz> quizzes = quizRegister.getQuizList();
        assertEquals(1, quizzes.size());
    }

    public void testGetQuiz() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz testQuiz = quizRegister.getQuiz(1);

        assertEquals( "Test Quiz", testQuiz.getName());
        assertEquals(5, testQuiz.getQuestions().size());
        assertEquals(3, testQuiz.getTeams().size());
        assertEquals(1, testQuiz.getId());
        assertTrue(testQuiz.isActive());
    }

    public void testGetQuizReturnsNullOnInvalidId() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz testQuiz = quizRegister.getQuiz(100);

        assertNull(testQuiz);
    }

    public void testGetTeam() {
        QuizRegister quizRegister = new QuizRegister();
        Team team = quizRegister.getTeam(2);

        assertEquals("T2", team.getTeamName());
        assertEquals(2, team.getScore());
        assertEquals(2, team.getId());
    }

    public void testGetTeamReturnsNullOnInvalidId() {
        QuizRegister quizRegister = new QuizRegister();
        Team team = quizRegister.getTeam(100);

        assertNull(team);
    }

    public void testGetQuestion() {
        QuizRegister quizRegister = new QuizRegister();
        Question question = quizRegister.getQuestion(5);

        assertEquals("Q5", question.getQuestion());
        assertEquals("A5", question.getAnswer());
        assertEquals(5, question.getId());
    }

    public void testGetQuestionReturnsNullOnInvalidId() {
        QuizRegister quizRegister = new QuizRegister();
        Question question = quizRegister.getQuestion(100);

        assertNull(question);
    }

    public void testSaveQuiz() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz quiz = quizRegister.getQuiz(1);
        quiz.setUrl("Google.com");
        quiz.setActive(false);
        quiz.setName("Altered name");

        Quiz returnQuiz = quizRegister.saveQuiz(quiz);
        Quiz savedQuiz = quizRegister.getQuiz(1);

        assertEquals(quiz, returnQuiz);
        assertEquals(quiz, savedQuiz);
    }

    public void testSaveTeam() {
        QuizRegister quizRegister = new QuizRegister();
        Team team = quizRegister.getTeam(3);
        team.setScore(69);
        team.setTeamName("Altered team name");

        Team returnTeam = quizRegister.saveTeam(team);
        Team savedTeam = quizRegister.getTeam(3);

        assertEquals(team, returnTeam);
        assertEquals(team, savedTeam);
    }

    public void testSaveQuestion() {
        QuizRegister quizRegister = new QuizRegister();
        Question question = quizRegister.getQuestion(4);
        question.setQuestion("New Question");
        question.setAnswer("New Answer");

        Question returnQuestion = quizRegister.saveQuestion(question);
        Question savedQuestion = quizRegister.getQuestion(4);

        assertEquals(question, returnQuestion);
        assertEquals(question, savedQuestion);
    }

    public void testSaveQuizAltersTeams() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz quiz = quizRegister.getQuiz(1);

        quiz.getTeams().remove(2);
        quiz.getTeams().get(3).setTeamName("Test");

        quiz = quizRegister.saveQuiz(quiz);

        assertFalse(quiz.getTeams().containsKey(2));
        assertNull(quizRegister.getTeam(2));
        assertEquals(quizRegister.getTeam(3), quiz.getTeams().get(3));
    }

    public void testSaveQuizAltersQuestions() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz quiz = quizRegister.getQuiz(1);

        quiz.getQuestions().remove(3);
        quiz.getQuestions().get(4).setQuestion("Test");

        quiz = quizRegister.saveQuiz(quiz);

        assertFalse(quiz.getQuestions().containsKey(3));
        assertNull(quizRegister.getQuestion(3));
        assertEquals(quizRegister.getQuestion(4), quiz.getQuestions().get(4));
    }

    public void testRemoveQuiz() {
        QuizRegister quizRegister = new QuizRegister();

        Quiz quiz = quizRegister.getQuiz(1);
        quizRegister.removeQuiz(quiz);

        ArrayList<Quiz> quizzes = quizRegister.getQuizList();
        assertEquals(0, quizzes.size());
        assertNull(quizRegister.getQuestion(1));
        assertNull(quizRegister.getTeam(1));
    }

    public void testRemoveTeam() {
        QuizRegister quizRegister = new QuizRegister();

        Quiz quiz = quizRegister.getQuiz(1);
        quizRegister.removeTeam(quiz, 1);

        assertEquals(2, quiz.getTeams().size());
        assertNull(quizRegister.getTeam(1));
        assertEquals(quiz.getTeams().get(2), quizRegister.getTeam(2));
    }

    public void testRemoveQuestion() {
        QuizRegister quizRegister = new QuizRegister();

        Quiz quiz = quizRegister.getQuiz(1);
        quizRegister.removeQuestion(quiz, 2);

        assertEquals(4, quiz.getQuestions().size());
        assertNull(quizRegister.getQuestion(2));
        assertEquals(quiz.getQuestions().get(1), quizRegister.getQuestion(1));
    }

    public void testRemoveTeamNotInDatabaseReturnsFalse() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz quiz = quizRegister.newQuiz();

        Team team = quizRegister.newTeam(quiz);
        quizRegister.removeTeam(quiz, team.getId());

        assertFalse(quizRegister.removeTeam(quiz, team.getId()));
    }

    public void testRemoveQuestionNotInDatabaseReturnsFalse() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz quiz = quizRegister.newQuiz();

        Question question = quizRegister.newQuestion(quiz);
        quizRegister.removeTeam(quiz, question.getId());

        assertFalse(quizRegister.removeTeam(quiz, question.getId()));
    }

    public void testSaveTeamNotInDataBaseReturnsNull() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz quiz = quizRegister.newQuiz();

        Team team = quizRegister.newTeam(quiz);
        quizRegister.removeTeam(quiz, team.getId());

        assertNull(quizRegister.saveTeam(team));
    }

    public void testSaveQuestionNotInDataBaseReturnsNull() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz quiz = quizRegister.newQuiz();

        Question question = quizRegister.newQuestion(quiz);
        quizRegister.removeQuestion(quiz, question.getId());

        assertNull(quizRegister.saveQuestion(question));
    }

    public void testGetArchivedQuizzes() {
        QuizRegister quizRegister = new QuizRegister();
        quizRegister.populateDatabase(1,2,3);

        Quiz quiz = quizRegister.getQuiz(2);
        System.out.println(quiz);
        quiz.setActive(false);

        quizRegister.saveQuiz(quiz);

        assertEquals(List.of(quizRegister.getQuiz(1)),quizRegister.getActiveQuizzes());
        assertEquals(List.of(quizRegister.getQuiz(2)), quizRegister.getArchivedQuizzes());
    }

    public void testPopulateDatabase() {
        QuizRegister quizRegister= new QuizRegister();
        quizRegister.populateDatabase(1, 2, 3);

        ArrayList<Quiz> quizzes = quizRegister.getQuizList();

        assertEquals(2, quizzes.size());
        assertEquals(2, quizzes.get(1).getTeams().size());
        assertEquals(3, quizzes.get(1).getQuestions().size());

        assertEquals("Quiz 1", quizzes.get(1).getName());

        assertEquals("Question 2", quizzes.get(1).getQuestions().get(7).getQuestion());
        assertEquals("Answer 3", quizzes.get(1).getQuestions().get(8).getAnswer());

        assertEquals("Team 2", quizzes.get(1).getTeams().get(5).getTeamName());
        assertEquals(2, quizzes.get(1).getTeams().get(5).getScore());
    }

    public void testPdfExport() {
        QuizRegister quizRegister = new QuizRegister();
        Quiz testQuiz = quizRegister.getQuiz(1);

        testQuiz.exportAnswersheetWithQuestions();
        testQuiz.exportAnswersheetWithoutQuestions();
        testQuiz.exportAnswersWithQuestions();
        testQuiz.exportAnswersWithoutQuestions();
    }
}