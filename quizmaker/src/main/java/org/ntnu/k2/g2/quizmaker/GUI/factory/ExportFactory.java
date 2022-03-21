package org.ntnu.k2.g2.quizmaker.GUI.factory;

import javafx.scene.Group;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ExportFactory {
    public static RadioButton createRadioButton(ToggleGroup group, String string) {
        RadioButton rb = new RadioButton(string);
        rb.setToggleGroup(group);
        return rb;
    }
}
