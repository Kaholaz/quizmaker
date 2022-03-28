package org.ntnu.k2.g2.quizmaker.GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.ntnu.k2.g2.quizmaker.Data.Quiz;
import org.ntnu.k2.g2.quizmaker.GUI.controllers.ExportPage;
import org.ntnu.k2.g2.quizmaker.GUI.controllers.QuizAdminPage;
import org.ntnu.k2.g2.quizmaker.GUI.factory.GUIFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        setSceneFromStage(primaryStage, "/GUI/mainPage.fxml");
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.setResizable(false);
    }

    public static void setSceneFromNode(Node node, String path) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(createScene(node, path));
    }

    public static Scene createScene(Node node, String path) {
        FXMLLoader loader = new FXMLLoader();
        Scene prev = node.getScene();
        loader.setLocation(GUI.class.getResource(path));
        Parent root = GUI.checkFXMLLoader(loader);

        Scene scene = new Scene(root, prev.getWidth(), prev.getHeight());

        return scene;
    }

    public static void createNewStage(Node node, String path) {
        Stage stage = new Stage();
        stage.setScene(createScene(node, path));
        stage.show();
    }

    public static void setSceneFromStage(Stage stage, String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUI.class.getResource(path));
        Parent root = checkFXMLLoader(loader);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static Parent checkFXMLLoader(FXMLLoader loader) {
        Parent root = null;

        try {
            root = loader.load();
        } catch (java.io.IOException e) {
            System.out.println("Could not load XML file...\n\n" + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Most likely, you mistyped the fxml resource path that you tried to load.");
            System.out.println("Remember to add / in the beginning of the path and give the path relative to the resources folder.");
            System.out.println("\n" + e.getClass() + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println("A different, unexpected exception was thrown while loading the FXML file...\n\n" + e.getClass() + ": " + e.getMessage());
        }

        return root;
    }

}
