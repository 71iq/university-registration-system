package com.swer348;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int input; // asks for action from the user
        do {
            System.out.println("Enter the value desired: ");
            System.out.println("Enter 1 to add a new member: ");
            System.out.println("Enter 2 to add a new semester: ");
            System.out.println("Enter 3 to add a new course: ");
            System.out.println("Enter 4 to remove a course: ");
            System.out.println("Enter 5 to switch section for a student: ");
            System.out.println("Enter 6 to get a list for all students in a specific course: ");
            System.out.println("Enter 7 to get a list for all students in a specific course and section: ");
            System.out.println("Enter 0 to exit the program: ");

            // Check if the next input is an integer
            if (sc.hasNextInt()) {
                input = sc.nextInt();

                // Process the input
                switch (input) {
                    case 1:
                        User.addUser();
                        break;
                    case 2:
                        System.out.println("Adding a new semester...");
                        break;
                    case 3:
                        CourseManager.addCourse();
                        break;
                    case 4:
                        CourseManager.removeCourse();
                        break;
                    case 5:
                        CourseManager.switchSection();
                        break;
                    case 6:
                        CourseManager.getAllStudents();
                        break;
                    case 7:
                        CourseManager.getAllStudentsSection();
                        break;
                    case 0:
                        System.out.println("Exiting the program. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a valid option.");
                        break;
                }
            } else {
                // Handle the case where the input is not an integer
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine(); // Consume the invalid input to avoid an infinite loop
                input = -1; // Set input to -1 to continue the loop
            }

        } while (input != 0);

        sc.close();
    }
}
