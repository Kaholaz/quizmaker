package org.ntnu.k2.g2.quizmaker.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class GUI extends Application {
    public Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        primaryStage.setTitle("CardGame");

        FXMLLoader loader = new FXMLLoader();
        URL mainPageURL = getClass().getResource("/GUI/mainPage.fxml");
        loader.setLocation(mainPageURL);

        // Load the fxml resource
        Parent root;
        try {
            root = loader.load();
        } catch (java.io.IOException e) {
            System.out.println("Could not load XML file...\n\n" + e.getMessage());
            return;
        } catch (IllegalStateException e) {
            System.out.println("Most likely, you mistyped the fxml resource path that you tried to load.");
            System.out.println("Remember to add / in the beginning of the path and give the path relative to the resources folder.");
            System.out.println("\n" + e.getClass() + ": " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("A different, unexpected exception was thrown while loading the FXML file...\n\n" + e.getClass() + ": " + e.getMessage());
            return;
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
