/**
 * Sample Skeleton for 'quizCreatorPage.fxml' Controller Class
 */

package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;

public class QuestionEditorPage {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="archive"
    private Button save; // Value injected by FXMLLoader

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="vBox"
    private VBox vBox; // Value injected by FXMLLoader

    // This is like a "state". This property can be set to the quiz that should be displayed. The question editor
    // will show all questions from this quiz. The quiz state can be set whenever in whichever file and is implemented
    // so that you can choose which quiz to render before directing the user to the quiz creator page.
    // For example... If someone clicks the "Create New Quiz" button, a new Quiz should be
    // created, then this variable should be set to that quiz instance, and then finally the user can be redirected
    // to this page.
    public static Quiz quiz;

    // This is also a "state". This is the page that the back button will return to.
    // TODO: Implement checks when setting to this variable
    public static String returnPage = "/GUI/mainPage.fxml";

    @FXML
    void onSave(ActionEvent event) {

    }

    @FXML
    void onBack(ActionEvent event) {
        GUI.setSceneFromNode(save, returnPage);
    }

    @FXML
    void onBtnCreateNewQuestionClick(ActionEvent event) {
        GUI.setSceneFromNode(save, "/GUI/questionEditorPage.fxml");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'quizCreatorPage.fxml'.";
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'quizCreatorPage.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'quizCreatorPage.fxml'.";

    }

}
