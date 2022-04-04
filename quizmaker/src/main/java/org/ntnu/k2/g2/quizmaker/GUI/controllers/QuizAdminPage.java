package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.UserData.QuizResultManager;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

/**
 * Controller class for the AdminPage. This page is an interface for editing and
 * manipulating questions.
 */

public class QuizAdminPage {
    @FXML // fx:id="quizName"
    private Text quizName; // Value injected by FXMLLoader

    @FXML // fx:id="retrieveScores"
    private Button retrieveScores; // Value injected by FXMLLoader

    @FXML // fx:id="sumQuestions"
    private Text sumQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="sumTeams"
    private Text sumTeams; // Value injected by FXMLLoader

    @FXML
    private Text errorMsg;

    private final QuizModel quiz = QuizHandlerSingelton.getQuiz();

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
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg.setText("En uventet feil oppstod: " + e.getMessage());
        }

        GUI.setSceneFromActionEvent(event, "/GUI/listQuizzesPage.fxml");
    }

    /**
     * Redirects back to listView and checks the singleton for
     * whether the quiz is active or archived.
     */

    @FXML
    void onBack(ActionEvent event) {
        String path = "/GUI/listArchivedQuizzesPage.fxml";

        if (quiz.isActive()) {
            path = "/GUI/listQuizzesPage.fxml";
        }

        GUI.setSceneFromActionEvent(event, path);
    }

    /**
     * Opens  the details page
     */

    @FXML
    void onDetails(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "/GUI/quizDetailsPage.fxml");
    }

    @FXML
    void onEditQuestion(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "/GUI/questionEditorPage.fxml");
    }

    /**
     * Opens the default browser, with the URL to the Google sheet
     * of the quiz.
     */

    @FXML
    void onEditTeams() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(quiz.getUrl()));

            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
                errorMsg.setText("Kunne ikke åpne url: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                errorMsg.setText("Det oppstod en uventet feil: " + e.getMessage());
            }
            retrieveScores.setStyle("-fx-background-color: yellow;");

        } else {
            errorMsg.setText("Kunne ikke åpne standardnettleser. Din maskin er kanskje ikke støttet.");
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
        String msg = "Importering vellykket";
        try {
            QuizResultManager.importResults(quiz);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            msg = ("Kunne ikke hente data: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            msg = ("Det oppstod en uventet feil.");
        }
        update();
        retrieveScores.setStyle("-fx-backgroud-color: lightblue;");
        errorMsg.setText(msg);
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
        update();
    }
}
