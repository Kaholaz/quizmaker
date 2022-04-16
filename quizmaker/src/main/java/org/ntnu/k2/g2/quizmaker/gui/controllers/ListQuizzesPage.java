package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.gui.factories.GUIFactory;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;

import java.util.ArrayList;

import static org.ntnu.k2.g2.quizmaker.gui.factories.GUIFactory.createNavBar;

/**
 * Controller for listQuizzesPages. It lists quizzes in a scrollpane.
 */

public class ListQuizzesPage {
    @FXML
    private VBox vBox; // Value injected by FXMLLoader

    @FXML
    private BorderPane borderPane; // Value injected by FXMLLoader

    private Button switchStatusButton;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        // Create navbar with switch status button
        switchStatusButton = new Button();
        switchStatusButton.setOnAction((ActionEvent e) -> {
            QuizHandlerSingelton.setActive(!QuizHandlerSingelton.isActive());
            update();
        });
        HBox navbar = createNavBar("/GUI/mainPage.fxml", switchStatusButton);
        borderPane.setTop(navbar);

        update();
    }

    /**
     * Updates the list from the database and checks if the active status has changed.
     */

    void update() {
        ArrayList<QuizModel> quizzes;

        if (QuizHandlerSingelton.isActive()) {
            quizzes = QuizRegister.getActiveQuizzes();
            switchStatusButton.setText("Arkivert");
        } else {
            switchStatusButton.setText("Aktive");
            quizzes = QuizRegister.getArchivedQuizzes();
        }

        vBox.getChildren().clear();

        quizzes.forEach(quiz -> vBox.getChildren().add(GUIFactory.listQuestionItem(quiz)));
    }
}
