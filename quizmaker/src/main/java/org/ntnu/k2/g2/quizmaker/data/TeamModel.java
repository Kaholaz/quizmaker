package org.ntnu.k2.g2.quizmaker.data;

import java.util.Objects;

/**
 * A class used to represent a team.
 */
public class TeamModel {
    private String teamName;
    private int score;
    private int id = -1;

    /**
     * Constructs a new Team-instance. This method is only used by the QuizRegister.
     * New teams should be created using the QuizRegister::newTeam method.
     */
    protected TeamModel(){}

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", score=" + score +
                '}';
    }

    /**
     * Checks the equality of two teams. All parameters are considered.
     * @param o The object to compare to.
     * @return The equality of the two teams. If the two teams an all properties are equal, true is returned.
     *         Otherwise, false is returned.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamModel team = (TeamModel) o;
        return score == team.score && id == team.id && Objects.equals(teamName, team.teamName);
    }

    /**
     * The HashCode of the object. All properties are considered.
     * @return The HashCode of the object using Objects.hash()
     */
    @Override
    public int hashCode() {
        return Objects.hash(teamName, score, id);
    }

    /**
     * @return The id of the team. This is set at creation and should not be changed.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the team. This is done at creation by TeamDAO.
     * @param id The id of the team.
     */
    protected void setId(int id) {
        this.id = id;
    }

    /**
     * @return The name of the team.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Sets the name of the team. Changes to this property will be reflected in the database
     * after QuizRegister::saveTeam is called.
     * @param teamName The name of the team.
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @return The score of the quiz.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the team. Changes to this property will be reflected in the database
     * after QuizRegister::saveTeam is called.
     * @param score The score of the team.
     */
    public void setScore(int score) {
        this.score = score;
    }
}
