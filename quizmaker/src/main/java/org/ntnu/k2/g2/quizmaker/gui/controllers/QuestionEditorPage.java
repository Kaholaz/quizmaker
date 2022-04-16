package org.ntnu.k2.g2.quizmaker.gui.controllers;

import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.gui.factories.GUIFactory;
import org.ntnu.k2.g2.quizmaker.data.QuestionModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;

import static org.ntnu.k2.g2.quizmaker.gui.factories.QuestionEditorFactory.createQuestionPane;

public class QuestionEditorPage {
    @FXML // fx:id="vBox"
    private VBox vBox; // Value injected by FXMLLoader

    @FXML
    private BorderPane borderPane;

    @FXML
    void onSave(ActionEvent event) {
        // For each question "set" (question-answer pair)
        for (int i = 0; i < vBox.getChildren().size(); i++) {
            // Get the pane and search for all text inputs
            Pane questionPane = (Pane) vBox.getChildren().get(i);
            Set<Node> textAreas = questionPane.lookupAll("TextArea");

            // Assume that the first text area is the question text area and the second is the answer text area
            TextArea questionTextArea = (TextArea) textAreas.toArray()[0];
            TextArea answerTextArea = (TextArea) textAreas.toArray()[1];

            // Get string values
            String newQuestion = questionTextArea.getText();
            String newAnswer = answerTextArea.getText();

            // Get the question we're changing from the Quiz.
            QuestionModel questionToChange = (QuestionModel) QuizHandlerSingelton.getQuiz().getQuestions().values().toArray()[i];
            questionToChange.setQuestion(newQuestion);
            questionToChange.setAnswer(newAnswer);
        }

        // Save the quiz to the database
        QuizRegister.saveQuiz(QuizHandlerSingelton.getQuiz());

        // Go back to quiz page
        GUI.setSceneFromNode(borderPane, "/gui/quizAdminPage.fxml");
    }

    @FXML
    void onBtnCreateNewQuestionClick(ActionEvent event) {
        QuestionModel newQuestion = QuizRegister.newQuestion(QuizHandlerSingelton.getQuiz());
        vBox.getChildren().add(createQuestionPane(newQuestion, QuizHandlerSingelton.getQuiz().getQuestions().values().size()));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        // Create save button
        Button saveButton = new Button();
        saveButton.setText("Lagre");
        saveButton.setOnAction((ActionEvent e) -> onSave(e));
        HBox navbar = GUIFactory.createNavBar("/gui/quizAdminPage.fxml", saveButton);

        // Add the navbar
        borderPane.setTop(navbar);

        // Load questions to VBox
        loadQuestionsToVBox();
    }

    private void loadQuestionsToVBox() {
        int questionCounter = 1;
        for (QuestionModel question : QuizHandlerSingelton.getQuiz().getQuestions().values()) {
            vBox.getChildren().add(createQuestionPane(question, questionCounter++));
        }
    }
}
