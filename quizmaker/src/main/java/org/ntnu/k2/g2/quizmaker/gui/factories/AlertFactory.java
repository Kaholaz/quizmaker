package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * Makes alert messages. Can either be a popup or an element.
 * All creation methods are static.
 */

public class AlertFactory {

    /**
     *  Private constructor. No need for instantiation.
     */

    private AlertFactory() {

    }

    /**
     * Creates an error alert popup. Error can only ble closed.
     *
     * @param message message to be shown
     * @return alert popup
     */

    public static Alert createNewErrorAlert(String message) {
        return new Alert(Alert.AlertType.ERROR, message,  ButtonType.CLOSE);
    }

}
