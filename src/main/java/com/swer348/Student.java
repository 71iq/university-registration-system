package com.swer348;

import java.time.LocalDate;
import java.util.ArrayList;

public class Student extends Person {
    String studentID;
    ArrayList<Course> coursesTaken;

    public Student(String fName, String lName, String phoneNum, String city, LocalDate dob, String studentID, ArrayList<Course> cT) {
        super(fName, lName, phoneNum, city, dob);
        this.studentID = studentID;
        this.coursesTaken = cT;
        if (coursesTaken.size() == 1 && coursesTaken.get(0).getName().equals("none"))
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

    public ArrayList<Course> getCoursesTaken() {
        return coursesTaken;
    }

    @Override
    public String toString() {
        return super.toString() + " studentID=" + getStudentID() + "\n";
    }
}
