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
        testDay1 = new Day("Monday");
        testDay2 = new Day("Tuesday");
        testDay3 = new Day("Wednesday");
        testDay4 = new Day("Thursday");
        schedule = new ArrayList<>();
        schedule.add(testDay1);
        schedule.add(testDay2);
        schedule.add(testDay3);
        schedule.add(testDay3);

    }

    @Test
    void testConstructor() {
        assertEquals(testDay1.getName(), testWeeklySchedule.getDay("Monday").getName());
        assertEquals(testDay2.getName(), testWeeklySchedule.getDay("Tuesday").getName());
        assertEquals(testDay3.getName(), testWeeklySchedule.getDay("Wednesday").getName());
        assertEquals(testDay4.getName(), testWeeklySchedule.getDay("Thursday").getName());
    }


    @Test
    void testValidDay() {
        assertEquals(null, testWeeklySchedule.validDay("kweb"));
        assertEquals("Monday", testWeeklySchedule.validDay("Monday").getName());
        assertEquals("Tuesday", testWeeklySchedule.validDay("Tuesday").getName());
    }

    @Test
    void testSetType() {
        testWeeklySchedule.setType("Monday", "leg");
        assertEquals("leg", testWeeklySchedule.getDay("Monday").getType());
        testWeeklySchedule.setType("Wednesday", "arm");
        assertEquals("arm", testWeeklySchedule.getDay("Wednesday").getType());
    }

}
