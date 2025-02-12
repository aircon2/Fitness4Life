package model;

import java.util.ArrayList;

// Person who wants a fitness schedule, keeps track of individual schedules, target caloric goal
public class Person {
    private String name;
    private int targetCalories;
    private ArrayList<String> schedule;

    // create a new person with a name and target calories
    public Person(String name, int targetCalories) {
        //TODO Auto-generated constructor stub
    }


    // REQUIRES: string length >= 0 
    // MODIFIES: this
    // EFFECTS: Set a name for given person
    public void setName() {
        
    }

    // REQUIRES: targetCalories >= 0
    // MODIFIES: this
    // EFFECTS: Set the calories target for the person
    public void setCaloriesBurned() {
        
    }

    //EFFECTS: return the name of the person
    public String getName() {
        return "";
    }

     //EFFECTS: return the caloric target of the person
    public int getTargetCalories() {
        return -1;
    }

    //EFFECTS: return the persons list schedule of the week 
    public WeeklySchedule getSchedule() {
        return null;
    }

}
