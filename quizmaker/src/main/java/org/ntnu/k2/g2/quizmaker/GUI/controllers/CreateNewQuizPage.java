package org.ntnu.k2.g2.quizmaker.GUI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class BrowseController implements Initializable {

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public GridPane quizzesContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.updateQuizzes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateQuizzes() throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            strings.add("joie");
            strings.add("hei");
        }

        int index = 0;
        for (String string : strings) {
            Text text = new Text();
            text.setText(string);
            quizzesContainer.add(text, 0, index);
            index++;
        }
    }


}