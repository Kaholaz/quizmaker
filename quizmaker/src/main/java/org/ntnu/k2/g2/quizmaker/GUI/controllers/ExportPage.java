package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.PdfExport.PdfManager;
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

    private final QuizModel quiz = QuizHandlerSingelton.getQuiz();

    /**
     * Checks the checkboxes. If they are selected it will import the selected option.
     */

    @FXML
    private void onExport() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) export.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        String msg = "Export successful";
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
                PdfManager.exportAnswersWithQuestions(quiz,file.toString());
                i++;
            }
            if (c6.isSelected()) {
                PdfManager.exportAnswersWithoutQuestions(quiz,file.toString());
                i++;
            }
        } catch (Exception e) {
            msg = "Export Unsuccessful" + e.getMessage();
        }
        if (i > 0) {
            exportMsg.setText(msg);
        } else {
            exportMsg.setText("Nothing was exported.");
        }
    }
}
