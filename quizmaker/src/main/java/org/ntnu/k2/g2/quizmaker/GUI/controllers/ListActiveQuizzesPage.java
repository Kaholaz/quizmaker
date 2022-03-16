
/**
 * Sample Skeleton for 'listActiveQuizzesPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;

public class ListActiveQuizzesPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="archive"
    private Button archive; // Value injected by FXMLLoader

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="quizzesContainer"
    private GridPane quizzesContainer; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPane"
    private ScrollPane scrollPane; // Value injected by FXMLLoader

    private int id;

    @FXML
    void onArchive(ActionEvent event) {

    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/GUI/mainPage.fxml")));
        Stage stage = (Stage) scrollPane.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert archive != null : "fx:id=\"archive\" was not injected: check your FXML file 'listActiveQuizzesPage.fxml'.";
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'listActiveQuizzesPage.fxml'.";
        assert quizzesContainer != null : "fx:id=\"quizzesContainer\" was not injected: check your FXML file 'listActiveQuizzesPage.fxml'.";
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'listActiveQuizzesPage.fxml'.";
        this.updateQuizzes();
    }


    void updateQuizzes() {
        QuizRegister quizRegister = new QuizRegister();
        ArrayList<Quiz> quizzes = quizRegister.getQuizList();

        int index = 1;

        for (Quiz quiz : quizzes) {
            Text text = new Text();
            Button button = new Button("Admin");
            button.setId(Integer.toString(quiz.getId()));
            button.setOnAction((ActionEvent e) -> {
                try {
                    this.goToAdminPage(quiz);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            });
            text.setText(quiz.getName());

            quizzesContainer.add(text, 0, index);
            quizzesContainer.add(button, 1, index);

            index++;
        }
    }

    @FXML
    void goToAdminPage(Quiz quiz) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/GUI/quizAdminPage.fxml")));
        Parent root = loader.load();
        QuizAdminPage quizAdminPage = loader.getController();
        quizAdminPage.setQuiz(quiz);
        Stage stage = (Stage) scrollPane.getScene().getWindow();
        stage.setScene(new Scene(root));
    }



}
