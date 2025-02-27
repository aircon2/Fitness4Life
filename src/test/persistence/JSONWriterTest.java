package persistence;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;


import model.Day;
import model.Exercise;
import model.ArmExercise;
import model.LegExercise;
import model.Person;
import model.WeeklySchedule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONWriterTest extends JSONTest{
    @Test
    void testWriterInvalidFile() {
        try {
            WeeklySchedule ws = new WeeklySchedule("ws");
            JSONWriter writer = new JSONWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWeeklySchedule() {
        try {
            WeeklySchedule ws = new WeeklySchedule("ws");
            JSONWriter writer = new JSONWriter("./data/testWriterEmptyWeeklySchedule.json");
            writer.open();
            writer.write(ws);
            writer.close();

            JSONReader reader = new JSONReader("./data/testWriterEmptyWeeklySchedule.json");
            ws = reader.read();
            assertEquals("My Schedule", ws.getName());
            assertEquals(0, ws.allExercises().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWeeklySchedule() {
        try {
            WeeklySchedule ws = new WeeklySchedule("ws");
            ArmExercise a = new ArmExercise("dumbbell", 1, 1, 1);
            LegExercise l = new LegExercise("squat", 1, 1, 1);
            ws.allExercises().get(0).add(a);
            ws.allExercises().get(1).add(l);
            JSONWriter writer = new JSONWriter("./data/testWriterGeneralWeeklySchedule.json");
            writer.open();
            writer.write(ws);
            writer.close();

            

            JSONReader reader = new JSONReader("./data/testWriterGeneralWeeklySchedule.json");
            ws = reader.read();
            assertEquals("My Schedule", ws.getName());
            ArrayList<ArrayList<Exercise>> exercises = ws.allExercises();
            assertEquals(7, exercises.size());
            assertEquals(1, exercises.get(0).size());
            assertEquals(1, exercises.get(1).size());
            checkExercise("dumbbell", 1, 1, 1, exercises.get(0).get(0)); 
            checkExercise("squat", 1, 1, 1, exercises.get(1).get(0)); 

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
