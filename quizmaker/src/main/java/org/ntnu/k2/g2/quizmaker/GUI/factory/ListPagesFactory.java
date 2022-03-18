package org.ntnu.k2.g2.quizmaker.GUI.factory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.GUI.controllers.QuizAdminPage;

import java.io.IOException;
import java.util.Objects;

public class ListPagesFactory {
    public static void makeQuestion(VBox vBox, Quiz quiz) {

            Text text = new Text(quiz.getName());

            Pane pane = new Pane();
            Button admin = new Button("Admin");

            admin.setId(String.valueOf(quiz.getId()));

            admin.setOnAction((ActionEvent e) -> {
                try {
                    goToAdminPage(vBox, quiz);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            });

            HBox hBox = new HBox();
            HBox.setHgrow(pane, Priority.ALWAYS);

            hBox.getChildren().addAll(text, pane, admin);
            vBox.getChildren().add(hBox);
    }


    public static void goToAdminPage(Node node, Quiz quiz) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(ListPagesFactory.class.getResource("/GUI/quizAdminPage.fxml")));
        Parent root = loader.load();
        QuizAdminPage quizAdminPage = loader.getController();
        quizAdminPage.setQuiz(quiz);
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
