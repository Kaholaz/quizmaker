package org.ntnu.k2.g2.quizmaker.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.gui.factories.GUIFactory;

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

    @FXML
    void onSubmitBtnClicked(ActionEvent event) {
        // Create the Quiz instance
        QuizModel createdQuiz;
        try {
            createdQuiz = QuizRegister.newQuiz();
            createdQuiz.setName(quizNameInputField.getText());
        } catch (Exception e) {
            GUIFactory.createNewErrorAlert("An unexpected error occured... \n" + e.getMessage());
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
     */
    @FXML
    void initialize() {
        borderPane.setTop(GUIFactory.createNavBar("/gui/mainPage.fxml"));
    }
}
