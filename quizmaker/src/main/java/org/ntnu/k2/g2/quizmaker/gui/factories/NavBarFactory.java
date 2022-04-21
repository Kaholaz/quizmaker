package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.decorators.ButtonDecorator;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import static org.ntnu.k2.g2.quizmaker.gui.factories.TextFactory.createSmallText;

/**
 * Creates elements meant for navigation.
 */

public class NavBarFactory {

    /**
     * Since the class is static, no constructor is needed.
     */
    private NavBarFactory() {}

    /**
    * Creates a top navigation bar. The backPage is optional.
    * If a backPage is specified (not null or empty) a back button will be created that redirects the user to
    * this page.
    *
    * @param backPage Page that the user will be redirected to by the back button.
    *                 This argument should be given as a path to an FXML document
    *                 relative to the 'resources' directory.
    * @param buttons Other buttons to add to the navigation bar.
    * @return HBox that is the navigation bar.
    */
    public static HBox createTopNavigationBar(String backPage, Button... buttons) {
        HBox navbar = new HBox();

        // Add logo.
        Image logo = new Image(String.valueOf(GUI.class.getResource("/gui/media/quiz-logo-full-transparent.png")));
        ImageView logoView = new ImageView();
        logoView.imageProperty().setValue(logo);
        logoView.setPreserveRatio(true);
        logoView.setSmooth(false);
        logoView.setFitHeight(50);
        navbar.getChildren().add(logoView);

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
        if (backPage != null && !backPage.isEmpty()) {
            Button backButton = ButtonFactory.createNavbarButton("Tilbake");
            ButtonDecorator.makeNavBarButton(backButton);

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
