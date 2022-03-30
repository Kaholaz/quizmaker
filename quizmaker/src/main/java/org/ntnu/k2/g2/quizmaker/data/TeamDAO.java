package org.ntnu.k2.g2.quizmaker.data;

import java.sql.*;
import java.util.HashMap;

/**
 * A class that deals with the "teams"-table in the database.
 * Instances of this class can be used to save and retrieve data about teams from the database.
 * This method is package-protected. Database interactions should be done using the QuizRegister class.
 */
class TeamDAO {
    /**
     * Gets a team from a ResultSet of an SQL query.
     *
     * @param result The ResultSet of an SQL query.
     * @return The team based on the ResultSet.
     */
    private Team getTeamFromResultSet(ResultSet result) {
        Team team = null;
        try {
            if (result.next()) {
                team = new Team();
                team.setId(result.getInt("id"));
                team.setTeamName(result.getString("name"));
                team.setScore(result.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(result);
        }
        return team;
    }

    /**
     * Returns a map of all teams from the ResultSet of an SQL query.
     *
     * @param result The ResultSet of an SQL query.
     * @return A Map of all teams from the ResultSet of an SQL query. The key is the id of the team,
     * and the value is the team-object.
     */
    private HashMap<Integer, Team> getTeamsFromResultSet(ResultSet result) {
        HashMap<Integer, Team> teams = new HashMap<>();

        try {
            while (result.next()) {
                Team team = new Team();
                team.setId(result.getInt("id"));
                team.setTeamName(result.getString("name"));
                team.setScore(result.getInt("score"));

                teams.put(team.getId(), team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(result);
        }
        return teams;
    }

    /**
     * Gets the quiz id of the quiz that a team is the component of form the ResultSet of an SQL query.
     *
     * @param result The ResultSet of an SQL query.
     * @return The id of the quiz that the team is a component of. Returns -1 if the ResultSet is empty.
     */
    private int getQuizIdFromResultSet(ResultSet result) {
        int quizId = -1;
        try {
            if (result.next()) {
                quizId = result.getInt("quizId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(result);
        }
        return quizId;
    }

    /**
     * Gets a team from its ID
     *
     * @param id The id of the team
     * @return The team that matches the ID. Returns null if no team was found.
     */
    public Team getTeamById(int id) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        Team team = null;

        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM teams WHERE id=?;");
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();

            team = getTeamFromResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return team;
    }

    /**
     * Gets all teams that are components of a quiz.
     *
     * @param quizId The id of the Quiz
     * @return A map of all the teams that are components of the quiz. The keys in the map are
     * the ids of the teams, while the keys are the teams objects.
     */
    public HashMap<Integer, Team> getTeamsByQuizId(int quizId) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        HashMap<Integer, Team> teams = null;

        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM teams WHERE quizId=?;");
            preparedStatement.setInt(1, quizId);
            result = preparedStatement.executeQuery();

            teams = getTeamsFromResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return teams;
    }

    /**
     * Gets the id of a quiz that a team is the component of.
     *
     * @param teamId The id of the team.
     * @return The id of the quiz. Returns -1 if the teamID is not found in the database.
     */
    public int getQuizIdByTeamId(int teamId) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        int quizId = -1;

        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM teams WHERE id=?;");
            preparedStatement.setInt(1, teamId);
            result = preparedStatement.executeQuery();

            quizId = getQuizIdFromResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return quizId;
    }

    /**
     * Updates the entry of a team in the database to reflect a team object
     *
     * @param team   The team object
     * @param quizId The id of the quiz that the team is a component of.
     * @return The team how it is saved in the database.
     */
    public Team updateTeam(Team team, int quizId) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            connection = DatabaseConnection.getConnection();
            if (team.getId() == -1) {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO teams (name, score, quizId) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(3, quizId);
            } else {
                preparedStatement = connection.prepareStatement(
                        "UPDATE teams SET name=?, score=? WHERE id=?");
                preparedStatement.setInt(3, team.getId());
            }
            preparedStatement.setString(1, team.getTeamName());
            preparedStatement.setInt(2, team.getScore());

            int resultRows = preparedStatement.executeUpdate();
            if (team.getId() == -1 && resultRows == 1) {
                result = preparedStatement.getGeneratedKeys();
                if (result.next()) team.setId(result.getInt(1));
            } else if (resultRows == 0) {
                team = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
            DatabaseConnection.closeResultSet(result);
        }
        return team;
    }

    /**
     * Updates the database entry of a team by finding the quizId implicitly.
     *
     * @param team The team to save in the database.
     * @return The team how it is saved in the database.
     */
    public Team updateTeam(Team team) {
        int quizId = getQuizIdByTeamId(team.getId());
        if (quizId == -1) return null;
        return updateTeam(team, quizId);
    }

    public boolean removeTeamById(int id) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        boolean result = false;

        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM teams WHERE id=?");
            preparedStatement.setInt(1, id);
            result = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closePreparedStatement(preparedStatement);
        }
        return result;
    }
}
