package com.example.CleanGreenIndore;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CleanGreenIndoreApp {

    public static void main(String[] args) {
        AudioPlayer ad=new AudioPlayer();
       ad.playSong("C:\\Users\\lenovo\\Desktop\\CleanGreenIndoreApp1 - Copy\\src\\main\\java\\com\\example\\CleanGreenIndore\\whatsapp-audio-2024-09-11-at-12540-pm_yuL3wIh6.wav");
        System.out.println();
       System.out.println("=================================================");
        String slogan = "Green city, our Indore, our duty";
        System.out.println("************" + slogan + "**************");

        ProgramService programService = new ProgramService();
        VolunteerService volunteerService = new VolunteerService();
        RegistrationService registrationService = new RegistrationService();
        LoginService loginService = new LoginService();
        Scanner scanner = new Scanner(System.in);
        System.out.println("==================================================");

        boolean isAuthenticated = false;
        String userRole = "";

        while (!isAuthenticated) {
            System.out.println();
            System.out.println("* Clean Green Indore *");
            System.out.println();
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            System.out.println();

            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    registrationService.registerNewUser();
                    break;
                case 2:
                    isAuthenticated = loginService.login();
                    userRole = loginService.getUserRole();
                    break;
                case 3:
                    System.out.println("Thank you for using the application! Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please choose again.");
            }
        }

        // Prompt user if they want to be a volunteer
        if ("User".equalsIgnoreCase(userRole)) {
            System.out.println();
            System.out.println("Would you like to become a volunteer? (yes/no)");
            scanner.nextLine();  // Consumes the leftover newline character
            String response = scanner.nextLine().trim().toLowerCase();
            if ("yes".equals(response)) {
                userVolunteerMenu(volunteerService, programService, scanner); // Passing programService here
            } else {
                System.out.println("Thank you for using the application!");
                System.exit(0);  // Exit the program
            }
        } else if ("Admin".equalsIgnoreCase(userRole)) {
            adminMainMenu(programService, volunteerService, scanner);
        }
    }

    // Main menu for Admin role
    public static void adminMainMenu(ProgramService programService, VolunteerService volunteerService, Scanner scanner) {
        while (true) {
            System.out.println("=================================================");
            System.out.println("* Admin Menu *");
            System.out.println("1. Program Operations");
            System.out.println("2. Volunteer Operations");
            System.out.println("3. Back to Main Menu");
            System.out.println();
            System.out.print("Enter your choice: ");

            int mainChoice = getValidInt(scanner);

            switch (mainChoice) {
                case 1:
                    programOperationsMenu(programService, scanner);
                    break;
                case 2:
                    volunteerOperationsMenu(volunteerService, scanner);
                    break;
                case 3:
                    return;  // Back to the main menu
                default:
                    System.out.println("Invalid choice! Please choose again.");
            }
        }
    }

    // Main menu for User role
    public static void userMainMenu(VolunteerService volunteerService, Scanner scanner) {
        while (true) {
            System.out.println("======================================================");
            System.out.println("* User Menu *");
            System.out.println("1. Volunteer Operations");
            System.out.println("2. Back to Main Menu");
            System.out.println();
            System.out.print("Enter your choice: ");

            int mainChoice = getValidInt(scanner);

            switch (mainChoice) {
                case 1:
                    volunteerOperationsMenu(volunteerService, scanner);
                    break;
                case 2:
                    return;  // back to main menu
                default:
                    System.out.println("Invalid choice! Please choose again.");
            }
        }
    }

    // Menu for users who chose to become volunteers
    public static void userVolunteerMenu(VolunteerService volunteerService, ProgramService programService, Scanner scanner) {
        while (true) {
            System.out.println("======================================================");
            System.out.println("* Volunteer Operations *");
            System.out.println("1. Display All Programs");
            System.out.println("2. Add New Volunteer");
            System.out.println("3. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    // Display all programs
                    System.out.println();
                    System.out.println("Here are all the available programs:");
                    System.out.println();
                    programService.displayAllPrograms();
                    break;
                case 2:
                    // Add a new volunteer
                    volunteerService.addNewVolunteer();
                    break;
                case 3:
                    System.out.println("Thank you for using the application! Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please choose again.");
            }
        }
    }

    // Submenu for Program Operations
    public static void programOperationsMenu(ProgramService programService, Scanner scanner) {
        while (true) {
            System.out.println("============================================================");
            System.out.println("* Program Operations *");
            System.out.println("1. Add New Program");
            System.out.println("2. Display All Programs");
            System.out.println("3. Update Program");
            System.out.println("4. Delete Program");
            System.out.println("5. Back to Admin Menu");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    programService.addNewProgram();
                    break;
                case 2:
                    programService.displayAllPrograms();
                    break;
                case 3:
                    programService.updateProgram();
                    break;
                case 4:
                    programService.deleteProgram();
                    break;
                case 5:
                    return;  // Back to admin menu
                default:
                    System.out.println("Invalid choice! Please choose again.");
            }
        }
    }

    // Submenu for Volunteer Operations
    public static void volunteerOperationsMenu(VolunteerService volunteerService, Scanner scanner) {
        while (true) {
            System.out.println("======================================================");
            System.out.println("* Volunteer Operations *");
            System.out.println("1. Add New Volunteer");
            System.out.println("2. Display All Volunteers");
            System.out.println("3. Update Volunteer");
            System.out.println("4. Delete Volunteer");
            System.out.println("5. Back to Admin Menu");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    volunteerService.addNewVolunteer();
                    break;
                case 2:
                    volunteerService.displayAllVolunteers();
                    break;
                case 3:
                    volunteerService.updateVolunteer();
                    break;
                case 4:
                    volunteerService.deleteVolunteer();
                    break;
                case 5:
                    return;  // Back to admin menu
                default:
                    System.out.println("Invalid choice! Please choose again.");
            }
        }
    }

    // Utility method to get valid integer input
    private static int getValidInt(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }
    }
}
 16 changes: 16 additions & 0 dele
