package org.ntnu.k2.g2.quizmaker;

import javafx.application.Application;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.factories.AlertFactory;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        try {
            QuizRegister.getQuizList();
        } catch (Exception e) {
            AlertFactory.showJOptionWarning("Det skjedde en uventet feil... \n" + e.getMessage());
            return;
        }

        //TODO: fjern i prod

        if (QuizRegister.getQuizList().isEmpty()) {
            AlertFactory.showJOptionWarning("Generer quizzer. Dette kan ta et par sekunder.");
            QuizRegister.populateDatabase(5, 100, 100);
        }
        Application.launch(GUI.class, args);
    }

}
