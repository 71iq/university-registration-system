package com.swer348;

import java.time.LocalDate;

public class Faculty extends Person{
    String facultyID;
    private Schedule schedule = new Schedule();
    private int credits = 0;
    public Faculty(String fName, String lName, String phoneNum, String city, LocalDate dob, String facultyID) {
        super(fName, lName, phoneNum, city, dob);
        this.facultyID = facultyID;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
    }

    public String getFacultyID() {
        return this.facultyID;
    }

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return String.format("%s facultyID=%s%n", super.toString(), getFacultyID());
    }

}
