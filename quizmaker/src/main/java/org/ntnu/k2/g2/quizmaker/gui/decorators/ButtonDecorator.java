package org.ntnu.k2.g2.quizmaker.gui.decorators;

import javafx.scene.control.Button;

/**
 * Decorates buttons. If buttons are already instantiated this class can be used
 * to style them differently. The decorator uses mainly only css style classes. Swing methods
 * should not be used for a better consistency. All methods are static.
 */
public class ButtonDecorator {

    /**
     * This is a static class and should not be initialized.
     */
    private ButtonDecorator() {}

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
     * Changes the style of the class to a default delete button.
     * This method clears the css-class of the button and adds the class 'button-delete'
     *
     * @param button button that should be decorated
     */
    public static void makeDefaultDeleteButton(Button button) {
        button.getStyleClass().clear();
        button.getStyleClass().add("button-delete");
    }

    /**
     * Changes the style of the class to a default green button.
     * This method clears the css-class of the button and adds the class 'button-green'
     *
     * @param button button that should be decorated
     */
    public static void makeDefaultGreenButton(Button button) {
        button.getStyleClass().clear();
        button.getStyleClass().add("button-green");
    }

    /**
     * Styles a quiz-button to the 'archived' style.
     * @param button The button to style.
     */
    public static void makeQuizButtonArchived(Button button) {
        button.setStyle("-fx-background-color: #E3BFBF");
    }

    /**
     * Styles a quiz-button to the 'active' style.
     * @param button The button to style.
     */
    public static void makeQuizButtonActive(Button button) {
        button.setStyle("-fx-background-color: #E7E7E7");
    }

    /**
     * Style a button to look disabled.
     * @param button The button to style.
     */
    public static void makeDisabled(Button button) {
        button.setStyle("-fx-background-color: lightgrey;-fx-text-fill: grey;-fx-border-color:grey;");
    }

    /**
     * Style a button blue.
     * @param button The button to style.
     */
    public static void makeBlue(Button button) {
        button.setStyle("-fx-background-color: lightblue");
    }
}
