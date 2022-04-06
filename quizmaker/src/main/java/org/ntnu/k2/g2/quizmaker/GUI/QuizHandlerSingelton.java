package org.ntnu.k2.g2.quizmaker.GUI;

import org.ntnu.k2.g2.quizmaker.data.QuizModel;

/**
 * Singleton for storing quizzes. This Singleton can be used for GUI applications
 * where moving quizzes between scenes is necessary. It also stores an active status
 * for whether the user has chosen an active or archived quiz. This is by default true.
 */

public class QuizHandlerSingelton {
    private static QuizModel currentQuiz = null;
    private static boolean active = true;

    /**
     * A singleton is not supposed to be instantiated, and the
     * constructor is therefore private.
     */

    private QuizHandlerSingelton() {
    }

    /**
     * Set the quiz in the Singleton. This changes the active status aswell.
     *
     * @param quiz
     */

    public static void setQuiz(QuizModel quiz) {
        active = quiz.isActive();
        currentQuiz = quiz;
    }

    /**
     * Get the current Quiz in the Singleton.
     *
     * @return The last saved quiz
     */

    public static QuizModel getQuiz() {
        return currentQuiz;
    }

    /**
     * Get the active status. By default, this is set true, but will be overridden
     * if the currentQuiz is not null.
     *
     * @return active status
     */

    public static boolean isActive() {
        if (currentQuiz != null) {
            if (currentQuiz.isActive() != active) {
                active = currentQuiz.isActive();
            }
        }
        return active;
    }

    /**
     * Set the active status. This is used when a quiz can not be added,
     * but changing the status is necessary. WARNING:
     * This also clears the currentQuiz
     *
     * @param active boolean for active status
     */

    public static void setActive(boolean active) {
        currentQuiz = null;
        QuizHandlerSingelton.active = active;
    }

    /**
     * Reset the Singleton. active status is set to default.
     */

    public static void clear() {
        currentQuiz = null;
        active = true;
    }
}
