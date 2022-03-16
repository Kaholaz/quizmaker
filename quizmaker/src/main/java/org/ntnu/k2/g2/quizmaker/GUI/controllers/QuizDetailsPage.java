/**
 * Sample Skeleton for 'quizDetailsPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.Team;

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

    private Quiz quiz;

    @FXML
    void onBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/GUI/quizAdminPage.fxml")));
        Parent root = loader.load();
        QuizAdminPage quizAdminPage = loader.getController();
        quizAdminPage.setQuiz(quiz);
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
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

    }

    void update() {
        sumQuestions.setText(String.valueOf(quiz.getQuestions().size()));
        lastChanged.setText(quiz.getLastChanged().toLocalDate().toString());
        sumTeams.setText(String.valueOf(quiz.getTeams().size()));

        AtomicInteger i = new AtomicInteger(1);

        quiz.getTeams().values().stream().sorted(Comparator.comparingInt(Team::getScore)).forEach(team -> {

            rankingGrid.addRow(i.get(), new Text(team.getTeamName()));
            i.getAndIncrement();
        });
    }

    void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        update();
    }
}
