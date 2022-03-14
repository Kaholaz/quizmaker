package org.ntnu.k2.g2.quizmaker.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CardGame");

        URL url = new File("src/main/resources/GUI/test.fxml").toURI().toURL();

        Parent root = FXMLLoader.load(url);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
