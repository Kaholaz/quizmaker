package org.ntnu.k2.g2.quizmaker.Data;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

public class Quiz {
    private String name;
    private String url;
    private boolean active = true;
    private int id = -1;
    private LocalDateTime lastChanged;

    private HashMap<Integer, Team> teams = new HashMap<>();
    private HashMap<Integer, Question> questions = new HashMap<>();

    protected Quiz(){}

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", active=" + active +
                ", lastChanged=" + lastChanged +
                ", teams=" + teams.values() +
                ", questions=" + questions.values() +
                '}';
    }

    /**
     * Checks the equality of two quizzes. All parameters and all contents of the parameters are considered.
     * Order of the question or teams does not matter as they are stored as HashMaps.
     * @param o The quiz to compare to.
     * @return True if the quizzes and all components are equal, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return id == quiz.id && Objects.equals(name, quiz.name) && Objects.equals(lastChanged, quiz.lastChanged)
                && Objects.equals(teams, quiz.teams) && Objects.equals(questions, quiz.questions);
    }

    /**
     * Hashes the object. All parameters and components are considered.
     * @return The objects hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, id, lastChanged, teams, questions);
    }

    /**
     * Gets the last time this object was changed. This propperty is set by the QuizDAO when the object is retrived.
     * @return The last time this object was changed.
     */
    public LocalDateTime getLastChanged() {
        return lastChanged;
    }

    /**
     * Sets the last time this object was changed. This is set by the QuizDAO when the object is retrieved, and
     * changed whenever the entry in the database is altered.
     * @param lastChanged The last time this object was altered.
     */
    protected void setLastChanged(LocalDateTime lastChanged) {
        this.lastChanged = lastChanged;
    }

    /**
     * Gets the current collection of teams. If this HashMap is altered, and QuizRegister::saveQuiz is called,
     * The changes made WILL be reflected in the database. In the HashMap, the key is the id of the team, while
     * the value is the team object.
     * @return The teams that are registered to this quiz.
     */
    public HashMap<Integer, Team> getTeams() {
        return teams;
    }

    /**
     * Sets the teams of this quiz. This is done at object creation by the QuizDAO.
     * @param teams The new HashMap of the teams. The key is the id of the team, while the value is the corresponding
     *              team object.
     */
    protected void setTeams(HashMap<Integer, Team> teams) {
        this.teams = teams;
    }

    /**
     * Gets the current collection of questions. If this HashMap is altered, and QuizRegister::saveQuiz is called,
     * The changes made WILL be reflected in the database. In the HashMap, the key is the id of the question, while
     * the value is the question object.
     * @return The questions that are registered to this quiz.
     */
    public HashMap<Integer, Question> getQuestions() {
        return questions;
    }

    /**
     * Sets the questions of this quiz. This is done at object creation by the QuizDAO.
     * @param questions The new HashMap of the teams. The key is the id of the question, while the value is the
     *              corresponding question object.
     */
    protected void setQuestions(HashMap<Integer, Question> questions) {
        this.questions = questions;
    }

    /**
     * @return The name of the quiz
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the quiz.
     * @param name The name of the quiz.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The id of the quiz. This is set by the QuizDAO at object creation, and should not be changed.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the quiz. This property is set by the QuizDAO at object creating, and should not be changed.
     * @param id The id of the quiz.
     */
    protected void setId(int id) {
        this.id = id;
    }

    /**
     * Gets url where users can submit data.
     * @return The url where users can submit data
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url where users can submit data.
     * @param url The url where users can submit data.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return Whenever or not the quiz is active. Archived quizzes have this attribute set to false.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets whenever or not the quiz is active or not. Archived quizzes should have this attribute set to false.
     * @param active Whenever or not this quiz is active.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public void exportAnswersheetWithQuestions() {
        // Creating a PdfWriter
        String dest = "C:/itextTest/answersheetWithQuestions.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfPage pdfPage = pdf.addNewPage();

        String title = getName();
        Paragraph paragraph1 = new Paragraph(title);
        document.add(paragraph1);
        PdfCanvas canvas = new PdfCanvas(pdfPage);

        HashMap<Integer, Question> questions = getQuestions();
        int counter = 1;

        for (Question q : questions.values()) {
            String header = "Question " + counter + ": ";
            String question = q.getQuestion();
            String answer = "\nAnswer: ";
            Paragraph quest = new Paragraph();
            quest.add(header);
            quest.add(question);
            quest.add(answer);
            document.add(quest);
            canvas.moveTo(80,787 - counter*44);
            canvas.lineTo(550,787 - counter*44);
            counter++;
        }
        canvas.closePathStroke();

        // Closing the document
        document.close();
    }

    public void exportAnswersheetWithoutQuestions() {
        // Creating a PdfWriter
        String dest = "C:/itextTest/answersheetWithoutQuestions.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfPage pdfPage = pdf.addNewPage();

        String title = getName();
        Paragraph paragraph1 = new Paragraph(title);
        document.add(paragraph1);
        PdfCanvas canvas = new PdfCanvas(pdfPage);

        HashMap<Integer, Question> questions = getQuestions();
        int counter = 1;

        for (Question q : questions.values()) {
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

        // Closing the document
        document.close();
    }

    public void exportAnswersWithQuestions() {
        // Creating a PdfWriter
        String dest = "C:/itextTest/answersWithQuestions.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        String title = getName();
        Paragraph paragraph1 = new Paragraph(title);
        document.add(paragraph1);

        HashMap<Integer, Question> questions = getQuestions();
        int counter = 1;

        for (Question q : questions.values()) {
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

        // Closing the document
        document.close();
    }

    public void exportAnswersWithoutQuestions() {
        // Creating a PdfWriter
        String dest = "C:/itextTest/answersWithoutQuestions.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        String title = getName();
        Paragraph paragraph1 = new Paragraph(title);
        document.add(paragraph1);

        HashMap<Integer, Question> questions = getQuestions();
        int counter = 1;

        for (Question q : questions.values()) {
            String header = "Question " + counter + ": ";
            String answer = "\nAnswer: " + q.getAnswer();
            Paragraph quest = new Paragraph();
            quest.add(header);
            quest.add(answer);
            document.add(quest);
            counter++;
        }

        // Closing the document
        document.close();
    }
}
