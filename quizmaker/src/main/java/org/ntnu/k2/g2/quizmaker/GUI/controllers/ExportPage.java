/**
 * Sample Skeleton for 'exportPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.GUI.factory.ExportFactory;

public class ExportPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="export"
    private Button export; // Value injected by FXMLLoader

    @FXML // fx:id="vBox"
    private VBox vBox; // Value injected by FXMLLoader

    @FXML
    private Text exportMsg;

    @FXML
    private CheckBox c1, c2, c3, c4, c5, c6, c7;

    private Quiz quiz;

    @FXML
    private void onExport() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) export.getScene().getWindow();
        directoryChooser.showDialog(stage);
        exportMsg.setText("Export successfull");

    }
    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert export != null : "fx:id=\"export\" was not injected: check your FXML file 'exportPage.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'exportPage.fxml'.";
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
