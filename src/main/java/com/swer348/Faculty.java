package com.swer348;

import java.time.LocalDate;

public class Faculty extends Person{
    String facultyID;
    private Schedule schedule;
    private int credits = 0;
    private Section section;
    public Faculty(String fName, String lName, String phoneNum, String city, LocalDate dob, String facultyID) {
        super(fName, lName, phoneNum, city, dob);
        this.facultyID = facultyID;
        this.schedule = new Schedule();
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Section getSection() {
        return section;
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

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return String.format("%s facultyID=%s%n", super.toString(), getFacultyID());
    }

}
