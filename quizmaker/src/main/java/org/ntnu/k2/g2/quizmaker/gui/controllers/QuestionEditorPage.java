package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.data.QuestionModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingleton;
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

    /**
     * Creates a new question, and binds it to the quiz. A new pane will be generated.
     */
    @FXML
    void onCreateNewQuestion() {
        QuestionModel question = QuizRegister.newQuestion(QuizHandlerSingleton.getQuiz());
        vBox.getChildren().add(createQuestionPane(question, QuizHandlerSingleton.getQuiz().getQuestions().size()));
    }

    /**
     * Initializes the page by generating all question buttons and a navbar.
     * This method is called after loading the FXML page.
     */
    @FXML
    void initialize() {
        // Create navigation bar
        HBox navbar = NavBarFactory.createTopNavigationBar("/gui/quizAdminPage.fxml");
        borderPane.setTop(navbar);

        // Set root color according to if the quiz is active or not
        if (QuizHandlerSingleton.isActive()) {
            ContainerDecorator.makeContainerActive(borderPane);
        } else {
            ContainerDecorator.makeContainerArchived(borderPane);
        }

        // Load questions to VBox
        loadQuestionsToVBox();
    }

    /**
     * Helper method that loops through all the questions and creates question-panes.
     */
    private void loadQuestionsToVBox() {
        vBox.getChildren().clear();

        // A list of all questions sorted by id (and by extension creation order)
        List<QuestionModel> sorted = QuizHandlerSingleton.getQuiz()
                .getQuestions().values().stream()
                .sorted(Comparator.comparingInt(QuestionModel::getId)).toList();

        for (int questionNumber = 1; questionNumber <= sorted.size(); questionNumber++) {
            QuestionModel question = sorted.get(questionNumber - 1);
            vBox.getChildren().add(createQuestionPane(question, questionNumber));
        }
    }

    /**
     * Saves all the edited questions to the database.
     * This method is triggered when the user pressed the save button.
     *
     * @param event The action event when the user presses the save button.
     */
    @FXML
    void onSave(ActionEvent event) {
        try {
            QuizRegister.saveQuiz(QuizHandlerSingleton.getQuiz());
        } catch (Exception e) {
            AlertFactory.createNewErrorAlert("Could not save the quiz... \n" + e.getMessage());
        }

        GUI.setSceneFromActionEvent(event, "/gui/quizAdminPage.fxml");
    }
}
