package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.ntnu.k2.g2.quizmaker.GUI.factory.GUIFactory;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;


public class CreateNewQuizPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnSubmit"
    private Button btnSubmit; // Value injected by FXMLLoader

    @FXML // fx:id="quizNameInputField"
    private TextField quizNameInputField; // Value injected by FXMLLoader

    @FXML
    private BorderPane borderPane;

    @FXML
    void onSubmitBtnClicked(ActionEvent event) {
        // Create the Quiz instance
        QuizModel createdQuiz = QuizRegister.newQuiz();
        createdQuiz.setName(quizNameInputField.getText());
        QuizRegister.saveQuiz(createdQuiz);

        // Set the states for the question editor page
        QuizHandlerSingelton.setQuiz(createdQuiz);

        // Redirect to question editor
        GUI.setSceneFromNode(btnSubmit, "/GUI/questionEditorPage.fxml");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        borderPane.setTop(GUIFactory.createNavBar("/GUI/mainPage.fxml"));
    }
}
