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
        JSONReader reader = new JSONReader("./data/noSuchFile.json");
        try {
            WeeklySchedule ws = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWeeklySchedule() {
        JSONReader reader = new JSONReader("./data/testReaderEmptyWorkRoom.json");
        try {
            WeeklySchedule ws = reader.read();
            assertEquals(7, ws.allExercises().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWeeklySchedule() {
        JSONReader reader = new JSONReader("./data/testReaderGeneralWorkRoom.json");
        try {
            WeeklySchedule ws = reader.read();
    
            ArrayList<ArrayList<Exercise>> exercises = ws.allExercises();
            assertEquals(7, exercises.size());
            assertEquals(1, exercises.get(0).size());
            assertEquals(1, exercises.get(1).size());
            checkExercise("dumbbell", 1, 1, 1, exercises.get(0).get(0)); 
            checkExercise("squat", 1, 1, 1, exercises.get(1).get(0)); 

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
