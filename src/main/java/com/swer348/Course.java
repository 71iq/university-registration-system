package com.swer348;

import java.util.*;
import java.util.stream.*;

public class Course {
    private final String name;
    private final int credits;
    private final ArrayList<Course> prerequisites;
    private final ArrayList<Section> sec = new ArrayList<>();

    Course(String name, int c, ArrayList<Course> pre) {
        this.name = name;
        this.credits = c;
        this.prerequisites = pre;
        if (pre.size() == 1 && pre.get(0).getName().equals("none")) pre.clear();
        sec.addAll(Arrays.asList(new Section(this, 'A', null), new Section(this, 'B', null), new Section(this, 'C', null), new Section(this, 'D', null)));
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

    String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public ArrayList<Course> getPrerequisites() {
        return prerequisites;
    }

    public boolean hasPrerequisites() {
        return this.getPrerequisites().size() == 1 && this.getPrerequisites().get(0).getName().equals("none");
    }

    public String toString() {
        return "Course: " + this.getName() + " has: " + this.getCredits() + " credits, and its' prerequisites are: " + this.getPrerequisites();
    }
}
