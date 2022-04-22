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
 * Class that handles export of quiz data to pdfs. That contains answer sheets and solutions.
 * All methods in the class is static.
 */
public class PdfManager {
    /**
     * Creates an answersheet with questions and QR code for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportAnswersheetWithQuestions(QuizModel quiz, String destination) {
        String title = quiz.getName();
        PdfWriter writer = null;
        String dest = destination + "/" + title + " - Quizark med spørsmål.pdf";
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert writer != null;
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        ImageData data = null;
        try {
            data = ImageDataFactory.create(GenerateQRCode.saveQR(quiz), Color.BLACK);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
        Image qrCode = new Image(data);
        qrCode.setFixedPosition(465,716);
        document.add(qrCode);

        Paragraph qrDescription = new Paragraph("Skann for å registrere poeng:");
        qrDescription.setWidth(105);
        qrDescription.setTextAlignment(TextAlignment.RIGHT);
        qrDescription.setFixedPosition(373,765,qrDescription.getWidth());
        document.add(qrDescription);

        Paragraph paragraph1 = new Paragraph(title);
        paragraph1.setWidth(345);
        paragraph1.setFontSize(30);
        document.add(paragraph1);

        Paragraph space = new Paragraph("\n");
        document.add(space);

        // A list of all questions sorted by id (and by extension creation order)
        List<QuestionModel> questionsSorted = quiz.getQuestions().values().stream()
                .sorted(Comparator.comparingInt(QuestionModel::getId)).toList();
        int counter = 1;

        for (QuestionModel q : questionsSorted) {
            Text header = new Text(counter + ") (" + q.getScore() + " poeng) ");
            header.setBold();
            String question = q.getQuestion();
            Text answer1 = new Text("\nSvar:");
            answer1.setBold();
            String answer2 = "_____________________________________________";
            Paragraph quest = new Paragraph();
            quest.add(header);
            quest.add(question);
            quest.add(answer1);
            quest.add(answer2);
            quest.setFontSize(18);
            quest.setKeepTogether(true);
            document.add(quest);
            counter++;
        }

        document.close();
    }

    /**
     * Creates an answersheet with QR but without questions for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportAnswersheetWithoutQuestions(QuizModel quiz, String destination) {
        String title = quiz.getName();
        PdfWriter writer = null;
        String dest = destination + "/" + title + " - Quizark uten spørsmål.pdf";
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        ImageData data = null;
        try {
            data = ImageDataFactory.create(GenerateQRCode.saveQR(quiz), Color.BLACK);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
        Image qrCode = new Image(data);
        qrCode.setFixedPosition(465,716);
        document.add(qrCode);

        Paragraph qrDescription = new Paragraph("Skann for å registrere poeng:");
        qrDescription.setWidth(93);
        qrDescription.setTextAlignment(TextAlignment.RIGHT);
        qrDescription.setFixedPosition(385,765,qrDescription.getWidth());
        document.add(qrDescription);

        Paragraph paragraph1 = new Paragraph(title);
        paragraph1.setWidth(345);
        paragraph1.setFontSize(30);
        document.add(paragraph1);

        Paragraph space = new Paragraph("\n");
        document.add(space);

        // A list of all questions sorted by id (and by extension creation order)
        List<QuestionModel> questionsSorted = quiz.getQuestions().values().stream()
                .sorted(Comparator.comparingInt(QuestionModel::getId)).toList();
        int counter = 1;

        for (QuestionModel q : questionsSorted) {
            Text header = new Text(counter + ") (" + q.getScore() + " poeng) ");
            header.setBold();
            Text answer1 = new Text("\nSvar:");
            answer1.setBold();
            String answer2 = "_____________________________________________";
            Paragraph quest = new Paragraph();
            quest.add(header);
            quest.add(answer1);
            quest.add(answer2);
            quest.setFontSize(18);
            quest.setKeepTogether(true);
            document.add(quest);
            counter++;
        }

        document.close();
    }

    /**
     * Creates a sheet with solutions and questions for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportSolution(QuizModel quiz, String destination) {
        String title = quiz.getName();
        PdfWriter writer = null;
        String dest = destination + "/" + title + " - Fasit.pdf";
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph paragraph1 = new Paragraph(title + " - Fasit");
        paragraph1.setWidth(345);
        paragraph1.setFontSize(30);
        document.add(paragraph1);

        Paragraph space = new Paragraph("\n");
        document.add(space);

        // A list of all questions sorted by id (and by extension creation order)
        List<QuestionModel> questionsSorted = quiz.getQuestions().values().stream()
                .sorted(Comparator.comparingInt(QuestionModel::getId)).toList();
        int counter = 1;

        for (QuestionModel q : questionsSorted) {
            Text header = new Text(counter + ") (" + q.getScore() + " poeng) ");
            header.setBold();
            String question = q.getQuestion();
            Text answer1 = new Text("\nSvar: ");
            answer1.setBold();
            String answer2 = q.getAnswer();
            Paragraph quest = new Paragraph();
            quest.add(header);
            quest.add(question);
            quest.add(answer1);
            quest.add(answer2);
            quest.setFontSize(18);
            quest.setKeepTogether(true);
            document.add(quest);
            counter++;
        }

        document.close();
    }
}
