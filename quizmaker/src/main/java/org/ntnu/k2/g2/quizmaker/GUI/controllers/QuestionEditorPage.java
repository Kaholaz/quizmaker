package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.Data.Question;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;

import static org.ntnu.k2.g2.quizmaker.GUI.factory.QuestionEditorFactory.createQuestionPane;

public class QuestionEditorPage {
    @FXML // fx:id="archive"
    private Button save; // Value injected by FXMLLoader

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="vBox"
    private VBox vBox; // Value injected by FXMLLoader

    // This is also a "state". This is the page that the back button will return to.
    // TODO: Implement checks when setting to this variable
    public static String returnPage = "/GUI/mainPage.fxml";

    @FXML
    void onSave(ActionEvent event) {
        for (int i = 0; i < vBox.getChildren().size(); i++) {
            Pane questionPane = (Pane) vBox.getChildren().get(i);
            Set<Node> textAreas = questionPane.lookupAll("TextArea");

            TextArea questionTextArea = (TextArea) textAreas.toArray()[0];
            TextArea answerTextArea = (TextArea) textAreas.toArray()[1];

            String newQuestion = questionTextArea.getText();
            String newAnswer = answerTextArea.getText();

            Question questionToChange = (Question) QuizHandlerSingelton.getQuiz().getQuestions().values().toArray()[i];
            questionToChange.setQuestion(newQuestion);
            questionToChange.setAnswer(newAnswer);
        }
    }

    @FXML
    void onBack(ActionEvent event) {
        GUI.setSceneFromNode(save, returnPage);
    }

    @FXML
    void onBtnCreateNewQuestionClick(ActionEvent event) {
        QuizRegister register = new QuizRegister();
        Question newQuestion = register.newQuestion(QuizHandlerSingelton.getQuiz());
        vBox.getChildren().add(createQuestionPane(newQuestion));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'quizCreatorPage.fxml'.";
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'quizCreatorPage.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'quizCreatorPage.fxml'.";
        loadQuestionsToVBox();
    }

    private void loadQuestionsToVBox() {
        QuizHandlerSingelton.getQuiz().getQuestions().forEach((id, question) -> {
            vBox.getChildren().add(createQuestionPane(question));
        });
    }
}
