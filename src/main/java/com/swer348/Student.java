package com.swer348;

import java.time.LocalDate;

public class Student extends Person {
    String studentID;

    public Student(String fName, String lName, String phoneNum, String city, LocalDate dob, String studentID) {
        super(fName, lName, phoneNum, city, dob);
        this.studentID = studentID;
    }

    public String getStudentID() {
        return this.studentID;
    }

    @Override
    public String toString() {
        return super.toString() + " studentID=" + getStudentID() + "\n";
    }
}
