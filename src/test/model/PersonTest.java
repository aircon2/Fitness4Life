package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class PersonTest {
    private Person test;
   
    @BeforeEach
    void runBefore() {
        test = new Person("Angela", 100, 0, 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Angela", test.getName());
        assertEquals(100, test.getTargetCalories());
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
        test.setTargetCalories(200);
        assertEquals(200, test.getTargetCalories());
    }

    @Test
    void testAddCaloriesBurned() {
        test.addTargetCalories(0);
        assertEquals(100, test.getTargetCalories());
        test.addTargetCalories(100);
        assertEquals(200, test.getTargetCalories());
        test.addTargetCalories(2);
        assertEquals(202, test.getTargetCalories());
    }

    @Test
    void testSetTime() {
        test.setTime(0);
        assertEquals(0, test.getTime());
    }

    @Test
    void testSetTimeHigh() {
        test.setTime(10000);
        assertEquals(10000, test.getTime());
    }


    @Test
    void testAddCurrentCalories() {
        test.addCurrentCalories(10);
        assertEquals(10, test.getCurrentCalories());
        test.addCurrentCalories(100);
        assertEquals(110, test.getCurrentCalories());
    }

    @Test
    void testSubstractCurrentCalories() {
        test.addCurrentCalories(10);
        test.substractCurrentCalories(10);
        assertEquals(0, test.getCurrentCalories());
        test.addCurrentCalories(100);
        test.substractCurrentCalories(10);
        assertEquals(90, test.getCurrentCalories());
    }

}
