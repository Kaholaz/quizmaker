package org.ntnu.k2.g2.quizmaker.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Camera;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.googlesheets.QuizResultManager;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingleton;
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

    @FXML
    private Button editQuestions;

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

    private final QuizModel quiz = QuizHandlerSingleton.getQuiz();

    /**
     * Provides user-feedback when the quiz status is changed.
     */
    @FXML
    void onChangeState() {
        try {
            quiz.setActive(!quiz.isActive());
            QuizRegister.saveQuiz(quiz);
            String msg = quiz.isActive() ? "Quizzen er nå aktiv" : "Quizzen er nå Inaktiv";
            errorMsg.setText(msg);
            ContainerDecorator.makeContainerArchived(borderPane);
        } catch (Exception e) {
            e.printStackTrace();
            AlertFactory.createNewErrorAlert("En uventet feil oppstod: " + e.getMessage()).show();
        }
        update();
    }

    /**
     * Redirects to the quizDetailspage when the details button is pressed.
     *
     * @param event The action event when the button is pressed.
     */
    @FXML
    void onDetails(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "/gui/quizDetailsPage.fxml");
    }

    /**
     * Redirects to the questionEditorPage when the edit questions button is pressed.
     * @param event The action event when the button is pressed.
     */
    @FXML
    void onEditQuestion(ActionEvent event) {
        if (!quiz.isActive()) {
            AlertFactory.createNewWarningAlert("Kan ikke endre spørsmålene til en arkivert quiz.").show();
            return;
        }
        GUI.setSceneFromActionEvent(event, "/gui/questionEditorPage.fxml");
    }

    /**
     * Opens URL to the Google sheet of the quiz in the default web-browser.
     * This is done by checking the os and calling the appropriate shell command to open a web-site.
     */
    @FXML
    void onEditTeams() {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        String url = quiz.getUrl();
        String command = null;

        // The sheet was not instantiated. And we will try to create a new one.
        if (quiz.getSheetId() == null) {
            try {
                QuizResultManager.createResultSheet(quiz);
                url = quiz.getUrl();
            } catch (IOException | GeneralSecurityException | NullPointerException e) {
                AlertFactory.createNewWarningAlert("Det er ble ikke laget et regneark for denne quizzen!\nPrøv igjen senere når du er koblet til internettet.").show();
                return;
            }
        }

        // Windows
        if (os.contains("win")) {
            command = "rundll32 url.dll,FileProtocolHandler " + url;
        }

        // Unix / Linux
        else if (os.contains("nix") || os.contains("nux")) {
            command = "xdg-open " + url;
        }

        // MacOS
        if (os.contains("mac")) {
            command = "open " + url;
        }

        if (command != null) {
            try {
                rt.exec(command);
            } catch (IOException e) {
                AlertFactory.createNewErrorAlert("Kunne ikke åpne netleser: " + e.getMessage()).show();
            }
        } else {
            AlertFactory.createNewErrorAlert("Operativsystemet ditt er ikke støttet for denne operasjonen!").show();
        }
    }

    /**
     * Opens new window and sets its scene to exportPage.fxml
     * This method is called when the export quiz button is pressed.
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
        if (quiz.getSheetId() == null) {
            AlertFactory.createNewErrorAlert("Poeng-regnearket eksisterer ikke.\nPrøv igjen senere når du har tilgang til internett.");
            return;
        }

        try {
            QuizResultManager.importResults(quiz);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            AlertFactory.createNewErrorAlert("Kunne ikke hente data: \n" + e.getMessage()).show();
            return;
        } catch (NullPointerException e) {
            AlertFactory.createNewErrorAlert("Kunne ikke hente data: \nRegnearket er tomt").show();
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
     * Updates the GUI elements according to the current quiz.
     */
    void update() {
        //change the details info
        quizName.setText(quiz.getName());
        sumQuestions.setText(String.valueOf(quiz.getQuestions().values().size()));
        sumTeams.setText(String.valueOf(quiz.getTeams().values().size()));
        difficulty.setText(QuizHandlerSingleton.getDifficulty());

        if (quiz.isActive()) {
            activeStatus.setText("Aktiv");
            ButtonDecorator.makeBlue(editQuestions);
            ButtonDecorator.makeBlue(retrieveScores);
            TextDecorator.makeTextGreen(activeStatus);
        } else {
            activeStatus.setText("Inaktiv");
            ButtonDecorator.makeFullWidthListElementDisabled(editQuestions);
            ButtonDecorator.makeFullWidthListElementDisabled(retrieveScores);
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
     * Initializes the page. It creates a navigation bar and updates the gui elements according to the quiz in the singleton.
     */
    @FXML
    void initialize() {
        HBox navbar = NavBarFactory.createTopNavigationBar("/gui/listQuizzesPage.fxml");
        borderPane.setTop(navbar);
        update();

        // No sheet. Try to instantiate a new one.
        if (quiz.getSheetId() == null) {
            try {
                QuizResultManager.createResultSheet(quiz);
            }
            catch (IOException | GeneralSecurityException | NullPointerException e) {
                AlertFactory.showJOptionWarning("Det ble ikke laget et poeng-regneark til denne quizzen.\nPrøv igjen senere når du har tilgang til internett.");
            }
        }
    }
}
