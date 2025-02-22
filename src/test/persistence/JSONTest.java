package persistence;
import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Exercise;
import model.Day;
import model.Exercise;
import model.Person;
import model.WeeklySchedule;


public class JSONTest {
    protected void checkExercise(String name, int rep, int time, int cals, Exercise e) {
        assertEquals(name, e.getName());
        assertEquals(rep, e.getReps());
        assertEquals(time, e.getTimeForExercise());
        assertEquals(cals, e.getCaloriesBurned());
    }
}



