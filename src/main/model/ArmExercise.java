package model;

import ui.Exercise;

// List of Arm exercises with time intervals, calories burned, and set of reps
public class ArmExercise implements Exercise {
    private int time;
    private int caloriesBurned;
    private int reps;

    //EFFECTS: create a new Arm Exercise with calories, time intervals, set of reps
    public void ArmExercise(int time, int caloriesBurned, int reps) {
        this.time = time;
        this.caloriesBurned = caloriesBurned;
        this.reps = reps;
    }

}
