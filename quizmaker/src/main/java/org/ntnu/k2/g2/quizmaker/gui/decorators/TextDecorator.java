package org.ntnu.k2.g2.quizmaker.gui.decorators;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TextDecorator {
    private TextDecorator() {
    }

    public static void makeTextGreen(Text text) {
        text.setFill(Color.GREEN);
    }

    public static void makeTextRed(Text text) {
        text.setFill(Color.RED);
    }
}
