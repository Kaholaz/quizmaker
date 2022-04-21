package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * This is a factory used to create nodes that display text.
 * These nodes can be text fields (editable) or labels (non-editable).
 */
public class TextFactory {

    /**
     * TextFactory is a static class so no constructor is needed.
     */
    private TextFactory() {

    }

    /**
     * Create small text. Used to display basic text elements in the GUI.
     *
     * @param string The text for the text element.
     * @return The constructed text element.
     */
    public static Text createSmallText(String string) {
        Text text = new Text();
        text.setText(string);
        text.getStyleClass().add("text");
        return text;
    }

    /**
     * Creates a basic text field. A text field excepts user input.
     *
     * @param string The pre-filled text field content.
     * @return The text field.
     */

    public static TextField createTextField(String string) {
        TextField textField = new TextField(string);
        textField.getStyleClass().add("text-field");
        return textField;
    }

    /**
     * Creates a text field that only accepts numbers (digits).
     *
     * @param integer The pre-filled content of the text field.
     * @return The integer only text field.
     */

    public static TextField createNumberOnlyTextField(int integer) {
        TextField textField = createTextField(String.valueOf(integer));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            //regex values to remove letters
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        return textField;
    }

    /**
     * Creates big header text.
     * @param s The string to create the text element of.
     * @return The text element with the supplied string.
     */
    public static Text createTitle(String s) {
        Text text = new Text(s);
        text.getStyleClass().clear();
        text.getStyleClass().add("quiz-title");
        return text;
    }
}
