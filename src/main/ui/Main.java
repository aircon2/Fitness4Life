package ui;

import java.io.FileNotFoundException;

public class Main {
    // main class, run application from here
    public static void main(String[] args) throws Exception {
        try { 
            new ScheduleApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application");
        }
    }
}
