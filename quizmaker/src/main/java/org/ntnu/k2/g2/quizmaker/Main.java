package org.ntnu.k2.g2.quizmaker;

import javafx.application.Application;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.factories.AlertFactory;

import java.net.URL;

/**
 * The main class of the application.
 */
public class Main {
    public static void main(String[] args) {
        URL googleCredentials = Main.class.getResource("/userdata/google-credentials.json");
        if (googleCredentials == null) {
            AlertFactory.showJOptionWarning(
                    "Det finnes ingen 'google-credentials.json' fil! Vennligst se installasjonsguiden!");
            return;
        }

        try {
            if (QuizRegister.isReadOnly()) {
                AlertFactory.showJOptionWarning("Kan ikke skrive til databasen!\nApplikasjonen trenger administratorrettigheter.");
            }
        } catch (NullPointerException e) {
            AlertFactory.showJOptionWarning("Kunne ikke koble til databasen! \n (Husk å kjøre applikasjonen som administrator)");
            return;
        }

        Application.launch(GUI.class, args);
    }

}
