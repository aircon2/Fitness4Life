package model;
// Interface for Leg and Arm exercises to inherit methods from
public interface Exercise {
    public void setName(String name);
    public void setTime(int time);
    public void setCaloriesBurned(int calories);
    public void setReps(int reps);
    public String getName();
    public int getTimeForExercise();
    public int getCaloriesBurned();
    public int getReps();
   
    
}
