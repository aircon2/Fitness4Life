package ui;
import javax.swing.*;
import javax.swing.border.Border;

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
    private JButton submitButton, quitButton, loadButton, startButton, setTargetButton, seeScheduleButton, setTimeButton;
    
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

        seeScheduleButton = new JButton("See Schedule");
        seeScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                printSched();
            }
        });

        setTimeButton = new JButton("Set Time");
        setTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                setTime();
            }
        });

        statsButtonPanel = new JPanel();
        statsButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        statsButtonPanel.add(setTargetButton);
        statsButtonPanel.add(seeScheduleButton);
        statsButtonPanel.add(setTimeButton);

        menuBar = new JMenuBar();
        JMenu actions = new JMenu("Actions");
        menuBar.add(actions);

        JMenuItem addType = new JMenuItem("Add a Type of Day");
        addType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                setType();
            }
        });

        JMenuItem addExercise = new JMenuItem("Add an Exercise");
        addExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                addExercise();
            }
        });

        JMenuItem removeExercise = new JMenuItem("Remove Exercises");
        removeExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                //TODO: choose();
            }
        });

        actions.add(addType);
        actions.add(addExercise);
        actions.add(removeExercise);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        buttonPanel.add(saveButton);

         
        // Add to the new frame
        start.setJMenuBar(menuBar);
        start.add(label, BorderLayout.CENTER);
        start.add(buttonPanel, BorderLayout.SOUTH);
        start.add(printStatsBar(), BorderLayout.NORTH);
        start.add(statsButtonPanel);
        start.setVisible(true);
    }

    public JPanel printStatsBar() {
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
         
        return statsPanel;
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
        JFrame setTime = new JFrame();
        setTime.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTime.setSize(700, 200);
        setTime.setLayout(new BorderLayout());

        JLabel enterJLabel = new JLabel("Please type in the time you want to work out every week (in minutes)");
        JTextField textField = new JTextField(20);
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String amount = textField.getText();
            person.setTime(Integer.parseInt(amount));
            startFresh();
            setTime.dispose();
        });
       
       
        setTime.add(enterJLabel, BorderLayout.NORTH);
        setTime.add(textField, BorderLayout.CENTER);
        setTime.add(submit, BorderLayout.SOUTH);
        setTime.setVisible(true);
    } 

    //MODIFIES: Day
    // EFFECTS: set type of day - arm or leg
    private void setType() {
        JFrame setType = new JFrame();
        setType.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setType.setSize(700, 200);
        setType.setLayout(new FlowLayout());

        JLabel enterJLabel = new JLabel("Which day of the week do you want to set?");
        JTextField textField = new JTextField(20);
        JLabel typeLabel = new JLabel("Please type which day this is, arm or leg day?");
        JTextField textField2 = new JTextField(20);
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String day = textField.getText();
            day = day.toLowerCase();
            String type = textField2.getText();
            type  = type.toLowerCase();
            sched.setType(day, type);
            startFresh();
            setType.dispose();
        });
        setType.add(enterJLabel);
        setType.add(textField);
        setType.add(typeLabel);
        setType.add(textField2);
        setType.add(submit);
        setType.setVisible(true);
    }

    //MODIFIES: this
    // EFFECTS: add an exercise to a day in the list
    private void addExercise() {
        JFrame addExercise = new JFrame("Add a new exercise!");
        addExercise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addExercise.setSize(900, 200);
        addExercise.setLayout(new BorderLayout());

        JPanel display = new JPanel();
        JLabel enterJLabel = new JLabel("Here is the days you can add exercises for: ");
        JTextArea types = new JTextArea();
        types.setEditable(false);
        types.setText(String.join("\n", sched.typeDay())); 
        display.setLayout(new FlowLayout(FlowLayout.CENTER));
        display.add(enterJLabel);
        display.add(types);


        JPanel input = new JPanel();
        JLabel typeLabel = new JLabel("Which day do you want to set?");
        JTextField textField = new JTextField(10);
        JButton submit = new JButton("Submit");
        input.setLayout(new FlowLayout(FlowLayout.CENTER));
        input.add(typeLabel);
        input.add(textField);
        input.add(submit);
       
       
        submit.addActionListener(e -> {
            String day = textField.getText();
            day = day.toLowerCase();
            if (sched.validDay(day) != null) {
                completeAdding(day);
            } else {
                startFresh();
            }
            addExercise.dispose();
        });

        
        addExercise.add(display, BorderLayout.WEST);
        addExercise.add(input, BorderLayout.EAST);
        addExercise.setVisible(true);
        
    }

    //MODIFIES: this 
    //EFFECTS: go through the steps of getting input and putting exercise into correct day
    public void completeAdding(String day) {
        JFrame addExerciseStats = new JFrame();
        addExerciseStats.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addExerciseStats.setSize(700, 200);
        addExerciseStats.setLayout(new FlowLayout());

        JLabel name = new JLabel("Whats the name of the exercise?");
        JTextField textField = new JTextField(20);
        JLabel calBurned = new JLabel("How many calories will be burned? (integer values)");
        JTextField textField2 = new JTextField(20);
        JLabel time = new JLabel("How much time will one rep take? (integer values)");
        JTextField textField3 = new JTextField(20);
        JLabel reps = new JLabel("How many reps will you do? (integer values)");
        JTextField textField4 = new JTextField(20);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String name1 = textField.getText();
            int cals = Integer.parseInt(textField2.getText());
            int time1 = Integer.parseInt(textField3.getText());
            int reps1 = Integer.parseInt(textField4.getText());
            

            if (sched.validDay(day).getType().equals("leg")) {
                result(addLegExercise(name1, cals, time1, reps1, day));
            } else if (sched.validDay(day).getType().equals("arm")) {
                result(addArmExercise(name1, cals, time1, reps1, day));
            } else {
                startFresh();
            }
            addExerciseStats.dispose();
        });

        addExerciseStats.add(name);
        addExerciseStats.add(textField);
        addExerciseStats.add(calBurned);
        addExerciseStats.add(textField2);
        addExerciseStats.add(time);
        addExerciseStats.add(textField3);
        addExerciseStats.add(reps);
        addExerciseStats.add(textField4);
        addExerciseStats.add(submit);
        addExerciseStats.setVisible(true);
    }

    public void result(String r) {
        JFrame result = new JFrame();
        result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        result.setSize(700, 200);
        result.setLayout(new FlowLayout());

        JLabel message = new JLabel(r);

        JButton returnHome = new JButton("Click Here to Return Home");
        returnHome.addActionListener(e -> {
            result.dispose();
            startFresh();
        });

        result.add(message);
        result.add(returnHome);
        result.setVisible(true);
    
    }
    

    //MODIFIES: this
    //EFFECTS: adds leg exercise to specified day
    public String addLegExercise(String name, int cals, int time, int reps, String day) {
        if (time * reps <= person.getTime()) { 
            LegExercise leg = new LegExercise(name, time, cals, reps);
            sched.validDay(day).addExercise(leg);
            person.setTime(person.getTime() - time * reps);
            person.addCurrentCalories(reps * cals);
            return "workout added!";
        } else {
             return "You do not have enough time for this exercise!";
        }
    }

    //MODIFIES: this
    //EFFECTS: adds arm exercise to specified day
    public String addArmExercise(String name, int cals, int time, int reps, String day) {
        if (time * reps <= person.getTime()) { 
            ArmExercise arm = new ArmExercise(name, time, cals, reps);
            sched.validDay(day).addExercise(arm);
            person.setTime(person.getTime() - time * reps);
            person.addCurrentCalories(reps * cals);
            return "workout added!";
        } else {
            return "You do not have enough time for this exercise!";
        }
    }

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


    //EFFECTS: prints out the schedule
    public void printSched() {
        ArrayList<ArrayList<Exercise>> exercises = sched.allExercises();
        int maxExercises = sched.maxExercises();

        JFrame tableFrame = new JFrame(person.getName() + "'s Weekly Schedule");
        tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableFrame.setSize(800, 500);
       
        String[] columnNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayList<String> types = sched.type();
        Object[][] tableData = new Object[maxExercises + 1][7];

        for (int i = 0; i < 7; i++) {
            tableData[0][i] = types.get(i); 
        }
    
        // Add exercises to the table data
        for (int i = 0; i < maxExercises; i++) {
            for (int j = 0; j < 7; j++) {
                if (i < exercises.get(j).size()) {
                    tableData[i + 1][j] = exercises.get(j).get(i).getName(); 
                } else {
                    tableData[i + 1][j] = ""; 
                }
            }
        }
    

        JButton returnHome = new JButton("Click Here to Return Home");
        returnHome.addActionListener(e -> {
            tableFrame.dispose();
            startFresh();
        });

        JTable table = new JTable(tableData, columnNames);
        tableFrame.setLayout(new BorderLayout());
        tableFrame.add(printStatsBar(), BorderLayout.NORTH);
        tableFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        tableFrame.add(returnHome, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(table);
        tableFrame.add(scrollPane, BorderLayout.CENTER);
        
        tableFrame.setVisible(true);
        
        
        
    }

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