package com.swer348;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The `Main` class serves as the entry point for the educational system program.
 * It provides a menu-driven interface to manage members, courses, schedules, and more.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-11-19
 */
public class Main {

    /**
     * Scanner object for user input.
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Flag to indicate if a course has already been assigned during initialization.
     */
    private static boolean assignedCourse = false;

    /**
     * Flag to indicate program exit.
     */
    private static boolean exit = false;

    /**
     * Reads data from a file and initializes members, courses, and rooms.
     *
     * @param file The name of the file to read.
     */
    public static void readFile(String file) {
        try {
            Scanner scanFile = new Scanner(new File("inputs/" + file + ".txt"));
            while (scanFile.hasNextLine()) {
                String line = scanFile.nextLine();
                String[] paras = line.split(",");

                if (file.equals("student"))
                    Member.getStudents().add(new Student(paras[0], paras[1], paras[2], paras[3], LocalDate.parse(paras[4]), paras[5], (ArrayList<Course>) Arrays.stream(paras[6].split("-")).map(e -> CourseManager.getCourses().get(CourseManager.courseIndex(e))).collect(Collectors.toList())));
                if (file.equals("faculty"))
                    Member.getFaculty().add(new Faculty(paras[0], paras[1], paras[2], paras[3], LocalDate.parse(paras[4]), paras[5]));
                if (file.equals("staff"))
                    Member.getStaff().add(new Staff(paras[0], paras[1], paras[2], paras[3], LocalDate.parse(paras[4]), paras[5]));
                if (file.equals("course") && !assignedCourse)
                    CourseManager.getCourses().add(new Course(paras[0].toLowerCase(), Integer.parseInt(paras[1]), new ArrayList<>()));
                else if (file.equals("course"))
                    CourseManager.getCourses().get(CourseManager.courseIndex(paras[0])).setPrerequisites(Arrays.stream(paras[2].split("-")).map(e -> CourseManager.getCourses().get(CourseManager.courseIndex(e))).collect(Collectors.toList()));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     * Main method to execute the educational system program.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        int input;
        Room.initializeRooms();
        readFile("course");
        assignedCourse = true;
        readFile("course");
        readFile("student");
        readFile("faculty");
        readFile("staff");
        do {
            // display menu options
            System.out.println("Enter the value desired: ");
            System.out.println("Enter 1 to manage members: ");
            System.out.println("Enter 2 to start a new semester: ");
            System.out.println("Enter 3 to manage courses");
            System.out.println("Enter 4 to print All the rooms: ");
            System.out.println("Enter 5 to access schedules: ");
            System.out.println("Enter 6 to get the Academic rank for every student: ");
            System.out.println("Enter 0 to exit the program: ");

            // Check if the next input is an integer
            if (sc.hasNextInt()) {
                input = sc.nextInt();

                // Process the input
                switch (input) {
                    case 1 -> Member.manageMember();
                    case 2 -> Semester.createSemester();
                    case 3 -> CourseManager.manageCourse();
                    case 4 -> System.out.format("%s\n", Room.getRooms());
                    case 5 -> Schedule.manageSchedule();
                    case 6 -> Semester.calculateAndPrintHonors();
                    case 0 -> System.out.println("Exiting the program. Goodbye!");
                    default -> System.out.print("Invalid input. Please enter a valid option.");
                }
                if (exit) {
                    System.out.println("Exiting the program. Goodbye!");
                    input = 0;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine();
                input = -1;
            }

        } while (input != 0);

        // Close the scanner
        sc.close();
    }

    /**
     * Retrieves the Scanner object for user input.
     *
     * @return The Scanner object.
     */
    public static Scanner getScanner() {
        return sc;
    }

    /**
     * Sets the exit flag to true, indicating program exit.
     */
    public static void setExit() {
        exit = true;
    }
}