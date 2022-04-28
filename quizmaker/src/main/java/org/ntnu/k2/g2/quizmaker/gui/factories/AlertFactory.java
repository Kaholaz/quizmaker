package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.*;

/**
 * Makes alert messages. Can either be a popup or an element. All creation methods are static.
 */
public class AlertFactory {

    /**
     * Since this class is static, a constructor is not needed.
     */
    private AlertFactory() {
    }

    /**
     * Creates an error alert popup. Error can only ble closed.
     *
     * @param message
     *            The message to show on the alert.
     *
     * @return An alert popup.
     */
    public static Alert createNewErrorAlert(String message) {
        return new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
    }

    /**
     * Creates a warning alert popup. Warnings can only ble closed.
     *
     * @param message
     *            The message to show on the alert.
     *
     * @return A warning pop-up.
     */
    public static Alert createNewWarningAlert(String message) {
        return new Alert(Alert.AlertType.WARNING, message, ButtonType.CLOSE);
    }

    /**
     * Show a JOption warning. This is to be used if something wrong happens before or during the initialization of the
     * GUI
     *
     * @param string
     *            The string to show on the warning.
     */
    public static void showJOptionWarning(String string) {
        JOptionPane.showMessageDialog(null, string, "Hey!", JOptionPane.ERROR_MESSAGE);
    }

}
