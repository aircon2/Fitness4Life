package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class SchedulePanel extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/WeeklySchedule.json";
    private Person person;
    private WeeklySchedule sched;
    private Scanner input;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JTextArea displayArea;
    private JTextField targetField, currentField, timeField; 
    private JFrame frame, start;
    private JLabel label, target, current, time;
    private JPanel statsPanel, statsButtonPanel;
    private JMenuBar menuBar;
    private JButton submitButton, quitButton, loadButton, startButton, setTargetButton, seeSheduleButton, setTimeButton;
    
    public SchedulePanel() {
        // Create the welcome label
        label = new JLabel("Welcome!");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setHorizontalAlignment(JLabel.CENTER);
       

        // Create the "Load from File" button
        loadButton = new JButton("Load from File");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality to load data from a file
                JOptionPane.showMessageDialog(null, "Load from File button clicked!");
            }
        });

        // Create the "Start New" button
        startButton = new JButton("Start New");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
                frame.dispose();
                startFresh();
            }
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Use FlowLayout to center the buttons
        buttonPanel.add(loadButton);
        buttonPanel.add(startButton);
        

        // Create the main frame
        frame = new JFrame("Fitness Weekly Schedule");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 800);
        frame.setLayout(new BorderLayout());

        // Add the welcome label to the center of the frame
        frame.add(label, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
     }

    public void processCommand(String command) {
        displayArea.append("Command: " + command + "\n");
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

    public void startFresh() {
        start = new JFrame("Fitness Weekly Schedule");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setSize(700, 800);
        start.setLayout(new BorderLayout());

        // Create the "Start New" button
        JButton saveButton = new JButton("Save to file");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //yay
            }
        });

        setTargetButton = new JButton("Set new Target");
        setTargetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                setCal();
            }
        });

        seeSheduleButton = new JButton("See Schedule");
        seeSheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //printSched();
            }
        });

        setTimeButton = new JButton("Set Time");
        setTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTime();
            }
        });

        statsButtonPanel = new JPanel();
        statsButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        statsButtonPanel.add(setTargetButton);
        statsButtonPanel.add(seeSheduleButton);
        statsButtonPanel.add(setTimeButton);

        menuBar = new JMenuBar();
        JMenu actions = new JMenu("Actions");
        menuBar.add(actions);

        JMenuItem addType = new JMenuItem("Add a Type of Day");
        JMenuItem addExercise = new JMenuItem("Add an Exercise");
        JMenuItem removeExercise = new JMenuItem("Remove Exercises");
        actions.add(addType);
        actions.add(addExercise);
        actions.add(removeExercise);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        buttonPanel.add(saveButton);

        // Create the target calories label
        target = new JLabel("Target Calories: ");
        target.setFont(new Font("Arial", Font.PLAIN, 15));
        target.setHorizontalAlignment(JLabel.LEFT);

        // Create the current calories label
        current = new JLabel("Current Calories: ");
        current.setFont(new Font("Arial", Font.PLAIN, 15));
        current.setHorizontalAlignment(JLabel.CENTER);

        // Create the weekly time label
        time = new JLabel("Weekly Time: ");
        time.setFont(new Font("Arial", Font.PLAIN, 15));
        time.setHorizontalAlignment(JLabel.RIGHT);

        // Create text fields for values (integer display)
        targetField = new JTextField(Integer.toString(person.getTargetCalories()), 5);
        targetField.setEditable(false);  
        targetField.setBackground(Color.pink);
        currentField = new JTextField(Integer.toString(person.getCurrentCalories()), 5);
        currentField.setEditable(false);
        currentField.setBackground(Color.pink);
        timeField = new JTextField(Integer.toString(person.getTime()), 5);
        timeField.setEditable(false);
        timeField.setBackground(Color.pink);

        targetField.setText(Integer.toString(person.getTargetCalories()));
        currentField.setText(Integer.toString(person.getCurrentCalories()));
        timeField.setText(Integer.toString(person.getTime()));

         // Create a panel for the stats labels and fields
        statsPanel = new JPanel();
        statsPanel.setBackground(Color.pink);
        statsPanel.setLayout(new GridLayout(1, 6, 10, 10)); 
        statsPanel.add(target);
        statsPanel.add(targetField);
        statsPanel.add(current);
        statsPanel.add(currentField);
        statsPanel.add(time);
        statsPanel.add(timeField);
         
        // Add to the new frame
        start.setJMenuBar(menuBar);
        start.add(label, BorderLayout.CENTER);
        start.add(buttonPanel, BorderLayout.SOUTH);
        start.add(statsPanel, BorderLayout.NORTH);
        start.add(statsButtonPanel);
        start.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }


    //MODIFIES: person
    // EFFECTS: set the caloric target
    private void setCal() {
        JFrame setCalories = new JFrame();
        setCalories.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCalories.setSize(700, 200);
        setCalories.setLayout(new BorderLayout());

        JLabel enterJLabel = new JLabel("Please type in the number you want to reach:");
        JTextField textField = new JTextField(20);
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String amount = textField.getText();
            person.setTargetCalories(Integer.parseInt(amount));
            startFresh();
            setCalories.dispose();
        });
       
       
        setCalories.add(enterJLabel, BorderLayout.NORTH);
        setCalories.add(textField, BorderLayout.CENTER);
        setCalories.add(submit, BorderLayout.SOUTH);
        setCalories.setVisible(true);

    }

    //MODIFIES: person
    // Set the amount of time you want to workout for each session
    private void setTime() {
        System.out.println("Please type in the time you want to work out every week (in minutes)");
        int amount = input.nextInt();
        person.setTime(amount);
        System.out.println("Thank you, I will organize your schedule accordingly!");
    }

    //     // MODIFIES: this
    //     // EFFECTS: processes user command
    //     private void processCommand(String command) {
    //         if (command.equals("s")) {
    //             setCal();
    //         } else if (command.equals("t")) {
    //             setTime();
    //         } else if (command.equals("d")) {
    //             setType();
    //         } else if (command.equals("c")) {
    //             printSched();
    //         } else if (command.equals("e")) {
    //             addExercise();
    //         } else if (command.equals("r")) {
    //             remove();
    //         } else if (command.equals("f")) {
    //             saveWeeklySchedule();
    //         } else if (command.equals("l")) {
    //             loadWeeklySchedule();
    //         } else {
    //             System.out.println("Selection not valid...");
    //         }
         
    //     

//     //MODIFIES: Day
//     // EFFECTS: set type of day - arm or leg
//     private void setType() {
//         System.out.println("Which day of the week do you want to set?");
//         String day = input.next();
//         day = day.toLowerCase();
//         System.out.println("Please type which day this is, arm or leg day?");
//         String type = input.next();
//         type  = type.toLowerCase();
//         sched.setType(day, type);
        
//     }

//     //MODIFIES: this
//     // EFFECTS: add an exercise to a day in the list
//     private void addExercise() {
//         System.out.println("Here are your current type days set, add exercises accordingly to only these days.");
//         printTypeDay();
//         System.out.println("Which day of the week do you want to set it for?");
//         String day = input.next();
//         day = day.toLowerCase();
//         if (sched.validDay(day) != null) {
//             completeAdding(day);
//         } else { 
//             System.out.println("can't find that day"); 
//         }
//     }

//     //MODIFIES: this 
//     //EFFECTS: go through the steps of getting input and putting exercise into correct day
//     public void completeAdding(String day) {
//         input.nextLine();
//         System.out.println("Whats the name of the exercise?");
//         String name = input.nextLine();
//         System.out.println("How many calories will be burned? (integer values)");
//         int cals = input.nextInt();
//         System.out.println("How much time will one rep take? (integer values)");
//         int time = input.nextInt();
//         System.out.println("How many reps will you do? (integer values)");
//         int reps = input.nextInt();
//         if (sched.validDay(day).getType().equals("leg")) {
//             addLegExercise(name, cals, time, reps, day);
//         } else if (sched.validDay(day).getType().equals("arm")) {
//             addArmExercise(name, cals, time, reps, day);
//         } else {
//             System.out.println("Invalid, couldn't add");
//         }
//     }

//     //MODIFIES: this
//     //EFFECTS: adds leg exercise to specified day
//     public void addLegExercise(String name, int cals, int time, int reps, String day) {
//         if (time * reps <= person.getTime()) { 
//             LegExercise leg = new LegExercise(name, time, cals, reps);
//             sched.validDay(day).addExercise(leg);
//             person.setTime(person.getTime() - time * reps);
//             person.addCurrentCalories(reps * cals);
//             System.out.println("workout added!");
//         } else {
//             System.out.println("You do not have enough time for this exercise!");
//         }
//     }

//     //MODIFIES: this
//     //EFFECTS: adds arm exercise to specified day
//     public void addArmExercise(String name, int cals, int time, int reps, String day) {
//         if (time * reps <= person.getTime()) { 
//             ArmExercise arm = new ArmExercise(name, time, cals, reps);
//             sched.validDay(day).addExercise(arm);
//             person.setTime(person.getTime() - time * reps);
//             person.addCurrentCalories(reps * cals);
//             System.out.println("workout added!");
//         } else {
//             System.out.println("You do not have enough time for this exercise!");
//         }
//     }

//     //MODIFIES: Day
//     // EFFECTS: remove an exercise from the list and update the current calories and time
//     private void remove() {
//         System.out.println("Do you want to remove one or clear exercises for a day?");
//         System.out.println("c -> clear");
//         System.out.println("r -> remove");
//         String c = input.next();
//         c = c.toLowerCase();
//         if (c.equals("c")) {
//             clear();
//         } else if (c.equals("r")) {
//             removeExercise();
//         } else {
//             System.out.println("Not a valid entry");
//         }
//     }

//     // MODIFIES: this 
//     //EFFECTS: clears the whole day of exercises
//     public void clear() {
//         System.out.println("Which day do you want to clear this exercise for?");
//         printTypeDay();
//         String day = input.next();
//         ArrayList<Integer> temp = sched.clearExercise(day);
//         if (temp.get(0) != 0) { 
//             person.substractCurrentCalories(temp.get(0));
//             person.setTime(person.getTime() + temp.get(1));
//         }
            
        
//     }

//     // MODIFIES: this 
//     //EFFECTS: removes an exercise for the day
//     public void removeExercise() {
//         System.out.println("Which day do you want to remove this exercise for?");
//         printTypeDay();
//         String day = input.next();
//         input.nextLine();
//         System.out.println("Whats the name of the exercise? (case sensitive)");
//         String name = input.nextLine();
//         ArrayList<Integer> temp = sched.removeExercise(day, name);
//         if (temp.get(0) != 0) {
//             person.substractCurrentCalories(temp.get(0));
//             person.setTime(person.getTime() + temp.get(1));
//         }
//     }

//     // EFFECTS: prints the type of day
//     public void printTypeDay() {
//         ArrayList<String> temp = sched.typeDay();
//         for (String s : temp) {
//             System.out.println(s);
//         }
//     }

//     //EFFECTS: prints out the schedule
//     public void printSched() {
//         System.out.printf("Target Calories: %10d   |   Current Calories: %10d  |   Time Remaining: %10d\n\n\n",
//                             person.getTargetCalories(), person.getCurrentCalories(), person.getTime());
//         System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |\n",
//                         "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
//         System.out.println("--------------------------------------------------------------------------------------");
//         System.out.print("|");
//         ArrayList<String> types = sched.type();
//         for (String s : types) {
//             System.out.printf(" %-10s |", s);
//         }
//         System.out.println(); 
//         System.out.println("--------------------------------------------------------------------------------------");
//         int maxExercises = sched.maxExercises();
//         printExercises(maxExercises);
//         System.out.println("--------------------------------------------------------------------------------------");
//     }

//     // EFFECTS: prints all exercises 
//     public void printExercises(int maxExercises) {
//         ArrayList<ArrayList<Exercise>> exercises = sched.allExercises();
//         for (int i = 0; i < maxExercises; i++) {
//             System.out.print("|");
//             for (int k = 0; k < exercises.size(); k++) { 
//                 if (i < exercises.get(k).size()) { 
//                     System.out.printf(" %-10s |", exercises.get(k).get(i).getName());
//                 } else {
//                     System.out.printf(" %-10s |", ""); 
//                 }
//             }
//             System.out.println(); 
//         }
//     }

//     // EFFECTS: saves the WeeklySchedule to file
//     private void saveWeeklySchedule() {
//         try {
//             jsonWriter.open();
//             jsonWriter.write(sched, person);
//             jsonWriter.close();
//             System.out.println("Saved " + sched.getName() + " to " + JSON_STORE);
//         } catch (FileNotFoundException e) {
//             System.out.println("Unable to write to file: " + JSON_STORE);
//         }
//     }

//     // MODIFIES: this
//     // EFFECTS: loads WeeklySchedule from file
//     private void loadWeeklySchedule() {
//         try {
//             sched = jsonReader.readWS();
//             person = jsonReader.readP();
//             System.out.println("Loaded " + sched.getName() + " from " + JSON_STORE);
//         } catch (IOException e) {
//             System.out.println("Unable to read from file: " + JSON_STORE);
//         }
//     }

// }

}