package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;


public class MainPage {

    @FXML
    public Button btnNewQuiz, btnListQuizzes;

    public void onListQuizzesBtnClicked(ActionEvent actionEvent) {
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
            quizRegister.populateDatabase(5,5,5);
        }
    }
}
