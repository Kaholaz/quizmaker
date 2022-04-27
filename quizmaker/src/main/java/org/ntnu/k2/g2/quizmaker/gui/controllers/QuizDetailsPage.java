package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.data.TeamModel;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingleton;
import org.ntnu.k2.g2.quizmaker.gui.decorators.ContainerDecorator;
import org.ntnu.k2.g2.quizmaker.gui.decorators.TextDecorator;
import org.ntnu.k2.g2.quizmaker.gui.factories.AlertFactory;
import org.ntnu.k2.g2.quizmaker.gui.factories.NavBarFactory;

import java.nio.file.FileSystems;
import java.util.Iterator;

/**
 * Controller class for the quizDetailsPage. It shows the details of each quiz,
 * and the ranking of the teams.
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

    @FXML
    private VBox quizContainer;

    @FXML // fx:id="difficulty"
    private Text activeStatus; // Value injected by FXMLLoader

    /**
     * Function called when the delete button has been pressed.
     * The quiz is deleted from the database.
     */
    @FXML
    void onDelete(ActionEvent event) {
        if (QuizRegister.removeQuiz(QuizHandlerSingleton.getQuiz())) {
            QuizHandlerSingleton.setQuiz(null);
            GUI.setSceneFromActionEvent(event, "/gui/listQuizzesPage.fxml");
        } else {
            AlertFactory.createNewErrorAlert("Kunne ikke slette quiz.").show();
        }
    }

    /**
     * Sets all text fields, buttons on the page, and rankingGrid, according to the quiz in the singleton.
     */
    void refreshDetails() {
        //update the details gridpane
        sumQuestions.setText(String.valueOf(QuizHandlerSingleton.getQuiz().getQuestions().size()));
        lastChanged.setText(QuizHandlerSingleton.getQuiz().getLastChanged().toLocalDate().toString());
        sumTeams.setText(String.valueOf(QuizHandlerSingleton.getQuiz().getTeams().size()));
        quizName.setText(QuizHandlerSingleton.getQuiz().getName());
        difficulty.setText(QuizHandlerSingleton.getDifficulty());

        if (QuizHandlerSingleton.isActive()) {
            activeStatus.setText("Aktiv");
            TextDecorator.makeTextGreen(activeStatus);
            ContainerDecorator.makeContainerActive(borderPane);
        } else {
            ContainerDecorator.makeContainerArchived(borderPane);
            activeStatus.setText("Inaktiv");
            TextDecorator.makeTextRed(activeStatus);
        }

        if (QuizHandlerSingleton.getQuiz().getDifficulty() == -1) {
            difficulty.setText("---");
        } else {
            average.setText(Double.toString(Math.round(QuizHandlerSingleton.getQuiz().getDifficulty()*100))+ "%");
        }

        // Refill the ranking table
        ranking.getItems().clear();
        Iterator<TeamModel> teamsSorted = QuizHandlerSingleton.getQuiz().getTeamsSortedByScore();
        while (teamsSorted.hasNext()) {
            ranking.getItems().add(teamsSorted.next());
        }
    }

    /**
     * Initializes the tableview. There are two columns, one for score and one for team name.
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
     * Initializes the page. This method is called after the FXML page is loaded.
     */
    @FXML
    void initialize() {
        HBox navbar = NavBarFactory.createTopNavigationBar("/gui/quizAdminPage.fxml");
        borderPane.setTop(navbar);

        refreshDetails();
        initTable();
    }
}
