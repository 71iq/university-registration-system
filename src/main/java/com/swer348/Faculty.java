package com.swer348;

import java.time.LocalDate;

public class Faculty extends Person{
    String facultyID;
    public Faculty(String fName, String lName, String phoneNum, String city, LocalDate dob, String facultyID) {
        super(fName, lName, phoneNum, city, dob);
        this.facultyID = facultyID;
    }

    public String getFacultyID() {
        return this.facultyID;
    }

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    @Override
    public String toString() {
        return "facultyID=" + getFacultyID() + "\n" ;
    }

}
