package org.ntnu.k2.g2.quizmaker.data;

import java.util.Objects;

/**
 * A class the represents a question
 */
public class QuestionModel {
    private String question;
    private String answer;
    private int score = 1; // default score per question is 1
    private int id = -1;

    /**
     * Create a new uninitialized Question-instance This method should only be used by QuizRegister to create new
     * questions. The QuizRegister makes sure that the created instance is added to the database and assigned the right
     * attributes (Question id and Quiz id)
     */
    protected QuestionModel() {
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", score=" + score + ", question='" + question + '\'' + ", answer='" + answer
                + '\'' + '}';
    }

    /**
     * Checks the equality between to questions. This method takes all properties into account.
     *
     * @param o
     *            Another question instance.
     *
     * @return True if the questions and all properties are equal, false if not.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        QuestionModel question1 = (QuestionModel) o;
        return id == question1.id && score == question1.score && Objects.equals(question, question1.question)
                && Objects.equals(answer, question1.answer);
    }

    /**
     * Computes the hash code of the question. All properties are considered.
     *
     * @return The hashcode of the question.
     */
    @Override
    public int hashCode() {
        return Objects.hash(question, score, answer, id);
    }

    /**
     * @return The question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question text
     *
     * @param question
     *            The new question text
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
     *
     * @param answer
     *            The new answer text
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Gets the maximum score that can be attained from this question. This value is defaulted to 1.
     *
     * @return The maximum score that can be attained form this question.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the maximum score that can be attained from this question. This value is defaulted to 1.
     *
     * @param score
     *            The new maximum score that can be attained form the question.
     *
     * @throws IllegalArgumentException
     *             Throws an exception if the score is lower than 0
     */
    public void setScore(int score) throws IllegalArgumentException {
        if (score < 0) {
            throw new IllegalArgumentException("The score needs to be greater than or equal to zero.");
        }

        this.score = score;
    }

    /**
     * @return The id of the question
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the is of the question. This is set during creation by the QuestionDAO
     *
     * @param id
     *            The id of the question
     */
    protected void setId(int id) {
        this.id = id;
    }
}
