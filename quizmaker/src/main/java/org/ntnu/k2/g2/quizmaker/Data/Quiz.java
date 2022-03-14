package org.ntnu.k2.g2.quizmaker.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Quiz {
    private String name;
    private int id = -1;
    private LocalDateTime lastEdited;

    private HashMap<Integer, Team> teams = new HashMap<>();
    private HashMap<Integer, Question> questions = new HashMap<>();

    protected Quiz(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return id == quiz.id && Objects.equals(name, quiz.name) && Objects.equals(lastEdited, quiz.lastEdited)
                && Objects.equals(teams, quiz.teams) && Objects.equals(questions, quiz.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, lastEdited, teams, questions);
    }

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    protected void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }

    public HashMap<Integer, Team> getTeams() {
        return teams;
    }

    protected void setTeams(HashMap<Integer, Team> teams) {
        this.teams = teams;
    }

    public HashMap<Integer, Question> getQuestions() {
        return questions;
    }

    protected void setQuestions(HashMap<Integer, Question> questions) {
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }
}
