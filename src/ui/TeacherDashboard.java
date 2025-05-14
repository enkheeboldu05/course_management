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
    private List<Course> allCourses;
    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private JTable displayTable;
    private JScrollPane displayScrollPane;

    public TeacherDashboard(User teacher) {
        this.teacher = teacher;

        setTitle("Teacher Dashboard - " + teacher.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        // Course dropdown
        allCourses = courseDAO.getAllCourses();
        String[] courseTitles = allCourses.stream()
            .map(c -> c.getId() + " - " + c.getTitle())
            .toArray(String[]::new);
        courseComboBox = new JComboBox<>(courseTitles);
        courseComboBox.setBounds(30, 260, 200, 25);
        add(courseComboBox);

        JLabel welcomeLabel = new JLabel("Welcome, " + teacher.getUsername());
        welcomeLabel.setBounds(20, 20, 300, 25);
        add(welcomeLabel);

        int startX = 30, startY = 60, width = 200, height = 30, gap = 10;
        int line = 0;

        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.setBounds(startX, startY + (height + gap) * line++, width, height);
        add(addStudentBtn);

        JButton viewStudentsBtn = new JButton("View Students");
        viewStudentsBtn.setBounds(startX, startY + (height + gap) * line++, width, height);
        add(viewStudentsBtn);

        JButton addCourseBtn = new JButton("Add Course");
        addCourseBtn.setBounds(startX, startY + (height + gap) * line++, width, height);
        add(addCourseBtn);

        JButton viewCoursesBtn = new JButton("View Courses");
        viewCoursesBtn.setBounds(startX, startY + (height + gap) * line++, width, height);
        add(viewCoursesBtn);

        JButton viewEnrolledStudentsBtn = new JButton("View Enrolled Students");
        viewEnrolledStudentsBtn.setBounds(startX, startY + (height + gap) * line++, width, height);
        add(viewEnrolledStudentsBtn);

        JButton profileBtn = new JButton("View My Profile");
        profileBtn.setBounds(startX, startY + (height + gap) * line++, width, height);
        add(profileBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(startX, startY + (height + gap) * line++, width, height);
        add(logoutBtn);

        // === Action Listeners ===
        addStudentBtn.addActionListener(e -> addStudent());
        viewStudentsBtn.addActionListener(e -> displayStudents());
        addCourseBtn.addActionListener(e -> addCourse());
        viewCoursesBtn.addActionListener(e -> displayCourses());
        viewEnrolledStudentsBtn.addActionListener(e -> displayEnrolledStudents());
        profileBtn.addActionListener(e -> showProfile());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginWindow();
        });

        setVisible(true);
    }

    private void setTableContent(String[][] data, String[] columns) {
        if (displayScrollPane != null) {
            remove(displayScrollPane); // remove the old one
        }

        displayTable = new JTable(data, columns);
        displayScrollPane = new JScrollPane(displayTable);
        displayScrollPane.setBounds(260, 60, 500, 260);
        add(displayScrollPane);
    
        repaint();
        revalidate();
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

    private void displayStudents() {
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

        setTableContent(data, columnNames);
    }

    private void displayCourses() {
        List<Course> courses = courseDAO.getAllCourses();
        String[] columnNames = {"ID", "Title", "Description"};
        String[][] data = new String[courses.size()][3];

        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            data[i][0] = String.valueOf(c.getId());
            data[i][1] = c.getTitle();
            data[i][2] = c.getDescription();
        }

        setTableContent(data, columnNames);
    }

    private void displayEnrolledStudents() {
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

        setTableContent(data, columnNames);
    }

    private void showProfile() {
        String info = "Username: " + teacher.getUsername() +
                      "\nEmail: " + teacher.getEmail() +
                      "\nDepartment: " + teacher.getDepartment();
        JOptionPane.showMessageDialog(this, info, "My Profile", JOptionPane.INFORMATION_MESSAGE);
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
}
