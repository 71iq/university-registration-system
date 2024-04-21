/**
 * The `CourseManager` class manages operations related to courses in an educational system.
 * It allows the user to add, remove, and manipulate courses, as well as manage students' enrollment.
 * The class provides a set of functionalities through a menu-driven console interface.
 *
 * @since 2024-01-03
 */
package com.ehab.urs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * The `CourseManager` class manages operations related to courses in an educational system.
 * It allows the user to add, remove, and manipulate courses, as well as manage students' enrollment.
 * The class provides a set of functionalities through a menu-driven console interface.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-12-01
 */
public class CourseManager {
    /**
     * The list of courses managed by the system.
     */
    private static final ArrayList<Course> courses = new ArrayList<>();
    /**
     * Scanner object to handle user input.
     */
    public static Scanner sc = Main.getScanner();

    /**
     * Retrieves the list of courses.
     *
     * @return The list of courses.
     */
    public static ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Manages various course-related operations through a menu-driven interface.
     */
    public static void manageCourse() {
        int input;
        do {
            System.out.println("OPTIONS FROM 3 TO 7 NEED STARTING A SEMESTER");
            System.out.println("Enter 1 to add a new course: ");
            System.out.println("Enter 2 to remove a course: ");
            System.out.println("Enter 3 to switch section for a student: ");
            System.out.println("Enter 4 to get a list for all students in a specific course: ");
            System.out.println("Enter 5 to get a list for all students in a specific course and section: ");
            System.out.println("Enter 6 to add a student to a course: ");
            System.out.println("Enter 7 to remove a student from a course: ");
            System.out.println("Enter 8 to print available courses: ");
            System.out.println("Enter 9 to print a course's prerequisites: ");
            System.out.println("Enter 0 to go back: ");
            System.out.println("Enter -1 to exit the program: ");

            if (sc.hasNextInt()) {
                input = sc.nextInt();
                switch (input) {
                    case 1 -> addCourse();
                    case 2 -> removeCourse();
                    case 3 -> switchSection();
                    case 4 -> getAllStudents();
                    case 5 -> getAllStudentsSection();
                    case 6 -> addStudent();
                    case 7 -> RemoveStudent();
                    case 8 -> System.out.println(getCourses());
                    case 9 -> printPres();
                    case 0 -> System.out.println("Going Back...");
                    case -1 -> Main.setExit();
                    default -> System.out.print("Invalid input. Please enter a valid option.");
                }
                if (input == 0) return;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine();
                input = 0;
            }
        } while (input != -1);
    }

    /**
     * Prints the prerequisites of a specified course.
     */
    public static void printPres() {
        System.out.println("Enter name of the course: ");
        sc.nextLine();
        String course = sc.nextLine().trim().toLowerCase();
        if (!courseExist(course)) System.out.println("Course doesn't exist");
        else {
            System.out.println(course + "'s prerequisites are: " + getCourses().get(courseIndex(course)).getPrerequisites().stream().map(Course::getName).toList());
        }
    }

    /**
     * Adds a new course to the system.
     */
    public static void addCourse() {
        System.out.println("Enter the name for the course you want to create: ");
        sc.nextLine();
        String name = sc.nextLine().trim().toLowerCase();
        if (courseExist(name)) {
            System.out.println("The course already EXISTS!");
            return;
        }
        System.out.println("Enter the number of credits for the course: ");
        while (!sc.hasNextInt())
            System.out.println("Please Enter a number");
        int cr = sc.nextInt();
        System.out.println("Enter the prerequisites or 'none' to stop");
        ArrayList<Course> pres = new ArrayList<>();
        String pre;
        sc.nextLine();
        do {
            pre = sc.nextLine().toLowerCase();
            pres.add(getCourses().get(courseIndex(pre)));
        } while (!pre.equals("none"));
        System.out.println(pres);
        pres.removeLast();
        System.out.println(pres);
        courses.add(new Course(name, cr, pres));
        try {
            FileWriter fw = new FileWriter("inputs/course.txt", true);
            String temp = pres.stream().map(Course::getName).toList().toString().replaceAll(", ", "-");
            temp = temp.substring(1, temp.length() - 1);
            fw.write(String.format("%s,%d,%s%n", name, cr, temp));
            fw.close();
            System.out.printf("The course %s has been added successfully%n", name);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Removes an existing course from the system.
     */
    public static void removeCourse() {
        System.out.println("Enter the name for the course you want to remove: ");
        String name = sc.next().trim().toLowerCase();
        if (!courseExist(name)) {
            System.out.println("The course does not EXIST!");
            return;
        }
        courses.remove(courseIndex(name));
        try {
            FileWriter fw = new FileWriter("inputs/course.txt", false);
            courses.forEach(e -> {
                try {
                    String pres = e.getPrerequisites().stream().map(Course::getName).toList().toString().replace(", ", "-");
                    pres = pres.substring(1, pres.length() - 1);
                    fw.append(String.format("%s,%s,%s", e.getName(), e.getCredits(), pres));
                    fw.write('\n');
                } catch (IOException e1) {
                    System.out.println(e1);
                }
            });
            fw.close();
            System.out.printf("The course %s has been removed successfully%n", name);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Switches the section of a student in a specified course.
     */
    public static void switchSection() {
        System.out.println("Enter the id for the student: ");
        String str = sc.next().trim().toLowerCase();
        if (!Member.studentExists(str)) {
            System.out.println("The student doesn't exist");
            return;
        }
        System.out.println("Enter the course: ");
        String co = sc.next().trim().toLowerCase();
        if (!courseExist(str)) {
            System.out.println("The course does not EXIST!");
            return;
        }
        System.out.println("Enter the section of the student: A/B/C/D");
        String s1 = sc.next().trim().toLowerCase();
        Character c1 = s1.charAt(0);
        System.out.println("Enter the section that you want the student to go to: A/B/C/D");
        String s2 = sc.next().toLowerCase();
        Character c2 = s2.charAt(0);
        courses.get(courseIndex(co)).switchSection(str, c1, c2);
    }

    /**
     * Adds a student to a specified course.
     */
    public static void addStudent() {
        System.out.println("Please Enter the Student ID: ");
        String studentID = sc.next().trim().toUpperCase();
        sc.nextLine();
        System.out.println("Please Enter the name for the course you want to add the student to: ");
        String courseName = sc.nextLine().trim().toLowerCase();

        Course course = courses.get(courseIndex(courseName));

        if (course == null) {
            System.out.println("This Course does not exist. Make sure you enter a valid course name");
            return;
        }

        // Check if the student already exists
        if (!Member.studentExists(studentID)) {
            System.out.println("Student with ID " + studentID + " does not exist. Please add the student first.");
            return;
        }

        Student student = (Student) Member.getMemberById(studentID);

        // Check if the student is already enrolled in the course
        if (course.getAllStudents().contains(student)) {
            System.out.println("Student is already enrolled in the course.");
        } else {
            // Add the student to the course
            course.addStudent(student);
            assert student != null;
            student.addCourse(course);
            System.out.println("Student added to the course successfully.");
        }
    }

    /**
     * Removes a student from a specified course.
     */
    public static void RemoveStudent() {
        System.out.println("Please Enter the Student ID: ");
        String studentID = sc.next().trim().toUpperCase();
        sc.nextLine();
        System.out.println("Please Enter the name for the course you want to delete the student from: ");
        String courseName = sc.nextLine().trim().toLowerCase();

        Course course = courses.get(courseIndex(courseName));

        if (course == null) {
            System.out.println("This Course does not exist. Make sure you enter a valid course name");
            return;
        }

        // Check if the student already exists
        if (!Member.studentExists(studentID)) {
            System.out.println("Student with ID " + studentID + " does not exist. Please add the student first.");
            return;
        }

        Student student = (Student) Member.getMemberById(studentID);

        if (!course.getAllStudents().contains(student))
            System.out.println("Student does not exist in this course");
        else {
            course.removeStudent(student);
            assert student != null;
            student.removeCourse(course);
            student.getSchedule().removeCourse(course);
            System.out.println("Student deleted from the course successfully.");
        }
    }

    /**
     * Retrieves and prints all students enrolled in a specified course.
     */
    static void getAllStudents() {
        System.out.println("Enter the name of the course: ");
        sc.nextLine();
        String str = sc.nextLine().trim().toLowerCase();
        if (!courseExist(str)) {
            System.out.println("The course does not EXIST!");
            return;
        }
        courses.get(courseIndex(str)).getAllStudents().forEach(System.out::println);
    }

    /**
     * Retrieves and prints all students in a specified section of a course.
     */
    static void getAllStudentsSection() {
        System.out.println("Enter the name of the course: ");
        sc.nextLine();
        String str = sc.nextLine().trim().toLowerCase();
        if (!courseExist(str)) {
            System.out.println("The course does not EXIST!");
            return;
        }
        System.out.println("Enter the section that you want: A/B/C/D");
        String s2 = sc.next().toUpperCase();
        Character c = s2.charAt(0);
        courses.get(courseIndex(str)).getAllStudentsSection(c).forEach(System.out::println);
    }

    /**
     * Checks if a course with the given name exists.
     *
     * @param name The name of the course to check.
     * @return true if the course exists, false otherwise.
     */
    static boolean courseExist(String name) {
        return courses.stream().anyMatch(c -> c.getName().equals(name));
    }

    /**
     * Retrieves the index of a course with the given name in the courses list.
     *
     * @param name The name of the course to find.
     * @return The index of the course, or -1 if not found.
     */
    static int courseIndex(String name) {
        int[] ind = {0};
        IntStream.range(0, courses.size()).forEach(i -> ind[0] = (courses.get(i).getName().equals(name) ? i : ind[0]));
        return ind[0];
    }
}