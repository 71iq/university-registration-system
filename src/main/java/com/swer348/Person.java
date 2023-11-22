package com.swer348;

import java.time.*;
import java.util.*;

public abstract class Person {
    private String fName;
    private String lName;
    private String phoneNum;
    private String city;
    private LocalDate dob;
    private static ArrayList<Person> students = new ArrayList<>();
    private static ArrayList<Faculty> faculty = new ArrayList<>();
    private static ArrayList<Staff> staff = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public Person(String fName, String lName, String phoneNum, String city, LocalDate dob) {
        this.fName = fName;
        this.lName = lName;
        this.phoneNum = phoneNum;
        this.city = city;
        this.dob = dob;
    }

    private static String fn, ln, nm, ct;
    private static int yd, md, dd;
    private static LocalDate DoB;

    public static void addPerson() {
        System.out.println("Enter the student first name: ");
        fn = sc.next();
        System.out.println("Enter the student last name: ");
        ln = sc.next();
        System.out.println("Enter the student phone number: ");
        nm = sc.next();
        System.out.println("Enter the student city: ");
        ct = sc.next();
        System.out.println("Enter the student year of birth");
        yd = sc.nextInt();
        System.out.println("Enter the student month number of birth");
        md = sc.nextInt();
        System.out.println("Enter the student day of birth");
        dd = sc.nextInt();
        DoB = LocalDate.of(yd, md, dd);
    }

    public static void addStudent() {
        addPerson();
        String studentId = String.valueOf(LocalDate.now().getYear()) + "0" + String.valueOf(students.size() + 1);
        students.add(new Student(fn, ln, nm, ct, DoB, studentId));
    }

    public static void addFaculty() {
        addPerson();
        String facultyId = String.valueOf(faculty.size() + 1);
        faculty.add(new Faculty(fn, ln, nm, ct, DoB, facultyId));
    }

    public static void addStaff() {
        addPerson();
        String staffId = String.valueOf(staff.size() + 1);
        staff.add(new Staff(fn, ln, nm, ct, DoB, staffId));
    }

    public String getFName() {
        return this.fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return this.lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Name= " + getFName() + " " + getLName() + "\n" +
                "phone Number='" + getPhoneNum() + "\n" +
                "City='" + getCity() + "\n" +
                "Date of Birth='" + getDob() + "\n";
    }
}