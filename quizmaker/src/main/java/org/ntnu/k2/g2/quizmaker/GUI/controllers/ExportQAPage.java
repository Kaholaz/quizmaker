/**
 * Sample Skeleton for 'exportQAPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.GUI.factory.ExportFactory;

public class ExportQAPage {

    String[] strings = {"1", "2"};

    ToggleGroup group;

    @FXML // fx:id="export"
    private Button export; // Value injected by FXMLLoader

    @FXML // fx:id="vBox"
    private VBox vBox; // Value injected by FXMLLoader

    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();

        for (String string : strings) {
            RadioButton rb = ExportFactory.createRadioButton(group, string);
            vBox.getChildren().add(rb);
        }
    }

    @FXML
    private void onExport() {
        System.out.println(group.getSelectedToggle());

    }

}
