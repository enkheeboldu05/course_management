package src.ui;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

import src.model.User;
import src.model.Course;
import src.db.CourseDAO;
import src.db.EnrollmentDAO;

public class StudentDashboard extends JFrame {
    private User student;
    private CourseDAO courseDAO = new CourseDAO();
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private JTable availableCoursesTable;

    public StudentDashboard(User student) {
        this.student = student;
        setTitle("Student Dashboard - " + student.getUsername());
        setSize(600, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome, " + student.getUsername());
        welcomeLabel.setBounds(20, 20, 300, 25);
        add(welcomeLabel);

        JButton viewAvailableBtn = new JButton("View Available Courses");
        viewAvailableBtn.setBounds(30,70,200,30);
        add(viewAvailableBtn);

        JButton enrollBtn = new JButton("Enroll in Selected Course");
        enrollBtn.setBounds(30,110,200,30);
        add(enrollBtn);

        JButton viewEnrolledBtn = new JButton("View My Courses");
        viewEnrolledBtn.setBounds(30, 150, 200, 30);
        add(viewEnrolledBtn);

        JButton profileBtn = new JButton("View My Profile");
        profileBtn.setBounds(30,230, 200, 30);
        add(profileBtn);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(30, 190, 200, 30);
        add(logoutBtn);

        viewAvailableBtn.addActionListener(e -> showAvailableCourses());
        enrollBtn.addActionListener(e -> enrollInSelectedCourse());
        viewEnrolledBtn.addActionListener(e -> showMyCourses());
        profileBtn.addActionListener( e-> showProfile());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginWindow();
        });

        setVisible(true);
    }
    
    private void showAvailableCourses(){
        List<Course> courses = courseDAO.getAllCourses();

        String[] columnNames = {"ID", "Title", "Desciption"};
        String[][] data = new String[courses.size()][3];

        for(int i = 0; i < courses.size(); i++){
            Course c = courses.get(i);
            data[i][0] = String.valueOf(c.getId());
            data[i][1] = c.getTitle();
            data[i][2] = c.getDescription();
        }

        availableCoursesTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(availableCoursesTable);
        scrollPane.setBounds(250,70,320,200);
        add(scrollPane);
        repaint();
        revalidate();
    }

    private void enrollInSelectedCourse() {
        if (availableCoursesTable == null || availableCoursesTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "❌ Please select a course to enroll.");
            return;
        }

        int selectedRow = availableCoursesTable.getSelectedRow();
        int courseId = Integer.parseInt((String) availableCoursesTable.getValueAt(selectedRow, 0));

        boolean success = enrollmentDAO.enrollStudent(student.getId(), courseId);
        if (success) {
            JOptionPane.showMessageDialog(this, "✅ Enrolled successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Already enrolled or error occurred.");
        }
    }
    
    private void showMyCourses() {
        List<Course> courses = enrollmentDAO.getCoursesByStudentId(student.getId());

        String[] columnNames = {"ID", "Title", "Description"};
        String[][] data = new String[courses.size()][3];

        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            data[i][0] = String.valueOf(c.getId());
            data[i][1] = c.getTitle();
            data[i][2] = c.getDescription();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("My Courses");
        frame.setSize(400, 300);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }
    private void showProfile() {
        String info = "Username: " + student.getUsername() +
                  "\nEmail: " + student.getEmail() +
                  "\nDepartment: " + student.getDepartment();
        JOptionPane.showMessageDialog(this, info, "My Profile", JOptionPane.INFORMATION_MESSAGE);
    }
}
