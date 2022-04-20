package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.gui.factories.ButtonFactory;

import java.util.ArrayList;

import static org.ntnu.k2.g2.quizmaker.gui.factories.NavBarFactory.createTopBar;

/**
 * Controller for listQuizzesPages. Used for pages where quizzes are listed.
 */

public class ListQuizzesPage {
    @FXML
    private VBox quizContainer; // Value injected by FXMLLoader

    @FXML
    private BorderPane borderPane;

    private Button switchStatusButton;

    /**
     * Initializes the page. Creates a navbar and updates the gui elements according to the database.
     * This method is called immediately after the fxml page is loaded.
     */
    @FXML
    void initialize() {
        // Create navbar with switch status button
        switchStatusButton = new Button();
        switchStatusButton.setOnAction(event -> {
            QuizHandlerSingelton.setActive(!QuizHandlerSingelton.isActive());
            refreshQuizList();
        });

        HBox navbar = createTopBar("/gui/mainPage.fxml", switchStatusButton);
        borderPane.setTop(navbar);

        refreshQuizList();
    }

    /**
     * This method refreshes the quiz list.
     * This is done in three steps:
     * <ol>
     *     <li>Clear all quizzes from the list.</li>
     *     <li>Fetch the list of quizzes from the database.</li>
     *     <li>Fill the quiz container with quiz elements according to the list of quizzes.</li>
     * </ol>
     *
     * This method takes into account the quiz status of the QuizHandler.
     */
    void refreshQuizList() {
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
        quizzes.forEach(quiz -> quizContainer.getChildren().add(ButtonFactory.listQuestionButton(quiz)));
    }
}
