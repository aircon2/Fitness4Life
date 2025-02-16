package model;

import java.util.ArrayList;

// Person who wants a fitness schedule, keeps track of individual schedules, target caloric goal
public class Person {
    private String name;
    private int targetCalories;
    private int time;
    private WeeklySchedule schedule;

    // create a new person with a name and target calories and empty weekly schedule
    public Person(String name, int targetCalories) {
        this.name = name;
        this.targetCalories = targetCalories;
        time = 0;
        schedule = new WeeklySchedule();
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
    }

    // REQUIRES: calories >= 0
    // MODIFIES: this
    // EFFECTS: Set the calories target for the person
    public void setCalories(int calories) {
        this.targetCalories = calories;
    }

    // REQUIRES: calories >= 0
    // MODIFIES: this
    // EFFECTS: Add to the calories target for the person
    public void addCalories(int calories) {
        targetCalories += calories;
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

}
