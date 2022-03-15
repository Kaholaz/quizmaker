package org.ntnu.k2.g2.quizmaker.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class mainPageController {

    @FXML
    public Button newQuiz, browse;

    public void browse(ActionEvent actionEvent) {
    }

    public void newQuiz() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/newQuiz.fxml"));
        Stage stage = (Stage) newQuiz.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
