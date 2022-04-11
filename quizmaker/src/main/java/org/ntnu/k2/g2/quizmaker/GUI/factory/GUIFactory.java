package org.ntnu.k2.g2.quizmaker.GUI.factory;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.data.TeamModel;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;

import java.util.ArrayList;

/**
 * Factory for creating GUI elements in the application.
 */

public class GUIFactory {

    /**
     * Creates a big button for one question in listViewPages.
     *
     * @param quiz that is being displayed
     * @return Button element
     */
    public static HBox listQuestionItem(QuizModel quiz) {
        HBox hBox = new HBox();
        Button admin = new Button(quiz.getName());

        admin.setId(String.valueOf(quiz.getId()));

        admin.setOnAction((ActionEvent e) -> {
            QuizHandlerSingelton.setQuiz(quiz);
            GUI.setSceneFromNode(admin, "/GUI/quizAdminPage.fxml");
        });

        admin.getStyleClass().add("listQuiz");

        hBox.getChildren().add(admin);

        return hBox;
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
