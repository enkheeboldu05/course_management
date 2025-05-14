package src.ui;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import src.model.Student;
import src.db.StudentDAO;
import src.model.Course;
import src.db.CourseDAO;
import src.model.User;
import src.ui.LoginWindow;

public class TeacherDashboard extends JFrame {
    private User teacher;
    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();

    public TeacherDashboard(User teacher) {
        this.teacher = teacher;

        setTitle("Teacher Dashboard - " + teacher.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome, " + teacher.getUsername());
        welcomeLabel.setBounds(20, 20, 300, 25);
        add(welcomeLabel);
        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.setBounds(120, 50, 150, 30);
        add(addStudentBtn);

        JButton viewStudentsBtn = new JButton("View Students");
        viewStudentsBtn.setBounds(120, 90, 150, 30);
        add(viewStudentsBtn);

        JButton addCourseBtn = new JButton("Add Course");
        addCourseBtn.setBounds(120, 130, 150, 30);
        add(addCourseBtn);

        JButton viewCoursesBtn = new JButton("View Courses");
        viewCoursesBtn.setBounds(120, 170, 150, 30);
        add(viewCoursesBtn);

        JButton profileBtn = new JButton("View My Profile");
        profileBtn.setBounds(30,230, 200, 30);
        add(profileBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(120, 210, 150, 30);
        add(logoutBtn);

        // === Action Listeners ===
        addStudentBtn.addActionListener(e -> addStudent());
        viewStudentsBtn.addActionListener(e -> viewStudents());
        addCourseBtn.addActionListener(e -> addCourse());
        viewCoursesBtn.addActionListener(e -> viewCourses());
        profileBtn.addActionListener( e-> showProfile());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginWindow();
        });

        setVisible(true);
    }

    private void addStudent() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField departmentField = new JTextField();

        Object[] fields = {
            "Name:", nameField,
            "Email:", emailField,
            "Department:", departmentField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Student student = new Student(nameField.getText(), emailField.getText(), departmentField.getText());
            studentDAO.addStudent(student);
        }
    }

    private void viewStudents() {
        List<Student> students = studentDAO.getAllStudents();
        String[] columnNames = {"ID", "Name", "Email", "Department"};
        String[][] data = new String[students.size()][4];

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            data[i][0] = String.valueOf(s.getId());
            data[i][1] = s.getName();
            data[i][2] = s.getEmail();
            data[i][3] = s.getDepartment();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame tableFrame = new JFrame("Student List");
        tableFrame.setSize(500, 300);
        tableFrame.add(scrollPane);
        tableFrame.setLocationRelativeTo(this);
        tableFrame.setVisible(true);
    }

    private void addCourse() {
        JTextField titleField = new JTextField();
        JTextField descField = new JTextField();

        Object[] fields = {
            "Title:", titleField,
            "Description:", descField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Course", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Course course = new Course(titleField.getText(), descField.getText());
            courseDAO.addCourse(course);
        }
    }

    private void viewCourses() {
        List<Course> courses = courseDAO.getAllCourses();
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

        JFrame tableFrame = new JFrame("Course List");
        tableFrame.setSize(500, 300);
        tableFrame.add(scrollPane);
        tableFrame.setLocationRelativeTo(this);
        tableFrame.setVisible(true);
    }

    private void showProfile() {
        String info = "Username: " + teacher.getUsername() +
                  "\nEmail: " + teacher.getEmail() +
                  "\nDepartment: " + teacher.getDepartment();
        JOptionPane.showMessageDialog(this, info, "My Profile", JOptionPane.INFORMATION_MESSAGE);
    }

}
