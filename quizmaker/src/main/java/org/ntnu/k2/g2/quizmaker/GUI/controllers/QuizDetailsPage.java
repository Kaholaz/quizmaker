/**
 * Sample Skeleton for 'quizDetailsPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.TeamModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.GUI.factory.GUIFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class QuizDetailsPage {
    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="difficulty"
    private Text difficulty; // Value injected by FXMLLoader

    @FXML // fx:id="quizName"
    private Text quizName; // Value injected by FXMLLoader

    @FXML // fx:id="rankingGrid"
    private GridPane rankingGrid; // Value injected by FXMLLoader

    @FXML // fx:id="sumQuestions"
    private Text sumQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="sumTeams"
    private Text sumTeams; // Value injected by FXMLLoader

    @FXML // fx:id="lastChange"
    private Text lastChanged; // Value injected by FXMLLoader

    @FXML // fx:id="delete"
    private Button delete; // Value injected by FXMLLoader

    private final QuizModel quiz = QuizHandlerSingelton.getQuiz();

    /**
     * Function called when the back button has been pressed.
     * Goes back the quizAdminPage.
     */

    @FXML
    void onBack() {
        GUI.setSceneFromNode(back, "/GUI/quizAdminPage.fxml");
    }

    /**
     * Function called when the delete button has been pressed.
     * The quiz is deleted from the database.
     */

    @FXML
    void onDelete() {
        QuizRegister quizRegister = new QuizRegister();
        if (quizRegister.removeQuiz(quiz)) {
            QuizHandlerSingelton.clear();
            GUI.setSceneFromNode(delete, "/GUI/listQuizzesPage.fxml");
        } else {
            //print error message here
        }
    }

    /**
     * updates the quiz fields on the page.
     */

    void update() {
        sumQuestions.setText(String.valueOf(quiz.getQuestions().size()));
        lastChanged.setText(quiz.getLastChanged().toLocalDate().toString());
        sumTeams.setText(String.valueOf(quiz.getTeams().size()));
        quizName.setText(quiz.getName());

        Iterator<TeamModel> teamsSorted = quiz.getTeamsSortedByScore();
        int i = 0;

        rankingGrid.getChildren().clear();

        while (teamsSorted.hasNext()) {
            rankingGrid.addRow(i, GUIFactory.basicText(teamsSorted.next().getTeamName()));
            i++;
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'quizDetailsPage.fxml'.";
        assert difficulty != null : "fx:id=\"difficulty\" was not injected: check your FXML file 'quizDetailsPage.fxml'.";
        assert quizName != null : "fx:id=\"quizName\" was not injected: check your FXML file 'quizDetailsPage.fxml'.";
        assert rankingGrid != null : "fx:id=\"rankingGrid\" was not injected: check your FXML file 'quizDetailsPage.fxml'.";
        assert sumQuestions != null : "fx:id=\"sumQuestions\" was not injected: check your FXML file 'quizDetailsPage.fxml'.";
        assert sumTeams != null : "fx:id=\"sumTeams\" was not injected: check your FXML file 'quizDetailsPage.fxml'.";
        update();
    }
}
