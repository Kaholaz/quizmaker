package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.gui.decorators.ButtonDecorator;
import org.ntnu.k2.g2.quizmaker.gui.decorators.ContainerDecorator;
import org.ntnu.k2.g2.quizmaker.gui.factories.ButtonFactory;

import java.util.ArrayList;

import static org.ntnu.k2.g2.quizmaker.gui.factories.NavBarFactory.createTopBar;

/**
 * Controller for listQuizzesPages. Used for pages where quizzes are listed.
 */

public class ListQuizzesPage {
    @FXML
    private VBox quizContainer; // Value injected by FXMLLoader

    @FXML
    private BorderPane borderPane;

    private Button switchStatusButton;

    /**
     * Initializes the page. Creates a navbar and updates the gui elements according to the database.
     */

    @FXML
    void initialize() {
        // Create navbar with switch status button
        switchStatusButton = new Button();
        switchStatusButton.setOnAction(event -> {
            QuizHandlerSingelton.setActive(!QuizHandlerSingelton.isActive());
            update();
        });

        HBox navbar = createTopBar("/gui/mainPage.fxml", switchStatusButton);
        borderPane.setTop(navbar);

        update();
    }

    /**
     * Updates the list from the database. The quizzes updated is dependent on the isActive status.
     */

    void update() {
        //clear the container before adding
        quizContainer.getChildren().clear();

        ArrayList<QuizModel> quizzes;

        //Get the quizzes from the database
        if (QuizHandlerSingelton.isActive()) {
            quizzes = QuizRegister.getActiveQuizzes();
            switchStatusButton.setText("Til inaktive");

        } else {
            switchStatusButton.setText("Til arkiv");
            quizzes = QuizRegister.getArchivedQuizzes();
        }

        //add all the quizzes
        quizzes.forEach(quiz -> {
            Button button = ButtonFactory.listQuestionButton(quiz);
            if (!quiz.isActive()) {
                ButtonDecorator.makeArchived(button);
            }
            quizContainer.getChildren().add(button);
        });
    }
}
