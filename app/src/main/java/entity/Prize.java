package entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giani-Luigi Driesen on 1/12/2016
 */
public class Prize {
    private int id;
    private int year;
    private int pointCost;
    private String name;
    private String description;

    /** Empty constructor; only needed as a hack to get toString() methods to identify entity types */
    public Prize() {}

    public Prize(JSONObject obj) {
        try {
            this.id = obj.getInt("id");
            this.year = obj.getInt("year");
            this.pointCost = obj.getInt("pointCost");
            this.name = obj.getString("name").replace('_', ' ');
            this.description = obj.getString("description").replace('_', ' ');
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Prize(int year, int pointCost, String name, String description) {
        this.year = year;
        this.pointCost = pointCost;
        this.name = name;
        this.description = description;
    }

    public Prize(int id, int year, int pointCost, String name, String description) {
        this.id = id;
        this.year = year;
        this.pointCost = pointCost;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPointCost() {
        return pointCost;
    }

    public void setPointCost(int pointCost) {
        this.pointCost = pointCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<String> getExportableFields() {
        List<String> fields = new ArrayList<>();
        fields.add("This prize is for the year "+year);
        fields.add("Name: "+name);
        fields.add("Description: "+description);
        fields.add("Laps to run: "+pointCost);
        return fields;
    }
}
