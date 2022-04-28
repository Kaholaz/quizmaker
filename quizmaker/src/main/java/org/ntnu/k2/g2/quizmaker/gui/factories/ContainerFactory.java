package org.ntnu.k2.g2.quizmaker.gui.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.ntnu.k2.g2.quizmaker.data.QuestionModel;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;
import org.ntnu.k2.g2.quizmaker.gui.GUI;
import org.ntnu.k2.g2.quizmaker.gui.QuizHandlerSingleton;
import org.ntnu.k2.g2.quizmaker.gui.decorators.ButtonDecorator;

import java.util.Objects;

import static org.ntnu.k2.g2.quizmaker.gui.factories.TextFactory.createNumberOnlyTextField;

/**
 * Creates various containers. The containers can have children nodes. All creation methods are static
 */
public class ContainerFactory {

    /**
     * Since the class is static, a constructor is not needed.
     */
    private ContainerFactory() {
    }

    /**
     * Creates a javafx Pane that can be used to show a Question in for example a list. This constructor sets the
     * question number to the question ID.
     *
     * @param question
     *            The question to create a Pane from.
     */
    public static Pane createQuestionPane(QuestionModel question, int questionNumber) {
        VBox mainContainer = new VBox();
        HBox buttonContainer = new HBox();
        QuizModel quiz = QuizHandlerSingleton.getQuiz();

        Button deleteBtn = ButtonFactory.createGrayButton("Slett");
        ButtonDecorator.makeDefaultDeleteButton(deleteBtn);

        deleteBtn.setOnAction(event -> {
            QuizRegister.removeQuestion(quiz, question.getId());
            GUI.setSceneFromNode(deleteBtn, "/gui/questionEditorPage.fxml");
        });

        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);

        // Create text labels that show where the question is and where the answer is
        Text questionLabel = TextFactory.createSmallText("Spørsmål " + questionNumber + ":");
        Text answerLabel = TextFactory.createSmallText("Svar: ");
        Text pointsLabel = TextFactory.createSmallText("Poeng: ");

        // Set the text fields with the question and answer
        TextField questionTextArea = TextFactory.createTextField(question.getQuestion());
        TextField answerTextArea = TextFactory.createTextField(question.getAnswer());
        TextField pointsField = createNumberOnlyTextField(question.getScore());

        pointsField.setOnKeyTyped(actionEvent -> {
            if (!Objects.equals(pointsField.getText(), "")) {
                question.setScore(Integer.parseInt(pointsField.getText()));
            }
        });
        questionTextArea.setOnKeyTyped(actionEvent -> question.setQuestion(questionTextArea.getText()));
        answerTextArea.setOnKeyTyped(actionEvent -> question.setAnswer(answerTextArea.getText()));

        // Add the text labels to the pane
        buttonContainer.getChildren().add(deleteBtn);
        mainContainer.getChildren().addAll(questionLabel, questionTextArea, answerLabel, answerTextArea, pointsLabel,
                pointsField, buttonContainer);

        mainContainer.getStylesheets().add("gui/css/containers.css");

        mainContainer.getStyleClass().add("question-container");

        return mainContainer;
    }
}
