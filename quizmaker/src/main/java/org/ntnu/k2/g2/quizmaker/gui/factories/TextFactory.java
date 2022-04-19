package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Creates nodes that display text. Can be editable.
 */

public class TextFactory {

    /**
     * Private constructor. No need for instantiation.
     */

    private TextFactory() {

    }

    /**
     * Create small text. Used to display basic things in the gui.
     *
     * @param string text
     * @return text element
     */

    public static Text createSmallText(String string) {
        Text text = new Text();
        text.setText(string);
        text.getStyleClass().add("text");
        return text;
    }

    /**
     * Creates a textfield. Element is editable.
     *
     * @param string text
     * @return text element
     */

    public static TextField createTextField(String string) {
        TextField textField = new TextField(string);
        textField.getStyleClass().add("text-field");
        return textField;
    }

    /**
     * Creates a number only textfield. Regex removes strings by and event listener.
     * Number is an integer.
     *
     * @param integer number to be displayed
     * @return integer only textfield
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
}
