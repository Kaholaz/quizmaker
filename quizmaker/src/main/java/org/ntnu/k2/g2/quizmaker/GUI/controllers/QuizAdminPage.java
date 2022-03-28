/**
 * Sample Skeleton for 'quizAdminPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.awt.Desktop;
import java.net.URI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.factory.ListPagesFactory;
import org.ntnu.k2.g2.quizmaker.UserData.QuizResultManager;
import org.ntnu.k2.g2.quizmaker.UserData.ResultSheet;

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

    @FXML
    private Text errorMsg;

    private Quiz quiz;

    @FXML
    void onChangeState(ActionEvent event) {
        QuizRegister quizRegister = new QuizRegister();
        quiz.setActive(!quiz.isActive());
        quizRegister.saveQuiz(quiz);

    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        //if check if is quiz is archived or active
        String path = "/GUI/listArchivedQuizzesPage.fxml";

        if (quiz.isActive()) {
            path = "/GUI/listActiveQuizzesPage.fxml";
        }
        GUI.setSceneFromNode(back, path);
    }

    @FXML
    void onDetails(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ListPagesFactory.class.getResource("/GUI/quizDetailsPage.fxml"));
        Parent root = GUI.checkFXMLLoader(loader);
        QuizDetailsPage quizDetailsPage = loader.getController();
        quizDetailsPage.setQuiz(quiz);
        Stage stage = (Stage) details.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void onEditQuestion(ActionEvent event) throws IOException {
        GUI.setSceneFromNode(editQuestions, "/GUI/questionEditorPage.fxml");
    }

    @FXML
    void onEditTeams(ActionEvent event) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("http://www.example.com"));

            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
                errorMsg.setText("Could not load URL from quiz.");
            }

        } else {
            errorMsg.setText("Could not load default browser.");
        }

        retrieveScores.setStyle("-fx-background-color: yellow;");
    }

    /**
     * Opens new Stage and sets quiz by the controller on the page.
     *
     * @param event Action event
     */

    @FXML
    void onExportQA(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ListPagesFactory.class.getResource("/GUI/exportPage.fxml"));
        Parent root = GUI.checkFXMLLoader(loader);
        ExportPage exportQAPage = loader.getController();
        exportQAPage.setQuiz(quiz);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    /**
     * @param event
     * @throws IOException
     * @throws GeneralSecurityException
     */

    @FXML
    void onRetrieveScores(ActionEvent event) {
        ResultSheet resultSheet = new ResultSheet();
        QuizResultManager quizResultManager = new QuizResultManager();

        try {
            quizResultManager.importResults(quiz);
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg.setText("Could not retrieve scores.");
        }

        retrieveScores.setStyle("-fx-background-color: lightblue;");
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
