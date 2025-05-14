package src.db;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;
import src.model.User;

public class UserDAO {

    public boolean registerUser(User user) {
        String checkQuery = "SELECT * FROM users WHERE username = ?";
        String insertQuery = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setString(1, user.getUsername());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("❌ Username already exists!");
                return false;
            }
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            insertStmt.setString(1, user.getUsername());
            insertStmt.setString(2, hashedPassword);
            insertStmt.setString(3, user.getEmail());
            insertStmt.setString(4, user.getRole());
            insertStmt.executeUpdate();

            System.out.println("✅ Registration successful!");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Registration error: " + e.getMessage());
            return false;
        }
    }

    public User loginUser(String username, String password) {
    String query = "SELECT * FROM users WHERE username = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String storedHash = rs.getString("password");

            if (BCrypt.checkpw(password, storedHash)) {
                User user = new User(
                    rs.getString("username"),
                    "", 
                    rs.getString("email")
                );
                user.setId(rs.getInt("id"));
                user.setRole(rs.getString("role"));
                System.out.println("✅ Login successful as " + user.getRole());
                return user;
            } else {
                System.out.println("❌ Incorrect password.");
            }
        } else {
            System.out.println("❌ Username not found.");
        }

    } catch (SQLException e) {
        System.out.println("❌ Login error: " + e.getMessage());
    }

    return null; 
}

}

