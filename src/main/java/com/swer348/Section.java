package com.swer348;

import java.util.*;

public class Section {
    private Character section;
    private Date time;
    private ArrayList<Student> students = new ArrayList<>();
    Section(Character section,Date time){
        this.section=section;
        this.time=time;
    }

    boolean studentExist(Student student){
        return students.contains(student);
    }

    int studentIndex(Student student){
        if(!studentExist(student)) return -1;
        return students.indexOf(student);
    }

    void addStudent(Student student){
        students.add(student);
    }

    void removeStudent(Student student){
        students.removeIf(s -> s.getStudentID().equals(student.getStudentID()));
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
        boolean exist = students.stream().anyMatch(s -> s.getStudentID().equals(id));
        return exist;
    }

    Student getStudentById(String id){
        Student student = students.stream()
        .filter(s -> s.getStudentID().equals(id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Student not found"));
        return student;
    }

    int getStudentsNumber(){
        return students.size();
    }

    Date getTime(){
        return time;
    }

    void setTime(Date time){
        this.time=time;
    }
}
