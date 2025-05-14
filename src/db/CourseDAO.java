package src.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import src.model.Course;

public class CourseDAO {
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (title, description) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getTitle());
            stmt.setString(2, course.getDescription());
            stmt.executeUpdate();
            System.out.println("✅ Course added successfully.");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error adding course: " + e.getMessage());
            return false;
        }
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Course c = new Course(
                    rs.getString("title"),
                    rs.getString("description")
                );
                c.setId(rs.getInt("id"));
                courses.add(c);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching courses: " + e.getMessage());
        }

        return courses;
    }
}
