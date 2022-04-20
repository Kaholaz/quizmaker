package org.ntnu.k2.g2.quizmaker.gui.controllers;

import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.BackgroundImage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;

/**
 * Controller for the mainPage
 */

public class MainPage {

    @FXML
    public Button btnNewQuiz, btnListQuizzes;

    @FXML
    public BorderPane borderPane;

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
