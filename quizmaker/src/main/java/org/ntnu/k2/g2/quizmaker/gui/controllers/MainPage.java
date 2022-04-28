package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingleton;

/**
 * Controller for the mainPage
 */
public class MainPage {

    @FXML
    public Button btnNewQuiz, btnListQuizzes;

    @FXML
    public BorderPane borderPane;

    /**
     * Redirects to listQuizzesPage and list all active quizzes.
     */
    @FXML
    void onListQuizzesBtnClicked() {
        GUI.setSceneFromNode(btnListQuizzes, "/gui/listQuizzesPage.fxml");
    }

    /**
     * Takes the user to createNewQuizPage
     */
    @FXML
    void onCreateNewQuizBtnClicked() {
        GUI.setSceneFromNode(btnNewQuiz, "/gui/createNewQuizPage.fxml");
    }

    /**
     * Sets current quiz to null after loading the launch page.
     */
    @FXML
    void initialize() {
        QuizHandlerSingleton.setQuiz(null);
    }
}
