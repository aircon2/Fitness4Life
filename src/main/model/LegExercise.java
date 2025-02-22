package model;

import org.json.JSONObject;

import persistence.Writable;

// leg exercises that can be recorded with name, time, calories burned, and reps
public class LegExercise implements Exercise, Writable {
    private String name;
    private int time;
    private int caloriesBurned;
    private int reps;

    //EFFECTS: create a new Leg Exercise with name, calories, time intervals, set of reps
    public LegExercise(String name, int time, int caloriesBurned, int reps) {
        this.name = name;
        this.time = time;
        this.caloriesBurned = caloriesBurned;
        this.reps = reps;
    }

    @Override
    // REQUIRES: string length > 0 
    // MODIFIES: this
    // EFFECTS: Set a name for given leg exercise 
    public void setName(String name) {
        this.name = name;
    }

    @Override
    // REQUIRES: time >= 0
    // MODIFIES: this
    // EFFECTS: Set a time for given leg exercise 
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    // REQUIRES: caloriesBurned >= 0
    // MODIFIES: this
    // EFFECTS: Set the calories burned for given leg exercise 
    public void setCaloriesBurned(int calories) {
        this.caloriesBurned = calories;
    }

    @Override
    // REQUIRES: reps >= 0
    // MODIFIES: this
    // EFFECTS: Set the number of reps for given leg exercise 
    public void setReps(int reps) {
        this.reps = reps;
    }

    @Override
    // EFFECTS: returns name for leg exercise 
    public String getName() {
        return name;
    }

    @Override
    // EFFECTS: returns time for leg exercise 
    public int getTimeForExercise() {
        return time;
    }

    @Override
    // EFFECTS: returns number of calories burned for exercise
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    @Override
    // EFFECTS: returns reps for leg exercise 
    public int getReps() {
        return reps;
    }


     @Override
    // EFFECTS: creates new JSON object
    public JSONObject toJson() {
        return null;
    }

}

 


