package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.GUI.factory.GUIFactory;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;

import java.util.ArrayList;

/**
 * Controller for listQuizzesPages. It lists quizzes in a scrollpane.
 */

public class ListQuizzesPage {
    @FXML // fx:id="quizzesContainer"
    private VBox vBox; // Value injected by FXMLLoader

    @FXML // fx:id="switchStatus"
    private Button switchStatus; // Value injected by FXMLLoader

    /**
     * Switches the active status and updates the page.
     */

    @FXML
    void onSwitchStatus() {
        QuizHandlerSingelton.setActive(!QuizHandlerSingelton.isActive());
        update();
    }

    /**
     * Redirects to the mainPage and clears the Singleton.
     *
     * @param event - click event
     */
    @FXML
    void onBack(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "/GUI/mainPage.fxml");
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        update();
    }

    /**
     * Updates the list from the database and checks if the active status has changed.
     */

    void update() {
        ArrayList<QuizModel> quizzes;

        if (QuizHandlerSingelton.isActive()) {
            quizzes = QuizRegister.getActiveQuizzes();
            switchStatus.setText("Arkivert");
        } else {
            switchStatus.setText("Aktive");
            quizzes = QuizRegister.getArchivedQuizzes();
        }

        vBox.getChildren().clear();

        quizzes.forEach(quiz -> vBox.getChildren().add(GUIFactory.listQuestionItem(quiz)));

    }
}
