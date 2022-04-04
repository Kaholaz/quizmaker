package org.ntnu.k2.g2.quizmaker.PdfExport;

import com.google.zxing.WriterException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.layout.property.TextAlignment;
import org.ntnu.k2.g2.quizmaker.data.QuestionModel;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;


public class PdfManager {
    /**
     * Creates an answersheet with questions for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportAnswersheetWithQuestions(QuizModel quiz, String destination) {
        try {
            GenerateQRCode.saveQR(quiz);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String title = quiz.getName();
        PdfWriter writer = null;
        String dest = destination + "/" + title + " - Answersheet With Questions.pdf";
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);


        String imageFile = "src/main/resources/PdfExport/" + title + ".png";
        ImageData data = null;
        try {
            data = ImageDataFactory.create(imageFile);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image qrCode = new Image(data);
        qrCode.setFixedPosition(465,716);
        document.add(qrCode);

        Paragraph qrDescription = new Paragraph("Scan this to open answerform:");
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

        HashMap<Integer, QuestionModel> questions = quiz.getQuestions();
        int counter = 1;

        for (QuestionModel q : questions.values()) {
            String header = counter + ") ";
            String question = q.getQuestion();
            String answer = "\nAnswer:_____________________________________________";
            Paragraph quest = new Paragraph();
            quest.add(header);
            quest.add(question);
            quest.add(answer);
            quest.setFontSize(18);
            quest.setKeepTogether(true);
            document.add(quest);
            counter++;
        }

        document.close();
    }

    /**
     * Creates an answersheet without questions for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportAnswersheetWithoutQuestions(QuizModel quiz, String destination) {
        String title = quiz.getName();
        PdfWriter writer = null;
        String dest = destination + "/" + title + " - Answersheet Without Questions.pdf";
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfPage pdfPage = pdf.addNewPage();

        Paragraph paragraph1 = new Paragraph(title);
        document.add(paragraph1);
        PdfCanvas canvas = new PdfCanvas(pdfPage);

        HashMap<Integer, QuestionModel> questions = quiz.getQuestions();
        int counter = 1;

        for (QuestionModel q : questions.values()) {
            String header = "Question " + counter + ": ";
            String answer = "\nAnswer: ";
            Paragraph quest = new Paragraph();
            quest.add(header);
            quest.add(answer);
            document.add(quest);
            canvas.moveTo(80,787 - counter*44);
            canvas.lineTo(550,787 - counter*44);
            counter++;
        }
        canvas.closePathStroke();

        document.close();
    }

    /**
     * Creates a sheet with answers and questions for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportAnswersWithQuestions(QuizModel quiz, String destination) {
        String title = quiz.getName();
        String dest = destination + "/" + title + " - Answers With Questions.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph paragraph1 = new Paragraph(title);
        document.add(paragraph1);

        HashMap<Integer, QuestionModel> questions = quiz.getQuestions();
        int counter = 1;

        for (QuestionModel q : questions.values()) {
            String header = "Question " + counter + ": ";
            String question = q.getQuestion();
            String answer = "\nAnswer: " + q.getAnswer();
            Paragraph quest = new Paragraph();
            quest.add(header);
            quest.add(question);
            quest.add(answer);
            document.add(quest);
            counter++;
        }

        document.close();
    }

    /**
     * Creates a sheet with answers for the quiz as a pdf saved locally on the computer.
     * Local destination can be changed in dest variable.
     * @param destination Chosen destination for the export to be saved
     */
    public static void exportAnswersWithoutQuestions(QuizModel quiz, String destination) {
        String title = quiz.getName();
        String dest = destination + "/" + title + " - Answers Without Questions.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph paragraph1 = new Paragraph(title);
        document.add(paragraph1);

        HashMap<Integer, QuestionModel> questions = quiz.getQuestions();
        int counter = 1;

        for (QuestionModel q : questions.values()) {
            String header = "Question " + counter + ": ";
            String answer = "\nAnswer: " + q.getAnswer();
            Paragraph quest = new Paragraph();
            quest.add(header);
            quest.add(answer);
            document.add(quest);
            counter++;
        }

        document.close();
    }
}
