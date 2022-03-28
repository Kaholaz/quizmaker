package org.ntnu.k2.g2.quizmaker.UserData;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.Data.Team;

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
    public static String createResultSheet(Quiz quiz) throws GeneralSecurityException, IOException {
        if (quiz.getSheetId() != null) {
            return quiz.getSheetId();
        }
        ResultSheet resultSheet = new ResultSheet();

        String sheetId = resultSheet.createSheet(quiz.getName());
        quiz.setSheetId(sheetId);

        return sheetId;
    }

    /**
     * Changes a name of quiz's associated result sheet. This sets the name of the sheet according to value returned by
     * quiz.getName()
     * @param quiz The quiz whose result sheet to change.
     * @return True if the operation was successful, false if not.
     */
    public static boolean changeResultSheetName(Quiz quiz) {
        /*
        TODO: Make it possible to change the name of a result sheet
         */
        return false;
    }

    /**
     * Removes a quiz's associated result sheet. This removes the result sheet of the quiz based on what
     * quiz.getSheetId() returns.
     * @param quiz The quiz whose result sheet to delete.
     * @return True if the operation was successful, false if not.
     */
    public static boolean deleteResultSheet(Quiz quiz) {
        /**
         * TODO: make it possible to remove a result sheet
         */
        return false;
    }

    /**
     * Imports scores and teams from the result sheet where the users have entered their score.
     * This method retrieves the scores with the Google API and
     * @param quiz The quiz to import the results for.
     */
    public static Quiz importResults(Quiz quiz) throws GeneralSecurityException, IOException {
        QuizRegister quizRegister = new QuizRegister();
        ResultSheet resultSheet = new ResultSheet();

        quiz.getTeams().keySet().stream().forEach(id -> quiz.getTeams().remove(id));
        var quizResult = resultSheet.fetchResultSheetValues(quiz.getSheetId());

        for (List row : quizResult) {
            String teamName = row.get(0).toString();
            int score = (Integer)row.get(1);

            Team team = quizRegister.newTeam(quiz);
            team.setTeamName(teamName);
            team.setScore(score);
        }

        quizRegister.saveQuiz(quiz);
        return quiz;
    }






}
