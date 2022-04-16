package org.ntnu.k2.g2.quizmaker;

import javafx.application.Application;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.factories.GUIFactory;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;

public class Main {
    public static void main(String[] args) {
        QuizRegister quizRegister = new QuizRegister();
        try {
            quizRegister.getQuizList();
        } catch (Exception e) {
            GUIFactory.createNewErrorAlert("Failed to get quizzes from database: \n" + e.getMessage());
            return;
        }
        Application.launch(GUI.class, args);
    }

}
