package com.swer348;

import java.io.*;
import java.time.*;
import java.util.*;

public abstract class Person {

    private static String fn, ln, nm, ct;
    private static LocalDate DoB;
    private String fName;
    private String lName;
    private String phoneNum;
    private String city;
    private LocalDate dob;
    private static final ArrayList<Student> students = new ArrayList<>();
    private static final ArrayList<Faculty> faculty = new ArrayList<>();
    private static final ArrayList<Staff> staff = new ArrayList<>();
    static Scanner sc = Main.getScanner();

    public Person(String fName, String lName, String phoneNum, String city, LocalDate dob) {
        this.fName = fName;
        this.lName = lName;
        this.phoneNum = phoneNum;
        this.city = city;
        this.dob = dob;
    }

    public static void addMember() {
        System.out.println("Enter the desired value: ");
        System.out.println("Enter 1 to add a new student: ");
        System.out.println("Enter 2 to add a new faculty: ");
        System.out.println("Enter 3 to add a new staff: ");
        System.out.println("Enter 0 to go back");
        if (sc.hasNextInt()) {
            int role = sc.nextInt();
            if (role == 1) Person.addStudent();
            if (role == 2) Person.addFaculty();
            if (role == 3) Person.addStaff();
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

    public static boolean studentNotExists(String id) {
        return students.stream().noneMatch(e -> e.getStudentID().equals(id));
    }

    public static boolean staffExists(String id) {
        return staff.stream().anyMatch(e -> e.getStaffID().equals(id));
    }

    public static boolean FacultyExists(String id) {
        return faculty.stream().anyMatch(e -> e.getFacultyID().equals(id));
    }

    static Student getStudentById(String id) {
        return students.stream().filter(s -> s.getStudentID().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // <editor-fold desc="getters and setters">
    public final String getFName() {
        return this.fName;
    }

    public final void setFName(String fName) {
        this.fName = fName;
    }

    public final String getLName() {
        return this.lName;
    }

    public final void setLName(String lName) {
        this.lName = lName;
    }

    public final String getPhoneNum() {
        return this.phoneNum;
    }

    public final void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public final String getCity() {
        return this.city;
    }

    public final void setCity(String city) {
        this.city = city;
    }

    public final LocalDate getDob() {
        return this.dob;
    }

    public final void setDob(LocalDate dob) {
        this.dob = dob;
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
        return String.format("Name: %s %s, Phone Number: %s, City: %s, Date of Birth: %s", getFName(), getLName(), getPhoneNum(), getCity(), getDob());
    }
}
