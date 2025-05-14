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
import src.db.EnrollmentDAO;

public class TeacherDashboard extends JFrame {
    private User teacher;
    private JComboBox<String> courseComboBox;
    private JButton viewEnrolledStudentsBtn;
    private List<Course> allCourses;
    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();

    public TeacherDashboard(User teacher) {
        this.teacher = teacher;

        setTitle("Teacher Dashboard - " + teacher.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        allCourses = courseDAO.getAllCourses();
        String[] courseTitles = allCourses.stream()
            .map(c -> c.getId() + " - " + c.getTitle())
            .toArray(String[]::new);
        courseComboBox = new JComboBox<>(courseTitles);
        courseComboBox.setBounds(120, 250, 150, 25);
        add(courseComboBox);

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
        profileBtn.setBounds(120 ,250, 150, 30);
        add(profileBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(120, 210, 150, 30);
        add(logoutBtn);

        JButton viewEnrolledStudentsBtn = new JButton("View Enrolled Students");
        viewEnrolledStudentsBtn.setBounds(120, 290, 180, 30);
        add(viewEnrolledStudentsBtn);

        // === Action Listeners ===
        addStudentBtn.addActionListener(e -> addStudent());
        viewStudentsBtn.addActionListener(e -> viewStudents());
        addCourseBtn.addActionListener(e -> addCourse());
        viewCoursesBtn.addActionListener(e -> viewCourses());
        profileBtn.addActionListener( e-> showProfile());
        viewEnrolledStudentsBtn.addActionListener(e -> viewEnrolledStudents());
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

    private void viewEnrolledStudents() {
    int selectedIndex = courseComboBox.getSelectedIndex();
    if (selectedIndex == -1) {
        JOptionPane.showMessageDialog(this, "❌ Please select a course.");
        return;
    }

    Course selectedCourse = allCourses.get(selectedIndex);
    int courseId = selectedCourse.getId();

    List<Student> students = new EnrollmentDAO().getStudentsByCourseId(courseId);

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

    JFrame tableFrame = new JFrame("Enrolled Students for: " + selectedCourse.getTitle());
    tableFrame.setSize(500, 300);
    tableFrame.add(scrollPane);
    tableFrame.setLocationRelativeTo(this);
    tableFrame.setVisible(true);
}

}
