package entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 05/12/2016
 * @author Koen
 */

public class RunnerAchievement implements Achievement {
    private int id;
    private String name;
    private String description;

    /** Empty constructor; only needed as a hack to get toString() methods to identify entity types */
    public RunnerAchievement() {}

    public RunnerAchievement(JSONObject obj) {
        try {
            this.id = obj.getInt("id");
            this.name = obj.getString("name").replace('_', ' ');
            this.description = obj.getString("description").replace('_', ' ');
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public RunnerAchievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RunnerAchievement(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String printType() {
        return "RunnerAchievement";
    }

    @Override
    public List<String> getExportableFields() {
        List<String> fields = new ArrayList<>();
        fields.add("This achievement is for Runners");
        fields.add("Name: "+name);
        fields.add("Description: "+description);
        return fields;
    }
}
