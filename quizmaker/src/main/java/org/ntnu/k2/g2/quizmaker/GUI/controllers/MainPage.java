package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage {

    @FXML
    public Button btnNewQuiz, btnListQuizzes;

    public void onListQuizzesBtnClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/listActiveQuizzesPage.fxml"));
        Stage stage = (Stage) btnListQuizzes.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void onCreateNewQuizBtnClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/listActiveQuizzesPage.fxml"));
        Stage stage = (Stage) btnNewQuiz.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
