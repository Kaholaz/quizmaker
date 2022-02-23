package ogr.ntnu.k2.g2.Quiz;

public class Team {
    private String teamName;
    private int score;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public int compareScore(Team team) {return 0;}

    public String getTeamName() {
        return teamName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
