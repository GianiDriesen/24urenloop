package entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giani-Luigi Driesen on 1/12/2016
 */

public class Team {
    private int id;
    private String name;
    private int year;
    private int leader_id = 0;  // 0 -> no leader
    private List<TeamAchievement> achievementList;
    private List<Team> previousTeams;
    private List<Person> runners;
    private int teamPoints; // TODO: Discuss with Koen how to do

    /** Empty constructor; only needed as a hack to get toString() methods to identify entity types */
    public Team() {}

    public Team(JSONObject obj) {
        try {
            this.id = obj.getInt("id");
            this.leader_id = obj.getInt("person_id");
            this.year = obj.getInt("year");
            this.name = obj.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** Constructor for teams new to the database */
    public Team(String name, int year, int leader_id) {
        this.name = name;
        this.year = year;
        this.leader_id = leader_id;
    }

    /** Constructor for teams already in the database */
    public Team(int id, String name, int year, int leader_id) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.leader_id = leader_id;
    }

    /** Constructor for teams already in the database */
    public Team(int id, String name, int year, int leader_id, List<Person> runners) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.leader_id = leader_id;
        this.runners = runners;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getLeaderId() {
        return leader_id;
    }

    public void setLeaderId(int leader_id) {
        this.leader_id = leader_id;
    }

    public List<TeamAchievement> getAchievementList() {
        return achievementList;
    }

    public void setAchievementList(List<TeamAchievement> achievementList) {
        this.achievementList = achievementList;
    }

    public void addAchievement(TeamAchievement achievement){
        this.achievementList.add(achievement);
    }

    public void removeAchievement(TeamAchievement achievement) {
        this.achievementList.remove(achievement);
    }

    public List<Team> getPreviousTeams() {
        return previousTeams;
    }

    public void setPreviousTeams(List<Team> previousTeams) {
        this.previousTeams = previousTeams;
    }

    public void addTeam(Team team){
        this.previousTeams.add(team);
    }

    public void removeTeam(Team team) {
        this.previousTeams.remove(team);
    }

    public int getTeamPoints() {
        return teamPoints;
    }

    public void setTeamPoints(int teamPoints) {
        this.teamPoints = teamPoints;
    }

    public List<Person> getRunners() {
        return runners;
    }

    public void setRunners(List<Person> runners) { this.runners = runners; }

    public void addRunner(Person runner) {
        runners.add(runner);
    }

    public void removeRunner(Person runner) {
        if (runners.contains(runner)) {
            runners.remove(runner);
        }
    }

    @Override
    public String toString() {
        return "Team "+name;
    }

    public List<String> getExportableFields() {
        List<String> fields = new ArrayList<>();
        fields.add("Year: "+year);
        return fields;
    }
}

