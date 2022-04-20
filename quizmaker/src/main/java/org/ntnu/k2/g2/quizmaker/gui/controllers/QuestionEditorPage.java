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
import org.ntnu.k2.g2.quizmaker.gui.decorators.ContainerDecorator;
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

    private final QuizModel quiz = QuizHandlerSingelton.getQuiz();

    /**
     * Creates a new question, and binds it to the quiz. A new pane will be generated.
     */

    @FXML
    void onCreateNewQuestion() {
        QuestionModel question = QuizRegister.newQuestion(quiz);
        vBox.getChildren().add(createQuestionPane(question, quiz.getQuestions().size()));
    }

    /**
     * Initializes the page by generating all panes with quizzes and a navbar.
     */
    @FXML
    void initialize() {
        // Create save button
        Button saveButton = new Button();
        HBox navbar = NavBarFactory.createTopBar("/gui/quizAdminPage.fxml");

        //set root color for wether the quiz is active or not
        if (quiz.isActive()) {
            ContainerDecorator.makeContainerActive(borderPane);
        } else {
            ContainerDecorator.makeContainerArchived(borderPane);
        }

        //Add the navbar
        borderPane.setTop(navbar);

        // Load questions to VBox
        loadQuestionsToVBox();

    }

    /**
     * Helper method that loops through all the questions and creates questionpanes.
     */

    private void loadQuestionsToVBox() {
        vBox.getChildren().clear();
        List<QuestionModel> sorted = quiz.getQuestions().values().stream().sorted(Comparator.comparingInt(QuestionModel::getId)).toList();
        for (int i = 0; i < sorted.size(); i++) {
            QuestionModel question = sorted.get(i);
            vBox.getChildren().add(createQuestionPane(question, i + 1));
        }
    }

    /**
     * Saves all the edited questions to the database.
     *
     * @param event
     */

    @FXML
    void onSave(ActionEvent event) {
        try {
            QuizRegister.saveQuiz(quiz);
        } catch (Exception e) {
            AlertFactory.createNewErrorAlert("Could not save the quiz... \n" + e.getMessage());
        }
        GUI.setSceneFromActionEvent(event, "/gui/quizAdminPage.fxml");
    }
}
