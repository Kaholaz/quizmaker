package org.ntnu.k2.g2.quizmaker.PdfExport;

import junit.framework.TestCase;
import org.ntnu.k2.g2.quizmaker.data.QuestionModel;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;

import java.io.File;

public class PdfExportTest extends TestCase {
    public void testPdfExport() {
        QuizRegister quizRegister = new QuizRegister();
        QuizModel testQuiz = quizRegister.newQuiz();
        testQuiz.setName("Test quiz");

        for (int i = 1; i <= 20; ++i) {
            QuestionModel question = quizRegister.newQuestion(testQuiz);
            if (i % 2 == 0) {
                question.setQuestion("Dette er et ekstremt langt spørsmål som tar helt sinnsykt mye plass?");
                question.setAnswer("Dette er et kort svar");
            } else {
                question.setQuestion("Dette er et kort spørsmål?");
                question.setAnswer("Dette er et veldig langt, omfattende og komplisert svar som egentlig kunne vært formulert mye bedre");
            }

        }

        String destination = "src/main/resources/PdfExport";
        File destionation_file = new File(destination);
        destionation_file.mkdir();

        PdfManager.exportAnswersheetWithQuestionsQR(testQuiz,destination);
        PdfManager.exportAnswersheetWithQuestions(testQuiz,destination);
        PdfManager.exportAnswersheetWithoutQuestionsQR(testQuiz,destination);
        PdfManager.exportAnswersheetWithoutQuestions(testQuiz,destination);
        PdfManager.exportSolutionWithQuestions(testQuiz,destination);
        PdfManager.exportSolutionWithoutQuestions(testQuiz,destination);
    }
}
