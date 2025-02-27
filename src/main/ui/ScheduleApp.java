package ui;

import java.util.ArrayList;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.ArmExercise;
import model.Exercise;
import model.LegExercise;
import model.Person;
import model.WeeklySchedule;

// UI design, displays options and takes in user input
public class ScheduleApp {
    private static final String JSON_STORE = "./data/WeeklySchedule.json";
    private Person person;
    private WeeklySchedule sched;
    private Scanner input;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    
    //runs schedule application 
    public ScheduleApp() throws FileNotFoundException {
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
        person = new Person("Angela", 0, 0, 0);
        sched = new WeeklySchedule("Angela", person);
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of input needed from user
    private void displayMenu() {
        System.out.println("Welcome to your personal weekly workout schedule!"
                            + "Please complete the steps below in order!");
        System.out.println("1. s -> set new caloric target");
        System.out.println("2. t -> set total time you want to workout for the week");
        System.out.println("3. d -> set type of day");
        System.out.println("4. e -> add an exercise");
        System.out.println("5. c -> see current schedule");
        System.out.println("6. r -> remove or clear exercise");
        System.out.println("7. f -> save work room to file");
        System.out.println("8. l -> load work room from file");
        System.out.println("9. q -> quit");
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
            printSched();
        } else if (command.equals("e")) {
            addExercise();
        } else if (command.equals("r")) {
            remove();
        } else if (command.equals("f")) {
            saveWeeklySchedule();
        } else if (command.equals("l")) {
            loadWeeklySchedule();
        } else {
            System.out.println("Selection not valid...");
        }
     
    }

    //MODIFIES: person
    // EFFECTS: set the caloric target
    private void setCal() {
        System.out.println("Please type in the number you want to reach:");
        int amount = input.nextInt();
        person.setTargetCalories(amount);
        System.out.println("Congrats, I wish you luck on reaching this caloric goal!");
    }

    //MODIFIES: person
    // Set the amount of time you want to workout for each session
    private void setTime() {
        System.out.println("Please type in the time you want to work out every week (in minutes)");
        int amount = input.nextInt();
        person.setTime(amount);
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
    private void addExercise() {
        System.out.println("Here are your current type days set, add exercises accordingly to only these days.");
        printTypeDay();
        System.out.println("Which day of the week do you want to set it for?");
        String day = input.next();
        day = day.toLowerCase();
        if (sched.validDay(day) != null) {
            completeAdding(day);
        } else { 
            System.out.println("can't find that day"); 
        }
    }

    //MODIFIES: this 
    //EFFECTS: go through the steps of getting input and putting exercise into correct day
    public void completeAdding(String day) {
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
            addLegExercise(name, cals, time, reps, day);
        } else if (sched.validDay(day).getType().equals("arm")) {
            addArmExercise(name, cals, time, reps, day);
        } else {
            System.out.println("Invalid, couldn't add");
        }
    }

    //MODIFIES: this
    //EFFECTS: adds leg exercise to specified day
    public void addLegExercise(String name, int cals, int time, int reps, String day) {
        if (time * reps <= person.getTime()) { 
            LegExercise leg = new LegExercise(name, time, cals, reps);
            sched.validDay(day).addExercise(leg);
            person.setTime(person.getTime() - time * reps);
            person.addCurrentCalories(reps * cals);
            System.out.println("workout added!");
        } else {
            System.out.println("You do not have enough time for this exercise!");
        }
    }

    //MODIFIES: this
    //EFFECTS: adds arm exercise to specified day
    public void addArmExercise(String name, int cals, int time, int reps, String day) {
        if (time * reps <= person.getTime()) { 
            ArmExercise arm = new ArmExercise(name, time, cals, reps);
            sched.validDay(day).addExercise(arm);
            person.setTime(person.getTime() - time * reps);
            person.addCurrentCalories(reps * cals);
            System.out.println("workout added!");
        } else {
            System.out.println("You do not have enough time for this exercise!");
        }
    }

    //MODIFIES: Day
    // EFFECTS: remove an exercise from the list and update the current calories and time
    private void remove() {
        System.out.println("Do you want to remove one or clear exercises for a day?");
        System.out.println("c -> clear");
        System.out.println("r -> remove");
        String c = input.next();
        c = c.toLowerCase();
        if (c.equals("c")) {
            clear();
        } else if (c.equals("r")) {
            removeExercise();
        } else {
            System.out.println("Not a valid entry");
        }
    }

    // MODIFIES: this 
    //EFFECTS: clears the whole day of exercises
    public void clear() {
        System.out.println("Which day do you want to clear this exercise for?");
        printTypeDay();
        String day = input.next();
        ArrayList<Integer> temp = sched.clearExercise(day);
        if (temp.get(0) != 0) { 
            person.substractCurrentCalories(temp.get(0));
            person.setTime(person.getTime() + temp.get(1));
        }
            
        
    }

    // MODIFIES: this 
    //EFFECTS: removes an exercise for the day
    public void removeExercise() {
        System.out.println("Which day do you want to remove this exercise for?");
        printTypeDay();
        String day = input.next();
        input.nextLine();
        System.out.println("Whats the name of the exercise? (case sensitive)");
        String name = input.nextLine();
        ArrayList<Integer> temp = sched.removeExercise(day, name);
        if (temp.get(0) != 0) {
            person.substractCurrentCalories(temp.get(0));
            person.setTime(person.getTime() + temp.get(1));
        }
    }

    // EFFECTS: prints the type of day
    public void printTypeDay() {
        ArrayList<String> temp = sched.typeDay();
        for (String s : temp) {
            System.out.println(s);
        }
    }

    //EFFECTS: prints out the schedule
    public void printSched() {
        System.out.printf("Target Calories: %10d   |   Current Calories: %10d  |   Time Remaining: %10d\n\n\n",
                            person.getTargetCalories(), person.getCurrentCalories(), person.getTime());
        System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |\n",
                        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.print("|");
        ArrayList<String> types = sched.type();
        for (String s : types) {
            System.out.printf(" %-10s |", s);
        }
        System.out.println(); 
        System.out.println("--------------------------------------------------------------------------------------");
        int maxExercises = sched.maxExercises();
        printExercises(maxExercises);
        System.out.println("--------------------------------------------------------------------------------------");
    }

    // EFFECTS: prints all exercises 
    public void printExercises(int maxExercises) {
        ArrayList<ArrayList<Exercise>> exercises = sched.allExercises();
        for (int i = 0; i < maxExercises; i++) {
            System.out.print("|");
            for (int k = 0; k < exercises.size(); k++) { 
                if (i < exercises.get(k).size()) { 
                    System.out.printf(" %-10s |", exercises.get(k).get(i).getName());
                } else {
                    System.out.printf(" %-10s |", ""); 
                }
            }
            System.out.println(); 
        }
    }

    // EFFECTS: saves the WeeklySchedule to file
    private void saveWeeklySchedule() {
        try {
            jsonWriter.open();
            jsonWriter.write(sched, person);
            jsonWriter.close();
            System.out.println("Saved " + sched.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads WeeklySchedule from file
    private void loadWeeklySchedule() {
        try {
            sched = jsonReader.readWS();
            person = jsonReader.readP();
            System.out.println("Loaded " + sched.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
