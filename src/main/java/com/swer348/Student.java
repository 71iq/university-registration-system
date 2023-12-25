package com.swer348;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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

    public String toStringSchedule(Student student) {
        StringBuilder scheduleStringBuilder = new StringBuilder();
        Schedule studentSchedule = student.getSchedule();
    
        for (DayOfWeek day : EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)) {
            List<Lecture> lectures = studentSchedule.getSchedule().get(day);
    
            if (!lectures.isEmpty()) {
                scheduleStringBuilder.append(day).append(":\n");
    
                lectures.forEach(lecture -> {
                    Section section = lecture.getSection();
    
                    // Add a null check for section
                    if (section != null) {
                        scheduleStringBuilder.append("  ").append(section.getCourse().getName()).append(" - Section ").append(section.getSection());
                        scheduleStringBuilder.append(" from ").append(lecture.getStartTime()).append(" to ").append(lecture.getEndTime()).append("\n");
                    } else {
                        // Handle the case where section is null
                        scheduleStringBuilder.append("  Lecture information not available\n");
                    }
                });
            }
        }
    
        return scheduleStringBuilder.toString();
    }
    
    
}
