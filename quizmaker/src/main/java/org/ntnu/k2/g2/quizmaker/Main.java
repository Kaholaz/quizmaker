package org.ntnu.k2.g2.quizmaker;

import javafx.application.Application;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        try {
            QuizRegister.getQuizList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An unexpected error occurred when connecting to database\n" + e.getMessage(),
                    "Hey!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (QuizRegister.getQuizList().isEmpty()) {
            QuizRegister.populateDatabase(5, 100, 100);
        }
        Application.launch(GUI.class, args);
    }

}
