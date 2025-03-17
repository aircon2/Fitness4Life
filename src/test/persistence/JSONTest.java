package persistence;
import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Exercise;


public class JsonTest {
    protected void checkExercise(String name, int rep, int time, int cals, Exercise e) {
        assertEquals(name, e.getName());
        assertEquals(rep, e.getReps());
        assertEquals(time, e.getTimeForExercise());
        assertEquals(cals, e.getCaloriesBurned());
    }
}



