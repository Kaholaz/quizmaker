package org.ntnu.k2.g2.quizmaker.gui.decorators;

import javafx.scene.Node;

public class ContainerDecorator {
    private ContainerDecorator() {

    }

    public static void makeContainerArchived(Node node) {
        node.setStyle("-fx-background-color: #E3BFBF");
    }
    public static void makeContainerActive(Node node) {
        node.setStyle("-fx-background-color: white;");
    }
}
