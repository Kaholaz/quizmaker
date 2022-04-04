
/**
 * Sample Skeleton for 'listQuizzesPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.Data.QuizModel;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.GUI.factory.GUIFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListQuizzesPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="archive"
    private Button archive; // Value injected by FXMLLoader

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="quizzesContainer"
    private VBox vBox; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPane"
    private ScrollPane scrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="switchStatus"
    private Button switchStatus; // Value injected by FXMLLoader

    @FXML
    void onSwitchStatus(ActionEvent event) {
        QuizHandlerSingelton.setActive(!QuizHandlerSingelton.isActive());
        update();
    }

    @FXML
    void onBack(ActionEvent event) {
        QuizHandlerSingelton.clear();
        GUI.setSceneFromNode(back, "/GUI/mainPage.fxml");
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'listQuizzesPage.fxml'.";
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'listQuizzesPage.fxml'.";

        this.update();
    }

    void update() {
        QuizRegister quizRegister = new QuizRegister();
        ArrayList<QuizModel> quizzes;

        if (QuizHandlerSingelton.isActive()) {
            quizzes = quizRegister.getActiveQuizzes();
            switchStatus.setText("Archived");
        } else {
            switchStatus.setText("Active");
            quizzes = quizRegister.getArchivedQuizzes();
        }
        vBox.getChildren().clear();
        quizzes.forEach(quiz -> vBox.getChildren().add(GUIFactory.listQuestionItem(quiz)));

    }
}
