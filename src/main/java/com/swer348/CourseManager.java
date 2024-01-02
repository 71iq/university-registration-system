package com.swer348;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CourseManager {

    private static final ArrayList<Course> courses = new ArrayList<>();
    public static Scanner sc = Main.getScanner();

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static void manageCourse() {
        int input;
        do {
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

    public static void printPres() {
        System.out.println("Enter name of the course: ");
        sc.nextLine();
        String course = sc.nextLine().trim().toLowerCase();
        if (!courseExist(course)) System.out.println("Course doesn't exist");
        else {
            System.out.println(course + "'s prerequisites are: " + getCourses().get(courseIndex(course)).getPrerequisites().stream().map(Course::getName).toList());
        }
    }

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
        do {
            pre = sc.nextLine().toLowerCase();
            pres.add(getCourses().get(courseIndex(pre)));
        } while (!pre.equals("none"));
        if (pres.size() == 1 && pres.getFirst().getName().equals("none")) pres.clear();
        courses.add(new Course(name, cr, pres));
        try {
            FileWriter fw = new FileWriter("inputs/course.txt", true);
            String temp = pres.toString().replaceAll(", ", "-");
            temp = temp.substring(2, temp.length() - 1);
            fw.write(String.format("%s,%d,%s%n", name, cr, temp));
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.printf("The course %s has been added successfully%n", name);
    }

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
                    fw.append(String.format("%s%n", e.toString().toLowerCase()));
                } catch (IOException e1) {
                    System.out.println(e1);
                }
            });
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.printf("The course %s has been removed successfully%n", name);
    }

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

    static boolean courseExist(String name) {
        return courses.stream().anyMatch(c -> c.getName().equals(name));
    }

    static int courseIndex(String name) {
        int[] ind = {0};
        IntStream.range(0, courses.size()).forEach(i -> ind[0] = courses.get(i).getName().equals(name) ? i : ind[0]);
        return ind[0];
    }
}