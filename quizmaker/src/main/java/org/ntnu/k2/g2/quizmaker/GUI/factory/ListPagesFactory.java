package org.ntnu.k2.g2.quizmaker.GUI.factory;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.Data.Team;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.controllers.QuizAdminPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ListPagesFactory {
    public static HBox makeQuestion(Quiz quiz) {

        Text text = new Text(quiz.getName());

        Pane pane = new Pane();
        Button admin = new Button("Admin");

        admin.setId(String.valueOf(quiz.getId()));

        HBox hBox = new HBox();

        admin.setOnAction((ActionEvent e) -> {
            try {
                goToAdminPage(hBox, quiz);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        HBox.setHgrow(pane, Priority.ALWAYS);

        hBox.getChildren().addAll(text, pane, admin);

        return hBox;
    }

    public static HBox makeQuestionv2(Quiz quiz) {
        HBox hBox = new HBox();

        Button admin = new Button(quiz.getName());

        Scene scene = admin.getScene();

        admin.setId(String.valueOf(quiz.getId()));

        admin.setOnAction((ActionEvent e) -> {
            try {
                goToAdminPage(admin, quiz);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        admin.getStyleClass().add("listQuiz");


        hBox.getChildren().add(admin);

        return hBox;
    }

    public static void goToAdminPage(Node node, Quiz quiz) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(ListPagesFactory.class.getResource("/GUI/quizAdminPage.fxml")));
        Parent root = GUI.checkFXMLLoader(loader);
        QuizAdminPage quizAdminPage = loader.getController();
        quizAdminPage.setQuiz(quiz);
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.setScene(new Scene(root, scene.getWidth(), scene.getHeight()));
    }


    public static HBox makeEditPaneForTeams(Team team, Quiz quiz) {
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

            team.setScore(Integer.parseInt(textField.getText()));
            quizRegister.saveTeam(team);
        });

        hBox.setStyle("-fx-padding: 4px;");
        hBox.getChildren().addAll(teamName, textField, button);

        return hBox;
    }
}
