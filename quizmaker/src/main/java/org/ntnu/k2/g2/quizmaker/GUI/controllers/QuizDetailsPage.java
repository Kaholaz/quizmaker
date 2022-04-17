package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    private TableView<TeamModel> ranking; // Value injected by FXMLLoader

    @FXML // fx:id="sumQuestions"
    private Text sumQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="sumTeams"
    private Text sumTeams; // Value injected by FXMLLoader

    @FXML // fx:id="lastChange"
    private Text lastChanged; // Value injected by FXMLLoader

    @FXML
    private BorderPane borderPane;

    private final QuizModel quiz = QuizHandlerSingelton.getQuiz();


    /**
     * Function called when the delete button has been pressed.
     * The quiz is deleted from the database.
     */

    @FXML
    void onDelete(ActionEvent event) {
        if (QuizRegister.removeQuiz(quiz)) {
            QuizHandlerSingelton.clear();
            GUI.setSceneFromActionEvent(event, "/GUI/listQuizzesPage.fxml");
        } else {
            GUIFactory.createNewErrorAlert("Kunne ikke slette quiz.").show();
        }
    }

    /**
     * updates the quiz fields on the page, and the rankingGrid, according to the quiz in the singleton.
     */

    void update() {
        //update the details gridpane
        sumQuestions.setText(String.valueOf(quiz.getQuestions().size()));
        lastChanged.setText(quiz.getLastChanged().toLocalDate().toString());
        sumTeams.setText(String.valueOf(quiz.getTeams().size()));
        quizName.setText(quiz.getName());
        difficulty.setText(QuizHandlerSingelton.getDifficulty());
        if (quiz.getDifficulty() == -1) {
            difficulty.setText("---");
        } else {
            average.setText(Double.toString(Math.round(quiz.getDifficulty()*100))+ "%");
        }

        //update the team tableview
        Iterator<TeamModel> teamsSorted = quiz.getTeamsSortedByScore();
        int i = 0;

        while (teamsSorted.hasNext()) {
            ranking.getItems().add(teamsSorted.next());
        }
    }

    /**
     * Initializes the tableview. There are two columns, one for score and one for teamname.
     */

    void initTable() {
        TableColumn<TeamModel, String> name = new TableColumn<>("Navn");
        name.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        TableColumn<TeamModel, String> score = new TableColumn<>("Score");
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        ranking.getColumns().add(name);
        ranking.getColumns().add(score);
    }

    /**
     * Initializes the page
     */

    @FXML
    void initialize() {
        HBox navbar = GUIFactory.createNavBar("/GUI/quizAdminPage.fxml");
        borderPane.setTop(navbar);

        update();
        initTable();
    }
}
