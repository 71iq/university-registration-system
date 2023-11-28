package com.swer348;

import java.util.ArrayList;

public class Course {
    private String name;
    private ArrayList<Section> sec = new ArrayList<>();
    Course(String name){
        this.name=name;
        sec.add(new Section('A', null)); sec.add(new Section('B', null)); sec.add(new Section('C', null)); sec.add(new Section('D', null));
    }

    void addStudent(Student student){
        if(courseFull()){
        System.out.println("THE Course is FULL!");
        return;
        }
        sec.forEach(s -> {
            if(!s.sectionFull()){
                s.AS(student);
                System.out.println("Student "+student.getFName()+" "+student.getLName()+" has been added to Course "+name+" Section "+s.getSection());
                return;
            }
        });
    }

    void removeStudent(Student student){
        if(!studentExist(student)){
            System.out.println("THE Student is not REGISTERED in this Course!");
            return;
        }
        sec.forEach(s -> {
            if(s.getStudents().contains(student)){
                s.RS(student);
                System.out.println("Student "+student.getFName()+" "+student.getLName()+" has been removed from Course "+name+s.getSection());
                return;
            }
        });

    }

    void switchSection(Student student, Character fromSection, Character toSection){
        
    }

    boolean studentExist(Student student){
        boolean exist = sec.stream().anyMatch(s -> s.studentExist(student));
        return exist;
    }

    boolean courseFull(){
        return getStudentsNumber()==20;
    }

    int getStudentsNumber(){
        return sec.stream().mapToInt(s -> s.getStudentsNumber()).sum();
    }

    void setName(String name){
        this.name=name;
    }
    String getName(){
        return name;
    }

    
}
