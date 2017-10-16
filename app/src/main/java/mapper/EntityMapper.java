package mapper;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import entity.*;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Singleton design pattern: standard methods for entities to-and-from the database.
 * @author Koen Van Kerckhoven
 * @version 1.1 Updated to use the JSON REST service on 21/12/2016
 * Created on 10/11/2016.
 */
public enum EntityMapper {
    UNIQUEMAPPER;

    /** Singleton design applied; use only one mapper for each entity. */
    private final LapMapper lMapper = LapMapper.UNIQUEMAPPER;
    private final TeamMapper tMapper = TeamMapper.UNIQUEMAPPER;
    private final PersonMapper pMapper = PersonMapper.UNIQUEMAPPER;
    private final PrizeMapper prizeMapper = PrizeMapper.UNIQUEMAPPER;
    private final AchievementMapper aMapper = AchievementMapper.UNIQUEMAPPER;
    private final TrainingMapper trainingMapper = TrainingMapper.UNIQUEMAPPER;
    private final TeamHasPersonMapper teamHasPersonMapper = TeamHasPersonMapper.UNIQUEMAPPER;
    private final PersonHasPrizeMapper personHasPrizeMapper = PersonHasPrizeMapper.UNIQUEMAPPER;

    private void initiateSingletons() {
        this.pMapper.setEntityMapper(this);
        this.lMapper.setEntityMapper(this);
        this.aMapper.setEntityMapper(this);
        this.tMapper.setEntityMapper(this);
        this.prizeMapper.setEntityMapper(this);
        this.trainingMapper.setEntityMapper(this);
        this.teamHasPersonMapper.setEntityMapper(this);
        this.personHasPrizeMapper.setEntityMapper(this);
    }
    private RequestQueue requestQueue;
    public RequestQueue getRequestQueue() { return this.requestQueue; }
    public void setRequestQueue(RequestQueue rq) { this.requestQueue = rq; }
    /** Singleton design applied; return the single mappers to other mappers that need them. */
    public PersonMapper getpMapper() { return pMapper; }
    public LapMapper getlMapper() { return lMapper; }
    public AchievementMapper getaMapper() { return aMapper; }
    public PrizeMapper getPrizeMapper() { return prizeMapper; }
    public TeamMapper gettMapper() { return tMapper; }
    public TrainingMapper getTrainingMapper() { return trainingMapper; }
    public TeamHasPersonMapper getTeamHasPersonMapper() {return teamHasPersonMapper;}
    public PersonHasPrizeMapper getPersonHasPrizeMapper() {return  personHasPrizeMapper;}

    /**
     * Prepare one of each entity, and one list for each of those entities.
     * Each time an entity or a list of them is requested from the DB, start
     * asynchronously requesting and parsing them, and fill the aforementioned
     * empty entities or lists with the parsed ones. As soon as they are filled,
     * set the dataReady field "true" to indicate the entities can be grabbed.
     */
    public Lap lap;
    public List<Lap> laps;
    public Person person;
    public List<Person> persons;
    public HashMap<Person, Integer> runnerQueue;
    private Prize prize;
    public List<Prize> prizes;
    public Team team;
    public List<Team> teams;
    private Training training;
    public List<Training> trainings;
    private Achievement achievement;
    public List<Achievement> achievements;
    public List<Person_has_prize> person_has_prizes;
    public List<Team_has_person> team_has_persons;
    private boolean dataPushed = false; // We can't let the singleton to mess with this value here,
    public boolean dataPushed() { return this.dataPushed; } // but we will allow the singleton to read it,
    public void dataSent() { this.dataPushed = false; } // and allow the singleton to reset the flag.
    private boolean dataReady = false; // We can't let the singleton to mess with this value here,
    public boolean dataReady() { return this.dataReady; } // but we will allow the singleton to read it,
    public void dataGrabbed() { // and allow the singleton to reset the flag, and reset all entities and lists
        this.dataReady = false;
        lap = null;
        laps = new LinkedList<>();
        team = null;
        teams = new LinkedList<>();
        prize = null;
        prizes = new LinkedList<>();
        person = null;
        persons = new LinkedList<>();
        runnerQueue = new HashMap<>();
        training = null;
        trainings = new LinkedList<>();
        achievement = null;
        achievements = new LinkedList<>();
        person_has_prizes = new LinkedList<>();
        team_has_persons = new LinkedList<>();
    }

    public void queryEntity(final Object obj, String url) {
        JsonArrayRequest json = new JsonArrayRequest(url
                , new Response.Listener<JSONArray>() { @Override public void onResponse(JSONArray response) {
            if (response.length() >= 1) {
                mapToEntity(response, obj);
            } else {
                dataReady = true;
            }
        } }
                , new Response.ErrorListener() { @Override public void onErrorResponse(VolleyError error) { error.printStackTrace(); } });
        requestQueue.add(json);
    }

    public void queryAnswerless(String url) {
        JsonArrayRequest json = new JsonArrayRequest(url
                , new Response.Listener<JSONArray>() { @Override public void onResponse(JSONArray response) { if (response.length() >= 1) { System.out.println("Were you expecting data with that query?"); } else { mappedToDatabase(); }} }
                , new Response.ErrorListener() { @Override public void onErrorResponse(VolleyError error) { error.printStackTrace(); } });
        requestQueue.add(json);
    }

    public void queryEntities(final Object obj, String url) {
        JsonArrayRequest json = new JsonArrayRequest(url
                , new Response.Listener<JSONArray>() { @Override public void onResponse(JSONArray response) {
            if (response.length() >= 1) {
                mapToEntities(response, obj);
            } else {
                dataReady = true;
            }
        } }
                , new Response.ErrorListener() { @Override public void onErrorResponse(VolleyError error) { error.printStackTrace(); } });
        requestQueue.add(json);
    }

    public void queryRunnerQueue(String url) {
        JsonArrayRequest json = new JsonArrayRequest(url
                , new Response.Listener<JSONArray>() { @Override public void onResponse(JSONArray response) {
            if (response.length() >= 1) {
                mapToQueue(response);
            } else {
                dataReady = true;
            }
        } }
                , new Response.ErrorListener() { @Override public void onErrorResponse(VolleyError error) { error.printStackTrace(); } });
        requestQueue.add(json);
    }

    private void mapToEntities(JSONArray json, Object obj) {
        for (int i=0;i<json.length();i++) {
            try {
                if (obj instanceof Lap) {
                    laps.add(new Lap(json.getJSONObject(i)));
                } else if (obj instanceof Person) {
                    persons.add(new Person(json.getJSONObject(i)));
                } else if (obj instanceof Prize) {
                    prizes.add(new Prize(json.getJSONObject(i)));
                } else if (obj instanceof Team) {
                    teams.add(new Team(json.getJSONObject(i)));
                } else if (obj instanceof Training) {
                    trainings.add(new Training(json.getJSONObject(i)));
                } else if (obj instanceof Achievement) {
                    if (json.getJSONObject(i).get("achievementType").equals("RunnerAchievement")) {
                        achievements.add(new RunnerAchievement(json.getJSONObject(i)));
                    } else if (json.getJSONObject(i).get("achievementType").equals("TeamAchievement")) {
                        achievements.add(new TeamAchievement(json.getJSONObject(i)));
                    }
                } else if (obj instanceof Person_has_prize) {
                    person_has_prizes.add(new Person_has_prize(json.getJSONObject(i)));
                } else if (obj instanceof Team_has_person) {
                    team_has_persons.add(new Team_has_person(json.getJSONObject(i)));
                }

            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        this.dataReady = true;
    }

    private void mapToQueue(JSONArray json) {
        Person tmpPerson;
        for (int i=0;i<json.length();i++) {
            try {
                tmpPerson = new Person(json.getJSONObject(i));
                runnerQueue.put(tmpPerson, json.getJSONObject(i).getInt("qID"));
                persons.add(tmpPerson);
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        this.dataReady = true;
    }

    private void mapToEntity(JSONArray json, Object obj) {
        try {
            if (obj instanceof Lap) {
                lap = new Lap(json.getJSONObject(0));
            } else if (obj instanceof Person) {
                person = new Person(json.getJSONObject(0));
            } else if (obj instanceof Prize) {
                prize = new Prize(json.getJSONObject(0));
            } else if (obj instanceof Team) {
                team = new Team(json.getJSONObject(0));
            } else if (obj instanceof Training) {
                training = new Training(json.getJSONObject(0));
            } else if (obj instanceof Achievement) {
                if (json.getJSONObject(0).get("achievementType").equals("RunnerAchievement")) {
                    achievement = new RunnerAchievement(json.getJSONObject(0));
                } else if (json.getJSONObject(0).get("achievementType").equals("TeamAchievement")) {
                    achievement = new TeamAchievement(json.getJSONObject(0));
                }
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
        this.dataReady = true;
    }

    private void mappedToDatabase() {
        this.dataPushed = true;
    }

    // Easter egg
    @Override
    public String toString() { return "Up Up Down Down Left Right Left Right B A Start, I get infinite fans!"; }

    EntityMapper() {
        initiateSingletons();
        dataGrabbed();
    }
}