package org.ntnu.k2.g2.quizmaker.gui;

import org.ntnu.k2.g2.quizmaker.data.QuizModel;

/**
 * Singleton for storing quizzes. This Singleton can be used for gui applications where moving quizzes between scenes is
 * necessary. It also stores an active status for whether the user has chosen an active or archived quiz. This is by
 * default true (active).
 */
public class QuizHandlerSingleton {
    private static QuizModel currentQuiz = null;
    private static boolean active = true;

    /**
     * A singleton is not supposed to be instantiated, and the constructor is therefore private.
     */
    private QuizHandlerSingleton() {
    }

    /**
     * Set the quiz in the Singleton. This changes the active status as well. If there is no specific current quiz,
     * current quiz can be set to null.
     *
     * @param quiz
     *            The new current Quiz.
     */
    public static void setQuiz(QuizModel quiz) {
        // Active is set to true if quiz is null.
        // Otherwise, it is set to the active status of the quiz.
        active = quiz == null || quiz.isActive();
        currentQuiz = quiz;
    }

    /**
     * Get the current Quiz in the Singleton.
     *
     * @return The current quiz.
     */
    public static QuizModel getQuiz() {
        return currentQuiz;
    }

    /**
     * Get the active status. By default, this is set true, but will be overridden if the currentQuiz is set or the
     * active status is specified by using the setActive method.
     *
     * @return active status
     */
    public static boolean isActive() {
        // returns the active attribute if current quiz is null,
        // else it returns the status of the current quiz.
        return currentQuiz == null ? active : currentQuiz.isActive();
    }

    /**
     * Set the active status. This is used when a quiz can not be added, but changing the status is necessary. This will
     * clear currentQuiz. If changing the current quiz is necessary, use setQuiz instead.
     *
     * @param active
     *            boolean for active status
     */
    public static void setActive(boolean active) {
        currentQuiz = null;
        QuizHandlerSingleton.active = active;
    }

    /**
     * @return A string that represents the difficulty of the quiz to be used in the GUI.
     */
    public static String getDifficulty() {
        double diff = currentQuiz.getDifficulty();
        if (diff > 0f && diff < 0.4) {
            return "Høy";
        } else if (diff >= 0.4 && diff < 0.7) {
            return "Middels";
        } else if (diff <= 1 && diff >= 0.7) {
            return "Lav";
        } else {
            return "---";
        }
    }
}
