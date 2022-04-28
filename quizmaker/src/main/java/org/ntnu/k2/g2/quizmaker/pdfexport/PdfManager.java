package org.ntnu.k2.g2.quizmaker.pdfexport;

import org.ntnu.k2.g2.quizmaker.data.QuizModel;

/**
 * Class that handles export of quiz data to pdfs. It contains answer sheets and solutions.
 * All methods in the class is static.
 */
public class PdfManager {
    /**
     * Creates an answer sheet with questions and QR code for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     *
     * @param destination Chosen destination for the export to be saved
     */

    public static void exportAnswerSheetWithQuestions(QuizModel quiz, String destination) {
        PdfBuilder pdfBuilder = new PdfBuilder(quiz, destination, quiz.getName() + " - Quizark med spørsmål");
        pdfBuilder.addQRcode().addHeader("").addTeamNameField()
                .addQuestions(true, false).addFooter().saveAndWrite();
    }

    /**
     * Creates an answer sheet with QR but without questions for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     *
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportAnswerSheetWithoutQuestions(QuizModel quiz, String destination) {
        PdfBuilder pdfBuilder = new PdfBuilder(quiz, destination, quiz.getName() + " - Quizark uten spørsmål");
        pdfBuilder.addQRcode().addHeader("").addTeamNameField()
                .addQuestions(false, false).addFooter().saveAndWrite();
    }

    /**
     * Creates a sheet with solutions and questions for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     *
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportAnswerKey(QuizModel quiz, String destination) {
        PdfBuilder pdfBuilder = new PdfBuilder(quiz, destination, quiz.getName() + "- Fasit");
        pdfBuilder.addHeader("- Fasit").addQuestions(true, true).saveAndWrite();
    }
}
