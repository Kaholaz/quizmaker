package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;

import java.io.IOException;

public class MainPage {

    @FXML
    public Button btnNewQuiz, btnListQuizzes;

    public void onListQuizzesBtnClicked(ActionEvent actionEvent) throws IOException {
        GUI.setSceneFromNode(btnListQuizzes, "/GUI/listActiveQuizzesPage.fxml");
    }

    public void onCreateNewQuizBtnClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(""));
        GUI.setSceneFromNode(btnNewQuiz, "/GUI/createNewQuizPage.fxml");

    }
}