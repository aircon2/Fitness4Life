package ui;

import java.util.ArrayList;
import java.util.Scanner;

import model.ArmExercise;
import model.Day;
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
    //TODO: lowercase shit

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
        System.out.println("s -> set new caloric target");
        System.out.println("t -> set time per workout");
        System.out.println("d -> set type of day");
        System.out.println("c -> see current schedule");
        System.out.println("e -> add an exercise");
        System.out.println("q -> quit");
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
            sched.printSched(currentCalories, p.getTargetCalories());
        } else if (command.equals("e")) {
           addExercise();
        } else {
            System.out.println("Selection not valid...");
        }
     
    }

    private void setCal() {
        System.out.println("Please type in the number you want to reach:");
        int amount = input.nextInt();
        p.setCalories(amount);
        System.out.println("Congrats, I wish you luck on reaching this caloric goal!");
    }

    private void setTime() {
        System.out.println("Please type in the time you want to work out everyday (in minutes)");
        int amount = input.nextInt();
        p.setTime(amount);
        System.out.println("Thank you, I will organize your schedule accordingly!");
    }

    private void setType() {
        System.out.println("Which day do you want to set?");
        String day = input.next();
        System.out.println("Please type which day this is, arm or leg day?");
        String type = input.next();
        sched.setType(day, type);

    }

     

    private void addExercise() {
        System.out.println("Which day do you want to set it for?");
        String day = input.next();
        if (sched.validDay(day) != null) {
            System.out.println("Whats the name of the exercise?");
            String name = input.next();
            System.out.println("How many calories will be burned?");
            int cals = input.nextInt();
            System.out.println("How much time will one rep take");
            int time = input.nextInt();
            System.out.println("How many reps will you do?");
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
            }
        } else { 
            System.out.println("can't find that day"); 
        }

    }

}
