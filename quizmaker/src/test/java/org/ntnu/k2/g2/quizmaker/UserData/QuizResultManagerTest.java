package org.ntnu.k2.g2.quizmaker.UserData;

import junit.framework.TestCase;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class QuizResultManagerTest extends TestCase {

    public void testChangeResultSheetName() {
    }

    public void testCreateSheetWithDatabase() throws IOException, GeneralSecurityException {
        QuizRegister quizRegister = new QuizRegister();
        QuizModel quiz = quizRegister.newQuiz();
        quiz.setName("TestCreateSheetWithDatabase");
        QuizResultManager.createResultSheet(quiz);
    }

    public void testImportResultSheet() {
        QuizRegister quizRegister = new QuizRegister();
        QuizModel quiz = quizRegister.newQuiz();
        quiz.setName("Test quiz");

        ResultSheet resultSheet = new ResultSheet();
        try {
            // Result sheets are not created automatically in tests
            QuizResultManager.createResultSheet(quiz);
            resultSheet.appendRowValues(quiz.getSheetId(), "Team1", "1");
            resultSheet.appendRowValues(quiz.getSheetId(), "Team2", "2");
            QuizResultManager.importResults(quiz);
        }
        catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }

        assertEquals(2, quiz.getTeams().size());
        assertEquals(3, quiz.getCombinedTeamScore());
    }
}