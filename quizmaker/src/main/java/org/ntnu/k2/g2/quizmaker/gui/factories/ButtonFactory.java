package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingleton;

/**
 * Creates buttons and clickable nodes in the gui. All the methods
 * for creating the elements are static.
 */

public class ButtonFactory {

    /**
     *  The class is static, so the constructor is private.
     */
    private ButtonFactory() {

    }

    /**
     * Creates a big button for one question in listViewPages.
     *
     * @param quiz The quiz the button is going to represent.
     * @return A button element styled to occupy the full width.
     */
    public static Button createQuestionListButton(QuizModel quiz) {
        Button admin = new Button(quiz.getName());

        // Add on-click event
        admin.setOnAction((ActionEvent e) -> {
            QuizHandlerSingleton.setQuiz(quiz);
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
     * Creates the default clickable button in the app.
     *
     * @param string The button text.
     * @return button The button element.
     */
    public static Button createGrayButton(String string) {
        Button button = new Button(string);
        button.getStyleClass().add("clickable-node-gray");
        return button;
    }

    /**
     * Creates a default delete button.
     *
     * @param string The button text.
     * @return button The constructed button element.
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
