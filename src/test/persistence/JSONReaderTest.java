package persistence;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import model.Exercise;
import model.Person;
import model.WeeklySchedule;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

 class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WeeklySchedule ws = reader.readWS();
            Person person = reader.readP();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWeeklySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWeeklySchedule.json");
        try {
            WeeklySchedule ws = reader.readWS();
            Person person = reader.readP();
            assertEquals("My ws", ws.getName());
            assertEquals("Angela", person.getName());
            assertEquals(0, person.getTargetCalories());
            assertEquals(0, person.getTime());
            assertEquals(0, person.getCurrentCalories());
            assertEquals(7, ws.allExercises().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWeeklySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWeeklySchedule.json");
        try {
            WeeklySchedule ws = reader.readWS();
            assertEquals("My ws", ws.getName());
            Person person = reader.readP();
            assertEquals("John Doe", person.getName());
            assertEquals(2000, person.getTargetCalories());
            assertEquals(60, person.getTime());
            assertEquals(60, person.getCurrentCalories());

            ArrayList<String> types = ws.type();
            assertEquals(7, types.size());
            assertEquals("arm", types.get(0));
            assertEquals("arm", types.get(1));

            ArrayList<ArrayList<Exercise>> exercises = ws.allExercises();
            assertEquals(7, exercises.size());
            assertEquals(1, exercises.get(0).size());
            assertEquals(2, exercises.get(1).size());
            checkExercise("squat", 1, 3, 1, exercises.get(1).get(0)); 

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
