package entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Giani-Luigi Driesen on 1/12/2016
 */

public class Person_has_prize {
    private int person_id;
    private int prize_id;
    private int isRecieved;

    /** Empty constructor; only needed as a hack to get toString() methods to identify entity types */
    public Person_has_prize() {}



    public Person_has_prize(JSONObject obj) {
        try {
            this.person_id = obj.getInt("person_id");
            this.prize_id = obj.getInt("prize_id");
            this.isRecieved = obj.getInt("isRecieved");
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

    public int getIsRecieved() {
        return isRecieved;
    }

    public void setIsRecieved(int isRecieved) {
        this.isRecieved = isRecieved;
    }

    public int getPrize_id() {
        return prize_id;
    }

    public void setPrize_id(int prize_id) {
        this.prize_id = prize_id;
    }

    @Override
    public String toString() {
        String notRecieved = "not recieved yet";
        String isRecieved = "recieved yet";
        String string;
        if (getIsRecieved()==0) {string = notRecieved;} else {string = isRecieved;}
        return "PersonID: "+person_id+" & PrizeID: "+prize_id+" and is "+string;
    }
}

