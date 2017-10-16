package entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Giani-Luigi Driesen on 1/12/2016
 */

@SuppressWarnings("deprecation")
public class Lap {
    private int id;
    private Timestamp beginDateTime;
    private Timestamp endDateTime;
    private int person_id = 0; // 0 -> no person

    /** Empty constructor; only needed as a hack to get toString() methods to identify entity types */
    public Lap() {}

    public Lap(JSONObject obj) {
        try {
            this.id = obj.getInt("id");
            try{
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
                Date parsedBeginDate = dateFormat.parse(obj.getString("beginDateTime"));
                this.beginDateTime = new Timestamp(parsedBeginDate.getTime());
                Date parsedEndDate = dateFormat.parse(obj.getString("endDateTime"));
                this.endDateTime = new Timestamp(parsedEndDate.getTime());
            } catch(Exception e){
                System.out.println("Error while parsing date in right format");
            }
            this.person_id = obj.getInt("person_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Lap(int id, Timestamp beginDateTime, Timestamp endDateTime, int person) {
        this.id = id;
        this.beginDateTime = beginDateTime;
        this.endDateTime = endDateTime;
        this.person_id = person;
    }

    public Lap(Timestamp beginDateTime, Timestamp endDateTime, int person) {
        this.beginDateTime = beginDateTime;
        this.endDateTime = endDateTime;
        this.person_id = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(Timestamp beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getPersonId() {
        return person_id;
    }

    public void setPersonId(int person) {
        this.person_id = person;
    }

    @Override
    public String toString() {
        return "Lap "+id;
    }

    public List<String> getExportableFields() {
        List<String> fields = new ArrayList<>();
        fields.add("This lap is from the year "+(1900+beginDateTime.getYear()));
        long timeBetween = endDateTime.getTime()-beginDateTime.getTime(); // The time between in ms
        int ms = (int) timeBetween % 1000;
        int secondsTotal = (int) timeBetween / 1000;
        int seconds = secondsTotal % 60;
        int minutes = secondsTotal / 60;
        fields.add("You ran the lap in "+minutes+":"+seconds+"."+ms);
        return fields;
    }

    public List<String> getExportableFieldsAdmin() {
        List<String> fields = new ArrayList<>();
        fields.add("This lap is from the year "+(1900+beginDateTime.getYear()));
        long timeBetween = endDateTime.getTime()-beginDateTime.getTime(); // The time between in ms
        int ms = (int) timeBetween % 1000;
        int secondsTotal = (int) timeBetween / 1000;
        int seconds = secondsTotal % 60;
        int minutes = secondsTotal / 60;
        fields.add("Laptime: "+minutes+":"+seconds+"."+ms);
        return fields;
    }
}
