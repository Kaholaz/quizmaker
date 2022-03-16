/**
 * Sample Skeleton for 'quizAdminPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class QuizAdminPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="details"
    private Button details; // Value injected by FXMLLoader

    @FXML // fx:id="difficulty"
    private Text difficulty; // Value injected by FXMLLoader

    @FXML // fx:id="editQuestions"
    private Button editQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="editScores"
    private Button editScores; // Value injected by FXMLLoader

    @FXML // fx:id="editTeams"
    private Button editTeams; // Value injected by FXMLLoader

    @FXML // fx:id="exportQA"
    private Button exportQA; // Value injected by FXMLLoader

    @FXML // fx:id="quizName"
    private Text quizName; // Value injected by FXMLLoader

    @FXML // fx:id="retrieveScores"
    private Button retrieveScores; // Value injected by FXMLLoader

    @FXML // fx:id="sumQuestions"
    private Text sumQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="sumTeams"
    private Text sumTeams; // Value injected by FXMLLoader

    @FXML
    void editScores(ActionEvent event) {

    }

    @FXML
    void onBack(ActionEvent event) {

    }

    @FXML
    void onDetails(ActionEvent event) {

    }

    @FXML
    void onEditQuestion(ActionEvent event) {

    }

    @FXML
    void onEditTeams(ActionEvent event) {

    }

    @FXML
    void onExportQA(ActionEvent event) {

    }

    @FXML
    void onRetrieveScores(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert details != null : "fx:id=\"details\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert difficulty != null : "fx:id=\"difficulty\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert editQuestions != null : "fx:id=\"editQuestions\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert editScores != null : "fx:id=\"editScores\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert editTeams != null : "fx:id=\"editTeams\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert exportQA != null : "fx:id=\"exportQA\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert quizName != null : "fx:id=\"quizName\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert retrieveScores != null : "fx:id=\"retrieveScores\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert sumQuestions != null : "fx:id=\"sumQuestions\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert sumTeams != null : "fx:id=\"sumTeams\" was not injected: check your FXML file 'quizAdminPage.fxml'.";

    }

}
