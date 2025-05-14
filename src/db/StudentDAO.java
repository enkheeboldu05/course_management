package src.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import src.model.Student;
import src.db.DBConnection;

public class StudentDAO {

    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, email, major) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getMajor());

            stmt.executeUpdate();
            System.out.println("✅ Student added successfully.");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error adding student: " + e.getMessage());
            return false;
        }
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("major")
                );
                student.setId(rs.getInt("id"));
                studentList.add(student);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching students: " + e.getMessage());
        }

        return studentList;
    }
}
