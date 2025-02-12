package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
    

public class ExerciseTest {
    private Exercise armTest;
    private Exercise legTest;

    @BeforeEach
    void runBefore() {
        armTest = new ArmExercise("dumbell curlups", 15, 50, 10);
        legTest = new LegExercise("squats", 20, 70, 20);
    }

    @Test
    void testConstructorArm() {
        assertEquals("dumbell curlups", armTest.getName());
        assertEquals(50, armTest.getCaloriesBurned());
        assertEquals(10, armTest.getReps());
        assertEquals(15, armTest.getTimeForExercise());
    }

    @Test
    void testConstructorLeg() {
        assertEquals("squats", legTest.getName());
        assertEquals(20, legTest.getCaloriesBurned());
        assertEquals(70, legTest.getReps());
        assertEquals(20, legTest.getTimeForExercise());
    }

    @Test
    void testSetName() {
        legTest.setName("Bob");
        armTest.setName("Peggy");
        assertEquals("Bob", legTest.getName());
        assertEquals("Peggy", armTest.getName());
    }

    @Test
    void testSetTime() {
        legTest.setTime(10);
        armTest.setTime(15);
        assertEquals(10, legTest.getTimeForExercise());
        assertEquals(15, armTest.getTimeForExercise());
    }

    @Test
    void testSetCaloriesBurned() {
        legTest.setCaloriesBurned(100);
        armTest.setCaloriesBurned(200);
        assertEquals(100, legTest.getCaloriesBurned());
        assertEquals(200, armTest.getCaloriesBurned());
    }

    @Test
    void testSetReps() {
        legTest.setReps(5);
        armTest.setReps(9);
        assertEquals(5, legTest.getReps());
        assertEquals(9, armTest.getReps());
    }


}