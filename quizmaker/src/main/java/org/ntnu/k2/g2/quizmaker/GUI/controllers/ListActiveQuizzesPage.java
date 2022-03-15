package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.Data.Question;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.Data.QuizRegister;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListActiveQuizzesPage implements Initializable {
    @FXML
    public GridPane quizzesContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.updateQuizzes();
    }

    private void updateQuizzes() {
        QuizRegister quizRegister = new QuizRegister();
        quizRegister.populateDatabase(10, 5, 5);
        ArrayList<Quiz> quizzes = quizRegister.getQuizList();

        int index = 1;
        for (Quiz quiz : quizzes) {
            Text text = new Text();
            Button button = new Button("Admin");
            text.setText(quiz.getName());
            quizzesContainer.add(text, 0, index);
            quizzesContainer.add(button, 1, index);
            index++;
        }
    }
}
