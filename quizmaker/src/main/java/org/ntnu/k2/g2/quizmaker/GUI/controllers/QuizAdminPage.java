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
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.UserData.QuizResultManager;

public class QuizAdminPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

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

    private Quiz quiz = QuizHandlerSingelton.getQuiz();

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

        GUI.setSceneFromNode(back, "/GUI/listQuizzesPage.fxml");
    }

    @FXML
    void onBack(ActionEvent event) {
        //if check if is quiz is archived or active
        String path = "/GUI/listArchivedQuizzesPage.fxml";

        if (quiz.isActive()) {
            path = "/GUI/listQuizzesPage.fxml";
        }
        GUI.setSceneFromNode(back, path);
    }

    @FXML
    void onDetails(ActionEvent event) {
        QuizHandlerSingelton.setQuiz(quiz);
        GUI.setSceneFromNode(details, "/GUI/quizDetailsPage.fxml");
    }

    @FXML
    void onEditQuestion(ActionEvent event) {
        GUI.setSceneFromNode(editQuestions, "/GUI/questionEditorPage.fxml");
    }

    @FXML
    void onEditTeams(ActionEvent event) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(quiz.getUrl()));

            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
                errorMsg.setText("Could not load URL from quiz.");
            } catch (Exception e) {
                e.printStackTrace();
                errorMsg.setText("An unexpected error occurred");
            }
            retrieveScores.setStyle("-fx-background-color: yellow;");

        } else {
            errorMsg.setText("Could not load default browser.");

        }

    }

    /**
     * Opens new Stage and sets quiz by the controller on the page.
     *
     * @param event Action event
     */

    @FXML
    void onExportQA(ActionEvent event) {
        QuizHandlerSingelton.setQuiz(quiz);
        GUI.createNewStage(exportQA, "/GUI/exportPage.fxml");
    }

    /**
     * @param event
     * @throws IOException
     * @throws GeneralSecurityException
     */

    @FXML
    void onRetrieveScores(ActionEvent event) {
        try {
            QuizResultManager.importResults(quiz);
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg.setText("Could not retrieve scores.");
        }

        update();
        retrieveScores.setStyle("-fx-backgroud-color: lightblue;");
        errorMsg.setText("Import successfull");
    }

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
