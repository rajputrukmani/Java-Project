 
package com.example.CleanGreenIndore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginService {

    private String userRole;  // Store user role for access control

    public boolean login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("**************** User Login ******************");
        System.out.println();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
         System.out.println();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println();
        // Validate user credentials
        boolean isAuthenticated = validateCredentials(email, password);

        if (isAuthenticated) {
            System.out.println("***************************************************");
            System.out.println("Login successful! Welcome to Clean Green Indore.");
            System.out.println("***************************************************");
            System.out.println("Your role: " + userRole);

            // Role-based messaging
            if (userRole.equalsIgnoreCase("Admin")) {

                System.out.println("You are logged in as an Admin.");

            } else {
                System.out.println("You are logged in as a regular User.");
            }
        } else {
            System.out.println("************************************************");
            System.out.println("Invalid email or password. Please try again.");
            System.out.println("************************************************");
        }

        return isAuthenticated;
    }

    private boolean validateCredentials(String email, String password) {
        // Query to fetch the user's password and role from the database
        String query = "SELECT password, role FROM Users1 WHERE email = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                userRole = rs.getString("role");

                // Compare the stored password with the input password
                // Consider using password hashing in production (e.g., BCrypt)
                return storedPassword.equals(password);  // For demo purposes; use hashed passwords
            } else {
                return false;  // User not found
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Getter for the user role, useful for access control after login
    public String getUserRole() {
        return userRole;
    }
}
