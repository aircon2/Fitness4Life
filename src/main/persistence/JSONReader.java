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

// Represents a person that has a WeeklySchedule from JSON data stored in file
public class JSONReader {
    private String source;

    // EFFECTS: constructs person that has a WeeklySchedule from source file
    public JSONReader(String source) {
    }

    // EFFECTS: gets WeeklySchedule from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WeeklySchedule read() throws IOException {
       return null;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return "";
    }

    // EFFECTS: parses WeeklySchedule from JSON object and returns it
    private WeeklySchedule parseWorkRoom(JSONObject jsonObject) {
        return null;
    }

    // MODIFIES: ws
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addThingies(WeeklySchedule ws, JSONObject jsonObject) {

    }

    // MODIFIES: ws
    // EFFECTS: parses thingy from JSON object and adds it to WeeklySchedule
    private void addThingy(WeeklySchedule ws, JSONObject jsonObject) {

    }
}
