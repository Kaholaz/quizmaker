/**
 * Sample Skeleton for 'quizDetailsPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.Data.Team;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.GUI.factory.GUIFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class QuizDetailsPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

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

    private final Quiz quiz = QuizHandlerSingelton.getQuiz();

    @FXML
    void onBack(ActionEvent event) throws IOException {
        GUI.setSceneFromNode(back, "/GUI/quizAdminPage.fxml");
    }

    @FXML
    void onDelete() {
        QuizRegister quizRegister = new QuizRegister();
        quizRegister.removeQuiz(quiz);
        QuizHandlerSingelton.clear();
        GUI.setSceneFromNode(delete, "/GUI/listQuizzesPage.fxml");
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

    void update() {
        sumQuestions.setText(String.valueOf(quiz.getQuestions().size()));
        lastChanged.setText(quiz.getLastChanged().toLocalDate().toString());
        sumTeams.setText(String.valueOf(quiz.getTeams().size()));
        quizName.setText(quiz.getName());

        Iterator<Team> teamsSorted = quiz.getTeamsSortedByScore();
        int i = 0;

        while (teamsSorted.hasNext()) {
            rankingGrid.addRow(i, GUIFactory.basicText(teamsSorted.next().getTeamName()));
            i++;
        }
    }


}
