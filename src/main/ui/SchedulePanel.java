package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.ArmExercise;
import model.Exercise;
import model.LegExercise;
import model.Person;
import model.WeeklySchedule;

// GUI design, displays options and takes in user actions
public class SchedulePanel extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/WeeklySchedule.json";
    private Person person;
    private WeeklySchedule sched;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JTextArea displayArea;
    private JTextArea types;
    private JTextField targetField;
    private JTextField currentField;
    private JTextField timeField; 
    private ImageIcon image;
    private JFrame frame;
    private JFrame addExercise;
    private JFrame tableFrame;
    private JFrame removeFrame;
    private JFrame addExerciseStats;
    private JFrame start;
    private JFrame clearFrame;
    private JLabel label;
    private JLabel enterJLabel;
    private JLabel target;
    private JLabel current;
    private JLabel time;
    private JPanel statsPanel;
    private JPanel display;
    private JPanel buttonPanel;
    private JPanel statsButtonPanel;
    private JMenuBar menuBar;
    private JButton loadButton;
    private JButton motivationButton;
    private JButton submit;
    private JButton returnHome;
    private JButton startButton;
    private JButton saveButton;
    private JButton setTargetButton;
    private JButton seeScheduleButton;
    private JButton setTimeButton;
    private JButton removeButton;
    private  JTable table;
    private JMenu actions;
    private JMenuItem addExerciseItem;
    private JMenuItem addTypeItem;
    private JMenuItem removeExerciseItem;
    
    // EFFECTS: Initialization, choose how to start
    public SchedulePanel() {
        label = new JLabel("Welcome!");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setHorizontalAlignment(JLabel.CENTER);
    
        createLoadButton();
        createStartButton();
        createButtonPanel();
        
        frame = new JFrame("Fitness Weekly Schedule");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 800);
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    //EFFECTS: Creates a button panel for selection
    public void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        buttonPanel.add(loadButton);
        buttonPanel.add(startButton);
    }

    //EFFECTS: Creates a load button to load from file
    public void createLoadButton() {
        loadButton = new JButton("Load from File");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
                frame.dispose();
                loadWeeklySchedule();
            }
        });
    }

    //EFFECTS: Creates a start button to begin a new planner
    public void createStartButton() {
        startButton = new JButton("Start New");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
                frame.dispose();
                startFresh();
            }
        });
    }


     // MODIFIES: this
    // EFFECTS: initializes person 
    public void init() {
        person = new Person("Angela", 0, 0, 0);
        sched = new WeeklySchedule("Angela", person);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: Main screen where all actions are performed
    public void startFresh() {
        startNewFrame();
        saveButton();
        setTargetButton();
        seeSchedButton();
        setTimeButton();
        statsButtonPanel();
        menuBar();
        motivationButton();

        actions.add(addTypeItem);
        actions.add(addExerciseItem);
        actions.add(removeExerciseItem);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        buttonPanel.add(saveButton);
        buttonPanel.add(motivationButton);
       
        start.setJMenuBar(menuBar);
        start.add(label, BorderLayout.CENTER);
        start.add(buttonPanel, BorderLayout.SOUTH);
        start.add(printStatsBar(), BorderLayout.NORTH);
        start.add(statsButtonPanel);
        start.setVisible(true);
    }

    //EFFECTS: menu bar that creates selection actions 
    public void menuBar() {
        menuBar = new JMenuBar();
        actions = new JMenu("Actions");
        menuBar.add(actions);

        addTypeItem = new JMenuItem("Add a Type of Day");
        addTypeItem.addActionListener(e -> {
            start.dispose();
            setType();
        }); 
    

        addExerciseItem = new JMenuItem("Add an Exercise");
        addExerciseItem.addActionListener(e -> {
            start.dispose();
            addExercise();
        }); 

        removeExerciseItem = new JMenuItem("Remove Exercises");
        removeExerciseItem.addActionListener(e -> {
            start.dispose();
            choose();
        }); 

    }

    //EFFECTS: Creates a frame where panels are put onto
    public void startNewFrame() {
        start = new JFrame("Fitness Weekly Schedule");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setSize(800, 800);
        start.setLayout(new BorderLayout());
    }

    //EFFECTS: saves current data to JSON 
    public void saveButton() {
        saveButton = new JButton("Save to file");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                saveWeeklySchedule();
            }
        });
    }

    //EFFECTS: Creates a button to display image
    public void motivationButton() {
        motivationButton = new JButton("Get some Motivation");
        motivationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                photo();
            }
        });
    }

    //EFFECTS: Creates a button panel to see schedule
    public void seeSchedButton() {
        seeScheduleButton = new JButton("See Schedule");
        seeScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                printSched();
            }
        });
    }

    // MODIFIES: this
    //EFFECTS: Creates a button to set a new time goal
    public void setTimeButton() {
        setTimeButton = new JButton("Set Time");
        setTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                setTime();
            }
        });
    }

    // MODIFIES: this
    //EFFECTS: Creates a button to set a new target calories goal
    public void setTargetButton() {
        setTargetButton = new JButton("Set new Target");
        setTargetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.dispose();
                setCal();
            }
        });
    }

    //EFFECTS: Creates a button panel to put buttons onto
    public void statsButtonPanel() {
        statsButtonPanel = new JPanel();
        statsButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        statsButtonPanel.add(setTargetButton);
        statsButtonPanel.add(seeScheduleButton);
        statsButtonPanel.add(setTimeButton);
    }

    //EFFECTS: choose frame to choose if user wants to remove or clear exercise
    public void choose() {  
        JFrame chooseFrame = new JFrame();
        chooseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooseFrame.setSize(700, 200);
        chooseFrame.setLayout(new FlowLayout());

        JLabel enterJLabel = new JLabel("Do you want to clear a whole day or remove an exercise?");
        JButton remove = new JButton("Remove");
        remove.addActionListener(e -> {
            chooseFrame.dispose();
            removeExercise();
        });
        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> {
            chooseFrame.dispose();
            clear();
        });
       
       
        chooseFrame.add(enterJLabel);
        chooseFrame.add(remove);
        chooseFrame.add(clear);
        chooseFrame.setVisible(true);

    }

    //EFFECTS: displays a frame of motivational image
    public void photo() {
        JFrame photoFrame = new JFrame();
        photoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        photoFrame.setSize(700, 700);
        photoFrame.setLayout(new BorderLayout());

        image = new ImageIcon("data/GymImage.png");
        Image temp = image.getImage();
        Image resizedImage = temp.getScaledInstance(400, 396, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel label = new JLabel(resizedIcon);

        JButton returnHome = new JButton("Click Here to Return Home");
        returnHome.addActionListener(e -> {
            photoFrame.dispose();
            startFresh();
        });

        photoFrame.add(returnHome, BorderLayout.SOUTH);
        photoFrame.add(label, BorderLayout.CENTER);
        photoFrame.setVisible(true);
    }


    //EFFECTS: creates a stats bar to display current data
    public JPanel printStatsBar() {
        createLabels();
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

    //EFFECTS: creates a labels for stats bar
    public void createLabels() {
        target = new JLabel("Target Calories: ");
        target.setFont(new Font("Arial", Font.PLAIN, 15));
        target.setHorizontalAlignment(JLabel.LEFT);

        current = new JLabel("Current Calories: ");
        current.setFont(new Font("Arial", Font.PLAIN, 15));
        current.setHorizontalAlignment(JLabel.CENTER);

        time = new JLabel("Weekly Time: ");
        time.setFont(new Font("Arial", Font.PLAIN, 15));
        time.setHorizontalAlignment(JLabel.RIGHT);

    }

    @Override
    //EFFECTS: overrides action performed
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
            setCalories.dispose();
            startFresh();
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
            setTime.dispose();
            startFresh();
        });
       
       
        setTime.add(enterJLabel, BorderLayout.NORTH);
        setTime.add(textField, BorderLayout.CENTER);
        setTime.add(submit, BorderLayout.SOUTH);
        setTime.setVisible(true);
    } 

    // MODIFIES: Day
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
            String type = textField2.getText();
            sched.setType(day.toLowerCase(), type.toLowerCase());
            setType.dispose();
            startFresh();
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
        addHelper();
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
            if (sched.validDay(day.toLowerCase()) != null) {
                completeAdding(day.toLowerCase());
            } else {
                startFresh();
            }
            addExercise.dispose();
        });
        addExercise.add(display, BorderLayout.WEST);
        addExercise.add(input, BorderLayout.EAST);
        addExercise.setVisible(true);
    }

    // EFFECTS: helps to finish adding the exercise
    public void addHelper() {
        addExercise = new JFrame("Add a new exercise!");
        addExercise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addExercise.setSize(900, 200);
        addExercise.setLayout(new BorderLayout());

        display = new JPanel();
        enterJLabel = new JLabel("Here is the days you can add exercises for: ");
        types = new JTextArea();
        types.setEditable(false);
        types.setText(String.join("\n", sched.typeDay())); 
        display.setLayout(new FlowLayout(FlowLayout.CENTER));
        display.add(enterJLabel);
        display.add(types);
    }

    //MODIFIES: this 
    //EFFECTS: go through the steps of getting input and putting exercise into correct day
    public void completeAdding(String day) {
        addExerciseStatsFrame();
        JLabel name = new JLabel("Whats the name of the exercise?");
        JTextField textField = new JTextField(20);
        JLabel calBurned = new JLabel("How many calories will be burned? (integer values)");
        JTextField textField2 = new JTextField(20);
        JLabel time = new JLabel("How much time will one rep take? (integer values)");
        JTextField textField3 = new JTextField(20);
        JLabel reps = new JLabel("How many reps will you do? (integer values)");
        JTextField textField4 = new JTextField(20);
        submitButton(textField, textField2, textField3, textField4, day);

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

    // EFFECTS: creates the add exercise statistics frame to collect data
    public void addExerciseStatsFrame() {
        addExerciseStats = new JFrame();
        addExerciseStats.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addExerciseStats.setSize(700, 200);
        addExerciseStats.setLayout(new FlowLayout());
    }

    //MODIFIES: this
    // EFFECTS: helps to submit the exercise and instantiate a new exercise
    public void submitButton(JTextField textField, JTextField textField2, 
                            JTextField textField3, JTextField textField4, String day) {
        submit = new JButton("Submit");
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
    }

    // EFFECTS: frame to show if the workout has been successfully added
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

    // MODIFIES: this 
    //EFFECTS: clears the whole day of exercises
    public void clear() {
        clearFrame();
        JPanel input = new JPanel();
        JLabel typeLabel = new JLabel("Which day do you want to clear this exercise for?");
        JTextField textField = new JTextField(10);
        JButton submit = new JButton("Submit");
        input.setLayout(new FlowLayout(FlowLayout.CENTER));
        input.add(typeLabel);
        input.add(textField);
        input.add(submit);
       
        submit.addActionListener(e -> {
            String day = textField.getText();
            ArrayList<Integer> temp = sched.clearExercise(day.toLowerCase());
            if (temp.get(0) != 0) { 
                person.substractCurrentCalories(temp.get(0));
                person.setTime(person.getTime() + temp.get(1));
            }
            clearFrame.dispose();
            startFresh();
        });
        clearFrame.add(display, BorderLayout.WEST);
        clearFrame.add(input, BorderLayout.EAST);
        clearFrame.setVisible(true);     
        
    }

    //MODIFIES: this
    // EFFECTS: new frame to help clear exercises in a day
    public void clearFrame() {
        clearFrame = new JFrame();
        clearFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clearFrame.setSize(900, 200);
        clearFrame.setLayout(new BorderLayout());

        display = new JPanel();
        enterJLabel = new JLabel("Here are the current days with exercises");
        types = new JTextArea();
        types.setEditable(false);
        types.setText(String.join("\n", sched.typeDay())); 
        display.setLayout(new FlowLayout(FlowLayout.CENTER));
        display.add(enterJLabel);
        display.add(types);

    }

    // MODIFIES: this 
    //EFFECTS: removes an exercise for the day
    public void removeExercise() {
        printTable();

        JPanel input = new JPanel();
        JLabel typeLabel = new JLabel("Which day do you want to remove this exercise for?");
        JTextField textField = new JTextField(10);
        JButton submit = new JButton("Submit");
        input.setLayout(new BorderLayout());
        input.add(typeLabel, BorderLayout.NORTH);
        input.add(textField, BorderLayout.CENTER);
        input.add(submit, BorderLayout.SOUTH);
       
        submit.addActionListener(e -> {
            String day = textField.getText();
            removeFrame.dispose();
            finishRemoveExercise(day.toLowerCase());
        });

        
        removeFrame.add(display, BorderLayout.WEST);
        removeFrame.add(input, BorderLayout.EAST);
        removeFrame.setVisible(true);     
    }

    // EFFECTS: helps to print the current data table
    public void printTable() {
        removeFrame = new JFrame();
        removeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        removeFrame.setSize(800, 200);
        removeFrame.setLayout(new BorderLayout());

        display = new JPanel();
        enterJLabel = new JLabel("Here are the current days that you can remove from");
        types = new JTextArea();
        types.setEditable(false);
        types.setText(String.join("\n", sched.typeDay())); 
        display.setLayout(new BorderLayout());
        display.add(enterJLabel, BorderLayout.NORTH);
        display.add(types, BorderLayout.CENTER);
    }

    //MODIFIES: this
    // EFFECTS: helper function to help finish removing exercises
    public void finishRemoveExercise(String day) {
        finishRemovingStart();
        ArrayList<Exercise> exerciseList = sched.getDay(day).getExercisesForTheDay();
        ArrayList<String> names = new ArrayList<>();
        for (Exercise e :  exerciseList) {
            names.add(e.getName());
        }
        displayArea.setText(String.join("\n", names)); 
        updateRemoving();

        JPanel input = new JPanel();
        input.setLayout(new BorderLayout());
        JLabel typeLabel = new JLabel("Whats the name of the exercise? (case sensitive)");
        JTextField textField = new JTextField(10);
        removeButton = new JButton("Submit");
        input.add(typeLabel, BorderLayout.NORTH);
        input.add(textField, BorderLayout.CENTER);
        input.add(removeButton, BorderLayout.SOUTH);
        removeButton(textField, day);
       
        removeFrame.add(display, BorderLayout.WEST);
        removeFrame.add(input, BorderLayout.EAST);
        removeFrame.setVisible(true);     
    }

    //MODIFIES: this
    // EFFECTS: helper function to help finish removing exercises
    public void finishRemovingStart() {
        removeFrame = new JFrame();
        removeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        removeFrame.setSize(700, 200);
        removeFrame.setLayout(new BorderLayout());

        display = new JPanel();
        enterJLabel = new JLabel("Here are the current exercises that you can remove from");
        displayArea = new JTextArea();
        displayArea.setEditable(false);
    }

    // EFFECTS: creates a Jframe to help remove exercises
    public void updateRemoving() {
        display.setLayout(new BorderLayout());
        display.add(enterJLabel, BorderLayout.NORTH);
        display.add(displayArea, BorderLayout.CENTER);
    }

    //MODIFIES: this
    // EFFECTS: helper function submit removing
    public void removeButton(JTextField textField, String day) {
        removeButton.addActionListener(e -> {
            String name = textField.getText();
            ArrayList<Integer> temp = sched.removeExercise(day, name);
            if (temp.get(0) != 0) {
                person.substractCurrentCalories(temp.get(0));
                person.setTime(person.getTime() + temp.get(1));
            }
            removeFrame.dispose();
            startFresh();
        });

    }

    //EFFECTS: prints out the schedule
    public void printSched() {
        ArrayList<ArrayList<Exercise>> exercises = sched.allExercises();
        int maxExercises = sched.maxExercises();
        setNewTable();
       
        String[] columnNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayList<String> types = sched.type();
        Object[][] tableData = new Object[maxExercises + 1][7];

        for (int i = 0; i < 7; i++) {
            tableData[0][i] = types.get(i); 
        }

        for (int i = 0; i < maxExercises; i++) {
            for (int j = 0; j < 7; j++) {
                if (i < exercises.get(j).size()) {
                    tableData[i + 1][j] = exercises.get(j).get(i).getName(); 
                } else {
                    tableData[i + 1][j] = ""; 
                }
            }
        }
        returnButton();
        table = new JTable(tableData, columnNames);
        finishNewTable();
       
    }

    // EFFECTS: creates a new table Frame
    public void setNewTable() {
        tableFrame = new JFrame(person.getName() + "'s Weekly Schedule");
        tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableFrame.setSize(800, 500);
    }

    // EFFECTS: adds all components to table
    public void finishNewTable() {
        tableFrame.setLayout(new BorderLayout());
        tableFrame.add(printStatsBar(), BorderLayout.NORTH);
        tableFrame.add(returnHome, BorderLayout.SOUTH);
        tableFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        tableFrame.setVisible(true);
    }

    // EFFECTS: creates JButton to return home
    public void returnButton() {
        returnHome = new JButton("Click Here to Return Home");
        returnHome.addActionListener(e -> {
            tableFrame.dispose();
            startFresh();
        });
    }



    // EFFECTS: saves the WeeklySchedule to file
    private void saveWeeklySchedule() {
        try {
            jsonWriter.open();
            jsonWriter.write(sched, person);
            jsonWriter.close();
            result("Saved!");
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
            startFresh();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}

