package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

// Day with name and type of day (arm or leg)
public class Day {
    private ArrayList<Exercise> day;
    private String name;
    private String type;

    // Create a Day with list of exercises for each day that has name, type, and empty list of days
    public Day(String name) {
        this.name = name;
        type = "";
        day = new ArrayList<>();
    }

    // REQUIRES: cannot have the same name as another exercise
    // MODIFIES: this
    // EFFECTS: add an exercise to daily workout plan
    public void addExercise(Exercise e) {
        day.add(e);
    }

    // EFFECTS: return the exercise that matches name, if not return null
    public Exercise getExercise(String name) {
        for (Exercise e : day) {
            if (e.getName() == name) {
                return e;
            }
        }
        return null;
    }

    // EFFECTS: returns name of day
    public String getName() {
        return name;
    }

    //REQUIRES: type is either "arm" or "leg"
    // MODIFIES: this
    // EFFECTS: returns type of day
    public void setType(String type) {
        this.type = type;
    }


    // EFFECTS: returns type of day
    public String getType() {
        return type;
    }

    // MODIFIES: this
    // EFFECTS: clears all exercises from day d
    public void cheatDay() {
        day.clear();
    }

    // EFFECTS: return the exercises needed to be completed per day
    public ArrayList<Exercise> getExercisesForTheDay() {
        return day;
    }


    // EFFECTS: creates new JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("exercises", exercisesToJson());
        return json;
    }

     // EFFECTS: returns exercises in this Day as a JSON array
    public JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < getExercisesForTheDay().size(); i++) {
            jsonArray.put(getExercisesForTheDay().get(i).toJson());
        } 
        return jsonArray;
    }


}
