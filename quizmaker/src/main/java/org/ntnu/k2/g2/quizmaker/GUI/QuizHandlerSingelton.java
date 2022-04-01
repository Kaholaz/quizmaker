package org.ntnu.k2.g2.quizmaker.GUI;

import org.ntnu.k2.g2.quizmaker.data.QuizModel;

public class QuizHandlerSingelton {
    private static QuizModel currentQuiz;
    private static boolean active;

    private QuizHandlerSingelton() {
    }

    public static void setQuiz(QuizModel quiz) {
        active = quiz.isActive();
        currentQuiz = quiz;
    }

    public static QuizModel getQuiz() {
        return currentQuiz;
    }

    public static boolean isActive() {
        return active;
    }

    public static void setActive(boolean active) {
        QuizHandlerSingelton.active = active;
    }

    public static void clear() {
        currentQuiz = null;
        active = true;
    }
}
