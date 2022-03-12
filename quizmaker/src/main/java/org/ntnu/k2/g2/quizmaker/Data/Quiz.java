package org.ntnu.k2.g2.quizmaker.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {
    private String name;
    private int id = -1;

    private HashMap<Integer, Team> teams = new HashMap<>();
    private HashMap<Integer, Question> questions = new HashMap<>();

    public Quiz(){}

    public HashMap<Integer, Team> getTeams() {
        return teams;
    }

    public void setTeams(HashMap<Integer, Team> teams) {
        this.teams = teams;
    }

    public HashMap<Integer, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<Integer, Question> questions) {
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

    public void setId(int id) {
        this.id = id;
    }
}
