package com.swer348;

import java.io.*;
import java.time.*;
import java.util.*;

public abstract class Member {

    private static String fn, ln, nm, ct;
    private static LocalDate DoB;
    private final String fName, lName, phoneNum, city;
    private final LocalDate dob;
    private static final ArrayList<Student> students = new ArrayList<>();
    private static final ArrayList<Faculty> faculty = new ArrayList<>();
    private static final ArrayList<Staff> staff = new ArrayList<>();
    static Scanner sc = Main.getScanner();

    public Member(String fName, String lName, String phoneNum, String city, LocalDate dob) {
        this.fName = fName;
        this.lName = lName;
        this.phoneNum = phoneNum;
        this.city = city;
        this.dob = dob;
    }

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

    public static void printProfile() {
        System.out.println("Enter the ID of the member: ");
        String id = sc.next().trim().toUpperCase();
        if (memberExists(id)) System.out.println(getMemberById(id));
        else System.out.println("Member doesn't exist");
    }

    public static boolean memberExists(String id) {
        return staffExists(id) || facultyExists(id) || studentExists(id);
    }

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
        fn = sc.next();
        System.out.println("Enter the last name: ");
        ln = sc.next();
        System.out.println("Enter the phone number: ");
        nm = sc.next();
        System.out.println("Enter the city: ");
        ct = sc.next();
        System.out.println("Enter the year of birth");
        int yd = sc.nextInt();
        System.out.println("Enter the month number of birth");
        int md = sc.nextInt();
        System.out.println("Enter the day of birth");
        int dd = sc.nextInt();
        DoB = LocalDate.of(yd, md, dd);
    }

    public static void addStudent() {
        addPerson();
        String studentId = "STU" + (students.size() + 1);
        students.add(new Student(fn, ln, nm, ct, DoB, studentId, new ArrayList<>(List.of(new Course("none", 0)))));
        try {
            FileWriter fw = new FileWriter("inputs/student.txt", true);
            String coursesTaken = students.getLast().getCoursesTaken().toString().replace(", ", "-");
            fw.write(String.format("%s,%s,%s,%s,%s,%s,%s%n", fn, ln, nm, ct, DoB.toString(), studentId, coursesTaken.substring(2, coursesTaken.length() - 1)));
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.printf("Student %s %s has been added successfully%n%n", fn, ln);
    }

    public static void addFaculty() {
        addPerson();
        String facultyId = "FAC" + (faculty.size() + 1);
        try {
            FileWriter fw = new FileWriter("inputs/faculty.txt", true);
            fw.write(String.format("%s,%s,%s,%s,%s,%s%n", fn, ln, nm, ct, DoB.toString(), facultyId));
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        faculty.add(new Faculty(fn, ln, nm, ct, DoB, facultyId));
        System.out.printf("Faculty %s %s has been added successfully%n%n", fn, ln);
    }

    public static void addStaff() {
        addPerson();
        String staffId = "STA" + (staff.size() + 1);
        try {
            FileWriter fw = new FileWriter("inputs/staff.txt", true);
            fw.write(String.format("%s,%s,%s,%s,%s,%s%n", fn, ln, nm, ct, DoB.toString(), staffId));
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        staff.add(new Staff(fn, ln, nm, ct, DoB, staffId));
        System.out.printf("Staff %s %s has been added successfully%n%n", fn, ln);
    }

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

    public static boolean studentExists(String id) {
        return students.stream().anyMatch(e -> e.getStudentID().equals(id));
    }

    public static boolean staffExists(String id) {
        return staff.stream().anyMatch(e -> e.getStaffID().equals(id));
    }

    public static boolean facultyExists(String id) {
        return faculty.stream().anyMatch(e -> e.getFacultyID().equals(id));
    }

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

    public String getFullName() {
        return this.getFName() + " " + this.getLName();
    }

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

    static Member getMemberById(String id) {
        if (id.substring(0, 3).equalsIgnoreCase("STU"))
            return students.stream().filter(s -> s.getStudentID().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Student not found"));
        else if (id.substring(0, 3).equalsIgnoreCase("STA"))
            return staff.stream().filter(s -> s.getStaffID().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Staff not found"));
        else if (id.substring(0, 3).equalsIgnoreCase("FAC"))
            return faculty.stream().filter(s -> s.getFacultyID().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Faculty not found"));
        return null;
    }

    // <editor-fold desc="getters and setters">
    public final String getFName() {
        return this.fName;
    }

    public final String getLName() {
        return this.lName;
    }

    public final String getPhoneNum() {
        return this.phoneNum;
    }

    public final String getCity() {
        return this.city;
    }

    public final LocalDate getDob() {
        return this.dob;
    }

    public static ArrayList<Staff> getStaff() {
        return staff;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static ArrayList<Faculty> getFaculty() {
        return faculty;
    }

    // </editor-fold>
    @Override
    public String toString() {
        return String.format("Name: %s %s\nPhone Number: %s\nCity: %s\nDate of Birth: %s\n", getFName(), getLName(), getPhoneNum(), getCity(), getDob());
    }
}
