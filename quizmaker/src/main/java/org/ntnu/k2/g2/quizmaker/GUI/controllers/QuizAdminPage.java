/**
 * Sample Skeleton for 'quizAdminPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.factory.ListPagesFactory;

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

    @FXML // fx:id="editScores"
    private Button editScores; // Value injected by FXMLLoader

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

    private Quiz quiz;

    @FXML
    void editScores(ActionEvent event) {

    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        //if check if is quiz is archived or active
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/GUI/listActiveQuizzesPage.fxml")));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void onDetails(ActionEvent event) throws IOException {
        details.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ListPagesFactory.class.getResource("/GUI/quizDetailsPage.fxml"));
            Parent root = GUI.checkFXMLLoader(loader);
            QuizDetailsPage quizDetailsPage = loader.getController();
            quizDetailsPage.setQuiz(quiz);
            Stage stage = (Stage) details.getScene().getWindow();
            stage.setScene(new Scene(root));
        });
    }

    @FXML
    void onEditQuestion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/GUI/questionEditorPage.fxml")));
        Stage stage = (Stage) editQuestions.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void onEditTeams(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/GUI/teamEditorPage.fxml")));
        Stage stage = (Stage) details.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void onExportQA(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/exportQAPage.fxml"));
        Parent root = GUI.checkFXMLLoader(loader);
        ExportQAPage exportQAPage = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void onRetrieveScores(ActionEvent event) throws IOException {
        //Not yet implemented
    }

    void update() {
        quizName.setText(quiz.getName());
        sumQuestions.setText(String.valueOf(quiz.getQuestions().values().size()));
        sumTeams.setText(String.valueOf(quiz.getTeams().values().size()));

    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        update();
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert details != null : "fx:id=\"details\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert difficulty != null : "fx:id=\"difficulty\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert editQuestions != null : "fx:id=\"editQuestions\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert editScores != null : "fx:id=\"editScores\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert editTeams != null : "fx:id=\"editTeams\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert exportQA != null : "fx:id=\"exportQA\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert quizName != null : "fx:id=\"quizName\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert retrieveScores != null : "fx:id=\"retrieveScores\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert sumQuestions != null : "fx:id=\"sumQuestions\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
        assert sumTeams != null : "fx:id=\"sumTeams\" was not injected: check your FXML file 'quizAdminPage.fxml'.";
    }

}
