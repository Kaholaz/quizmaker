package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ResourceBundle;
import java.awt.Desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.UserData.QuizResultManager;

public class QuizAdminPage {
    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="details"
    private Button details; // Value injected by FXMLLoader

    @FXML // fx:id="difficulty"
    private Text difficulty; // Value injected by FXMLLoader

    @FXML // fx:id="editQuestions"
    private Button editQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="editTeams"
    private Button editTeams; // Value injected by FXMLLoader

    @FXML // fx:id="exportQA"
    private Button exportQA; // Value injected by FXMLLoader

    @FXML // fx:id="quizName"
    private Text quizName; // Value injected by FXMLLoader

    @FXML // fx:id="retrieveScores"
    private Button retrieveScores; // Value injected by FXMLLoader

    @FXML // fx:id="sumQuestions"
    private Text sumQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="sumTeams"
    private Text sumTeams; // Value injected by FXMLLoader

    @FXML
    private Button changeState;

    @FXML
    private Text errorMsg;

    private QuizModel quiz = QuizHandlerSingelton.getQuiz();

    /**
     * Changes between viewing active - and archived quizzes when the change state button
     * has been pressed.
     *
     * @param event - click event
     */
    @FXML
    void onChangeState(ActionEvent event) {
        QuizRegister quizRegister = new QuizRegister();

        try {
            quiz.setActive(!quiz.isActive());
            quizRegister.saveQuiz(quiz);
            QuizHandlerSingelton.setQuiz(quiz);
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg.setText("Could not change Quiz state");
        }

        GUI.setSceneFromActionEvent(event, "/GUI/listQuizzesPage.fxml");
    }

    /**
     * Redirects back to listView and checks the singleton for
     * whether the quiz is active or archived.
     */

    @FXML
    void onBack() {
        //if check if is quiz is archived or active
        String path = "/GUI/listArchivedQuizzesPage.fxml";

        if (quiz.isActive()) {
            path = "/GUI/listQuizzesPage.fxml";
        }

        GUI.setSceneFromNode(back, path);
    }

    /**
     * Opens  the details page
     */

    @FXML
    void onDetails() {
        GUI.setSceneFromNode(details, "/GUI/quizDetailsPage.fxml");
    }

    @FXML
    void onEditQuestion() {
        GUI.setSceneFromNode(editQuestions, "/GUI/questionEditorPage.fxml");
    }

    /**
     * Opens the default browser, and open up the URL to the google sheet
     * of the quiz
     */

    @FXML
    void onEditTeams() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(quiz.getUrl()));

            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
                errorMsg.setText("Could not load URL from quiz: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                errorMsg.setText("An unexpected error occurred: " + e.getMessage());
            }
            retrieveScores.setStyle("-fx-background-color: yellow;");

        } else {
            errorMsg.setText("Could not load default browser. Your desktop might not be supported.");
        }
    }

    /**
     * Opens new Stage and sets the scene to the exportPage.fxml
     */

    @FXML
    void onExportQA() {
        GUI.createNewStage("/GUI/exportPage.fxml");
    }

    /**
     * Retrieve scores from the Google API.
     * If the retrieve scores button has been pressed.
     */

    @FXML
    void onRetrieveScores() {
        try {
            QuizResultManager.importResults(quiz);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            errorMsg.setText("Could not retrieve scores: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg.setText("An unexpected error occurred.");
        }
        update();
        retrieveScores.setStyle("-fx-backgroud-color: lightblue;");
        errorMsg.setText("Import successful.");
    }

    /**
     * Updates information of the quiz fields.
     */

    void update() {
        quizName.setText(quiz.getName());
        sumQuestions.setText(String.valueOf(quiz.getQuestions().values().size()));
        sumTeams.setText(String.valueOf(quiz.getTeams().values().size()));
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert details != null : "fx:id=\"details\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert difficulty != null : "fx:id=\"difficulty\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert editQuestions != null : "fx:id=\"editQuestions\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert editTeams != null : "fx:id=\"editTeams\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert exportQA != null : "fx:id=\"exportQA\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert quizName != null : "fx:id=\"quizName\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert retrieveScores != null : "fx:id=\"retrieveScores\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert sumQuestions != null : "fx:id=\"sumQuestions\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert sumTeams != null : "fx:id=\"sumTeams\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        update();
    }
}
