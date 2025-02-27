package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// weekly schedule to keep track of workouts
public class WeeklySchedule implements Writable {
    protected ArrayList<Day> schedule;
    protected String name;
    protected Person person;

    // creates a weekly schedule list of 7 days of the week 
    public WeeklySchedule(String name, Person person) {
        this.person = person;
        this.name = name;
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


    //EFFECTS: return the name of the owner of the schedule
    public String getName() {
        return name;
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

    //EFFECTS: return the person who this schedule belongs to
    public Person getPerson() {
        return person;
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
                } 
            
            } 
        }
    }

    //EFFECTS: checks if day entered is a day of the week, returns the day if it matches or returns null if not found
    public Day validDay(String day) {
        for (Day d : schedule) {
            if (d.getName().equals(day)) {
                return d;
            }
        }
        return null;
    }

    //EFFECTS: returns the days and their types
    public ArrayList<String> typeDay() {
        ArrayList<String> returns = new ArrayList<>();
        for (Day d : schedule) {
            if (d.getType() != "") {
                returns.add(d.getName() + " - " + d.getType());
            }
        }
        return returns;
    }

     //EFFECTS: returns list of types for each day
    public ArrayList<String> type() {
        ArrayList<String> returns = new ArrayList<>();
        for (Day d : schedule) {
            if (d.getType() != "") {
                returns.add(d.getType());
            } else {
                returns.add("");
            }
        }
        return returns;
    }


    //EFFECTS: the max number of exercises throughout all days
    public int maxExercises() {
        int maxExercises = 0;
        for (Day day : schedule) {
            if (day.getExercisesForTheDay().size() > maxExercises) {
                maxExercises = day.getExercisesForTheDay().size();
            }
        }
        return maxExercises;
    }

    //EFFECTS: returns the list of list of exercises for each day 
    public ArrayList<ArrayList<Exercise>> allExercises() {
        ArrayList<ArrayList<Exercise>> returns = new ArrayList<>();
        for (Day day : schedule) {
            returns.add(day.getExercisesForTheDay());
        }
        return returns;
    }
    

    //MODIFIES: this
    //EFFECTS: remove the exercise for given day, returns array list of the
    //         amount calories that exercise was worth and returns time it took
    public ArrayList<Integer> removeExercise(String day, String name) {
        ArrayList<Integer> returns = new ArrayList<>();
        for (Day d : schedule) {
            if (d.getName().equals(day)) {
                List<Exercise> exercises = d.getExercisesForTheDay();
                for (int i = 0; i < exercises.size(); i++) {
                    int cals = exercises.get(i).getCaloriesBurned() *  exercises.get(i).getReps();
                    int time = exercises.get(i).getTimeForExercise() *  exercises.get(i).getReps();
                    exercises.remove(i);
                    returns.add(cals);
                    returns.add(time);
                    return returns;
                }
            }
        } 
        returns.add(0);
        return returns;
    }

    //MODIFIES: this
    //EFFECTS: clears all exercises for given day, returns array list of calories 
    //         the amount that exercise was worth and returns time it took
    public ArrayList<Integer> clearExercise(String day) {
        ArrayList<Integer> returns = new ArrayList<>();
        int caloriesWorth = 0;
        int timeWorth = 0;
        for (Day d : schedule) {
            if (d.getName().equals(day)) {
                List<Exercise> exercises = d.getExercisesForTheDay();
                for (int i = 0; i < exercises.size(); i++) {
                    caloriesWorth += exercises.get(i).getCaloriesBurned() * exercises.get(i).getReps();
                    timeWorth += exercises.get(i).getTimeForExercise() *  exercises.get(i).getReps();
                }
                d.cheatDay();
                returns.add(caloriesWorth);
                returns.add(timeWorth);
                return returns;
            }
        } 
        returns.add(0);
        return returns;
    }

    @Override
    // EFFECTS: creates new JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("person", person.toJson());
        json.put("types", typesToJson());
        json.put("exercises", exercisesToJson());
        return json;
    }


    // EFFECTS: returns types of days in this WeeklySchedule as a JSON array
    private JSONArray typesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String t : type()) {
            jsonArray.put(t);
        }
        return jsonArray;
    }


    // EFFECTS: returns exercises in this WeeklySchedule as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Day d : schedule) {
            jsonArray.put(d.exercisesToJson());
        }
        return jsonArray;
    }
}
