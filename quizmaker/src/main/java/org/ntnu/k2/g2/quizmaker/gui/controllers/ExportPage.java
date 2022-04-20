package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.gui.decorators.TextDecorator;
import org.ntnu.k2.g2.quizmaker.gui.factories.AlertFactory;
import org.ntnu.k2.g2.quizmaker.pdfexport.PdfManager;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;

import java.io.File;

/**
 * Controller for the exportQAPage. It handles what types of pdfs should be exported.
 */

public class ExportPage {
    @FXML // fx:id="export"
    private Button export; // Value injected by FXMLLoader

    @FXML
    private Text exportMsg;

    @FXML
    private CheckBox c1, c2, c3, c4, c5, c6;

    @FXML // fx:id="close"
    private Button close; // Value injected by FXMLLoader


    private final QuizModel quiz = QuizHandlerSingelton.getQuiz();

    /**
     * Closes the stage when the user is finished
     *
     * @param event triggering event from button
     */

    @FXML
    private void onClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Checks the checkboxes. If they are selected it will import the selected option.
     */

    @FXML
    private void onExport() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) export.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        String msg = "Eksportering vellykket";
        int i = 0;

        try {
            if (c1.isSelected()) {
                PdfManager.exportAnswersheetWithQuestionsQR(quiz,file.toString());
                i++;
            }
            if (c2.isSelected()) {
                PdfManager.exportAnswersheetWithQuestions(quiz,file.toString());
                i++;
            }
            if (c3.isSelected()) {
                PdfManager.exportAnswersheetWithoutQuestionsQR(quiz,file.toString());
                i++;
            }
            if (c4.isSelected()) {
                PdfManager.exportAnswersheetWithoutQuestions(quiz,file.toString());
                i++;
            }
            if (c5.isSelected()) {
                PdfManager.exportSolutionWithQuestions(quiz,file.toString());
                i++;
            }
            if (c6.isSelected()) {
                PdfManager.exportSolutionWithoutQuestions(quiz,file.toString());
                i++;
            }
        } catch (Exception e) {
            AlertFactory.createNewErrorAlert("En uventet feil oppstod: \n" + e.getMessage());
        }
        if (i > 0) {
            TextDecorator.makeTextGreen(exportMsg);
            exportMsg.setText(msg);
        } else {
            TextDecorator.makeTextRed(exportMsg);
            exportMsg.setText("Ingenting ble eksportert");
        }
        close.setText("Lukk");
    }
}
