package ui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchedulePanel {

    public SchedulePanel() {
        JLabel label = new JLabel();
        label.setText("Welcome!");

        JFrame frame = new JFrame("My Swing App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setVisible(true);
        frame.add(label);
        JPanel panel = new JPanel();
    }
    
}
