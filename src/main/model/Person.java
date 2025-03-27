package model;

import org.json.JSONObject;

import persistence.Writable;

// person with name, target calories, and time for each week they want to exercise
public class Person implements Writable {
    private String name;
    private int targetCalories;
    private int currentCalories;
    private int time;

    // create a new person with a name and target calories, time for exercises, and empty weekly schedule
    public Person(String name, int targetCalories, int time, int currentCalories) {
        this.name = name;
        this.targetCalories = targetCalories;
        this.time = time;
        this.currentCalories = currentCalories;
    }


    // REQUIRES: string length >= 0 
    // MODIFIES: this
    // EFFECTS: Set a name for given person
    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES: time >= 0
    // MODIFIES: this
    // EFFECTS: set time each person will workout for per day
    public void setTime(int time) {
        this.time = time;
        EventLog.getInstance().logEvent(new Event("Time set to " +  this.time));
    }

    // REQUIRES: calories >= 0
    // MODIFIES: this
    // EFFECTS: Set the calories target for the person
    public void setTargetCalories(int calories) {
        this.targetCalories = calories;
        EventLog.getInstance().logEvent(new Event("Target set to " +  this.targetCalories));
    }

    // REQUIRES: calories >= 0
    // MODIFIES: this
    // EFFECTS: Add to the calories target for the person
    public void addTargetCalories(int calories) {
        targetCalories += calories;
        EventLog.getInstance().logEvent(new Event("Updated target calories for the week, target is now:  " 
                                                  + targetCalories));
    }

    // REQUIRES: calories >= 0
    // MODIFIES: this
    // EFFECTS: Add to the curernt calories for the person
    public void addCurrentCalories(int calories) {
        currentCalories += calories;
        EventLog.getInstance().logEvent(new Event("Updated current calories for the week, current is now:  " 
                                                  + currentCalories));
    }

    // REQUIRES: calories >= 0
    // MODIFIES: this
    // EFFECTS: substract to the curernt calories for the person
    public void substractCurrentCalories(int calories) {
        currentCalories -= calories;
    }


    // EFFECTS: get the curernt calories for the person
    public int getCurrentCalories() {
        return currentCalories;
    }

    //EFFECTS: return the name of the person
    public String getName() {
        return name;
    }

    //EFFECTS: return the time each person works out for
    public int getTime() {
        return time;
    }

    //EFFECTS: return the caloric target of the person
    public int getTargetCalories() {
        return targetCalories;
    }

    @Override
    // EFFECTS: creates new JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("targetCalories", targetCalories);
        json.put("time", time);
        json.put("currentCalories", currentCalories);
        return json;
    }


}
