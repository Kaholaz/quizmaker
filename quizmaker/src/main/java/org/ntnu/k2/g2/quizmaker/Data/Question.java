package org.ntnu.k2.g2.quizmaker.Data;

public class Question {
    private String question;
    private String answer;
    private int id;

    public Question(String question, String answer) {}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
