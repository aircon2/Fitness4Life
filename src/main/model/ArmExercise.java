package model;

import org.json.JSONObject;

import persistence.Writable;

// Arm exercises that can be recorded with name, time, calories burned, and reps
public class ArmExercise implements Exercise, Writable {
    private String name;
    private int time;
    private int caloriesBurned;
    private int reps;

    //EFFECTS: create a new Arm Exercise with calories, time intervals, set of reps
    public ArmExercise(String name, int time, int caloriesBurned, int reps) {
        this.name = name;
        this.time = time;
        this.caloriesBurned = caloriesBurned;
        this.reps = reps;
    }

    @Override
    // REQUIRES: string length > 0 
    // MODIFIES: this
    // EFFECTS: Set a name for given arm exercise 
    public void setName(String name) {
        this.name = name;
    }

    @Override
    // REQUIRES: time >= 0
    // MODIFIES: this
    // EFFECTS: Set a time for given arm exercise 
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    // REQUIRES: caloriesBurned >= 0
    // MODIFIES: this
    // EFFECTS: Set the calories burned for given arm exercise 
    public void setCaloriesBurned(int calories) {
        this.caloriesBurned = calories;
    }

    @Override
    // REQUIRES: reps >= 0
    // MODIFIES: this
    // EFFECTS: Set the number of reps for given arm exercise 
    public void setReps(int reps) {
        this.reps = reps;
    }

    @Override
     // EFFECTS: returns name for arm exercise 
     public String getName() {
        return name;
    }

    @Override
    // EFFECTS: returns time for arm exercise 
    public int getTimeForExercise() {
        return time;
    }

    @Override
    // EFFECTS: returns number of calories burned for exercise
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    @Override
    // EFFECTS: returns reps for arm exercise 
    public int getReps() {
        return reps;
    }


    @Override
    // EFFECTS: creates new JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("reps", reps);
        json.put("time", time);
        json.put("cals", caloriesBurned);
        return json;
    }

}
