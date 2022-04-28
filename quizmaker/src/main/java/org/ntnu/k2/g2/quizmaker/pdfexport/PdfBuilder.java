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
    private boolean hasQr = false;

    /**
     * Creates a new PDF builder that can be used to build a pdf. Use the appropriate methods to construct a suitable
     * PDF. Once the PDF has been build, use the {@code saveAndWrite} method to save the PDF at its designated destination.
     *
     * @param quiz        Quiz that is connected to the pdf
     * @param destination In what directory to save the PDF once it has been built
     *                    (please use forwards-slash ('/') as a path separator)
     * @param filename    The name the PDF will be saved as. The .pdf extension may be omitted.
     */
    public PdfBuilder(QuizModel quiz, String destination, String filename) {
        this.document = initDocument(destination, filename);
        this.quiz = quiz;
    }


    /**
     * Helper method for initializing the Document.
     * TODO: Should not be in the builder class, saving should not be handled by the builder class.
     */
    private static Document initDocument(String destination, String filename) {

        String dest = destination + "/" + filename;
        if (!dest.endsWith(".pdf")) {
            dest += ".pdf";
        }
        PdfDocument pdf = null;

        try (PdfWriter writer = new PdfWriter(dest)) {
            pdf = new PdfDocument(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Document(pdf);
    }

    /**
     * Add a QR code to the pdf. The QR code is linked to the Google Sheet of the stored quiz.
     * The QR code will get placed in the top right corner of the first page.
     * Multiple QR codes can not be added.
     *
     * @return The updated builder.
     */
    public PdfBuilder addQRcode() {
        // Should not add multiple QR codes as its position is absolute.
        if (hasQr) {
            return this;
        }

        ImageData data = null;

        try {
            data = ImageDataFactory.create(QRCodeGenerator.getQRImage(quiz), Color.BLACK);
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

        hasQr = true; // QR added :)
        return this;
    }

    /**
     * Create a header for the pdf. The header contains the name of the quiz concatenated with the provided header text.
     *
     * @param headerText Optional text to add to the header in addition to the name of the quiz. If headerText is null
     *                   or an empty string, no text is added.
     * @return The updated builder.
     */
    public PdfBuilder addHeader(String headerText) {

        Paragraph quizName = new Paragraph(quiz.getName() + " " + ((headerText == null) ? "" : headerText))
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
     * @param includeQuestion If questions are wanted. If this is false the method will just write the ordinal of the question
     *                        and the possible score that can be gained form the question. If this is set to true,
     *                        the question will also get added.
     * @param includeAnswer   if answers are wanted. If this is set to false, a line where participants can enter their score
     *                        is added. If this is set to true, the full answer string will be added.
     * @return The updated builder.
     */
    public PdfBuilder addQuestions(boolean includeQuestion, boolean includeAnswer) {

        // Nothing is added if the quiz contains no questions.
        if (quiz.getQuestions().isEmpty()) {
            return this;
        }

        // Questions sorted by id (and by extension when they were added).
        List<QuestionModel> questions = quiz.getQuestions().values().stream().sorted(Comparator.comparingInt(QuestionModel::getId)).toList();

        for (int i = 0; i < questions.size(); i++) {
            QuestionModel question = questions.get(i);

            // The header is formatted like this '2) (3 poeng) '
            Text header = new Text((i + 1) + ") (" + question.getScore() + " poeng) ").setBold();

            // Sets the question text.
            // No question text if question is null or includeQuestion is set to false.
            String questionText = "";
            if (includeQuestion && question.getQuestion() != null) {
                questionText = question.getQuestion();
            }

            Text answer1 = new Text("\nSvar: ").setBold();
            String answer2 = "_______________________________________________";
            if (question.getAnswer() == null) {
                answer2 = "";
            } else if (includeAnswer) {
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
     * Adds vertical space to the document.
     *
     * @return The updated builder.
     */
    public PdfBuilder addVerticalSpace() {
        Paragraph space = new Paragraph("\n");
        document.add(space);
        return this;
    }

    /**
     * Add a footer to the pdf. The footer includes a field for writing score.
     *
     * @return The updated builder.
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
     * Adds a team name entry field to the PDF.
     *
     * @return The updated builder.
     */
    public PdfBuilder addTeamNameField() {
        Paragraph teamName = new Paragraph("Lagnavn:____________________________________________\n").setFontSize(18);
        document.add(teamName);
        return this;
    }

    /**
     * Builds the pdf. And saves it to its destination.
     *
     */
    public void saveAndWrite() {
        document.close();
    }
}
