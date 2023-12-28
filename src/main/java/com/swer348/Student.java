package com.swer348;

import java.time.LocalDate;
import java.util.ArrayList;

public class Student extends Person {
    String studentID;
    private int credits = 0;
    ArrayList<Course> coursesTaken;
    Schedule schedule;

    public Student(String fName, String lName, String phoneNum, String city, LocalDate dob, String studentID, ArrayList<Course> cT) {
        super(fName, lName, phoneNum, city, dob);
        this.studentID = studentID;
        this.coursesTaken = cT;
        this.schedule = new Schedule();
        if (coursesTaken.size() == 1 && coursesTaken.getFirst().getName().equals("none")) coursesTaken.clear();
    }

    public double calculateGPA() {
        if (coursesTaken.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (int i = 0; i < coursesTaken.size(); i++) {
            Course course = coursesTaken.get(i);
            Grade grade = course.getGrades().get(i);
            totalPoints += grade.getValue() * course.getCredits();
            totalCredits += course.getCredits();
        }

        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    public String getStudentID() {
        return this.studentID;
    }

    public boolean eligible(Course course) {
        if (course.hasPrerequisites()) for (Course c : course.getPrerequisites())
            if (!this.getCoursesTaken().contains(c)) return false;
        return !coursesTaken.contains(course);
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
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
