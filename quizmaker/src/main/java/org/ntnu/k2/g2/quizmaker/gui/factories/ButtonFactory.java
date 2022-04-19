package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;

/**
 * Creates buttons and clickable nodes in the gui. All the methods
 * for creating the elements are static.
 */

public class ButtonFactory {

    /**
     *  Private constructor. No need for instantiation.
     */

    private ButtonFactory() {

    }

    /**
     * Creates a big button for one question in listViewPages.
     *
     * @param quiz  that is being displayed
     * @return a button element styled to occupy the full width.
     */

    public static Button listQuestionButton(QuizModel quiz) {
        Button admin = new Button(quiz.getName());

        // Add on-click event
        admin.setOnAction((ActionEvent e) -> {
            QuizHandlerSingelton.setQuiz(quiz);
            GUI.setSceneFromNode(admin, "/gui/quizAdminPage.fxml");
        });

        // Add styling
        admin.getStylesheets().add("/gui/css/clickable-nodes.css");
        admin.getStylesheets().add("/gui/css/buttons.css");
        admin.getStyleClass().add("full-width-list-element");
        admin.getStyleClass().add("clickable-node-lightgreen");

        return admin;
    }

    /**
     * Default clickable button in the app.
     *
     * @param string button text
     * @return button
     */

    public static Button createGrayButton(String string) {
        Button button = new Button(string);
        button.getStyleClass().add("clickable-node-gray");
        return button;
    }

    /**
     * Default delete button. Is a button used to warn the user.
     *
     * @param string button text
     * @return button
     */


    public static Button createNavbarButton(String string) {
        Button button = new Button(string);
        button.getStylesheets().add("/gui/css/buttons.css");
        button.getStylesheets().add("/gui/css/clickable-nodes.css");
        button.getStyleClass().add("navbar-button");
        button.getStyleClass().add("clickable-node-gray");
        return button;
    }
}
