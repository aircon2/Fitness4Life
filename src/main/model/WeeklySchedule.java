package model;

import java.util.ArrayList;

// Produces weekly schedule and exercises done each day 
public class WeeklySchedule {
    private ArrayList<Day> schedule;

    // creates a list of 7 days of the week 
    public WeeklySchedule() {
        Day monday = new Day("Monday");
        Day tuesday = new Day("Tuesday");
        Day wednesday = new Day("Wednesday");
        Day thursday = new Day("Thursday");
        Day friday = new Day("Friday");
        Day saturday = new Day("Saturday");
        Day sunday = new Day("Sunday");
        schedule = new ArrayList<>();
        schedule.add(monday);
        schedule.add(tuesday);
        schedule.add(wednesday);
        schedule.add(thursday);
        schedule.add(friday);
        schedule.add(saturday);
        schedule.add(sunday);
    }



    // 
    //EFFECTS: return the day with (weekday) index position, if none are added, return null
    public Day getDay(String weekday) {
        for (Day d : schedule) {
            if (d.getName().equals(weekday)) {
                return d;
            }
        }
        return null;
    }

    //EFFECTS: prints out the schedule
    public void printSched() {
        // top right current calories planned: 
        //header: leg or arm day
         // Print headers
         System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |\n", 
                           "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
         System.out.println("----------------------------------------------------------------------------------------------");
 
         // Print events row by row
         
         for (Day day : schedule) {
             for ( int i=0; i< day.getExercisesForTheDay().size(); i++) {
                 if (i < day.getExercisesForTheDay().size()) { 
                     System.out.printf("| %-10s ", day.getExercisesForTheDay().get(i).getName());
                 } else {
                     System.out.printf("| %-10s ", ""); // Empty space for missing events
                 }
             }
         }
         System.out.println("|");
         
 
         System.out.println("----------------------------------------------------------------------------------------------");
     }

    //EFFECTS: return the current schedule of exercises
    public ArrayList<Day> getSchedule() {
        return schedule;
    }

    public void setType(String day, String type) {
        for (Day d : schedule) {
            if (d.getName().equals(day)) { 
               if (type.equals("leg") || type.equals("arm")) {
                    d.setType(type);
                    break;
               } else {
                    System.out.println("Not a valid answer");
               }
            }
        }
    }

    public Day validDay(String day) {
        for (Day d : schedule) {
            if (d.getName().equals(day)) {
                return d;
             }
        }
        return null;
    }


}
