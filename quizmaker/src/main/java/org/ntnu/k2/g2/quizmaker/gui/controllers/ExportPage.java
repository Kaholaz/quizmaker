package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingleton;
import org.ntnu.k2.g2.quizmaker.gui.decorators.TextDecorator;
import org.ntnu.k2.g2.quizmaker.gui.factories.AlertFactory;
import org.ntnu.k2.g2.quizmaker.pdfexport.PdfManager;

import java.io.File;
import java.io.IOException;

/**
 * Controller for the exportQAPage. It handles what types of pdfs should be exported.
 */

public class ExportPage {
    @FXML // fx:id="export"
    private Button export; // Value injected by FXMLLoader

    @FXML
    private Text exportMsg;

    @FXML
    private CheckBox c1, c2, c3;

    @FXML // fx:id="close"
    private Button close; // Value injected by FXMLLoader

    /**
     * An event listener for when the user presses the close/cancel button.
     * This causes the export window to close.
     *
     * @param event Triggering event from the button press.
     */
    @FXML
    private void onClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Creates pdfs based on the checkboxes checked.
     * Sets the appropriate messages after a successful export, or alerts the user if the export was unsuccessful.
     */
    @FXML
    private void onExport() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) export.getScene().getWindow();
        File exportPath = directoryChooser.showDialog(stage);
        boolean exported = false;

        try {
            if (c1.isSelected()) {
                PdfManager.exportAnswerSheetWithQuestions(QuizHandlerSingleton.getQuiz(), exportPath.toString());
                exported = true;
            }
            if (c2.isSelected()) {
                PdfManager.exportAnswerSheetWithoutQuestions(QuizHandlerSingleton.getQuiz(), exportPath.toString());
                exported = true;
            }
            if (c3.isSelected()) {
                PdfManager.exportAnswerKey(QuizHandlerSingleton.getQuiz(), exportPath.toString());
                exported = true;
            }
        } catch (IOException e) {
            AlertFactory.createNewErrorAlert("Kunne ikke skrive til fil.\n" + e.getMessage()).show();
            exported = false;
        } catch (NullPointerException e) {
            TextDecorator.makeTextRed(exportMsg);
            exportMsg.setText("Ingen mappe ble valgt");
            return;
        }

        // Set the appropriate export msg based on if anything was exported.
        if (exported) {
            TextDecorator.makeTextGreen(exportMsg);
            exportMsg.setText("Eksportering vellykket");
        } else {
            TextDecorator.makeTextRed(exportMsg);
            exportMsg.setText("Ingenting ble eksportert");
        }

        // Changes the text on the cancel button to 'close'
        close.setText("Lukk");
    }
}
