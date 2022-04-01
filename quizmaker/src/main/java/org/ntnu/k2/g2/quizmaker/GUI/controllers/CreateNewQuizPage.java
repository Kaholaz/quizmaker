package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
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
    void onSubmitBtnClicked(ActionEvent event) {
        // Create the Quiz instance
        QuizRegister register = new QuizRegister();
        Quiz createdQuiz = register.newQuiz();
        createdQuiz.setName(quizNameInputField.getText());

        // TODO: Check if a quiz with the name already exists

        // Set the states for the question editor page
        QuizHandlerSingelton.setQuiz(createdQuiz);
        QuestionEditorPage.returnPage = "/GUI/mainPage.fxml";

        // Redirect to question editor
        GUI.setSceneFromNode(btnSubmit, "/GUI/questionEditorPage.fxml");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnSubmit != null : "fx:id=\"btnSubmit\" was not injected: check your FXML file 'createNewQuizPage.fxml'.";

    }

}
