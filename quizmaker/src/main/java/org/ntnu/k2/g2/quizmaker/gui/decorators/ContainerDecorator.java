package org.ntnu.k2.g2.quizmaker.gui.decorators;

import javafx.scene.Node;

/**
 * A decorator used to style containers.
 */
public class ContainerDecorator {

    /**
     * The class is static and should not be instansiated.
     */
    private ContainerDecorator() {
    }

    /**
     * Styles the background color for archived quizzes.
     *
     * @param node
     *            The gui element.
     */
    public static void makeContainerArchived(Node node) {
        node.setStyle("-fx-background-color: #E3BFBF");
    }

    /**
     * Styles the background color for active quizzes.
     *
     * @param node
     *            The gui element
     */
    public static void makeContainerActive(Node node) {
        node.setStyle("-fx-background-color: white;");
    }
}
