package persistence;

import model.Day;
import model.Exercise;
import model.LegExercise;
import model.Person;
import model.WeeklySchedule;
import model.ArmExercise;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;
//CITATION: CODE MODELED FROM JSONSERIALIZATION DEMO
// Represents a person that has a WeeklySchedule from JSON data stored in file

public class JsonReader {
    private String source;

    // EFFECTS: constructs person that has a WeeklySchedule from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: gets WeeklySchedule from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WeeklySchedule readWS() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWeeklySchedule(jsonObject);
    }

    // EFFECTS: gets Person from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Person readP() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject personJson = jsonObject.getJSONObject("person");
        return parsePerson(personJson);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses person from JSON object and returns it
    private Person parsePerson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int targetCalories = jsonObject.getInt("targetCalories");
        int time = jsonObject.getInt("time");
        int currentCalories = jsonObject.getInt("currentCalories");
        Person person = new Person(name, targetCalories, time, currentCalories);
        return person;
    }

    // EFFECTS: parses WeeklySchedule from JSON object and returns it
    private WeeklySchedule parseWeeklySchedule(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONObject personJson = jsonObject.getJSONObject("person");
        Person person = parsePerson(personJson);

        WeeklySchedule ws = new WeeklySchedule(name, person); // Pass person to constructor

        // Retrieve types from JSON and assign to each day dynamically
        JSONArray typesArray = jsonObject.getJSONArray("types");
        int index = 0;
    
        for (Day d : ws.getSchedule()) {
            if (index < typesArray.length()) {
                String type = typesArray.getString(index);
                d.setType(type);
            }
            index++;
        }
        addExercises(ws, jsonObject);
        return ws;
    }

    // MODIFIES: ws
    // EFFECTS: parses exercises from JSON object and adds them to WeeklySchedule
    private void addExercises(WeeklySchedule ws, JSONObject jsonObject) {
        JSONArray outerArray = jsonObject.getJSONArray("exercises");
    
        for (int i = 0; i < outerArray.length(); i++) {
            JSONArray innerArray = outerArray.getJSONArray(i);
            for (int j = 0; j < innerArray.length(); j++) {
                JSONObject exerciseObject = innerArray.getJSONObject(j);
                addExercise(ws, exerciseObject, i); 
            }
        }
    }


    // MODIFIES: ws
    // EFFECTS: parses thingy from JSON object and adds it to WeeklySchedule
    private void addExercise(WeeklySchedule ws, JSONObject jsonObject, int dayIndex) {
        String day = getDay(dayIndex);
        String name = jsonObject.getString("name");
        int reps = jsonObject.getInt("reps");
        int cals = jsonObject.getInt("cals");
        int time = jsonObject.getInt("time");
        Exercise exercise = new LegExercise(name, time, cals, reps);
        if (ws.getSchedule().get(dayIndex).getType() ==  "arm") {
            exercise = new ArmExercise(name, time, cals, reps);
        }
        ws.getDay(day).addExercise(exercise);
    }

    // EFFECTS: returns the day based on the index position of the array list 
    private String getDay(int dayIndex) {
        String temp = "";
        if (dayIndex == 0) {
            temp = "monday";
        } else if (dayIndex == 1) {
            temp = "tuesday";
        } else if (dayIndex == 2) {
            temp = "wednesday";
        } else if (dayIndex == 3) {
            temp = "thursday";
        } else if (dayIndex == 4) {
            temp = "friday";
        } else if (dayIndex == 5) {
            temp = "saturday";
        } else if (dayIndex == 6) {
            temp = "sunday";
        } else {
            temp = "monday";
        }

        return temp;

    }

   
}
