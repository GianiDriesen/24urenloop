package entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Giani-Luigi Driesen on 1/12/2016
 */

public class Team_has_person {
    private int person_id;
    private int team_id;

    /** Empty constructor; only needed as a hack to get toString() methods to identify entity types */
    public Team_has_person() {}



    public Team_has_person(JSONObject obj) {
        try {
            this.person_id = obj.getInt("person_id");
            this.team_id = obj.getInt("team_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }


    public int getPrize_id() {
        return team_id;
    }

    public void setPrize_id(int prize_id) {
        this.team_id = prize_id;
    }

    @Override
    public String toString() {
        return "PersonID: "+person_id+" & TeamID: "+team_id;
    }
}

