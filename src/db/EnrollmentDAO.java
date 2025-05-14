package src.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import src.model.Course;
import src.model.Student;

public class EnrollmentDAO {
    public boolean enrollStudent(int studentId, int courseId) {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
            System.out.println("✅ Enrollment successful.");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Enrollment error: " + e.getMessage());
            return false;
        }
    }

    public List<Course> getCoursesByStudentId(int studentId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.id, c.title, c.description FROM courses c " +
                     "JOIN enrollments e ON c.id = e.course_id " +
                     "WHERE e.student_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Course course = new Course(
                    rs.getString("title"),
                    rs.getString("description")
                );
                course.setId(rs.getInt("id"));
                courses.add(course);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error retrieving enrolled courses: " + e.getMessage());
        }

        return courses;
    }

    public List<Student> getStudentsByCourseId(int courseId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT u.id, u.username, u.email, u.department FROM enrollments e " +
                 "JOIN users u ON e.student_id = u.id " +
                 "WHERE e.course_id = ?";

        try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, courseId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Student s = new Student(
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("department")
            );
            s.setId(rs.getInt("id"));
            students.add(s);
        }

        } catch (SQLException e) {
            System.out.println("❌ Error retrieving students for course: " + e.getMessage());
        }

    return students;    
    }

}