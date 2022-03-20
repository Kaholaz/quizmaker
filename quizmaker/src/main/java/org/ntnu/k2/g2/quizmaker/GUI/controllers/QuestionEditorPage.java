/**
 * Sample Skeleton for 'quizCreatorPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;

public class QuestionEditorPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="archive"
    private Button save; // Value injected by FXMLLoader

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="vBox"
    private VBox vBox; // Value injected by FXMLLoader

    @FXML
    void onSave(ActionEvent event) {

    }

    @FXML
    void onBack(ActionEvent event) {
        GUI.setSceneFromNode(save, "/GUI/mainPage.fxml");
    }

    @FXML
    void onBtnCreateNewQuestionClick(ActionEvent event) {
        GUI.setSceneFromNode(save, "/GUI/questionEditorPage.fxml");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'quizCreatorPage.fxml'.";
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'quizCreatorPage.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'quizCreatorPage.fxml'.";

    }

}
