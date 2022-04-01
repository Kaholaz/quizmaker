package org.ntnu.k2.g2.quizmaker.GUI.factory;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.data.TeamModel;
import org.ntnu.k2.g2.quizmaker.GUI.GUI;
import org.ntnu.k2.g2.quizmaker.GUI.QuizHandlerSingelton;

import java.util.ArrayList;

public class GUIFactory {

    public static HBox listQuestionItem(QuizModel quiz) {
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

    public static HBox makeEditPaneForTeams(TeamModel team, QuizModel quiz) {
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
            //expensive to do sql requests on every keytype
            quizRegister.saveTeam(team);
        });

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
