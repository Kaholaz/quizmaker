package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingleton;
import org.ntnu.k2.g2.quizmaker.gui.decorators.ButtonDecorator;
import org.ntnu.k2.g2.quizmaker.gui.factories.ButtonFactory;

import java.util.ArrayList;

import static org.ntnu.k2.g2.quizmaker.gui.factories.NavBarFactory.createTopNavigationBar;

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
        // Switch status button for navigation bar
        switchStatusButton = new Button();
        switchStatusButton.setOnAction(event -> {
            QuizHandlerSingleton.setActive(!QuizHandlerSingleton.isActive());
            refreshQuizList();
        });

        // Create navigation mar
        HBox navbar = createTopNavigationBar("/gui/mainPage.fxml", switchStatusButton);
        borderPane.setTop(navbar);

        refreshQuizList();
    }

    /**
     * This method refreshes the quiz list.
     * This is done in three steps:
     * <ol>
     *     <li>Clear all quizzes from the gui.</li>
     *     <li>Fetch the list of quizzes from the database.</li>
     *     <li>Fill the quiz container with quiz elements according to the list of quizzes.</li>
     * </ol>
     *
     * This method takes into account the quiz status of the QuizHandler.
     */
    void refreshQuizList() {
        //clear the container before adding.
        quizContainer.getChildren().clear();

        ArrayList<QuizModel> quizzes;

        //Fetch quizzes from the database
        if (QuizHandlerSingleton.isActive()) {
            quizzes = QuizRegister.getActiveQuizzes();
            switchStatusButton.setText("Til inaktive");
        } else {
            switchStatusButton.setText("Til aktive");
            quizzes = QuizRegister.getArchivedQuizzes();
        }

        //Add all the quizzes to the container.
        quizzes.forEach(quiz -> {
            Button button = ButtonFactory.createQuestionListButton(quiz);

            // The button is styled differently if the quiz is archived or not.
            if (quiz.isActive()) {
                ButtonDecorator.makeQuizButtonActive(button);
            } else {
                ButtonDecorator.makeQuizButtonArchived(button);
            }

            // Adds the quiz button to the container.
            quizContainer.getChildren().add(button);
        });
    }
}
