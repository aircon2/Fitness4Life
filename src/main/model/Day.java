package model;

import java.util.ArrayList;

public class Day {
    private ArrayList<Exercise> day;

    // Create a Day with list of exercises for each day
    public void Day() {
        //TODO
    }

    // REQUIRES: cannot have the same name as another exercise
    // MODIFIES: this
    // EFFECTS: add a day to workout plan
    public void addExercise(Exercise e) {
    }

    // EFFECTS: return the exercise picked 
    public Exercise getExercise(String Name) {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: clears all exercises from day d
    public void cheatDay() {

    }

    // EFFECTS: return the exercises needed to be completed per day
    public ArrayList<Exercise> getExercisesForTheDay() {
        return null;
    }
}
