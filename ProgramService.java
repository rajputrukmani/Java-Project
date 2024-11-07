 
package com.example.CleanGreenIndore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProgramService {

    // Method to add a new program
    public void addNewProgram() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            String query = "INSERT INTO Programs (P_id, Program_Name, Date, Location, Description, V_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter Program ID: ");
            int id = getValidInt(scanner);
            scanner.nextLine();  // Clear the buffer
            System.out.println();
            // Validation loop for Program Name input
            String name = "";
            while (true) {
                System.out.print("Enter Program Name: ");
                name = scanner.nextLine();
                if (name.matches("[a-zA-Z\\s]+")) { // Check if name contains only letters and spaces
                    break;  // Valid name entered, exit the loop
                } else {
                    System.out.println("Invalid Program Name. Please enter only letters.");
                }
            }

            System.out.print("Enter Program Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();
             System.out.println();
            System.out.print("Enter Location: ");
            String location = scanner.nextLine();
             System.out.println();
            System.out.print("Enter Description: ");
            String description = scanner.nextLine();
             System.out.println();
            System.out.print("Enter Volunteer ID: ");
            int vid = getValidInt(scanner);

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, date);
            stmt.setString(4, location);
            stmt.setString(5, description);
            stmt.setInt(6, vid);

            stmt.executeUpdate();
            System.out.println("************************************************");
            System.out.println("Program added successfully!");
            System.out.println("************************************************");

        } catch (SQLException e) {
            System.out.println("*************************************************");
            System.out.println("Error while adding the program: " + e.getMessage());
            System.out.println("****************************************************");
        }
    }

    // Method to display all programs
    public void displayAllPrograms() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            String query = "SELECT * FROM Programs";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (!rs.isBeforeFirst()) {
                System.out.println("***********************************");
                System.out.println("No programs found in the database.");
                System.out.println("*************************************");
            } else {
                while (rs.next()) {
                    System.out.println("Program ID: " + rs.getInt("P_id"));
                    System.out.println("Name: " + rs.getString("Program_Name"));
                    System.out.println("Date: " + rs.getString("Date"));
                    System.out.println("Location: " + rs.getString("Location"));
                    System.out.println("Description: " + rs.getString("Description"));
                    System.out.println("Volunteer ID: " + rs.getInt("V_id"));
                    System.out.println("=================================");
                }
            }
        } catch (SQLException e) {
            System.out.println("************************************************");
            System.out.println("Error while displaying programs: " + e.getMessage());
            System.out.println("************************************************");
        }
    }

    // Method to update an existing program
    public void updateProgram() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the Program ID to update: ");
            int programId = getValidInt(scanner);
            scanner.nextLine(); // Clear the buffer
            System.out.println();
            // Validation loop for Program Name input
            String name = "";
            while (true) {
                System.out.print("Enter new Program Name (or press enter to skip): ");
                name = scanner.nextLine();
                if (name.isEmpty() || name.matches("[a-zA-Z\\s]+")) { // Allow empty input to skip or check for valid name
                    break;  // Valid name entered or skipped, exit the loop
                } else {
                    System.out.println("***********************************************************************");
                    System.out.println("Invalid Program Name. Please enter only letters or press enter to skip.");
                    System.out.println("*************************************************************************");
                }
            }

            System.out.print("Enter new Date (YYYY-MM-DD) (or press enter to skip): ");
            String date = scanner.nextLine();
            System.out.println();
            System.out.print("Enter new Location (or press enter to skip): ");
            String location = scanner.nextLine();
            System.out.println();
            System.out.print("Enter new Description (or press enter to skip): ");
            String description = scanner.nextLine();
            System.out.println();
            System.out.print("Enter new Volunteer ID (or press enter to skip): ");
            int vid = getValidInt(scanner);
             System.out.println();
            StringBuilder queryBuilder = new StringBuilder("UPDATE Programs SET ");
            boolean hasUpdates = false;

            if (!name.isEmpty()) {
                queryBuilder.append("Program_Name = ?, ");
                hasUpdates = true;
            }
            if (!date.isEmpty()) {
                queryBuilder.append("Date = ?, ");
                hasUpdates = true;
            }
            if (!location.isEmpty()) {
                queryBuilder.append("Location = ?, ");
                hasUpdates = true;
            }
            if (!description.isEmpty()) {
                queryBuilder.append("Description = ?, ");
                hasUpdates = true;
            }
            if (vid > 0) {
                queryBuilder.append("V_id = ?, ");
                hasUpdates = true;
            }

            if (hasUpdates) {
                queryBuilder.setLength(queryBuilder.length() - 2); // Remove trailing ", "
                queryBuilder.append(" WHERE P_id = ?");
            } else {
                System.out.println("*************************************************");
                System.out.println("No updates provided.");
                System.out.println("**************************************************");
                return;
            }

            PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString());

            int paramIndex = 1;

            if (!name.isEmpty()) {
                stmt.setString(paramIndex++, name);
            }
            if (!date.isEmpty()) {
                stmt.setString(paramIndex++, date);
            }
            if (!location.isEmpty()) {
                stmt.setString(paramIndex++, location);
            }
            if (!description.isEmpty()) {
                stmt.setString(paramIndex++, description);
            }
            if (vid > 0) {
                stmt.setInt(paramIndex++, vid);
            }

            stmt.setInt(paramIndex, programId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("***********************************************");
                System.out.println("Program updated successfully!");
                System.out.println("************************************************");
            } else {
                System.out.println("************************************************");
                System.out.println("Program ID not found.");
                System.out.println("************************************************");
            }
        } catch (SQLException e) {
            System.out.println("***************************************************");
            System.out.println("Error while updating the program: " + e.getMessage());
            System.out.println("****************************************************");
        }
    }

    // Method to delete a program
    public void deleteProgram() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the Program ID to delete: ");
            int programId = getValidInt(scanner);

            String query = "DELETE FROM Programs WHERE P_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, programId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("********************************************");
                System.out.println("Program deleted successfully!");
                System.out.println("*********************************************");
            } else {
                System.out.println("*******************************************");
                System.out.println("Program ID not found.");
                System.out.println("*******************************************");
            }
        } catch (SQLException e) {
            System.out.println("**************************************************");
            System.out.println("Error while deleting the program: " + e.getMessage());
            System.out.println("***************************************************");
        }
    }

    // Utility method to get valid integer input
    private int getValidInt(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("*********************************************");
                System.out.println("Invalid input. Please enter a valid integer.");
                System.out.println("**********************************************");
                scanner.next(); // Clear the invalid input
            }
        }
    }
}
  
