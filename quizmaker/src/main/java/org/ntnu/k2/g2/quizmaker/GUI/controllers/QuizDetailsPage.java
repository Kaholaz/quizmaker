package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.GUI.factory.GUIFactory;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.data.TeamModel;

import java.util.Iterator;

/**
 * Controller class for the quizDetailsPage. It shows the details of each quiz,
 * and the ranking the teams.
 */

public class QuizDetailsPage {
    @FXML // fx:id="difficulty"
    private Text difficulty; // Value injected by FXMLLoader

    @FXML // fx:id="average"
    private Text average; // Value injected by FXMLLoader

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

    private final QuizModel quiz = QuizHandlerSingelton.getQuiz();

    /**
     * Function called when the back button has been pressed.
     * Goes back the quizAdminPage.
     */

    @FXML
    void onBack(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "/GUI/quizAdminPage.fxml");
    }

    /**
     * Function called when the delete button has been pressed.
     * The quiz is deleted from the database.
     */

    @FXML
    void onDelete(ActionEvent event) {
        QuizRegister quizRegister = new QuizRegister();
        if (quizRegister.removeQuiz(quiz)) {
            QuizHandlerSingelton.clear();
            GUI.setSceneFromActionEvent(event, "/GUI/listQuizzesPage.fxml");
        } else {
            //TODO: print error message here
        }
    }

    /**
     * updates the quiz fields on the page, and the rankingGrid.
     */

    void update() {
        sumQuestions.setText(String.valueOf(quiz.getQuestions().size()));
        lastChanged.setText(quiz.getLastChanged().toLocalDate().toString());
        sumTeams.setText(String.valueOf(quiz.getTeams().size()));
        quizName.setText(quiz.getName());
        difficulty.setText("Not implemented");
        average.setText("Not implemented");

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
        update();
    }
}
