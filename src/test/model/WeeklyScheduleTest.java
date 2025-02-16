package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WeeklyScheduleTest {
    private WeeklySchedule weeklySchedule;

    @BeforeEach
    void runBefore() {
        weeklySchedule = new WeeklySchedule(); 

    }

    @Test
    void testConstructor() {
        ArrayList<Day> schedule = weeklySchedule.getSchedule();
        assertEquals(7, schedule.size()); 
        assertEquals("monday", schedule.get(0).getName());
        assertEquals("sunday", schedule.get(6).getName());
    }

   
    @Test
    void testGetDayValid() {
        assertNotNull(weeklySchedule.getDay("monday"));
        assertEquals("monday", weeklySchedule.getDay("monday").getName());
    }

    
    @Test
    void testGetDayInvalid() {
        assertNull(weeklySchedule.getDay("nonexistent-day"));
    }

   
    @Test
    void testSetTypeValid() {
        weeklySchedule.setType("monday", "leg");
        assertEquals("leg", weeklySchedule.getDay("monday").getType());

        weeklySchedule.setType("tuesday", "arm");
        assertEquals("arm", weeklySchedule.getDay("tuesday").getType());
    }

    
    @Test
    void testSetTypeInvalid() {
        weeklySchedule.setType("monday", "invalidType");
        assertNotEquals("invalidType", weeklySchedule.getDay("monday").getType()); 
    }

    
    @Test
    void testValidDayExists() {
        assertNotNull(weeklySchedule.validDay("friday"));
    }

   
    @Test
    void testValidDayNotExists() {
        assertNull(weeklySchedule.validDay("randomDay"));
    }

   
    @Test
    void testRemoveExercise() {
        Day monday = weeklySchedule.getDay("monday");
        Exercise exercise = new ArmExercise("Push-ups", 100, 10, 1);
        monday.addExercise(exercise);
    
        ArrayList<Integer> result = weeklySchedule.removeExercise("monday", "Push-ups");
        
        assertEquals(10, result.get(0)); 
        assertEquals(0, monday.getExercisesForTheDay().size());
    }
    

   
    @Test
    void testRemoveExerciseNotFound() {
        ArrayList<Integer> result = weeklySchedule.removeExercise("monday", "Push-ups");
        assertEquals(0, result.get(0)); 
    }

    
    @Test
    void testClearExercise() {
        Day tuesday = weeklySchedule.getDay("tuesday");
        tuesday.addExercise(new LegExercise("Squats", 50, 5, 5));
        tuesday.addExercise(new ArmExercise("Burpees", 75, 4, 5));
    
        ArrayList<Integer> result = weeklySchedule.clearExercise("tuesday");
    
        assertEquals(45, result.get(0)); 
        assertEquals(625, result.get(1)); 
        assertEquals(0, tuesday.getExercisesForTheDay().size()); 
    }
    

   
     @Test
    void testClearExerciseWhenEmpty() {
        ArrayList<Integer> result = weeklySchedule.clearExercise("sunday"); 

        assertEquals(0, result.get(0));
    }

    @Test
    void testClearExerciseWhenInvalid() {
        ArrayList<Integer> result = weeklySchedule.clearExercise("wegwu");

        assertEquals(0, result.get(0));
    }


   
    @Test
    void testPrintTypeDay() {
        weeklySchedule.setType("monday", "leg");
        weeklySchedule.setType("tuesday", "arm");
        weeklySchedule.printTypeDay(); 
    }

    
    @Test
    void testGetSchedule() {
        assertEquals(7, weeklySchedule.getSchedule().size());
    }
}

