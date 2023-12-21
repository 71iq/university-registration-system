package com.swer348;

import java.time.LocalDate;
import java.util.ArrayList;

public class Student extends Person {
    String studentID;
    private int credits = 0;
    ArrayList<Course> coursesTaken;
    Schedule schedule = new Schedule();

    public Student(String fName, String lName, String phoneNum, String city, LocalDate dob, String studentID, ArrayList<Course> cT) {
        super(fName, lName, phoneNum, city, dob);
        this.studentID = studentID;
        this.coursesTaken = cT;
        if (coursesTaken.size() == 1 && coursesTaken.getFirst().getName().equals("none"))
            coursesTaken.clear();
    }

    public String getStudentID() {
        return this.studentID;
    }

    public boolean eligible(Course course) {
        if (course.hasPrerequisites())
            for (Course c : course.getPrerequisites())
                if (!this.getCoursesTaken().contains(c)) return false;
        return true;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public ArrayList<Course> getCoursesTaken() {
        return coursesTaken;
    }

    public void addCourse(Course course) {
        this.getCoursesTaken().add(course);
    }

    @Override
    public String toString() {
        return String.format("%s studentID=%s%n", super.toString(), getStudentID());
    }
}
