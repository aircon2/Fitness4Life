package model;

import java.util.ArrayList;
import java.util.List;

// Produces weekly schedule and exercises done each day 
public class WeeklySchedule {
    protected ArrayList<Day> schedule;

    // creates a list of 7 days of the week 
    public WeeklySchedule() {
        Day monday = new Day("monday");
        Day tuesday = new Day("tuesday");
        Day wednesday = new Day("wednesday");
        Day thursday = new Day("thursday");
        Day friday = new Day("friday");
        Day saturday = new Day("saturday");
        Day sunday = new Day("sunday");
        schedule = new ArrayList<>();
        schedule.add(monday);
        schedule.add(tuesday);
        schedule.add(wednesday);
        schedule.add(thursday);
        schedule.add(friday);
        schedule.add(saturday);
        schedule.add(sunday);
    }



    //EFFECTS: return the day with (weekday) index position, if none are added, return null
    public Day getDay(String weekday) {
        for (Day d : schedule) {
            if (d.getName().equals(weekday)) {
                return d;
            }
        }
        return null;
    }

    //EFFECTS: prints out the schedule
    public void printSched(int currentCalories, int targetCalories) {
    System.out.printf("Target Calories: %10d   |   Current Calories: %10d\n\n", targetCalories, currentCalories);
    System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |\n",
                      "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

    System.out.println("--------------------------------------------------------------------------------------");
    System.out.print("|");
    for (Day day : schedule) {
        String type = day.getType();  
        System.out.printf(" %-10s |", type);
    }
    System.out.println(); 

    System.out.println("--------------------------------------------------------------------------------------");

    int maxExercises = 0;
    for (Day day : schedule) {
        if (day.getExercisesForTheDay().size() > maxExercises) {
            maxExercises = day.getExercisesForTheDay().size();
        }
    }
    for (int i = 0; i < maxExercises; i++) {
        System.out.print("|"); 
        for (Day day : schedule) {
            List<Exercise> exercises = day.getExercisesForTheDay();
            if (i < exercises.size()) {
                System.out.printf(" %-10s |", exercises.get(i).getName());
            } else {
                System.out.printf(" %-10s |", "");  
            }
        }
        System.out.println();  
    }
    System.out.println("--------------------------------------------------------------------------------------");
}


    //EFFECTS: return the current schedule of exercises
    public ArrayList<Day> getSchedule() {
        return schedule;
    }

    // MODIFIED: this
    // EFFECTS: set the type of day as arm or leg 
    public void setType(String day, String type) {
        for (Day d : schedule) {
            if (d.getName().equals(day)) { 
               if (type.equals("leg") || type.equals("arm")) {
                    d.setType(type);
                    break;
               } else {
                    System.out.println("Not a valid answer");
               }
            }
        }
    }

    //EFFECTS: checks if day entered is a day of the week
    public Day validDay(String day) {
        for (Day d : schedule) {
            if (d.getName().equals(day)) {
                return d;
             }
        }
        return null;
    }

    //EFFECTS: prints out the days with types set 
    public void printTypeDay() {
        for (Day d : schedule) {
            if (d.getType() != "") {
            System.out.println(d.getName() + " - " + d.getType());
            }
        }
    }

    //EFFECTS: remove the exercise for given day, returns the amount that exercise was worth
    public int removeExercise(String day, String name) {
        for (Day d : schedule) {
            if (d.getName().equals(day)) {
                List<Exercise> exercises = d.getExercisesForTheDay();
                for (int i=0; i < exercises.size(); i++) {
                    int cals = exercises.get(i).getCaloriesBurned() *  exercises.get(i).getReps();
                    exercises.remove(i);
                    System.out.println("exercise has been removed");
                    return cals;
                }
            }
        } 
        System.out.println("no exercises to be cleared");
        return 0;
    }

    //REQUIRES: schedule is not empty
    //EFFECTS: clears all exercises for given day, returns number of calories these exercises were worth
    public int clearExercise(String day) {
        int caloriesWorth = 0;
        for (Day d : schedule) {
            if (d.getName().equals(day)) {
                List<Exercise> exercises = d.getExercisesForTheDay();
                for (int i=0; i < exercises.size(); i++) {
                    caloriesWorth += exercises.get(i).getCaloriesBurned() * exercises.get(i).getReps();
                }
                d.cheatDay();
                System.out.println("Exercises have been cleared!");
                return caloriesWorth;
            }
        } 
        System.out.println("no exercises to be cleared");
        return 0;
    }



}
