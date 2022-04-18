package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.data.QuestionModel;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.gui.factories.GUIFactory;

import java.util.Comparator;
import java.util.List;

import static org.ntnu.k2.g2.quizmaker.gui.factories.QuestionEditorFactory.createQuestionPane;


public class QuestionEditorPage {
    @FXML // fx:id="vBox"
    private VBox vBox; // Value injected by FXMLLoader

    @FXML
    private BorderPane borderPane;

    private final QuizModel quiz = QuizHandlerSingelton.getQuiz();

    private QuestionModel target;

    @FXML
    void onSave(ActionEvent event) {
        QuizRegister.saveQuiz(quiz);
        GUI.setSceneFromActionEvent(event, "/gui/quizAdminPage.fxml");
    }

    @FXML
    void onBtnCreateNewQuestionClick() {
        QuestionModel newQuestion = QuizRegister.newQuestion(quiz);
        vBox.getChildren().add(createQuestionPane(newQuestion, quiz.getQuestions().size()));
    }

    @FXML
    void initialize() {
        // Create save button
        Button saveButton = new Button();
        saveButton.setText("Lagre");
        saveButton.setOnAction(this::onSave);
        HBox navbar = GUIFactory.createNavBar("/gui/quizAdminPage.fxml", saveButton);

        //Add the navbar
        borderPane.setTop(navbar);

        // Load questions to VBox
        loadQuestionsToVBox();
    }

    private void loadQuestionsToVBox() {
        vBox.getChildren().clear();
        List<QuestionModel> sorted = quiz.getQuestions().values().stream().sorted(Comparator.comparingInt(QuestionModel::getId)).toList();
        for (int i = 0; i < sorted.size(); i++) {
            QuestionModel question = sorted.get(i);
            vBox.getChildren().add(createQuestionPane(question, i + 1));
        }
    }
}
