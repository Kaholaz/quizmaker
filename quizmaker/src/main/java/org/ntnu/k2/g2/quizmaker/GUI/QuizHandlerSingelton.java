package org.ntnu.k2.g2.quizmaker.GUI;

import org.ntnu.k2.g2.quizmaker.Data.Quiz;

public class QuizHandlerSingelton {
    private static Quiz currentQuiz;
    private static Quiz previousQuiz;

    private QuizHandlerSingelton() {

    }

    public static void setQuiz(Quiz quiz) {
        previousQuiz = currentQuiz;
        currentQuiz = quiz;
    }

    public static Quiz getQuiz() {
        return currentQuiz;
    }

    public static Quiz getPreviousQuiz() {
        return previousQuiz;
    }
    public static void clear() {
        previousQuiz = null;
        currentQuiz = null;

    }




}
