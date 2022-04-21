package org.ntnu.k2.g2.quizmaker.gui.decorators;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * A decorator class to set the styling of text.
 */
public class TextDecorator {

    /**
     * This class is static and should not be instantiated.
     */
    private TextDecorator() {}

    /**
     * Makes text green.
     * @param text The text to change.
     */
    public static void makeTextGreen(Text text) {
        text.setFill(Color.GREEN);
    }

    /**
     * Makes text red.
     * @param text The text to change.
     */
    public static void makeTextRed(Text text) {
        text.setFill(Color.RED);
    }
}
