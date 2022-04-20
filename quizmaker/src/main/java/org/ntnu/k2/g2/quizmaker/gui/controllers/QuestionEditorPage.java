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
import org.ntnu.k2.g2.quizmaker.gui.factories.AlertFactory;
import org.ntnu.k2.g2.quizmaker.gui.factories.NavBarFactory;

import java.util.Comparator;
import java.util.List;

import static org.ntnu.k2.g2.quizmaker.gui.factories.ContainerFactory.createQuestionPane;

/**
 * Controller for the questionEditorPage. Allows user to edit questions.
 *
 */

public class QuestionEditorPage {
    @FXML
    private VBox vBox;

    @FXML
    private BorderPane borderPane;

    /**
     * Creates a new question, and binds it to the quiz. A new pane will be generated.
     */

    @FXML
    void onCreateNewQuestion() {
        QuestionModel question = QuizRegister.newQuestion(QuizHandlerSingelton.getQuiz());
        vBox.getChildren().add(createQuestionPane(question, QuizHandlerSingelton.getQuiz().getQuestions().size()));
    }

    /**
     * Initializes the page by generating all panes with quizzes and a navbar.
     */
    @FXML
    void initialize() {
        // Create save button
        Button saveButton = new Button();
        saveButton.setText("Lagre");
        saveButton.setOnAction(this::onSave);
        HBox navbar = NavBarFactory.createTopBar("/gui/quizAdminPage.fxml", saveButton);

        //Add the navbar
        borderPane.setTop(navbar);

        // Load questions to VBox
        loadQuestionsToVBox();
    }

    /**
     * Helper method that loops through all the questions and creates question-panes.
     */
    private void loadQuestionsToVBox() {
        vBox.getChildren().clear();

        // A list of all questions sorted by id (and by extension creation order)
        List<QuestionModel> sorted = QuizHandlerSingelton.getQuiz()
                .getQuestions().values().stream()
                .sorted(Comparator.comparingInt(QuestionModel::getId)).toList();

        for (int questionNumber = 1; questionNumber <= sorted.size(); questionNumber++) {
            QuestionModel question = sorted.get(questionNumber - 1);
            vBox.getChildren().add(createQuestionPane(question, questionNumber));
        }
    }

    /**
     * Saves all the edited questions to the database.
     *
     * @param event
     */

    void onSave(ActionEvent event) {
        try {
            QuizRegister.saveQuiz(QuizHandlerSingelton.getQuiz());
        } catch (Exception e) {
            AlertFactory.createNewErrorAlert("Could not save the quiz... \n" + e.getMessage());
        }
        GUI.setSceneFromActionEvent(event, "/gui/quizAdminPage.fxml");
    }
}
