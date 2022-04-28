package org.ntnu.k2.g2.quizmaker.googlesheets;

import com.google.api.services.drive.Drive;
import junit.framework.TestCase;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.data.TeamModel;

import java.io.IOException;
import java.util.Iterator;

public class QuizResultManagerTest extends TestCase {

    /**
     * Spreadsheet containing 4 Teams with points.
     * READ ONLY
     * https://docs.google.com/spreadsheets/d/121tMrONqwBucH8vJ2ERIeKly8TJyUjBaIqJB8q2FoH0
     */
    String publicSpreadsheet2 = "121tMrONqwBucH8vJ2ERIeKly8TJyUjBaIqJB8q2FoH0";

    public void testChangeResultSheetName() throws IOException {
        QuizModel quiz = QuizRegister.newQuiz();
        quiz.setName("TestQuiz");
        QuizResultManager.createResultSheet(quiz);

        quiz.setName("New Name");
        QuizResultManager.changeResultSheetName(quiz);

        ResultSheet resultSheet = new ResultSheet();
        assertEquals(quiz.getName(), resultSheet.getSheetTitle(quiz.getSheetId()));

        QuizRegister.removeQuiz(quiz);
    }

    public void testCreateSheetWithDatabase() throws IOException {
        QuizModel quiz = QuizRegister.newQuiz();
        quiz.setName("TestCreateSheetWithDatabase");
        QuizResultManager.createResultSheet(quiz); // No exceptions thrown

        ResultSheet resultSheet = new ResultSheet();
        Drive driveService = resultSheet.createDriveService();
        resultSheet.deleteSheet(driveService, quiz.getSheetId());
    }

    public void testImportResultSheet() throws IOException {
        QuizModel quiz = QuizRegister.newQuiz();

        try {
            // Result sheets are not created automatically in tests
            quiz.setSheetId(publicSpreadsheet2);
            QuizResultManager.importResults(quiz);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(4, quiz.getTeams().size());
        assertEquals(10d, quiz.getCombinedTeamScore());
    }

    public void testImportResultSheetParsesDecimals() throws IOException {
        QuizModel quiz = QuizRegister.newQuiz();
        quiz.setName("TestQuiz");

        QuizResultManager.createResultSheet(quiz);
        ResultSheet resultSheet = new ResultSheet();
        resultSheet.appendRowValues(quiz.getSheetId(), "Team 1", "1.2");
        resultSheet.appendRowValues(quiz.getSheetId(), "Team 2", "2,1");

        QuizResultManager.importResults(quiz);
        Iterator<TeamModel> teams = quiz.getTeamsSortedByScore();
        assertEquals(2.1, teams.next().getScore());
        assertEquals(1.2, teams.next().getScore());

        QuizRegister.removeQuiz(quiz);
    }

    public void testRemoveSheet() throws IOException {
        QuizModel quiz = QuizRegister.newQuiz();

        QuizResultManager.createResultSheet(quiz);
        String sheetId = quiz.getSheetId();

        QuizResultManager.removeResultSheet(quiz);

        try {
            ResultSheet resultSheet = new ResultSheet();
            resultSheet.getSheetTitle(sheetId);
            fail("Reading removed sheet should result in IException");
        } catch (IOException ignored) {
        } catch (Exception e) {
            fail("Reading removed sheet should result in IException");
        }
    }
}