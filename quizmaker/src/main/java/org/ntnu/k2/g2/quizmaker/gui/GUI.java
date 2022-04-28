package org.ntnu.k2.g2.quizmaker.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This is the main gui class of the application. The class
 * has also many helper methods that can be used in Controller classes.
 */
public class GUI extends Application {

    private static final int STAGE_MIN_HEIGHT = 450;
    private static final int STAGE_MIN_WIDTH = 600;

    /**
     * Main method of the gui. This method starts the GUI application and sends the user to the launch page.
     *
     * @param primaryStage primary window of the application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image(String.valueOf(GUI.class.getResource("/gui/media/quiz-logo-transparent.png"))));
        primaryStage.setTitle("QuizMaker");
        setSceneFromStage(primaryStage, "/gui/mainPage.fxml");
    }

    /**
     * Sets a scene by getting the current stage from a given nod and replacing the scene.
     * The path should be expressed relative to the 'resources' folder.
     *
     * @param node A nod that can extract the current stage.
     * @param path The path for the FXML sheet for the next scene.
     */
    public static void setSceneFromNode(Node node, String path) {
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();

        Scene prev = node.getScene();
        loader.setLocation(GUI.class.getResource(path));
        Parent root = GUI.checkedFXMLLoader(loader);
        Scene scene = new Scene(root, prev.getWidth(), prev.getHeight());


        stage.setScene(scene);
    }

    /**
     * Create a new stage (window).
     * The path should be expressed relative to the 'resources' folder.
     *
     * @param path The path for the FXML document for the next scene.
     */
    public static void createNewStage(String path) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUI.class.getResource(path));
        stage.setScene(new Scene(checkedFXMLLoader(loader)));
        stage.getIcons().add(new Image(String.valueOf(GUI.class.getResource("/gui/media/quiz-logo-transparent.png"))));
        stage.show();
    }

    /**
     * Change the scene of a given stage.
     * The path should be expressed relative to the 'resources' folder.
     *
     * @param stage The stage to swap the scene of.
     * @param path  The path of the FXML page of the new scene relative to the 'resources' folder.
     */
    public static void setSceneFromStage(Stage stage, String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUI.class.getResource(path));
        stage.setScene(new Scene(checkedFXMLLoader(loader)));
        stage.setMinHeight(STAGE_MIN_HEIGHT);
        stage.setMinWidth(STAGE_MIN_WIDTH);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * Set the scene of the stage by extracting the stage from an event.
     * This might be a better solution if there is no node to extract the stage from.
     * The path should be expressed relative to the 'resources' folder.
     *
     * @param actionEvent A javaFX event to extract the stage from.
     * @param path        The path of the FXML page of the new scene relative to the 'resources' folder.
     */
    public static void setSceneFromActionEvent(ActionEvent actionEvent, String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUI.class.getResource(path));
        Parent root = checkedFXMLLoader(loader);
        Scene prev = ((Node) actionEvent.getSource()).getScene();
        Stage stage = (Stage) prev.getWindow();
        stage.setScene(new Scene(root, prev.getWidth(), prev.getHeight()));
        stage.show();
    }

    /**
     * This is a helper method that checks the loader for exceptions and returns
     * the Parent if successful. This makes it easier to troubleshoot errors.
     *
     * @param loader the FXMLLoader that is being loaded.
     * @return The loaded Parent.
     */
    protected static Parent checkedFXMLLoader(FXMLLoader loader) {
        Parent root = null;

        try {
            root = loader.load();
        } catch (java.io.IOException e) {
            System.out.println("Could not load XML file... Check the controller class for " + e.getMessage());
            System.out.println("Stack Trace:");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("Most likely, you mistyped the fxml resource path that you tried to load.");
            System.out.println("Remember to add / in the beginning of the path and give the path relative to the resources folder.");
            System.out.println("\n" + e.getClass() + ": " + e.getMessage());
            System.out.println("Stack Trace:");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("A different, unexpected exception was thrown while loading the FXML file...\n\n" + e.getClass() + ": " + e.getMessage());
            System.out.println("Stack Trace:");
            e.printStackTrace();
        }

        // We don't want a NullPointerException down the line.
        if (root == null) {
            throw new IllegalStateException("Something went wrong during the loading of the FXML page");
        }
        return root;
    }
}
