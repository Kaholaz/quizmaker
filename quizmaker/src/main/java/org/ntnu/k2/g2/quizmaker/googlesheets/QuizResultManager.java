package org.ntnu.k2.g2.quizmaker.googlesheets;
import com.google.api.services.drive.Drive;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.data.TeamModel;

import java.io.IOException;
import java.util.List;

public class QuizResultManager {
    /**
     * This method creates an associated result sheet for a quiz
     * @param quiz The quiz to create the result sheet for.
     * @throws IOException
     */
    public static String createResultSheet(QuizModel quiz) throws IOException {
        if (quiz.getSheetId() != null) {
            return quiz.getSheetId();
        }
        ResultSheet resultSheet = new ResultSheet();

        String sheetId = resultSheet.createSheet(quiz.getName());

        //Updates spreadsheet permission
        Drive driveService = resultSheet.createDriveService();
        resultSheet.makeSpreadsheetPublic(driveService, sheetId);

        resultSheet.addRowValues(sheetId, "Teams", "Scores", "1");
        quiz.setSheetId(sheetId);
        return sheetId;
    }

    /**
     * Changes a name of quiz's associated result sheet. This sets the name of the sheet according to value returned by
     * quiz.getName()
     * @param quiz The quiz whose result sheet to change.
     * @return True if the operation was successful, false if not.
     */
    public static boolean changeResultSheetName(QuizModel quiz) throws IOException {
        ResultSheet resultSheet = new ResultSheet();

        String newName = quiz.getName();
        String sheetId = quiz.getSheetId();

        return resultSheet.setSheetTitle(newName,sheetId);
    }

    /**
     * Imports scores and teams from the result sheet where the users have entered their score.
     * This method retrieves the scores with the Google API and
     * @param quiz The quiz to import the results for.
     */
    public static QuizModel importResults(QuizModel quiz) throws IOException {
        ResultSheet resultSheet = new ResultSheet();

        quiz.getTeams().keySet().stream().toList().forEach(id -> quiz.getTeams().remove(id));
        var quizResult = resultSheet.fetchResultSheetValues(quiz.getSheetId());

        for (List<Object> row : quizResult) {
            String teamName = row.get(0).toString();
            int score = Integer.parseInt((String) row.get(1));

            TeamModel team = QuizRegister.newTeam(quiz);
            team.setTeamName(teamName);
            team.setScore(score);
        }

        QuizRegister.saveQuiz(quiz);
        return quiz;
    }

    /**
     * Removes a sheet from a Google Drive.
     * @param quiz The quiz' result sheet to remove.
     * @return true if the operation was successful.
     * @throws IOException Throws an exception if the operation was unsuccessful.
     */
    public static boolean removeResultSheet(QuizModel quiz) throws IOException {
        ResultSheet resultSheet = new ResultSheet();
        Drive driveService = resultSheet.createDriveService();
        resultSheet.deleteSheet(driveService, quiz.getSheetId());
        quiz.setSheetId(null);
        return true;
    }
}
