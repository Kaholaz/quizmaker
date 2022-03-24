
/**
 * Sample Skeleton for 'listActiveQuizzesPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.factory.ListPagesFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListActiveQuizzesPage {

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

    private int id;

    @FXML
    void onArchive(ActionEvent event) {
        GUI.setSceneFromNode(archive, "/GUI/listArchivedQuizzesPage.fxml");
    }

    @FXML
    void onBack(ActionEvent event) {
        GUI.setSceneFromNode(back, "/GUI/mainPage.fxml");
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert archive != null : "fx:id=\"archive\" was not injected: check your FXML file 'listActiveQuizzesPage.fxml'.";
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'listActiveQuizzesPage.fxml'.";
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'listActiveQuizzesPage.fxml'.";

        this.updateQuizzes();
    }


    void populateDatabase(QuizRegister quizRegister) {
        if (quizRegister.getQuizList().isEmpty()) {
            quizRegister.populateDatabase(20, 6, 16);
        }
    }

    void updateQuizzes() {
        QuizRegister quizRegister = new QuizRegister();
        populateDatabase(quizRegister);

        ArrayList<Quiz> quizzes = quizRegister.getActiveQuizzes();

        quizzes.forEach(quiz -> vBox.getChildren().add(ListPagesFactory.makeQuestionv2(quiz)));
    }
}
