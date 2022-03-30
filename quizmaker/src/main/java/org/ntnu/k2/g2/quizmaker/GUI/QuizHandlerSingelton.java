package org.ntnu.k2.g2.quizmaker.GUI;

import org.ntnu.k2.g2.quizmaker.Data.Quiz;

public class QuizHandlerSingelton {
    private static Quiz currentQuiz;
    private static boolean active;

    private QuizHandlerSingelton() {
    }

    public static void setQuiz(Quiz quiz) {
        active = quiz.isActive();
        currentQuiz = quiz;
    }

    public static Quiz getQuiz() {
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
