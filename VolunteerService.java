package com.example.CleanGreenIndore;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VolunteerService {

    // Method to add a new volunteer
    public void addNewVolunteer() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            String query = "INSERT INTO Volunteers (V_id, name, contact, email) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter Volunteer ID: ");
            int V_id = getValidInt(scanner);
            scanner.nextLine(); // Clear the buffer
            System.out.println();
            // Validation loop for the Volunteer Name input
            String name = "";
            while (true) {
                System.out.print("Enter Volunteer Name: ");
                name = scanner.nextLine();
                System.out.println();
                if (name.matches("[a-zA-Z\\s]+")) { // Check if name contains only letters and spaces
                    break;  // Valid name entered, exit the loop
                } else {
                    System.out.println("**************************************************");
                    System.out.println("Invalid Volunteer Name. Please enter only letters.");
                    System.out.println("************************************************** ");
                }
            }

            System.out.print("Enter Contact Number: ");
            String contact = scanner.nextLine();
            System.out.println();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
             System.out.println();
            stmt.setInt(1, V_id);
            stmt.setString(2, name);
            stmt.setString(3, contact);
            stmt.setString(4, email);

            stmt.executeUpdate();
            System.out.println("**********************************");
            System.out.println("Volunteer added successfully!");
            System.out.println("***********************************");

        } catch (SQLException e) {
            System.out.println("***************************************************");
            System.out.println("Error while adding the volunteer: " + e.getMessage());
            System.out.println("*****************************************************");
        }
    }

    // Method to display all volunteers
    public void displayAllVolunteers() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            String query = "SELECT * FROM Volunteers";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("*******************************************");
                System.out.println("No volunteers found in the database.");
                System.out.println("*****************************************");
            } else {
                while (rs.next()) {
                    System.out.println("Volunteer ID: " + rs.getInt("V_id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Contact: " + rs.getString("contact"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("=================================");
                }
            }
        } catch (SQLException e) {
            System.out.println("******************************************************");
            System.out.println("Error while displaying volunteers: " + e.getMessage());
            System.out.println("********************************************************");
        }
    }

    // Method to update a volunteer
    public void updateVolunteer() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            Scanner scanner = new Scanner(System.in);
             System.out.println();
            System.out.print("Enter the Volunteer ID to update: ");
            int V_id = getValidInt(scanner);
            scanner.nextLine(); // Clear the buffer

            // Validation loop for updating Volunteer Name
            String name = "";
            while (true) {
                System.out.println();
                System.out.print("Enter new Volunteer Name (or press enter to skip): ");
                name = scanner.nextLine();
                if (name.isEmpty() || name.matches("[a-zA-Z\\s]+")) { // Allow empty input to skip or check for valid name
                    break;  // Valid name entered or skipped, exit the loop
                } else {
                    System.out.println("******************************************************************************");
                    System.out.println("Invalid Volunteer Name. Please enter only letters or press enter to skip.");
                    System.out.println("*******************************************************************************");
                }
            }

            System.out.print("Enter new Contact (or press enter to skip): ");
            String contact = scanner.nextLine();
             System.out.println();
            System.out.print("Enter new Email (or press enter to skip): ");
            String email = scanner.nextLine();
             System.out.println();
            StringBuilder queryBuilder = new StringBuilder("UPDATE Volunteers SET ");
            boolean hasUpdates = false;
            System.out.println();
            if (!name.isEmpty()) {
                queryBuilder.append("name = ?, ");
                hasUpdates = true;
            }
            if (!contact.isEmpty()) {
                queryBuilder.append("contact = ?, ");
                hasUpdates = true;
            }
            if (!email.isEmpty()) {
                queryBuilder.append("email = ?, ");
                hasUpdates = true;
            }

            if (hasUpdates) {
                queryBuilder.setLength(queryBuilder.length() - 2); // Remove trailing ", "
                queryBuilder.append(" WHERE V_id = ?");
            } else {

                System.out.println("*************************");
                System.out.println("No updates provided.");
                System.out.println("****************************");
                return;
            }

            PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString());

            int paramIndex = 1;

            if (!name.isEmpty()) {
                stmt.setString(paramIndex++, name);
            }
            if (!contact.isEmpty()) {
                stmt.setString(paramIndex++, contact);
            }
            if (!email.isEmpty()) {
                stmt.setString(paramIndex++, email);
            }

            stmt.setInt(paramIndex, V_id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("**********************************");
                System.out.println("Volunteer updated successfully!");
                System.out.println("************************************");
            } else {
                System.out.println("**********************************");
                System.out.println("Volunteer ID not found.");
                System.out.println("*************************************");
            }
        } catch (SQLException e) {
            System.err.println("****************************************************");
            System.out.println("Error while updating the volunteer: " + e.getMessage());
            System.out.println("******************************************************");
        }
    }

    // Method to delete a volunteer
    public void deleteVolunteer() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the Volunteer ID to delete: ");
            int volunteerId = getValidInt(scanner);
            System.out.println();
            String query = "DELETE FROM Volunteers WHERE V_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, volunteerId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("**********************************");
                System.out.println("Volunteer deleted successfully!");
                System.out.println("***********************************");
            } else {
                System.out.println("***************************");
                System.out.println("Volunteer ID not found.");
                System.out.println("******************************");
            }
        } catch (SQLException e) {
            System.out.println("****************************************************");
            System.out.println("Error while deleting the volunteer: " + e.getMessage());
            System.out.println("******************************************************");
        }
    }

    // Utility method to get valid integer input
    private int getValidInt(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("***********************************************");
                System.out.println("Invalid input. Please enter a valid integer.");
                System.out.println("************************************************");
                scanner.next(); // Clear the invalid input
            }
        }
    }
}
