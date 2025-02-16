package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
    

public class DayTest {
    private Day testDay;
    private ArrayList<Exercise> exercises;
    private Exercise workout;

    @BeforeEach
    void runBefore() {
        testDay = new Day("Monday");
        exercises = new ArrayList<>();
        workout = new ArmExercise("pull up", 20, 100, 10);
    }

    @Test 
    void testConstructor() {
        assertEquals(exercises, testDay.getExercisesForTheDay());
    }

    @Test
    void testAddExercise() {
        testDay.addExercise(workout);
        exercises.add(workout);
        assertEquals(exercises, testDay.getExercisesForTheDay());
    }

    @Test
    void testAddMultipleExercise() {
        Exercise leg = new LegExercise("chair sit", 10, 10 , 10);
        testDay.addExercise(workout);
        testDay.addExercise(leg);
        exercises.add(workout);
        exercises.add(leg);
        assertEquals(exercises, testDay.getExercisesForTheDay());
        assertEquals(leg, testDay.getExercise("chair sit"));
        assertEquals(workout, testDay.getExercise("pull up"));
    }

    @Test
    void testCheatDay() { 
        testDay.addExercise(workout);
        testDay.addExercise(workout);
        testDay.addExercise(workout);
        testDay.cheatDay();
        assertEquals(exercises, testDay.getExercisesForTheDay());
    }

}
