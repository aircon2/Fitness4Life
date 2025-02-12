package model;

// List of leg exercises with time intervals, calories burned, and set of reps
public class LegExercise implements Exercise {
    private String name;
    private int time;
    private int caloriesBurned;
    private int reps;

    //EFFECTS: create a new Leg Exercise with calories, time intervals, set of reps
    public LegExercise(String name, int time, int caloriesBurned, int reps) {
       //TODO
    }

    // REQUIRES: string length > 0 
    // MODIFIES: this
    // EFFECTS: Set a name for given leg exercise 
    public void setName() {
        
    }

    // REQUIRES: time >= 0
    // MODIFIES: this
    // EFFECTS: Set a time for given leg exercise 
    public void setTime() {
        
    }

    // REQUIRES: caloriesBurned >= 0
    // MODIFIES: this
    // EFFECTS: Set the calories burned for given leg exercise 
    public void setCaloriesBurned() {
        
    }
    // REQUIRES: reps >= 0
    // MODIFIES: this
    // EFFECTS: Set the number of reps for given leg exercise 
    public void setReps() {
        
    }

     // EFFECTS: returns name for leg exercise 
     public String getName() {
        return "";
    }

    // EFFECTS: returns time for leg exercise 
    public int getTimeForExercise() {
        return -1;
    }

    // EFFECTS: returns time for leg exercise 
    public int getCaloriesBurned() {
        return -1;
    }

    // EFFECTS: returns reps for leg exercise 
    public int getReps() {
        return -1;
    }

}

 


