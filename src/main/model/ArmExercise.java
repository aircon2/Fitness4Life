package model;

// List of Arm exercises with time intervals, calories burned, and set of reps
public class ArmExercise implements Exercise {
    private String name;
    private int time;
    private int caloriesBurned;
    private int reps;

    //EFFECTS: create a new Arm Exercise with calories, time intervals, set of reps
    public ArmExercise(String name, int time, int caloriesBurned, int reps) {
       //TODO
    }

    @Override
    // REQUIRES: string length > 0 
    // MODIFIES: this
    // EFFECTS: Set a name for given arm exercise 
    public void setName(String name) {
        
    }

    @Override
    // REQUIRES: time >= 0
    // MODIFIES: this
    // EFFECTS: Set a time for given arm exercise 
    public void setTime(int time) {
        
    }

    @Override
    // REQUIRES: caloriesBurned >= 0
    // MODIFIES: this
    // EFFECTS: Set the calories burned for given arm exercise 
    public void setCaloriesBurned(int calories) {
        
    }

    @Override
    // REQUIRES: reps >= 0
    // MODIFIES: this
    // EFFECTS: Set the number of reps for given arm exercise 
    public void setReps(int reps) {
        
    }

    @Override
     // EFFECTS: returns name for arm exercise 
     public String getName() {
        return "";
    }

    @Override
    // EFFECTS: returns time for arm exercise 
    public int getTimeForExercise() {
        return -1;
    }

    @Override
    // EFFECTS: returns time for arm exercise 
    public int getCaloriesBurned() {
        return -1;
    }

    @Override
    // EFFECTS: returns reps for arm exercise 
    public int getReps() {
        return -1;
    }

}
