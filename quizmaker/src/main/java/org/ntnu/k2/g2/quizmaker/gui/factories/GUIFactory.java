package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;

/**
 * Factory for creating gui elements in the application.
 */

public class GUIFactory {

    /**
     * Creates a big button for one question in listViewPages.
     *
     * @param quiz  that is being displayed
     * @return a button element styled to occupy the full width.
     */
    public static Button listQuestionItem(QuizModel quiz) {
        Button admin = new Button(quiz.getName());

        // Add on-click event
        admin.setOnAction((ActionEvent e) -> {
            QuizHandlerSingelton.setQuiz(quiz);
            GUI.setSceneFromNode(admin, "/gui/quizAdminPage.fxml");
        });

        // Add styling
        admin.getStylesheets().add("/gui/css/lists.css");
        admin.getStylesheets().add("/gui/css/clickable-nodes.css");
        admin.getStyleClass().add("full-width-list-element");
        admin.getStyleClass().add("clickable-node-lightgreen");

        return admin;
    }

    public static Text basicText(String string) {
        Text text = new Text();
        text.setText(string);
        text.getStyleClass().add("text");
        return text;
    }

    public static Alert createNewErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message,  ButtonType.CLOSE);
        alert.showAndWait();
        return alert;
    }
}
