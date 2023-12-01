package com.swer348;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        if(studentExist(student)){
            System.out.println("THE Student is already REGISTERED in this Course!");
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

    void switchSection(String studentId, Character fromSection, Character toSection){
        if(fromSection >= 65 && fromSection <= 68 && toSection >= 65 && toSection <= 68 && toSection != fromSection){
            int from = getSectionIndex(fromSection), to = getSectionIndex(toSection);
            if(!studentExistById(studentId)){
                System.out.println("There is no Student that carries the id: "+studentId+" in the course:"+name);
                return;
            }
            if(!sec.get(from).studentExistByID(studentId)){
                System.out.println("There is no Student that carries the id: "+studentId+" in the course:"+name + fromSection);
                return;
            }
            if(sec.get(to).studentExistByID(studentId)){
                System.out.println("The Student that carries the id: "+studentId+" is already in the course:"+name + toSection);
                return;
            }
            Student stu = sec.get(from).getStudentById(studentId);
            sec.get(from).RS(sec.get(from).getStudentById(studentId));
            sec.get(to).AS(stu);
            System.out.println("The Student "+stu.getFName()+" "+stu.getLName()+" that carries the ID: "+stu.getStudentID()+" has been removed from section "+fromSection+" and got added to section "+toSection);
        }
        else System.out.println("The inputs are incorrect");
    }

    boolean studentExist(Student student){
        boolean exist = sec.stream().anyMatch(s -> s.studentExist(student));
        return exist;
    }

    boolean studentExistById(String id){
        boolean exist = sec.stream().anyMatch(s -> s.studentExistByID(id));
        return exist;
    }

    boolean courseFull(){
        return getStudentsNumber()==20;
    }

    int getSectionIndex(Character c){
        int ind = IntStream.range(0, sec.size())
                .filter(i -> sec.get(i).getSection()==c)
                .findFirst()
                .orElse(-1);
        return ind;
    }

    ArrayList<Student> getAllStudents(){
        ArrayList<Student> list = (ArrayList<Student>)sec.stream()
                .flatMap(s -> s.getStudents().stream())
                .collect(Collectors.toList());
        return list;
    }

    ArrayList<Student> getAllStudentsSection(Character c){
        return sec.get(getSectionIndex(c)).getStudents();
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
