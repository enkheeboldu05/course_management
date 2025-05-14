package src.ui;

import javax.swing.*;
import src.model.User;
import src.model.Course;
import src.db.EnrollmentDAO;

public class StudentDashboard extends JFrame {
    private User student;

    public StudentDashboard(User student) {
        this.student = student;
        setTitle("Student Dashboard - " + student.getUsername());
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel label = new JLabel("Welcome, " + student.getUsername() + "!");
        label.setBounds(80, 50, 200, 25);
        add(label);

        JLabel note = new JLabel("(Courses view coming soon)");
        note.setBounds(60, 80, 200, 25);
        add(note);

        setVisible(true);
    }
}
