package org.ntnu.k2.g2.quizmaker.pdfexport;

import com.google.zxing.WriterException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import org.ntnu.k2.g2.quizmaker.data.QuestionModel;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * This class can be used to build pdf from quizzes. These pdf files can be exported.
 * The builder should only be accessed by classes in the same package. The PdfManager class
 * is responsible for building pdfs and exporting them.
 */
class PdfBuilder {

    private final Document document;
    private final QuizModel quiz;

    /**
     * Initializes an pdf. The pdf is given a filename. The pdf is connected
     * to a quiz, and it is therefore stored in the builder.
     *
     * @param quiz        quiz that is connected to the pdf
     * @param destination where the file will be stored
     * @param filename    name of the file
     */
    public PdfBuilder(QuizModel quiz, String destination, String filename) {
        this.document = init(quiz, destination, filename);
        this.quiz = quiz;
    }


    /**
     * Helper method for initializing the Document.
     * TODO: Should not be in the builder class, saving should not be done in the builder class
     */
    private static Document init(QuizModel quiz, String destination, String filename) {
        PdfWriter writer = null;
        String dest = destination + "/" + filename + ".pdf";
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PdfDocument pdf = new PdfDocument(writer);

        return new Document(pdf);
    }

    /**
     * Add a QR code to the pdf. The QR code is linked to the google sheet of the stored quiz.
     *
     * @return built element
     */
    public PdfBuilder addQRcode() {
        ImageData data = null;

        try {
            data = ImageDataFactory.create(QRCodeGenerator.saveQR(quiz), Color.BLACK);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }

        Image qrCode = new Image(data)
                .setFixedPosition(464, 716);
        document.add(qrCode);

        Paragraph qrDescription = new Paragraph("Skann for å registrere poeng:")
                .setWidth(92)
                .setTextAlignment(TextAlignment.RIGHT);
        qrDescription.setFixedPosition(384, 765, qrDescription.getWidth());
        document.add(qrDescription);

        return this;
    }

    /**
     * Create a header for the pdf. The header already includes the name of the quiz, and can include other optional
     * text. headerText can be left blank if no optional text is specified.
     *
     * @param headerText optional headerText
     * @return
     */
    public PdfBuilder addHeader(String headerText) {

        Paragraph quizName = new Paragraph(quiz.getName() + " " + headerText)
                .setWidth(344)
                .setFontSize(29);
        document.add(quizName);

        return this;
    }

    /**
     * Add all questions to the pdf. If includeQuestion is true, questions will be included in the document. If
     * includeAnswer is true, the answer will be included. If includeAnswer is false there will be exported a blank
     * answer field.
     *
     * @param includeQuestion if questions are wanted
     * @param includeAnswer   if answers are wanted
     * @return updated builder
     */
    public PdfBuilder addQuestions(boolean includeQuestion, boolean includeAnswer) {
        if (quiz.getQuestions().isEmpty()) {
            return this;
        }
        List<QuestionModel> questions = quiz.getQuestions().values().stream().sorted(Comparator.comparingInt(QuestionModel::getId)).toList();

        for (int i = 0; i < questions.size(); i++) {
            QuestionModel question = questions.get(i);

            Text header = new Text(i +1 + ") (" + question.getScore() + " poeng) ").setBold();

            String questionText = "";
            if (includeQuestion && question.getQuestion() != null) {
                questionText = question.getQuestion();
            }

            Text answer1 = new Text("\nSvar:").setBold();
            String answer2 = "_______________________________________________";
            if (includeAnswer && question.getAnswer() != null) {
                answer2 = question.getAnswer();
            }

            Paragraph quest = new Paragraph()
                    .add(header)
                    .add(questionText)
                    .add(answer1)
                    .add(answer2)
                    .setFontSize(18)
                    .setKeepTogether(true);
            document.add(quest);
        }

        return this;
    }

    /**
     * Adding a paragraph element to the pdf
     *
     * @param paragraph paragraph element added
     * @return updated builder
     */
    public PdfBuilder addParagraph(Paragraph paragraph) {
        document.add(paragraph);
        return this;
    }

    /**
     * Add a footer to the pdf. The footer includes a field for writing score.
     *
     * @return updated builder
     */
    public PdfBuilder addFooter() {
        Paragraph score = new Paragraph("Poengsum:___/" + quiz.getMaxScore())
                .setFontSize(17);
        document.add(score);

        Paragraph footer = new Paragraph("PDF created with iText 6 (www.itextpdf.com)")
                .setFontSize(9);
        footer.setFixedPosition(document.getLeftMargin(), 19, footer.getWidth());
        document.add(footer);

        return this;
    }

    /**
     * Builds the pdf. The document will be closed.
     *
     * @return the built pdf
     */

    public Document build() {
        document.close();
        return document;
    }
}
