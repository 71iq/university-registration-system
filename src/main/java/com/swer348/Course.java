package com.swer348;

import java.util.*;
import java.util.stream.*;

public class Course {
    private String name;
    private final ArrayList<Section> sec = new ArrayList<>();

    Course(String name) {
        this.name = name;
        sec.addAll(Arrays.asList(new Section('A', null), new Section('B', null), new Section('C', null), new Section('D', null)));
    }

    void addStudent(Student student) {
        if (courseFull()) {
            System.out.println("The Course is FULL!");
            return;
        }
        if (studentExist(student)) {
            System.out.println("The Student is already REGISTERED in this Course!");
            return;
        }
        sec.forEach(s -> {
            if (!s.sectionFull()) {
                s.addStudent(student);
                System.out.println("Student " + student.getFName() + " " + student.getLName()
                        + " has been added to Course " + name + " Section " + s.getSection());
            }
        });
    }

    void removeStudent(Student student) {
        if (!studentExist(student)) {
            System.out.println("THE Student is not REGISTERED in this Course!");
            return;
        }
        sec.forEach(s -> {
            if (s.getStudents().contains(student)) {
                s.removeStudent(student);
                System.out.println("Student " + student.getFName() + " " + student.getLName()
                        + " has been removed from Course " + name + s.getSection());
            }
        });

    }

    void switchSection(String studentId, Character fromSection, Character toSection) {
        if (fromSection >= 'A' && fromSection <= 'D' && toSection >= 'A' && toSection <= 'D' && toSection != fromSection) {
            int from = fromSection - 'A', to = toSection - 'A';
            if (!studentExistById(studentId)) {
                System.out.println("There is no Student that carries the id: " + studentId + " in the course:" + name);
                return;
            }
            if (!sec.get(from).studentExistByID(studentId)) {
                System.out.println("There is no Student that carries the id: " + studentId + " in the course:" + name + fromSection);
                return;
            }
            if (sec.get(to).studentExistByID(studentId)) {
                System.out.println("The Student that carries the id: " + studentId + " is already in the course:" + name + toSection);
                return;
            }
            Student stu = sec.get(from).getStudentById(studentId);
            sec.get(from).removeStudent(sec.get(from).getStudentById(studentId));
            sec.get(to).addStudent(stu);
            System.out.println("The Student " + stu.getFName() + " " + stu.getLName() + " that carries the ID: "
                    + stu.getStudentID() + " has been removed from section " + fromSection
                    + " and got added to section " + toSection);
        } else
            System.out.println("The inputs are incorrect");
    }

    boolean studentExist(Student student) {
        return sec.stream().anyMatch(s -> s.studentExist(student));
    }

    boolean studentExistById(String id) {
        return sec.stream().anyMatch(s -> s.studentExistByID(id));
    }

    boolean courseFull() {
        return getStudentsNumber() == 20;
    }

    ArrayList<Student> getAllStudents() {
        return (ArrayList<Student>) sec.stream()
                .flatMap(s -> s.getStudents().stream())
                .collect(Collectors.toList());
    }

    ArrayList<Student> getAllStudentsSection(Character c) {
        return sec.get(c - 'A').getStudents();
    }

    int getStudentsNumber() {
        return sec.stream().mapToInt(Section::getStudentsNumber).sum();
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    public String toString() {
        return this.getName();
    }
}
