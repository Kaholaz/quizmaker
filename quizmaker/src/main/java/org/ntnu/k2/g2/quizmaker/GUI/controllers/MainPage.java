package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;

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
        GUI.setSceneFromNode(btnListQuizzes, "/GUI/listQuizzesPage.fxml");
    }

    public void onCreateNewQuizBtnClicked() {
        GUI.setSceneFromNode(btnNewQuiz, "/GUI/createNewQuizPage.fxml");
    }

    @FXML
    void initialize() {
        QuizRegister quizRegister = new QuizRegister();
        if (quizRegister.getQuizList().isEmpty()) {
            quizRegister.populateDatabase(5, 5, 5);
        }
    }
}
