package org.ntnu.k2.g2.quizmaker.data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class QuizModel {
    private String name;
    private final String SHEET_URL = "https://docs.google.com/spreadsheets/d/";
    private String sheetId;
    private boolean active = true;
    private int id = -1;
    private LocalDateTime lastChanged;

    private HashMap<Integer, TeamModel> teams = new HashMap<>();
    private HashMap<Integer, QuestionModel> questions = new HashMap<>();

    protected QuizModel(){}

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sheetId='" + sheetId + '\'' +
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
        QuizModel quiz = (QuizModel) o;
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
    public HashMap<Integer, TeamModel> getTeams() {
        return teams;
    }

    /**
     * Get all teams that are part of a team, sorted in descending order according to their score.
     * @return The teams of the quiz in descending order according to their score.
     */
    public Iterator<TeamModel> getTeamsSortedByScore() {
        return teams.values().stream().sorted(
                        Comparator.comparingInt(TeamModel::getScore).reversed())
                .iterator();
    }

    /**
     * Sets the teams of this quiz. This is done at object creation by the QuizDAO.
     * @param teams The new HashMap of the teams. The key is the id of the team, while the value is the corresponding
     *              team object.
     */
    protected void setTeams(HashMap<Integer, TeamModel> teams) {
        this.teams = teams;
    }

    /**
     * Gets the current collection of questions. If this HashMap is altered, and QuizRegister::saveQuiz is called,
     * The changes made WILL be reflected in the database. In the HashMap, the key is the id of the question, while
     * the value is the question object.
     * @return The questions that are registered to this quiz.
     */
    public HashMap<Integer, QuestionModel> getQuestions() {
        return questions;
    }

    /**
     * Sets the questions of this quiz. This is done at object creation by the QuizDAO.
     * @param questions The new HashMap of the teams. The key is the id of the question, while the value is the
     *              corresponding question object.
     */
    protected void setQuestions(HashMap<Integer, QuestionModel> questions) {
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
     * @return The sheet id of the quiz.
     */
    public String getSheetId() {
        return sheetId;
    }

    /**
     * Sets the sheetId of this quiz. This is the ID of the page where users can submit data.
     * @param sheetId The sheetId of the quiz.
     */
    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    /**
     * Gets url where users can submit data.
     * @return The url where users can submit data
     */
    public String getUrl() {
        return SHEET_URL + sheetId;
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


}
