package com.swer348;

import java.time.*;
import java.util.*;

public class Section {
    private final Character section;
    private final ArrayList<Student> students = new ArrayList<>();
    private final Course course;
    private final ArrayList<Lecture> lectures = new ArrayList<Lecture>();
    Section(Course course, Character section){
        this.section=section;
        this.course = course;
    }

    boolean studentExist(Student student){
        return students.contains(student);
    }

    void addStudent(Student student){
        students.add(student);
    }

    void removeStudent(Student student){
        students.removeIf(s -> s.getStudentID().equals(student.getStudentID()));
    }

    public void addTimeSlot(LocalDateTime startTime) {
        startTimes.add(startTime);
    }

    public ArrayList<LocalDateTime> getStartTimes() {
        return startTimes;
    }

    boolean sectionFull(){
        return getStudentsNumber() == 5;
    }

    Character getSection(){
        return section;
    }

    ArrayList<Student> getStudents(){
        return students;
    }

    boolean studentExistByID(String id){
        return students.stream().anyMatch(s -> s.getStudentID().equals(id));
    }

    Student getStudentById(String id){
        return students.stream()
        .filter(s -> s.getStudentID().equals(id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    int getStudentsNumber(){
        return students.size();
    }

}
