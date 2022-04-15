package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;

/**
 * Controller for the mainPage
 */

public class MainPage {

    @FXML
    public Button btnNewQuiz, btnListQuizzes;

    /**
     * Redirects to listQuizzesPage and sets the Singleton active status to true.
     */

    @FXML
    void onListQuizzesBtnClicked() {
        QuizHandlerSingelton.setActive(true);
        GUI.setSceneFromNode(btnListQuizzes, "/gui/listQuizzesPage.fxml");
    }

    public void onCreateNewQuizBtnClicked() {
        GUI.setSceneFromNode(btnNewQuiz, "/gui/createNewQuizPage.fxml");
    }
}
