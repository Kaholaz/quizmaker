package ogr.ntnu.k2.g2.Quiz;

import java.util.Iterator;
import java.util.List;

public class Quiz {
    private List<Team> teamList;
    private List<Question> questionList;

    public void sortTeamsByScore() {};

    public Iterator<Team> getTeamList() {return null;}

    public void addQuestion(Question question) {};

    public Question removeQuestion(int index) throws IndexOutOfBoundsException {return null;}

    public void addTeam(String teamName) {}

    public Team removeTeam(int index) throws IndexOutOfBoundsException {return null;}
}
