package com.swer348;

import java.io.*;
import java.time.*;
import java.util.*;

/**
 * The abstract class representing a member of an educational institution.
 * It serves as the base class for specific member types like Students, Faculty, and Staff.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-11-19
 */
public abstract class Member {

    // static fields used in the class
    private static String firstName, lastName, phoneNumber, city2;
    private static LocalDate DoB;

    // Fields common to all members
    private final String fName, lName, phoneNum, city;
    private final LocalDate dob;

    // Collections to store instances of specific member types
    private static final ArrayList<Student> students = new ArrayList<>();
    private static final ArrayList<Faculty> faculty = new ArrayList<>();
    private static final ArrayList<Staff> staff = new ArrayList<>();

    // Scanner for user input
    static Scanner sc = Main.getScanner();

    /**
     * Constructs a Member with the provided personal information.
     *
     * @param fName    The first name of the member.
     * @param lName    The last name of the member.
     * @param phoneNum The phone number of the member.
     * @param city     The city of residence of the member.
     * @param dob      The date of birth of the member.
     */
    public Member(String fName, String lName, String phoneNum, String city, LocalDate dob) {
        this.fName = fName;
        this.lName = lName;
        this.phoneNum = phoneNum;
        this.city = city;
        this.dob = dob;
    }

    /**
     * Manages member-related operations such as adding, removing, and printing profiles.
     */
    public static void manageMember() {
        int input;
        do {
            System.out.println("Enter 1 to add a new member: ");
            System.out.println("Enter 2 to remove a member: ");
            System.out.println("Enter 3 to print a student's grades: ");
            System.out.println("Enter 4 to print a member's profile: ");
            System.out.println("Enter 0 to go back: ");
            System.out.println("Enter -1 to exit the program: ");

            if (sc.hasNextInt()) {
                input = sc.nextInt();
                switch (input) {
                    case 1 -> addMember();
                    case 2 -> removeMember();
                    case 3 -> printGrades();
                    case 4 -> printProfile();
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
     * Retrieves a member (Student, Faculty, or Staff) based on their ID.
     *
     * @param id The ID of the member.
     * @return The member instance if found, or null if not found.
     */
    public static Member getMemberById(String id) {
        if (id.substring(0, 3).equalsIgnoreCase("STU"))
            return students.stream().filter(s -> s.getStudentID().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Student not found"));
        else if (id.substring(0, 3).equalsIgnoreCase("STA"))
            return staff.stream().filter(s -> s.getStaffID().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Staff not found"));
        else if (id.substring(0, 3).equalsIgnoreCase("FAC"))
            return faculty.stream().filter(s -> s.getFacultyID().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Faculty not found"));
        return null;
    }

    /**
     * Method to print the profile of a member based on their ID.
     */
    public static void printProfile() {
        System.out.println("Enter the ID of the member: ");
        String id = sc.next().trim().toUpperCase();
        if (memberExists(id)) System.out.println(getMemberById(id));
        else System.out.println("Member doesn't exist");
    }

    /**
     * Method to check if a member with a given ID exists.
     */
    public static boolean memberExists(String id) {
        return staffExists(id) || facultyExists(id) || studentExists(id);
    }

    /**
     * Method to add a new member, allowing the addition of students, faculty, and staff.
     */
    public static void addMember() {
        System.out.println("Enter the desired value: ");
        System.out.println("Enter 1 to add a new student: ");
        System.out.println("Enter 2 to add a new faculty: ");
        System.out.println("Enter 3 to add a new staff: ");
        System.out.println("Enter 0 to go back");
        if (sc.hasNextInt()) {
            int role = sc.nextInt();
            if (role == 1) Member.addStudent();
            if (role == 2) Member.addFaculty();
            if (role == 3) Member.addStaff();
        }
    }

    public static void addPerson() {
        System.out.println("Enter the first name: ");
        firstName = sc.next();
        System.out.println("Enter the last name: ");
        lastName = sc.next();
        System.out.println("Enter the phone number: ");
        phoneNumber = sc.next();
        System.out.println("Enter the city: ");
        city2 = sc.next();
        System.out.println("Enter the year of birth");
        int yd = sc.nextInt();
        System.out.println("Enter the month number of birth");
        int md = sc.nextInt();
        System.out.println("Enter the day of birth");
        int dd = sc.nextInt();
        DoB = LocalDate.of(yd, md, dd);
    }

    /**
     * Method to add a new student to the system.
     */
    public static void addStudent() {
        addPerson();
        String studentId = "STU" + (students.size() + 1);
        students.add(new Student(firstName, lastName, phoneNumber, city2, DoB, studentId, new ArrayList<>(List.of(new Course("none", 0)))));
        try {
            FileWriter fw = new FileWriter("inputs/student.txt", true);
            String coursesTaken = students.getLast().getCoursesTaken().toString().replace(", ", "-");
            fw.write(String.format("%s,%s,%s,%s,%s,%s,%s%n", firstName, lastName, phoneNumber, city2, DoB.toString(), studentId, coursesTaken.substring(2, coursesTaken.length() - 1)));
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.printf("Student %s %s has been added successfully%n%n", firstName, lastName);
    }

    /**
     * Method to add a new faculty member to the system.
     */
    public static void addFaculty() {
        addPerson();
        String facultyId = "FAC" + (faculty.size() + 1);
        try {
            FileWriter fw = new FileWriter("inputs/faculty.txt", true);
            fw.write(String.format("%s,%s,%s,%s,%s,%s%n", firstName, lastName, phoneNumber, city2, DoB.toString(), facultyId));
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        faculty.add(new Faculty(firstName, lastName, phoneNumber, city2, DoB, facultyId));
        System.out.printf("Faculty %s %s has been added successfully%n%n", firstName, lastName);
    }

    /**
     * Method to add a new staff member to the system.
     */
    public static void addStaff() {
        addPerson();
        String staffId = "STA" + (staff.size() + 1);
        try {
            FileWriter fw = new FileWriter("inputs/staff.txt", true);
            fw.write(String.format("%s,%s,%s,%s,%s,%s%n", firstName, lastName, phoneNumber, city2, DoB.toString(), staffId));
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        staff.add(new Staff(firstName, lastName, phoneNumber, city2, DoB, staffId));
        System.out.printf("Staff %s %s has been added successfully%n%n", firstName, lastName);
    }

    /**
     * Method to remove a member based on their role (student, faculty, staff).
     */
    public static void removeMember() {
        System.out.println("Enter the role you want to remove:");
        System.out.println("1. Student");
        System.out.println("2. Faculty");
        System.out.println("3. Staff");
        System.out.println("0. Cancel");

        if (sc.hasNextInt()) {
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    removeStudent();
                    break;
                case 2:
                    removeFaculty();
                    break;
                case 3:
                    removeStaff();
                    break;
                case 0:
                    System.out.println("Operation canceled.");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } else {
            System.out.println("Invalid input.");
        }
    }

    /**
     * Method to remove a student from the system.
     */
    private static void removeStudent() {
        System.out.println("Enter the student ID to remove:");
        String studentID = sc.next().trim().toUpperCase();

        if (!studentExists(studentID)) {
            System.out.println("Student not found.");
            return;
        }

        Student studentToRemove = (Student) getMemberById(studentID);
        students.remove(studentToRemove);
        System.out.println("Student removed successfully.");
    }

    /**
     * Method to remove a faculty member from the system.
     */
    private static void removeFaculty() {
        System.out.println("Enter the faculty ID to remove:");
        String facultyID = sc.next().trim().toUpperCase();

        if (!facultyExists(facultyID)) {
            System.out.println("Faculty not found.");
            return;
        }

        Faculty facultyToRemove = faculty.stream().filter(e -> e.getFacultyID().equals(facultyID)).findFirst().orElse(null);
        faculty.remove(facultyToRemove);
        System.out.println("Faculty removed successfully.");
    }

    /**
     * Method to remove a staff member from the system.
     */
    private static void removeStaff() {
        System.out.println("Enter the staff ID to remove:");
        String staffID = sc.next().trim().toUpperCase();

        if (!staffExists(staffID)) {
            System.out.println("Staff not found.");
            return;
        }

        Staff staffToRemove = staff.stream().filter(e -> e.getStaffID().equals(staffID)).findFirst().orElse(null);
        staff.remove(staffToRemove);
        System.out.println("Staff removed successfully.");
    }

    /**
     * Method to check if a student with a given ID exists.
     */
    public static boolean studentExists(String id) {
        return students.stream().anyMatch(e -> e.getStudentID().equals(id));
    }

    /**
     * Method to check if a staff member with a given ID exists.
     */
    public static boolean staffExists(String id) {
        return staff.stream().anyMatch(e -> e.getStaffID().equals(id));
    }

    /**
     * Method to check if a faculty member with a given ID exists.
     */
    public static boolean facultyExists(String id) {
        return faculty.stream().anyMatch(e -> e.getFacultyID().equals(id));
    }

    /**
     * Method to print the grades of a student based on their ID.
     */
    public static void printGrades() {
        System.out.println("Enter Student ID");
        String id = sc.next().trim().toUpperCase();
        if (!studentExists(id)) {
            System.out.println("Student doesn't Exist");
            return;
        }
        System.out.printf("Student's name: %s Student's GPA: %.2f Student's Grades: %n", Objects.requireNonNull(getMemberById(id)).getFullName(), ((Student) Objects.requireNonNull(getMemberById(id))).calculateGPA());
        ((Student) Objects.requireNonNull(getMemberById(id))).getGrades().forEach((key, value) -> System.out.println(key.getName() + " " + value));
    }

    /**
     * Method to get the full name of a member.
     */
    public final String getFullName() {
        return this.getFName() + " " + this.getLName();
    }

    /**
     * Method to print the schedule of a member based on their ID.
     */
    public static void printSchedule() {
        System.out.println("Enter the person's ID");
        String id = sc.next().trim().toUpperCase();
        if (studentExists(id))
            System.out.println(students.stream().filter(e -> e.getStudentID().equals(id)).toList().getFirst().getSchedule().toString());
        else if (facultyExists(id))
            System.out.println(faculty.stream().filter(e -> e.getFacultyID().equals(id)).toList().getFirst().getSchedule().toString());
        else if (staffExists(id))
            System.out.println("This Member doesn't have a schedule");
        else System.out.println("Member doesn't exist");
    }

    // Getters for member information

    /**
     * Gets the first name of the member.
     *
     * @return The first name of the member.
     */
    public final String getFName() {
        return this.fName;
    }

    /**
     * Gets the last name of the member.
     *
     * @return The last name of the member.
     */
    public final String getLName() {
        return this.lName;
    }

    /**
     * Gets the phone number of the member.
     *
     * @return The phone number of the member.
     */
    public final String getPhoneNum() {
        return this.phoneNum;
    }

    /**
     * Gets the city of residence of the member.
     *
     * @return The city of residence of the member.
     */
    public final String getCity() {
        return this.city;
    }

    /**
     * Gets the date of birth of the member.
     *
     * @return The date of birth of the member.
     */
    public final LocalDate getDob() {
        return this.dob;
    }
// Getters for specific member collections

    /**
     * Gets the list of staff members.
     *
     * @return The list of staff members.
     */
    public static ArrayList<Staff> getStaff() {
        return staff;
    }

    /**
     * Gets the list of student members.
     *
     * @return The list of student members.
     */
    public static ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Gets the list of faculty members.
     *
     * @return The list of faculty members.
     */
    public static ArrayList<Faculty> getFaculty() {
        return faculty;
    }

    /**
     * String representation of the Member, including name, phone number, city, and date of birth.
     *
     * @return A formatted string representing the Member.
     */
    @Override
    public String toString() {
        return String.format("Name: %s %s\nPhone Number: %s\nCity: %s\nDate of Birth: %s\n", getFName(), getLName(), getPhoneNum(), getCity(), getDob());
    }
}
