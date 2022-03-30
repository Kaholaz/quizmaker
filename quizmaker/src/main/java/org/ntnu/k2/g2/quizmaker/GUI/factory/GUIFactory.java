package org.ntnu.k2.g2.quizmaker.GUI.factory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.Data.Team;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;
import org.ntnu.k2.g2.quizmaker.GUI.controllers.QuizAdminPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GUIFactory {

    public static HBox listQuestionItem(Quiz quiz) {
        HBox hBox = new HBox();
        Button admin = new Button(quiz.getName());

        admin.setId(String.valueOf(quiz.getId()));

        admin.setOnAction((ActionEvent e) -> {
            QuizHandlerSingelton.setQuiz(quiz);
            GUI.setSceneFromNode(admin, "/GUI/quizAdminPage.fxml");
        });

        admin.getStyleClass().add("listQuiz");

        hBox.getChildren().add(admin);

        return hBox;
    }

    public static HBox makeEditPaneForTeams(Team team, Quiz quiz, Button save) {
        QuizRegister quizRegister = new QuizRegister();

        HBox hBox = new HBox();
        Text teamName = new Text(team.getTeamName());
        TextField textField = new TextField();
        Button button = new Button("Delete");

        button.setOnAction(actionEvent -> {
                    quizRegister.removeTeam(quiz, team.getId());
                    hBox.getChildren().clear();
                }
        );

        textField.setText(Integer.toString(team.getScore()));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        save.setOnAction(e -> team.setScore(Integer.parseInt(textField.getText())));

        hBox.setStyle("-fx-padding: 4px;");
        hBox.getChildren().addAll(teamName, textField, button);

        return hBox;
    }

    public static CheckBox createCheckBoxButton(String string, ArrayList<Boolean> checkBoxes, int i) {
        CheckBox checkBox = new CheckBox();
        checkBox.setText(string);
        return checkBox;
    }

    public static Text basicText(String string) {
        Text text = new Text();
        text.setText(string);
        text.setStyle("-fx-padding: 4px;");
        return text;
    }
}
