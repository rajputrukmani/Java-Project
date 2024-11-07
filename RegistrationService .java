 
package com.example.CleanGreenIndore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RegistrationService {

    private static final String ADMIN_AUTH_CODE = "123"; // Replace with your actual authorization code

    public void registerNewUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("** User Registration **");
        System.out.println();
        System.out.println();

        // Validation loop for the name input
        String name = "";
        while (true) {
            System.out.print("Enter your name: ");
            name = scanner.nextLine();

            if (name.matches("[a-zA-Z\\s]+")) { // Check if name contains only letters and spaces
                break;  // Valid name entered, exit the loop
            } else {
                System.out.println();
                System.out.println("Invalid name. Please enter only letters.");
                System.out.println();
            }
        }

        // Validation loop for the email input
        String email = "";
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();

            if (isValidEmail(email)) {
                break;  // Valid email entered, exit the loop
            } else {
                System.out.println("Invalid email. Please enter a valid email address (e.g., user@example.com).");
                System.out.println();
            }
        }

        System.out.println();

        // Validation loop for the phone number input
        String phone_number = "";
        while (true) {
            System.out.print("Enter your phone number: ");
            phone_number = scanner.nextLine();

            if (isValidPhoneNumber(phone_number)) {
                break;  // Valid phone number entered, exit the loop
            } else {
                System.out.println("Invalid phone number. Please enter a valid 10-digit phone number.");
                System.out.println();
            }
        }

        System.out.println();

        // Validation loop for the password input
        String password = "";
        while (true) {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();

            if (isValidPassword(password)) { // Validate the password using custom validation function
                break; // Valid password, exit loop
            } else {
                System.out.println("Invalid password. Please ensure it meets the following criteria:");
                System.out.println("- At least 8 characters long");
                System.out.println("- Contains at least one uppercase letter");
                System.out.println("- Contains at least one lowercase letter");
                System.out.println("- Contains at least one digit");
                System.out.println("- Contains at least one special character (e.g., !@#$%^&*)");
                System.out.println();
            }
        }

        String role = "";
        while (true) {  // Loop to ensure correct role or user decision
            System.out.print("Enter role (Admin/User): ");
            role = scanner.nextLine();

            if (role.equalsIgnoreCase("Admin")) {
                System.out.print("Enter admin authorization code: ");
                String adminCode = scanner.nextLine();

                if (!adminCode.equals(ADMIN_AUTH_CODE)) {
                    System.out.println("****************************************");
                    System.out.println("Invalid authorization code for Admin role.");
                    System.out.println("Please try again.");
                    System.out.println("*******************************************");
                    // Let the user retry entering the role or the correct admin code.
                } else {
                    System.out.println("******************************************");
                    System.out.println("Admin authorization successful.");
                    System.out.println("******************************************");
                    break;  // Exit the loop since Admin role is valid.
                }
            } else if (role.equalsIgnoreCase("User")) {
                role = "User";  // Valid user role selected.
                break;
            } else {
                System.out.println("***************************************************");
                System.out.println("Invalid role. Please choose 'Admin' or 'User'.");
                System.out.println("*****************************************************");
            }
        }

        // Save user to the database
        saveUserToDatabase(name, email, phone_number, password, role);
    }

    // Method to validate email using regex
    private boolean isValidEmail(String email) {
        // Regex for validating email format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }

    // Method to validate phone number using regex
    private boolean isValidPhoneNumber(String phone) {
        // Regex to check if phone number contains exactly 10 digits
        String phoneRegex = "\\d{10}";
        return Pattern.matches(phoneRegex, phone);
    }

    // Method to validate password using regex
    private boolean isValidPassword(String password) {
        // Regex for password validation
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    private void saveUserToDatabase(String name, String email, String phone, String password, String role) {
       // Query to insert data
        String query = "INSERT INTO Users1 (name, email, phone_number, password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();  // try with resource
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name); // to set in database
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, password);  // Consider hashing passwords in a real app
            stmt.setString(5, role);

            int rowsAffected = stmt.executeUpdate();  // Update in database

            if (rowsAffected > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("User registration failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
  
