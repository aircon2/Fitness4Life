package model;

// Person who wants a fitness schedule, keeps track of individual schedules, target caloric goal
public class Person {
    private String name;
    private int targetCalories;
    private ArrayList<String> schedule;

    public void Person(String name, int targetCalories) {
        this.name = name;
        this.targetCalories = targetCalories;
        schedule = new ArrayList();
    }

}
