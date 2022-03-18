package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class CreateNewQuizPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnSubmit"
    private Button btnSubmit; // Value injected by FXMLLoader

    @FXML
    void onSubmitBtnClicked(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnSubmit != null : "fx:id=\"btnSubmit\" was not injected: check your FXML file 'createNewQuizPage.fxml'.";

    }

}
