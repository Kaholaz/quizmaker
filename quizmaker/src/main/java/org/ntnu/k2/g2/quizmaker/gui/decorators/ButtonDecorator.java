package org.ntnu.k2.g2.quizmaker.gui.decorators;

import javafx.scene.control.Button;

/**
 * Decorates buttons. If buttons are already instantiated this class can be used
 * to style them differently. The decorator uses mainly only css style classes. Swing methods
 * should not be used for a better consistency. All methods are static.
 */

public class ButtonDecorator {

    /**
     * No need for instantiation
     */

    private ButtonDecorator() {

    }

    /**
     * Converts a button to a navBavButton.
     *
     * @param button button that is being decorated
     */

    public static void makeNavBarButton(Button button) {
        button.getStylesheets().add("/gui/css/buttons.css");
        button.getStylesheets().add("/gui/css/clickable-nodes.css");
        button.getStyleClass().add("navbar-button");
        button.getStyleClass().add("clickable-node-gray");
    }

    /**
     * Decorates to a default delete button.
     *
     * @param button button that should be decorated
     */

    public static void makeDefaultDeleteButton(Button button) {
        button.getStyleClass().clear();
        button.getStyleClass().add("button-delete");
    }

    public static void makeDefaultGreenButton(Button button) {
        button.getStyleClass().clear();
        button.getStyleClass().add("button-green");
    }

    public static void makeArchived(Button button) {
        button.setStyle("-fx-background-color: #E3BFBF");
    }

    public static void makeActive(Button button) {
        button.setStyle("-fx-background-color: #E7E7E7");
    }

    public static void makeGray(Button button) {
        button.setStyle("-fx-background-color: lightgrey");
    }

    public static void makeBlue(Button button) {
        button.setStyle("-fx-background-color: lightblue");
    }
}
