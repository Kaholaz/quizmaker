/**
 * Sample Skeleton for 'exportPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class ExportPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="export"
    private Button export; // Value injected by FXMLLoader

    @FXML // fx:id="vBox"
    private VBox vBox; // Value injected by FXMLLoader

    @FXML
    private Text exportMsg;

    @FXML
    private CheckBox c1, c2, c3, c4, c5, c6;

    private Quiz quiz;

    @FXML
    private void onExport() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) export.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        String msg = "Export successful";
        //make with script?
        try {
            if (c1.isSelected()) {
                quiz.exportAnswersheetWithoutQuestions(file.toString());
            }
            if (c2.isSelected()) {
                quiz.exportAnswersheetWithQuestions(file.toString());
            }
            if (c3.isSelected()) {
                quiz.exportAnswersheetWithoutQuestions(file.toString());
            }
            if (c4.isSelected()) {
                quiz.exportAnswersheetWithoutQuestions(file.toString());
            }
            if (c5.isSelected()) {
                quiz.exportAnswersWithQuestions(file.toString());
            }
            if (c6.isSelected()) {
                quiz.exportAnswersWithoutQuestions(file.toString());
            }
        } catch (Exception e) {
            msg = "Export Unsuccessful";
        }
        exportMsg.setText(msg);
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert export != null : "fx:id=\"export\" was not injected: check your FXML file 'exportPage.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'exportPage.fxml'.";

    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
