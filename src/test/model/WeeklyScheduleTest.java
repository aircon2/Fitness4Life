package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
    



public class WeeklyScheduleTest {
    private WeeklySchedule testWeeklySchedule;
    private ArrayList<Day> schedule;
    private Day testDay1;
    private Day testDay2;
    private Day testDay3;
    private Day testDay4;
      

    @BeforeEach
    void runBefore() {
        testWeeklySchedule = new WeeklySchedule();
        testDay1 = new Day();
        testDay2 = new Day();
        testDay3 = new Day();
        testDay4 = new Day();
        schedule = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals(null, testWeeklySchedule.getDay(0));
    }

    @Test
    void testAddDay() {
        testWeeklySchedule.addDay(testDay1);
        schedule.add(testDay1);
        assertEquals(schedule, testWeeklySchedule.getSchedule());
    }

    @Test
    void testAddMultipleDay() {
        testWeeklySchedule.addDay(testDay1);
        testWeeklySchedule.addDay(testDay2);
        testWeeklySchedule.addDay(testDay3);
        testWeeklySchedule.addDay(testDay4);
        schedule.add(testDay1);
        schedule.add(testDay2);
        schedule.add(testDay3);
        schedule.add(testDay4);
        assertEquals(schedule, testWeeklySchedule.getSchedule());
        assertEquals(testDay2, testWeeklySchedule.getDay(2));
        assertEquals(testDay4, testWeeklySchedule.getDay(4));
    }

    @Test
    void testRemoveDay() {
        testWeeklySchedule.addDay(testDay1);
        testWeeklySchedule.removeDay(testDay1);
        assertEquals(schedule, testWeeklySchedule.getSchedule());
    }

    @Test
    void testRemoveMultipleDay() {
        testWeeklySchedule.addDay(testDay1);
        testWeeklySchedule.addDay(testDay2);
        testWeeklySchedule.addDay(testDay3);
        testWeeklySchedule.addDay(testDay4);
        testWeeklySchedule.removeDay(testDay2);
        schedule.add(testDay1);
        schedule.add(testDay3);
        schedule.add(testDay4);
        assertEquals(schedule, testWeeklySchedule.getSchedule());
        testWeeklySchedule.removeDay(testDay4);
        schedule.remove(testDay4);
        assertEquals(schedule, testWeeklySchedule.getSchedule());
        
    }

}
