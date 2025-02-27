package persistence;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import model.Exercise;
import model.Day;
import model.Exercise;
import model.Person;
import model.WeeklySchedule;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

 class JSONReaderTest extends JSONTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WeeklySchedule ws = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWeeklySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWeeklySchedule.json");
        try {
            WeeklySchedule ws = reader.read();
            assertEquals("My ws", ws.getName());
            assertEquals(7, ws.allExercises().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWeeklySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWeeklySchedule.json");
        try {
            WeeklySchedule ws = reader.read();
            assertEquals("My ws", ws.getName());
            ArrayList<ArrayList<Exercise>> exercises = ws.allExercises();
            assertEquals(7, exercises.size());
            assertEquals(6, exercises.get(0).size());
            assertEquals(0, exercises.get(1).size());
            checkExercise("squat", 1, 3, 1, exercises.get(0).get(0)); 

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
