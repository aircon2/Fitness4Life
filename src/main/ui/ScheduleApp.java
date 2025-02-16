package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.ArmExercise;
import model.Day;
import model.Exercise;
import model.LegExercise;
import model.Person;
import model.WeeklySchedule;

public class ScheduleApp {
    private Person p;
    private WeeklySchedule sched;
    private Scanner input;
    private int currentCalories;
    
    //runs schedule application 
    public ScheduleApp() {
        runSchedule();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSchedule() {
        boolean keepGoing = true;
        String command = null;
        
        init();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes person 
    public void init() {
        p = new Person("Angela", 0);
        sched = new WeeklySchedule();
        input = new Scanner(System.in);
        currentCalories = 0;
    }

    // EFFECTS: displays menu of input needed from user
    private void displayMenu() {
        System.out.println("Welcome to your personal weekly workout schedule! Please complete the steps below in order!");
        System.out.println("1. s -> set new caloric target");
        System.out.println("2. t -> set total time you want to workout for the week");
        System.out.println("3. d -> set type of day");
        System.out.println("4. e -> add an exercise");
        System.out.println("5. c -> see current schedule");
        System.out.println("6. r -> remove or clear exercise");
        System.out.println("7. q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("s")) {
            setCal();
        } else if (command.equals("t")) {
           setTime();
        } else if (command.equals("d")) {
            setType();
        } else if (command.equals("c")) {
            printSched(currentCalories, p.getTargetCalories(), p.getTime());
        } else if (command.equals("e")) {
           addExercise();
        } else if (command.equals("r")) {
            remove();
        } else {
            System.out.println("Selection not valid...");
        }
     
    }

    //MODIFIES: person
    // EFFECTS: set the caloric target
    private void setCal() {
        System.out.println("Please type in the number you want to reach:");
        int amount = input.nextInt();
        p.setCalories(amount);
        System.out.println("Congrats, I wish you luck on reaching this caloric goal!");
    }

    //MODIFIES: person
    // Set the amount of time you want to workout for each session
    private void setTime() {
        System.out.println("Please type in the time you want to work out everyday (in minutes)");
        int amount = input.nextInt();
        p.setTime(amount);
        System.out.println("Thank you, I will organize your schedule accordingly!");
    }

    //MODIFIES: Day
    // EFFECTS: set type of day - arm or leg
    private void setType() {
        System.out.println("Which day of the week do you want to set?");
        String day = input.next();
        day = day.toLowerCase();
        System.out.println("Please type which day this is, arm or leg day?");
        String type = input.next();
        type  = type.toLowerCase();
        sched.setType(day, type);
        
    }

     //MODIFIES: this
    // EFFECTS: add an exercise to a day in the list
    @SuppressWarnings("methodlength")
    private void addExercise() {
        System.out.println("Here are your current type days set, add exercises accordingly to only these days.");
        printTypeDay();
        System.out.println("Which day of the week do you want to set it for?");
        String day = input.next();
        day = day.toLowerCase();
        if (sched.validDay(day) != null) {
            input.nextLine();
            System.out.println("Whats the name of the exercise?");
            String name = input.nextLine();
            System.out.println("How many calories will be burned? (integer values)");
            int cals = input.nextInt();
            System.out.println("How much time will one rep take? (integer values)");
            int time = input.nextInt();
            System.out.println("How many reps will you do? (integer values)");
            int reps = input.nextInt();
            if (sched.validDay(day).getType().equals("leg")) {
                if (time*reps <= p.getTime()) { 
                    LegExercise leg = new LegExercise(name, time, cals, reps);
                    sched.validDay(day).addExercise(leg);
                    p.setTime(p.getTime() - time*reps);
                    currentCalories += reps*cals;
                    System.out.println("workout added!");
                } else {
                    System.out.println("You do not have enough time for this exercise!");
                }
                
            } else if (sched.validDay(day).getType().equals("arm")) {
                if (time*reps <= p.getTime()) { 
                    ArmExercise arm = new ArmExercise(name, time, cals, reps);
                    sched.validDay(day).addExercise(arm);
                    p.setTime(p.getTime() - time*reps);
                    currentCalories += reps*cals;
                    System.out.println("workout added!");
                } else {
                    System.out.println("You do not have enough time for this exercise!");
                }
            } else {
                System.out.println("Invalid, couldn't add");
            }
        } else { 
            System.out.println("can't find that day"); 
        }
    }

    //MODIFIES: Day
    // EFFECTS: remove an exercise from the list and update the current calories and time
    @SuppressWarnings("methodlength")
    private void remove() {
        System.out.println("Do you want to remove one or clear exercises for a day?");
        System.out.println("c -> clear");
        System.out.println("r -> remove");
        String c = input.next();
        c = c.toLowerCase();

        if (c.equals("c")) {
            System.out.println("Which day do you want to clear this exercise for?");
            printTypeDay();
            String day = input.next();

            ArrayList<Integer> temp = sched.clearExercise(day);
            if (temp.get(0) != 0) { 
                currentCalories -= temp.get(0);
                p.setTime(p.getTime() + temp.get(1));
            }
            
        } else if (c.equals("r")) {
            System.out.println("Which day do you want to remove this exercise for?");
            printTypeDay();
            String day = input.next();
            input.nextLine();
            System.out.println("Whats the name of the exercise? (case sensitive)");
            String name = input.nextLine();
            ArrayList<Integer> temp = sched.removeExercise(day, name);
            if (temp.get(0) != 0) {
                currentCalories -= temp.get(0);
                p.setTime(p.getTime() + temp.get(1));
            }
            
        } else {
            System.out.println("Not a valid entry");
        }
    }

    public void printTypeDay() {
        ArrayList<String> temp = sched.TypeDay();
        for (String s : temp) {
            System.out.println(s);
        }
    }

    //EFFECTS: prints out the schedule
    @SuppressWarnings("methodlength")
    public void printSched(int currentCalories, int targetCalories, int timeLeft) {
        System.out.printf("Target Calories: %10d   |   Current Calories: %10d  |   Time Remaining: %10d\n\n\n", targetCalories, currentCalories, timeLeft);
        System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |\n",
                        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.print("|");
        ArrayList<String> types = sched.Type();
        for (String s : types) {
            System.out.printf(" %-10s |", s);
        }
        System.out.println(); 
        System.out.println("--------------------------------------------------------------------------------------");
        
        int maxExercises = sched.maxExercises();
        ArrayList<ArrayList<Exercise>> exercises = sched.allExercises();

        for (int i = 0; i < maxExercises; i++) {
            System.out.print("|");
            for (int k = 0; k < exercises.size(); k++) { // Loop through days
                if (i < exercises.get(k).size()) { // Ensure index is within bounds
                    System.out.printf(" %-10s |", exercises.get(k).get(i).getName());
                } else {
                    System.out.printf(" %-10s |", ""); // Print empty if no exercise
                }
            }
            System.out.println(); // Move to the next row
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }


}
