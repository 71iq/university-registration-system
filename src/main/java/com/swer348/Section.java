package com.swer348;

import java.util.ArrayList;
import java.util.Date;

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

    void AS(Student student){
        students.add(student);
    }

    void RS(Student student){
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
