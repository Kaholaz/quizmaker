package org.ntnu.k2.g2.quizmaker.pdfexport;

import com.itextpdf.layout.element.Paragraph;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;

/**
 * Class that handles export of quiz data to pdfs. It contains answer sheets and solutions.
 * All methods in the class is static.
 */
public class PdfManager {
    /**
     * Creates an answersheet with questions and QR code for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     *
     * @param destination Chosen destination for the export to be saved
     */

    public static void exportAnswersheetWithQuestions(QuizModel quiz, String destination) {
        PdfBuilder pdfBuilder = new PdfBuilder(quiz, destination, quiz.getName() + " - Quizark med spørsmål");
        Paragraph teamname = new Paragraph("Lagnavn:____________________________________________\n").setFontSize(18);
        pdfBuilder.addQRcode().addHeader("").addParagraph(teamname).addQuestions(true, false).addFooter().build();
    }

    /**
     * Creates an answersheet with QR but without questions for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     *
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportAnswersheetWithoutQuestions(QuizModel quiz, String destination) {
        PdfBuilder pdfBuilder = new PdfBuilder(quiz, destination, quiz.getName() + " - Quizark uten spørsmål");
        Paragraph teamname = new Paragraph("Lagnavn:____________________________________________\n").setFontSize(18);
        pdfBuilder.addQRcode().addHeader("").addParagraph(teamname).addQuestions(false, false).addFooter().build();
    }

    /**
     * Creates a sheet with solutions and questions for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     *
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportSolution(QuizModel quiz, String destination) {
        PdfBuilder pdfBuilder = new PdfBuilder(quiz, destination, quiz.getName() + "- Fasit");
        pdfBuilder.addHeader("- Fasit").addQuestions(true, true).build();
    }
}
