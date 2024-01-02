package com.swer348;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Student extends Member {
    String studentID, standing;
    private int credits = 0;
    ArrayList<Course> coursesTaken;
    HashMap<Course, Grade> grades = new HashMap<>();
    Schedule schedule;

    public Student(String fName, String lName, String phoneNum, String city, LocalDate dob, String studentID, ArrayList<Course> cT) {
        super(fName, lName, phoneNum, city, dob);
        this.studentID = studentID;
        this.coursesTaken = cT;
        this.schedule = new Schedule();
        if (coursesTaken.size() == 1 && coursesTaken.getFirst().getName().equals("none")) coursesTaken.clear();
        assignRandomGrades();
        assignStanding();
    }

    public void assignStanding() {
        double gpa = this.calculateGPA();
        standing = (gpa >= 3.9 ? "Highest Honor" : (gpa >= 3.0 ? "Honor" : (gpa == 0.0 ? "no standing yet" : (gpa < 1.0 ? "Failure" : (gpa < 2.0 ? "Probation" : "Not Bad")))));
    }

    public void assignRandomGrades() {
        for (Course course : coursesTaken) addGrade(course, Grade.assignRandomGrade());
    }

    public double calculateGPA() {
        if (coursesTaken.isEmpty()) {
            return 0.0;
        }

        double[] totalPoints = {0.0};
        int[] totalCredits = {0};

        getGrades().forEach((key, value) -> {
            totalCredits[0] += key.getCredits();
            totalPoints[0] += value.getValue() * key.getCredits();
        });

        return totalCredits[0] == 0 ? 0.0 : totalPoints[0] / totalCredits[0];
    }

    public String getStudentID() {
        return this.studentID;
    }

    public boolean eligible(Course course) {
        if (course.hasPrerequisites()) for (Course c : course.getPrerequisites())
            if (!this.getCoursesTaken().contains(c) || this.getCoursesTaken().stream().anyMatch(e -> e.getName().equals(c.getName()) && getGrades().get(e).getValue() < 1.0))
                return false;
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

    public void removeCourse(Course course) {
        this.getCoursesTaken().remove(course);
    }

    public void addGrade(Course course, Grade grade) {
        this.getGrades().put(course, grade);
    }

    public HashMap<Course, Grade> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return String.format("%sStudentID=%s\nGPA=%.2f", super.toString(), getStudentID(), calculateGPA());
    }
}
