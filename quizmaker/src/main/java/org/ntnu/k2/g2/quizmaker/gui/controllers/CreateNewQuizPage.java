package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingleton;
import org.ntnu.k2.g2.quizmaker.gui.factories.AlertFactory;
import org.ntnu.k2.g2.quizmaker.gui.factories.NavBarFactory;
import org.ntnu.k2.g2.quizmaker.gui.factories.TextFactory;

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
    private VBox mainContainer;

    @FXML
    void onSubmitBtnClicked() {
        // Create the Quiz instance

        String name;
        name = quizNameInputField.getText();
        mainContainer.getChildren().clear();
        mainContainer.getChildren().add(TextFactory.createTitle("Lager quiz..."));

        new Thread(() -> { //use another thread so long process does not block gui
            QuizModel createdQuiz;
            try {
                createdQuiz = QuizRegister.newQuiz();
                createdQuiz.setName(name);
                QuizRegister.saveQuiz(createdQuiz);
            } catch (Exception e) {
                Platform.runLater(() -> {
                    AlertFactory.createNewErrorAlert("Could not create quiz...");
                    GUI.setSceneFromNode(mainContainer, "/gui/createNewQuizPage.fxml");
                });
                return;
            }
            // Set the states for the question editor page
            QuizHandlerSingleton.setQuiz(createdQuiz);

            Platform.runLater(() -> GUI.setSceneFromNode(mainContainer, "/gui/questionEditorPage.fxml"));
        }).start();
    }

    /**
     * Initializes the page. Inserts a navigation bar at the top of the page.
     * This method is called after the fxml page is loaded.
     */
    @FXML
    void initialize() {
        borderPane.setTop(NavBarFactory.createTopNavigationBar("/gui/mainPage.fxml"));
    }
}
