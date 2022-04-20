package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.gui.factories.AlertFactory;
import org.ntnu.k2.g2.quizmaker.gui.factories.NavBarFactory;

/**
 * Controller for the newQuizPage. Allows the user to create a new quiz.
 */

public class CreateNewQuizPage {

    @FXML
    private Button btnSubmit;

    @FXML
    private TextField quizNameInputField;

    @FXML
    private BorderPane borderPane;

    /**
     * Event listener for when the create quiz button is pressed.
     * This triggers the creation of the new quiz and changes the scene to the question editor for this quiz.
     * This allows the users to add questions for the newly created quiz.
     *
     * @param event The event for when the create quiz button is pressed.
     */
    @FXML
    void onSubmitBtnClicked(ActionEvent event) {
        // Create the Quiz instance
        QuizModel createdQuiz;
        try {
            createdQuiz = QuizRegister.newQuiz();
            createdQuiz.setName(quizNameInputField.getText());
        } catch (Exception e) {
            AlertFactory.createNewErrorAlert("An unexpected error occured... \n" + e.getMessage());
            GUI.setSceneFromActionEvent(event, "gui/mainPage.fxml");
            return;
        }
        QuizRegister.saveQuiz(createdQuiz);

        // Set the states for the question editor page
        QuizHandlerSingelton.setQuiz(createdQuiz);

        // Redirect to question editor
        GUI.setSceneFromNode(btnSubmit, "/gui/questionEditorPage.fxml");
    }

    /**
     * Initializes the page. Creates a navbar on top of the page.
     * This method is called after the fxml page is loaded.
     */
    @FXML
    void initialize() {
        borderPane.setTop(NavBarFactory.createTopBar("/gui/mainPage.fxml"));
    }
}
