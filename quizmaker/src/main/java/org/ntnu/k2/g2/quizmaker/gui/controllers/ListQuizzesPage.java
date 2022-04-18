package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private VBox quizContainer; // Value injected by FXMLLoader

    @FXML
    private BorderPane borderPane;

    private Button switchStatusButton;

    /**
     * Initializes the page. Creates a navbar and updates the gui elements according to the database.
     */

    @FXML
    void initialize() {
        // Create navbar with switch status button
        switchStatusButton = new Button();
        switchStatusButton.setOnAction(event -> {
            QuizHandlerSingelton.setActive(!QuizHandlerSingelton.isActive());
            update();
        });

        HBox navbar = createNavBar("/gui/mainPage.fxml", switchStatusButton);
        borderPane.setTop(navbar);

        update();
    }

    /**
     * Updates the list from the database. The quizzes updated is dependent on the isActive status.
     */

    void update() {
        //clear the container before adding
        quizContainer.getChildren().clear();

        ArrayList<QuizModel> quizzes;

        //Get the quizzes from the database
        if (QuizHandlerSingelton.isActive()) {
            quizzes = QuizRegister.getActiveQuizzes();
            switchStatusButton.setText("Arkivert");
        } else {
            switchStatusButton.setText("Aktive");
            quizzes = QuizRegister.getArchivedQuizzes();
        }

        //add all the quizzes
        quizzes.forEach(quiz -> quizContainer.getChildren().add(GUIFactory.listQuestionItem(quiz)));
    }
}
