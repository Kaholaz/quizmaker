package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.decorators.ButtonDecorator;

import static org.ntnu.k2.g2.quizmaker.gui.factories.TextFactory.createSmallText;

/**
 * Creates elements meant for navigation.
 */

public class NavBarFactory {

    /**
     *  Private constructor. No need for instantiation.
     */

    private NavBarFactory() {

    }

    /**
     * Creates a top navigation bar. The backPage is optional.
     * If a backpage is specified a back button will be created that redirects the user to
     * the page.
     *
     * @param backPage page that the user will be redirected to by the back button
     * @param buttons other buttons with different functionality
     * @return HBox that is the navbar
     */
     public static HBox createTopBar(String backPage, Button... buttons) {
        HBox navbar = new HBox();

        // Add logo / quiz-maker title
        Text title = createSmallText("Quiz-maker");
        title.setFont(Font.font("verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 18));
        navbar.getChildren().add(title);

        // Add pane for correct formatting
        Pane formatterPane = new Pane();
        formatterPane.setPrefWidth(0);
        HBox.setHgrow(formatterPane, Priority.ALWAYS);
        navbar.getChildren().add(formatterPane);

        // Add all the buttons to be added:
        for (Button button : buttons) {
            // Add to navbar HBox
            ButtonDecorator.makeNavBarButton(button);
            navbar.getChildren().add(button);
        }

        // If a back page is specified, add a back button.
        if (!backPage.isEmpty()) {
            Button backButton = ButtonFactory.createNavbarButton("Tilbake");

            // Make back button take you back
            backButton.setOnAction(event -> GUI.setSceneFromNode(backButton, backPage));

            // Add back button to navbar
            navbar.getChildren().add(backButton);
        }
        // Style the navbar
        navbar.getStylesheets().add("/gui/css/menu.css");
        navbar.getStyleClass().add("menu-bar");
        navbar.setAlignment(Pos.CENTER_LEFT);

        return navbar;
    }
}
