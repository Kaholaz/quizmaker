package org.ntnu.k2.g2.quizmaker.pdfexport;

import org.junit.After;
import org.junit.Test;
import org.ntnu.k2.g2.quizmaker.data.QuestionModel;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;
import org.ntnu.k2.g2.quizmaker.data.QuizRegister;

import java.io.File;

public class PdfExportTest {
    @After
    public void tearDown() {
        String dest = "src/main/resources/pdfexport";
        File dir = new File(dest);
        for(File file: dir.listFiles()) {
            if (!file.isDirectory()) file.delete();
        }
    }

    @Test
    public void testPdfExport() {
        QuizModel testQuiz = QuizRegister.newQuiz();
        testQuiz.setName("Test quiz");

        for (int i = 1; i <= 20; ++i) {
            QuestionModel question = QuizRegister.newQuestion(testQuiz);
            if (i % 2 == 0) {
                question.setQuestion("Dette er et ekstremt langt spørsmål som tar helt sinnsykt mye plass?");
                question.setAnswer("Dette er et kort svar");
            } else {
                question.setQuestion("Dette er et kort spørsmål?");
                question.setAnswer("Dette er et veldig langt, omfattende og komplisert svar som egentlig kunne vært formulert mye bedre");
            }

        }

        String destination = "src/main/resources/pdfexport";
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
