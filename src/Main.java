package src;
import src.ui.LoginWindow;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Launch the GUI on the Swing Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginWindow());
    }
}


// import java.util.List;
// import java.util.Scanner;

// public class Main {
//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         UserDAO userDAO = new UserDAO();
//         StudentDAO studentDAO = new StudentDAO();

//         System.out.println("=== Welcome to the System ===");
//         System.out.println("1 - Register");
//         System.out.println("2 - Login");
//         System.out.print("Choice: ");
//         int choice = sc.nextInt();
//         sc.nextLine(); // consume newline

//         User loggedInUser = null;

//         if (choice == 1) {
//             System.out.print("Enter username: ");
//             String username = sc.nextLine();
//             System.out.print("Enter password: ");
//             String password = sc.nextLine();
//             System.out.print("Enter email: ");
//             String email = sc.nextLine();
//             System.out.print("Are you registering as 'teacher' or 'student'? ");
//             String role = sc.nextLine().toLowerCase();

//             if (!role.equals("teacher") && !role.equals("student")) {
//                 System.out.println("❌ Invalid role. Must be 'teacher' or 'student'.");
//                 return;
//             }

//             User user = new User(username, password, email);
//             user.setRole(role);

//             if (userDAO.registerUser(user)) {
//                 System.out.println("✅ You can now log in.");
//             } else {
//                 return;
//             }
//         }

//         if (choice == 2 || choice == 1) {
//             System.out.print("Enter username: ");
//             String username = sc.nextLine();
//             System.out.print("Enter password: ");
//             String password = sc.nextLine();

//             loggedInUser = userDAO.loginUser(username, password);

//             if (loggedInUser == null) {
//                 System.out.println("❌ Login failed.");
//                 return;
//             }
//         }

//         // === Role-based menu ===
//         if (loggedInUser != null) {
//             String role = loggedInUser.getRole();

//             if ("teacher".equals(role)) {
//                 // 👨‍🏫 Teacher menu
//                 while (true) {
//                     System.out.println("\n=== Teacher Menu ===");
//                     System.out.println("1 - Add Student");
//                     System.out.println("2 - View All Students");
//                     System.out.println("3 - Logout");
//                     System.out.print("Choice: ");
//                     int tChoice = sc.nextInt();
//                     sc.nextLine();

//                     switch (tChoice) {
//                         case 1:
//                             System.out.print("Student name: ");
//                             String name = sc.nextLine();
//                             System.out.print("Student email: ");
//                             String email = sc.nextLine();
//                             System.out.print("Student major: ");
//                             String major = sc.nextLine();
//                             Student s = new Student(name, email, major);
//                             studentDAO.addStudent(s);
//                             break;
//                         case 2:
//                             List<Student> students = studentDAO.getAllStudents();
//                             System.out.println("\n--- Students ---");
//                             for (Student stu : students) {
//                                 System.out.printf("ID: %d | Name: %s | Email: %s | Major: %s\n",
//                                         stu.getId(), stu.getName(), stu.getEmail(), stu.getMajor());
//                             }
//                             break;
//                         case 3:
//                             System.out.println("Logging out...");
//                             return;
//                         default:
//                             System.out.println("❌ Invalid option.");
//                     }
//                 }
//             } else if ("student".equals(role)) {
//                 // 🎓 Student menu
//                 while (true) {
//                     System.out.println("\n=== Student Menu ===");
//                     System.out.println("1 - View Profile (Coming Soon)");
//                     System.out.println("2 - Logout");
//                     System.out.print("Choice: ");
//                     int sChoice = sc.nextInt();
//                     sc.nextLine();

//                     switch (sChoice) {
//                         case 1:
//                             System.out.println("🔒 You’ll be able to view your enrolled courses soon.");
//                             break;
//                         case 2:
//                             System.out.println("Logging out...");
//                             return;
//                         default:
//                             System.out.println("❌ Invalid option.");
//                     }
//                 }
//             } else {
//                 System.out.println("❌ Unknown user role.");
//             }
//         }
//     }
// }
