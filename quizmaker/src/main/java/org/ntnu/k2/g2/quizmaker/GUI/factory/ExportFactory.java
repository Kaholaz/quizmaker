package org.ntnu.k2.g2.quizmaker.GUI.factory;

import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ExportFactory {
    public static CheckBox createCheckBoxButton(String string, ArrayList<Boolean> checkBoxes, int i) {
        CheckBox checkBox = new CheckBox();
        checkBox.setText(string);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                boolean val = checkBoxes.get(i);
                checkBoxes.set(i, !val);
            }
        };
        return checkBox;
    }
}
