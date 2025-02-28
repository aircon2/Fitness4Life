package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WeeklyScheduleTest {
    private WeeklySchedule weeklySchedule;

    @BeforeEach
    void runBefore() {
        weeklySchedule = new WeeklySchedule("ws", new Person("nice", 0, 0, 0)); 

    }

    @Test
    void testGetName(){
        assertEquals("ws", weeklySchedule.getName());
        assertEquals("nice", weeklySchedule.getPerson().getName());
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
        ArrayList<Integer> result = weeklySchedule.removeExercise("monday", "NA");
        assertEquals(0, result.get(0));
        Day monday = weeklySchedule.getDay("monday");
        monday.addExercise(new ArmExercise("Jumping Jacks", 50, 5, 10)); 
        assertEquals(1, result.size());
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
        ArrayList<String> temp = new ArrayList<>();
        weeklySchedule.setType("monday", "leg");
        weeklySchedule.setType("tuesday", "arm");
        temp.add("monday - leg");
        temp.add("tuesday - arm");
        assertEquals(temp, weeklySchedule.typeDay()); 
    }

    
    @Test
    void testGetSchedule() {
        assertEquals(7, weeklySchedule.getSchedule().size());
    }

    @Test
    void testType() {
        weeklySchedule.setType("monday", "leg");
        weeklySchedule.setType("wednesday", "arm");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("leg");   
        expected.add("");     
        expected.add("arm");   
        expected.add("");     
        expected.add("");      
        expected.add("");      
        expected.add("");     

        assertEquals(expected, weeklySchedule.type());
    }

    @Test
    void testMaxExercises() {
        weeklySchedule.getDay("monday").addExercise(new LegExercise("Squat", 10, 50, 5));
        weeklySchedule.getDay("tuesday").addExercise(new ArmExercise("Push-ups", 5, 30, 3));
        weeklySchedule.getDay("tuesday").addExercise(new ArmExercise("Burpees", 8, 40, 4));

        assertEquals(2, weeklySchedule.maxExercises()); 
    }

    @Test
    void testAllExercises() {
        Exercise squat = new LegExercise("Squat", 10, 50, 5);
        Exercise pushUps = new ArmExercise("Push-ups", 5, 30, 3);
        Exercise burpees = new ArmExercise("Burpees", 8, 40, 4);

        weeklySchedule.getDay("monday").addExercise(squat);
        weeklySchedule.getDay("tuesday").addExercise(pushUps);
        weeklySchedule.getDay("tuesday").addExercise(burpees);

        ArrayList<ArrayList<Exercise>> allExercises = weeklySchedule.allExercises();

        assertEquals(1, allExercises.get(0).size()); 
        assertEquals(squat, allExercises.get(0).get(0)); 

        assertEquals(2, allExercises.get(1).size()); 
        assertEquals(pushUps, allExercises.get(1).get(0));
        assertEquals(burpees, allExercises.get(1).get(1)); 

        assertEquals(0, allExercises.get(2).size()); 
    }




}

