package org.ntnu.k2.g2.quizmaker.UserData;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.data.TeamModel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class QuizResultManager {
    /**
     * This method creates an associated result sheet for a quiz
     * @param quiz The quiz to create the result sheet for.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String createResultSheet(QuizModel quiz) throws GeneralSecurityException, IOException {
        if (quiz.getSheetId() != null) {
            return quiz.getSheetId();
        }
        ResultSheet resultSheet = new ResultSheet();

        String sheetId = resultSheet.createSheet(quiz.getName());
        quiz.setSheetId(sheetId);
        resultSheet.addRowValues(sheetId, "Teams", "Scores", "1");
        return sheetId;
    }

    /**
     * Changes a name of quiz's associated result sheet. This sets the name of the sheet according to value returned by
     * quiz.getName()
     * @param quiz The quiz whose result sheet to change.
     * @return True if the operation was successful, false if not.
     */
    public static boolean changeResultSheetName(QuizModel quiz) {
        ResultSheet resultSheet = new ResultSheet();

        String newName = quiz.getName();
        String sheetId = quiz.getSheetId();

        try {
            return resultSheet.setSheetTitle(sheetId,newName);
        }catch (GeneralSecurityException | IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Imports scores and teams from the result sheet where the users have entered their score.
     * This method retrieves the scores with the Google API and
     * @param quiz The quiz to import the results for.
     */
    public static QuizModel importResults(QuizModel quiz) throws GeneralSecurityException, IOException {
        ResultSheet resultSheet = new ResultSheet();

        quiz.getTeams().keySet().stream().toList().forEach(id -> quiz.getTeams().remove(id));
        var quizResult = resultSheet.fetchResultSheetValues(quiz.getSheetId());

        for (List row : quizResult) {
            String teamName = row.get(0).toString();
            int score = Integer.parseInt((String) row.get(1));

            TeamModel team = QuizRegister.newTeam(quiz);
            team.setTeamName(teamName);
            team.setScore(score);
        }

        QuizRegister.saveQuiz(quiz);
        return quiz;
    }
}
