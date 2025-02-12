package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;



public class PersonTest {
    private Person test;
    private  WeeklySchedule schedule;
    
    @BeforeEach
    void runBefore() {
        test = new Person("Angela", 100);
        schedule = new WeeklySchedule();
    }

    @Test
    void testConstructor() {
        assertEquals("Angela", test.getName());
        assertEquals(100, test.getTargetCalories());
        assertEquals(schedule, test.getSchedule());
    }

    @Test
    void testSetName() {
        test.setName("Bruno");
        assertEquals("Bruno", test.getName());
    }

    @Test
    void testSetNameTwice() {
        test.setName("Bruno");
        assertEquals("Bruno", test.getName());
        test.setName("Nice");
        assertEquals("Nice", test.getName());
    }

    @Test
    void testSetCaloriesBurned() {
        test.setCalories(200);
        assertEquals(200, test.getTargetCalories());
    }

    @Test
    void testAddCaloriesBurned() {
        test.addCalories(0);
        assertEquals(0, test.getTargetCalories());
        test.addCalories(100);
        assertEquals(100, test.getTargetCalories());
        test.addCalories(2);
        assertEquals(102, test.getTargetCalories());
    }


}
