package persistence;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;


import model.Exercise;
import model.ArmExercise;
import model.LegExercise;
import model.Person;
import model.WeeklySchedule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class JSONWriterTest extends JSONTest{
    @Test
    void testWriterInvalidFile() {
        try {
            WeeklySchedule ws = new WeeklySchedule("My ws", new Person("Angela", 0, 0));
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWeeklySchedule() {
        try {
            WeeklySchedule ws = new WeeklySchedule("My ws", new Person("Angela", 0, 0));
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWeeklySchedule.json");
            writer.open();
            writer.write(ws);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWeeklySchedule.json");
            ws = reader.read();
            assertEquals("My ws", ws.getName());
            assertEquals(7, ws.allExercises().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWeeklySchedule() {
        try {
            WeeklySchedule ws = new WeeklySchedule("My ws", new Person("Angela", 0, 0));
            ArmExercise a = new ArmExercise("dumbbell", 1, 1, 1);
            LegExercise l = new LegExercise("squat", 1, 1, 1);
            ws.allExercises().get(0).add(a);
            ws.allExercises().get(1).add(l);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWeeklySchedule.json");
            writer.open();
            writer.write(ws);
            writer.close();

            

            JsonReader reader = new JsonReader("./data/testWriterGeneralWeeklySchedule.json");
            ws = reader.read();
            assertEquals("My ws", ws.getName());
            ArrayList<ArrayList<Exercise>> exercises = ws.allExercises();
            assertEquals(7, exercises.size());
            assertEquals(1, exercises.get(0).size());
            assertEquals(1, exercises.get(1).size());
            checkExercise("dumbbell", 1, 1, 1, exercises.get(0).get(0)); 
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
