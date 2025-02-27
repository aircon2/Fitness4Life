package persistence;
import model.Day;
import model.Exercise;
import model.Person;
import model.WeeklySchedule;
import model.LegExercise; 
import model.ArmExercise;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;
//CITATION: CODE MODELED FROM JSONSERIALIZATION DEMO
// Represents a person that has a WeeklySchedule from JSON data stored in file

public class JSONReader {
    private String source;

    // EFFECTS: constructs person that has a WeeklySchedule from source file
    public JSONReader(String source) {
        this.source = source;
    }

    // EFFECTS: gets WeeklySchedule from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WeeklySchedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWeeklySchedule(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses WeeklySchedule from JSON object and returns it
    private WeeklySchedule parseWeeklySchedule(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WeeklySchedule ws = new WeeklySchedule(name);
        addExercises(ws, jsonObject);
        return ws;
    }

    // MODIFIES: ws
    // EFFECTS: parses exercises from JSON object and adds them to workroom
    private void addExercises(WeeklySchedule ws, JSONObject jsonObject) {
        JSONArray outerArray = jsonObject.getJSONArray("exercises");
    
        for (int i = 0; i < outerArray.length(); i++) {
            JSONArray innerArray = outerArray.getJSONArray(i);
            for (int j = 0; j < innerArray.length(); j++) {
                JSONObject exerciseObject = innerArray.getJSONObject(j);
                addExercise(ws, exerciseObject); 
            }
        }
    }


    // MODIFIES: ws
    // EFFECTS: parses thingy from JSON object and adds it to WeeklySchedule
    private void addExercise(WeeklySchedule ws, JSONObject jsonObject) {
        String day = "monday"; 
        if (jsonObject.has("day")) {
            day = jsonObject.getString("day");
        }
        String name = jsonObject.getString("name");
        int reps = jsonObject.getInt("reps");
        int cals = jsonObject.getInt("cals");
        int time = jsonObject.getInt("time");
        Exercise exercise = new ArmExercise(name, time, cals, reps);
        ws.getDay(day).addExercise(exercise);
    }

   
}
