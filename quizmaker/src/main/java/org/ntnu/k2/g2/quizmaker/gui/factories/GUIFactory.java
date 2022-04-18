package org.ntnu.k2.g2.quizmaker.gui.factories;

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
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;

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
        return new Alert(Alert.AlertType.ERROR, message,  ButtonType.CLOSE);
    }

    public static HBox createNavBar(String backPage, Button... buttons) {
        HBox navbar = new HBox();

        // Add logo / quiz-maker title
        Text title = basicText("Quiz-maker");
        title.setFont(Font.font("verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 18));
        navbar.getChildren().add(title);

        // Add pane for correct formatting
        Pane formatterPane = new Pane();
        formatterPane.setPrefWidth(0);
        HBox.setHgrow(formatterPane, Priority.ALWAYS);
        navbar.getChildren().add(formatterPane);

        // Add all the buttons to be added:
        for (Button button : buttons) {
            // Add styling
            button.getStylesheets().add("/gui/css/buttons.css");
            button.getStylesheets().add("/gui/css/clickable-nodes.css");
            button.getStyleClass().add("navbar-button");
            button.getStyleClass().add("clickable-node-gray");

            // Add to navbar HBox
            navbar.getChildren().add(button);
        }

        // If a back page is specified, add a back button.
        if (!backPage.isEmpty()) {
            Button backButton = new Button();
            backButton.setText("Tilbake");

            // Make back button take you back
            backButton.setOnAction(event -> GUI.setSceneFromNode(backButton, backPage));

            // Add styling
            backButton.getStylesheets().add("/gui/css/buttons.css");
            backButton.getStylesheets().add("/gui/css/clickable-nodes.css");
            backButton.getStyleClass().add("navbar-button");
            backButton.getStyleClass().add("clickable-node-gray");

            // Add back button to navbar
            navbar.getChildren().add(backButton);
        }

        // Style the navbar
        navbar.getStylesheets().add("/gui/css/menu.css");
        navbar.getStyleClass().add("menu-bar");
        navbar.setAlignment(Pos.CENTER_LEFT);

        return navbar;
    }


    public static TextField createNumberOnlyTextField(int integer) {
        TextField textField = new TextField(String.valueOf(integer));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            //regex values to remove letters
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        return  textField;
    }

    public static Button createClickableGrayButton(String string) {
        Button button = new Button(string);
        button.getStyleClass().add("clickable-node-gray");
        return button;
    }

    public static TextField createTextField(String string) {
        TextField textField = new TextField(string);
        textField.getStyleClass().add("text-field");
        return textField;
    }
}
