package org.ntnu.k2.g2.quizmaker.data;

import junit.framework.TestCase;

public class QuizModelTest extends TestCase {
    public void testGetMaxScore() {
        QuizModel quiz = new QuizModel();
        for (int i = 1; i <= 3; ++i) {
            QuestionModel question = new QuestionModel();
            question.setScore(i);
            quiz.getQuestions().put(i, question);
        }

        assertEquals(6, quiz.getMaxScore());
    }

    public void testGetCombinedTeamScore() {
        QuizModel quiz = new QuizModel();
        for (int i = 1; i <= 3; ++i) {
            TeamModel team = new TeamModel();
            team.setScore(i);
            quiz.getTeams().put(i, team);
        }

        assertEquals(6d, quiz.getCombinedTeamScore());
    }

    public void testGetDifficulty() {
        QuizModel quiz = new QuizModel();
        for (int i = 1; i <= 3; ++i) {
            QuestionModel question = new QuestionModel();
            question.setScore(i);
            quiz.getQuestions().put(i, question);

            TeamModel team = new TeamModel();
            team.setScore(i);
            quiz.getTeams().put(i, team);
        }

        assertEquals(1d/3d, quiz.getDifficulty());
    }

    public void testGetDifficultyWhenThereAreZeroTeams() {
        QuizModel quiz = new QuizModel();

        assertEquals(-1d, quiz.getDifficulty());
    }
}
