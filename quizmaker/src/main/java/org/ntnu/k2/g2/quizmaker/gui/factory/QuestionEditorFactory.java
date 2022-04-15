package org.ntnu.k2.g2.quizmaker.gui.factory;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuestionModel;

public class QuestionEditorFactory {
    /**
     * Creates a javafx Pane that can be used to show a Question in for example a list.
     * This constructor sets the question number to the question ID.
     *
     * @param question The question to create a Pane from.
     */
    public static Pane createQuestionPane(QuestionModel question) {
        Pane questionPane = new Pane();
        questionPane.setPrefHeight(150);

        // Create text labels that show where the question is and where the answer is
        Text title1 = new Text();
        Text title2 = new Text();
        title1.setText("Question " + question.getId() + ":");
        title2.setText("Answer:");

        // Set the text fields with the question and answer
        TextArea questionTextArea = new TextArea();
        TextArea answerTextArea = new TextArea();
        questionTextArea.setText(question.getQuestion());
        answerTextArea.setText(question.getAnswer());

        // Add the text labels to the pane
        questionPane.getChildren().addAll(title1, questionTextArea, title2, answerTextArea);

        // Format the labels correctly
        title1.setLayoutX(14);
        title2.setLayoutX(14);
        questionTextArea.setLayoutX(18);
        answerTextArea.setLayoutX(18);

        title1.setLayoutY(19);
        title2.setLayoutY(80);
        questionTextArea.setLayoutY(23);
        answerTextArea.setLayoutY(84);

        questionTextArea.setPrefWidth(455);
        questionTextArea.setPrefHeight(0);
        answerTextArea.setPrefWidth(455);
        answerTextArea.setPrefHeight(0);

        questionPane.setStyle("-fx-border-color: black");
        questionPane.setStyle("-fx-border-width: 0 1 1 1"); // Take away the top border to avoid double borders

        return questionPane;
    }

    /**
     * Creates a javafx Pane that can be used to show a Question in for example a list.
     * This constructor sets the question number to the given displayNumber.
     *
     * @param question The question to create a Pane from.
     */
    public static Pane createQuestionPane(QuestionModel question, int displayNumber) {
        Pane questionPane = new Pane();
        questionPane.setPrefHeight(150);

        // Create text labels that show where the question is and where the answer is
        Text title1 = new Text();
        Text title2 = new Text();
        title1.setText("Question " + displayNumber + ":");
        title2.setText("Answer:");

        // Set the text fields with the question and answer
        TextArea questionTextArea = new TextArea();
        TextArea answerTextArea = new TextArea();
        questionTextArea.setText(question.getQuestion());
        answerTextArea.setText(question.getAnswer());

        // Add the text labels to the pane
        questionPane.getChildren().addAll(title1, questionTextArea, title2, answerTextArea);

        // Format the labels correctly
        title1.setLayoutX(14);
        title2.setLayoutX(14);
        questionTextArea.setLayoutX(18);
        answerTextArea.setLayoutX(18);

        title1.setLayoutY(19);
        title2.setLayoutY(80);
        questionTextArea.setLayoutY(23);
        answerTextArea.setLayoutY(84);
        
        questionTextArea.setPrefWidth(455);
        questionTextArea.setPrefHeight(0);
        answerTextArea.setPrefWidth(455);
        answerTextArea.setPrefHeight(0);

        questionPane.setStyle("-fx-border-color: black");
        questionPane.setStyle("-fx-border-width: 0 1 1 1"); // Take away the top border to avoid double borders

        return questionPane;
    }
}
