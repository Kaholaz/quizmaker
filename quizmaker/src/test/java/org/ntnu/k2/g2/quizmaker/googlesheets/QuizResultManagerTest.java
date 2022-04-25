package org.ntnu.k2.g2.quizmaker.googlesheets;

import com.google.api.services.drive.Drive;
import junit.framework.TestCase;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class QuizResultManagerTest extends TestCase {

    /**
     * Spreadsheet containing 4 Teams with points.
     * READ ONLY
     * https://docs.google.com/spreadsheets/d/121tMrONqwBucH8vJ2ERIeKly8TJyUjBaIqJB8q2FoH0
     */
    String publicSpreadsheet2 = "121tMrONqwBucH8vJ2ERIeKly8TJyUjBaIqJB8q2FoH0";

    public void testChangeResultSheetName() {
    }

    public void testCreateSheetWithDatabase() throws IOException {
        QuizModel quiz = QuizRegister.newQuiz();
        quiz.setName("TestCreateSheetWithDatabase");
        QuizResultManager.createResultSheet(quiz);

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
        assertEquals(10, quiz.getCombinedTeamScore());
    }
}