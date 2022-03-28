package org.ntnu.k2.g2.quizmaker.UserData;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.Data.Team;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class QuizResultManager {

    /**
     * @param quizId
     * @param quizName
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public String createResultSheet(int quizId, String quizName) throws GeneralSecurityException, IOException {
        QuizRegister quizRegister = new QuizRegister();
        Quiz quiz = quizRegister.getQuiz(quizId);

        ResultSheet resultSheet = new ResultSheet();

        String sheetId = resultSheet.createSheet(quizName);
        resultSheet.addRowValues(sheetId,"Team Name", "Points","1");

        quiz.setUrl("https://docs.google.com/spreadsheets/d/" + sheetId);

        quizRegister.saveQuiz(quiz);
        return sheetId;
    }

    /**
     * Adds the results from the quiz result-sheet to the database
     * @param quizResult List<List<Object>> list containing the results from the quiz fetched from result sheet
     * @param quizId quiz id in the database
     */
    public void registerResults(int quizId, List<List<Object>> quizResult){
        QuizRegister quizRegister = new QuizRegister();
        Quiz quiz = quizRegister.getQuiz(quizId);

        quiz.getTeams().keySet().stream().forEach(teamId-> quizRegister.removeTeam(quiz, teamId));

        for (List row : quizResult) {
            String teamName = row.get(0).toString();
            int score = Integer.parseInt((String) row.get(1));

            Team team = quizRegister.newTeam(quiz);
            team.setTeamName(teamName);
            team.setScore(score);
        }
        quizRegister.saveQuiz(quiz);
    }






}
