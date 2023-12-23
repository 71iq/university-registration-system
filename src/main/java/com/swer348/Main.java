package com.swer348;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    static boolean assignedCourse = false;

    public static void readFile(String file) {
        try {
            Scanner scanFile = new Scanner(new File("inputs/" + file + ".txt"));
            while (scanFile.hasNextLine()) {
                String line = scanFile.nextLine();
                String[] paras = line.split(",");

                if (file.equals("student"))
                    Person.getStudents().add(new Student(paras[0], paras[1], paras[2], paras[3], LocalDate.parse(paras[4]), paras[5], (ArrayList<Course>) Arrays.stream(paras[6].split("-")).map(e -> CourseManager.getCourses().get(CourseManager.courseIndex(e))).collect(Collectors.toList())));
                if (file.equals("faculty"))
                    Person.getFaculty().add(new Faculty(paras[0], paras[1], paras[2], paras[3], LocalDate.parse(paras[4]), paras[5]));
                if (file.equals("staff"))
                    Person.getStaff().add(new Staff(paras[0], paras[1], paras[2], paras[3], LocalDate.parse(paras[4]), paras[5]));
                if (file.equals("course") && !assignedCourse)
                    CourseManager.getCourses().add(new Course(paras[0].toLowerCase(), Integer.parseInt(paras[1]), new ArrayList<>()));
                else if (file.equals("course"))
                    CourseManager.getCourses().get(CourseManager.courseIndex(paras[0])).setPrerequisites(Arrays.stream(paras[2].split("-")).map(e -> CourseManager.getCourses().get(CourseManager.courseIndex(e))).collect(Collectors.toList()));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

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
                    case 1 -> Person.addMember();
                    case 2 -> {
                        Person.getStudents().forEach(e -> e.setCredits(0));
                        // for Maamoun add something to empty the courses and sections from their students,,, actually empty everything almost
                        Semester.createSemester();
                        System.out.println("New Semester Added Successfully!");
                    }
                    case 3 -> CourseManager.addCourse();
                    case 4 -> CourseManager.removeCourse();
                    case 5 -> CourseManager.switchSection();
                    case 6 -> CourseManager.getAllStudents();
                    case 7 -> CourseManager.getAllStudentsSection();
                    case 0 -> System.out.println("Exiting the program. Goodbye!");
                    default -> System.out.println("Invalid input. Please enter a valid option.");
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

    public static Scanner getScanner() {
        return sc;
    }
}