/**
 * Sample Skeleton for 'listArchivedQuizzesPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.factory.GUIFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListArchivedQuizzesPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="vBox"
    private VBox vBox; // Value injected by FXMLLoader

    @FXML
    void onBack(ActionEvent event) throws IOException {
        GUI.setSceneFromNode(back, "/GUI/listActiveQuizzesPage.fxml");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'listArchivedQuizzesPage.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'listArchivedQuizzesPage.fxml'.";
        updateQuizzes();
    }
    void updateQuizzes() {
        QuizRegister quizRegister = new QuizRegister();
        ArrayList<Quiz> quizzes = quizRegister.getArchivedQuizzes();
        quizzes.forEach(quiz -> vBox.getChildren().add(GUIFactory.makeQuestionv2(quiz)));
    }



}
