package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuestionModel;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingelton;

public class QuestionEditorFactory {
    /**
     * Creates a javafx Pane that can be used to show a Question in for example a list.
     * This constructor sets the question number to the question ID.
     *
     * @param question The question to create a Pane from.
     */
    public static Pane createQuestionPane(QuestionModel question, int questionNumber) {
        VBox mainContainer = new VBox();
        HBox buttonContainer = new HBox();
        QuizModel quiz = QuizHandlerSingelton.getQuiz();

        Button deletebtn = GUIFactory.createClickableGrayButton("Slett");


        deletebtn.setOnAction(event -> {
            QuizRegister.removeQuestion(quiz, question.getId());
            GUI.setSceneFromNode(deletebtn, "/gui/questionEditorPage.fxml");
        });

        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);

        // Create text labels that show where the question is and where the answer is
        Text questionLabel = GUIFactory.basicText("Question " + questionNumber + ":");
        Text answerLabel = GUIFactory.basicText("Answer:");
        Text pointsLabel = GUIFactory.basicText("Points");

        // Set the text fields with the question and answer
        TextField questionTextArea = GUIFactory.createTextField(question.getQuestion());
        TextField answerTextArea = GUIFactory.createTextField(question.getAnswer());
        TextField pointsField = GUIFactory.createNumberOnlyTextField(question.getScore());

        pointsField.setOnKeyTyped(actionEvent -> question.setScore(Integer.parseInt(pointsField.getText())));
        questionTextArea.setOnKeyTyped(actionEvent -> question.setQuestion(questionTextArea.getText()));
        answerTextArea.setOnKeyTyped(actionEvent ->  question.setAnswer(answerTextArea.getText()));

        // Add the text labels to the pane
        buttonContainer.getChildren().add(deletebtn);
        mainContainer.getChildren().addAll(questionLabel, questionTextArea, answerLabel, answerTextArea, pointsLabel, pointsField, buttonContainer);

        mainContainer.getStylesheets().add("gui/css/containers.css");

        mainContainer.getStyleClass().add("question-container");

        return mainContainer;
    }
}
