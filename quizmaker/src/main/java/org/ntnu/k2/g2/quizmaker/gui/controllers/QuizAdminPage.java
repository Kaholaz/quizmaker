package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.googlesheets.QuizResultManager;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.gui.decorators.ButtonDecorator;
import org.ntnu.k2.g2.quizmaker.gui.decorators.ContainerDecorator;
import org.ntnu.k2.g2.quizmaker.gui.decorators.TextDecorator;
import org.ntnu.k2.g2.quizmaker.gui.factories.AlertFactory;
import org.ntnu.k2.g2.quizmaker.gui.factories.NavBarFactory;

import java.io.IOException;
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

    @FXML // fx:id="changeState"
    private Button changeState; // Value injected by FXMLLoader

    @FXML // fx:id="sumQuestions"
    private Text sumQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="sumTeams"
    private Text sumTeams; // Value injected by FXMLLoader

    @FXML // fx:id="quizName"
    private Text activeStatus; // Value injected by FXMLLoader

    @FXML
    private Text errorMsg;

    @FXML
    private Text difficulty;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox quizContainer;

    private final QuizModel quiz = QuizHandlerSingelton.getQuiz();

    /**
     * Changes between viewing active - and archived quizzes when the change state button
     * has been pressed.
     */

    @FXML
    void onChangeState() {
        try {
            quiz.setActive(!quiz.isActive());
            QuizRegister.saveQuiz(quiz);
            String msg = "Quizzen er nå aktiv";
            if (!quiz.isActive()) {
                msg = "Quizzen er nå Inaktiv";
            }
            errorMsg.setText(msg);
            ContainerDecorator.makeContainerArchived(borderPane);
        } catch (Exception e) {
            e.printStackTrace();
            AlertFactory.createNewErrorAlert("En uventet feil oppstod: " + e.getMessage()).show();
        }
        update();
    }

    /**
     * Redirects to the quizDetailspage
     */

    @FXML
    void onDetails(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "/gui/quizDetailsPage.fxml");
    }

    @FXML
    void onEditQuestion(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "/gui/questionEditorPage.fxml");
    }

    /**
     * Opens the default browser, with the URL to the Google sheet
     * of the quiz. It checks the os and uses the appropriate system commands to open
     * a browser.
     */

    @FXML
    void onEditTeams() {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        String url = quiz.getUrl();
        String command = null;

        if (os.contains("win")) {
            command = "rundll32 url.dll,FileProtocolHandler " + url;
        }

        if (os.contains("nix") || os.contains("nux")) {
            String[] browsers = {"google-chrome", "firefox", "mozilla", "opera"};

            StringBuilder cmd = new StringBuilder();
            for (int i = 0; i < browsers.length; i++)
                if (i == 0)
                    cmd.append(String.format("%s \"%s\"", browsers[i], url));
                else
                    cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
            try {
                rt.exec(new String[]{"sh", "-c", cmd.toString()});
            } catch (IOException e) {
                AlertFactory.createNewErrorAlert("Kunne ikke åpne nettleser: " + e.getMessage()).show();
            }
        }

        if (os.contains("mac")) {
            command = "open " + url;
        }

        if (command != null) {
            try {
                rt.exec(command);
            } catch (IOException e) {
                AlertFactory.createNewErrorAlert("Kunne ikke åpne netleser: " + e.getMessage()).show();
            }
        }
    }

    /**
     * Opens new Stage and sets the scene to the exportPage.fxml
     */

    @FXML
    void onExportQA() {
        GUI.createNewStage("/gui/exportPage.fxml");
    }

    /**
     * Retrieve scores from the Google API.
     * If the retrieve scores button has been pressed.
     */

    @FXML
    void onRetrieveScores() {
        if (!quiz.isActive()) {
            AlertFactory.createNewWarningAlert("Kan ikke importere fra inaktiv quiz").show();
            return;
        }
        try {
            QuizResultManager.importResults(quiz);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            AlertFactory.createNewErrorAlert("Kunne ikke hente data: \n" + e.getMessage()).show();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            AlertFactory.createNewErrorAlert("En uventet feil oppstod:\n " + e.getMessage()).show();
            return;
        }
        update();

        retrieveScores.setStyle("-fx-backgroud-color: lightblue;");
        errorMsg.setText("Importering vellykket");
    }

    /**
     * Updates the GUI elements according to the quiz.
     */

    void update() {
        //change the details info
        quizName.setText(quiz.getName());
        sumQuestions.setText(String.valueOf(quiz.getQuestions().values().size()));
        sumTeams.setText(String.valueOf(quiz.getTeams().values().size()));
        difficulty.setText(QuizHandlerSingelton.getDifficulty());

        if (quiz.isActive()) {
            activeStatus.setText("Aktiv");
            ButtonDecorator.makeBlue(retrieveScores);
            TextDecorator.makeTextGreen(activeStatus);
        } else {
            activeStatus.setText("Inaktiv");
            ButtonDecorator.makeGray(retrieveScores);
            TextDecorator.makeTextRed(activeStatus);

        }

        //change the active status button text
        if (quiz.isActive()) {
            ContainerDecorator.makeContainerActive(borderPane);
            changeState.setText("Arkiver quiz");
        } else {
            ContainerDecorator.makeContainerArchived(borderPane);
            changeState.setText("Åpne quiz");
        }
    }

    /**
     * Initializes the page. It creates a topbar and updates the gui elements to the quiz in the singleton.
     */
    @FXML
    void initialize() {
        HBox navbar = NavBarFactory.createTopBar("/gui/listQuizzesPage.fxml");
        borderPane.setTop(navbar);
        update();
    }
}
