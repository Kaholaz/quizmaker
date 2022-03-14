package org.ntnu.k2.g2.quizmaker.Data;

import java.util.Objects;

<<<<<<< HEAD

/**
 * A class the represents a question
 */
=======
>>>>>>> dev
public class Question {
    private String question;
    private String answer;
    private int id = -1;

<<<<<<< HEAD
    /**
     * Create a new uninitialized Question-instance
     * This method should only be used by QuizRegister to create new questions.
     * The QuizRegister makes sure that the created instance is added to the database and assigned the right attributes
     * (Question id and Quiz id)
     */
    protected Question(){}

    /**
     * Checks the equality between to questions. This method takes all properties into account.
     * @param o Another question instance.
     * @return True if the questions and all properties are equal, false if not.
     */
=======
    protected Question(){}

>>>>>>> dev
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return id == question1.id && Objects.equals(question, question1.question) && Objects.equals(answer, question1.answer);
    }

<<<<<<< HEAD
    /**
     * Computes the hash code of the question. All properties are considered.
     * @return The hashcode of the question.
     */
=======
>>>>>>> dev
    @Override
    public int hashCode() {
        return Objects.hash(question, answer, id);
    }

    /**
     * @return The question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question text
     * @param question The new question text
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return The answer text of the question
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the question's answer text
     * @param answer The new answer text
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return The id of the question
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    protected void setId(int id) {
        this.id = id;
    }
}
