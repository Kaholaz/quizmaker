package org.ntnu.k2.g2.quizmaker.GUI.factory;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;

import org.ntnu.k2.g2.quizmaker.GUI.GUI;

/**
 * Factory for creating GUI elements in the application.
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
            GUI.setSceneFromNode(admin, "/GUI/quizAdminPage.fxml");
        });

        // Add styling
        admin.getStylesheets().add("/GUI/css/lists.css");
        admin.getStylesheets().add("/GUI/css/clickable-nodes.css");
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
        return new Alert(Alert.AlertType.ERROR, message,  ButtonType.CLOSE);
    }

    public static HBox createNavBar(String backPage, Button... buttons) {
        HBox navbar = new HBox();

        // Add logo / quiz-maker title
        Text title = basicText("Quiz-maker");
        title.setFont(Font.font("verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 18));
        HBox.setHgrow(title, Priority.ALWAYS);
        navbar.getChildren().add(title);

        // Add pane for correct formatting
        Pane formatterPane = new Pane();
        HBox.setHgrow(formatterPane, Priority.ALWAYS);
        navbar.getChildren().add(formatterPane);

        // Add all the buttons to be added:
        for (Button button : buttons) {
            // Add styling
            button.getStylesheets().add("/GUI/css/buttons.css");
            button.getStylesheets().add("/GUI/css/clickable-nodes.css");
            button.getStyleClass().add("navbar-button");
            button.getStyleClass().add("clickable-node-gray");

            // Add to navbar HBox
            navbar.getChildren().add(button);
        }

        // If a back page is specified, add a back button.
        if (backPage != null) {
            Button backButton = new Button();
            backButton.setText("Tilbake");

            // Make back button take you back
            backButton.setOnAction((ActionEvent e) -> {
                GUI.setSceneFromNode(backButton, backPage);
            });

            // Add styling
            backButton.getStylesheets().add("/GUI/css/buttons.css");
            backButton.getStylesheets().add("/GUI/css/clickable-nodes.css");
            backButton.getStyleClass().add("navbar-button");
            backButton.getStyleClass().add("clickable-node-gray");

            // Add back button to navbar
            navbar.getChildren().add(backButton);
        }

        // Style the navbar
        navbar.getStylesheets().add("/GUI/css/menu.css");
        navbar.getStyleClass().add("menu-bar");
        navbar.setAlignment(Pos.CENTER_LEFT);

        return navbar;
    }
}
