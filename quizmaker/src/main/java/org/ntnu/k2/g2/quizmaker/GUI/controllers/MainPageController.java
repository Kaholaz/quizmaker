package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    @FXML
    public Button newQuiz, browse;

    public void browse(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/browse.fxml"));
        Stage stage = (Stage) newQuiz.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void newQuiz() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/newQuiz.fxml"));
        Stage stage = (Stage) newQuiz.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
