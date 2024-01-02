package com.swer348;

import java.util.*;
import java.util.stream.*;

public class Course {
    private final String name;
    private final int credits;
    private List<Course> prerequisites;
    private final List<Section> sec = new ArrayList<>();

    Course(String name, int c, ArrayList<Course> pre) {
        this.name = name;
        this.credits = c;
        this.prerequisites = pre.isEmpty() ? Collections.emptyList() : new ArrayList<>(pre);
        sec.addAll(IntStream.rangeClosed('A', 'D').mapToObj(section -> new Section(this, (char) section)).toList());
    }

    public Course(String name, int c) {
        this.name = name;
        this.prerequisites = new ArrayList<>();
        this.credits = c;
        sec.addAll(IntStream.rangeClosed('A', 'D').mapToObj(section -> new Section(this, (char) section)).toList());
    }

    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
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
        final boolean[] isAdded = {false};
        sec.forEach(s -> {
            if (!s.sectionFull() && !isAdded[0]) {
                s.addStudent(student);
                isAdded[0] = true;
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
                System.out.println("Student " + student.getFName() + " " + student.getLName() + " has been removed from Course " + name + s.getSection());
            }
        });

    }

    void switchSection(String studentId, Character fromSection, Character toSection) {
        if (fromSection >= 'A' && fromSection <= 'D' && toSection >= 'A' && toSection <= 'D' && toSection != fromSection) {
            int from = fromSection - 'A', to = toSection - 'A';
            if (!studentExistById(studentId)) {
                System.out.printf("There is no Student that carries the id: %s in the course: %s%n", studentId, name);
                return;
            }
            if (!sec.get(from).studentExistByID(studentId)) {
                System.out.printf("There is no Student that carries the id: %s in the course: %s%s%n", studentId, name, fromSection);
                return;
            }
            if (sec.get(to).studentExistByID(studentId)) {
                System.out.printf("The Student that carries the id: %s is already in the course: %s%s%n", studentId, name, toSection);
                return;
            }
            Student stu = sec.get(from).getStudentById(studentId);
            sec.get(from).removeStudent(stu);
            sec.get(to).addStudent(stu);
            System.out.printf("The Student %s %s that carries the ID: %s has been removed from section %s and added to section %s%n", stu.getFName(), stu.getLName(), stu.getStudentID(), fromSection, toSection);
        }
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
        return (ArrayList<Student>) sec.stream().flatMap(s -> s.getStudents().stream()).collect(Collectors.toList());
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

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public boolean hasPrerequisites() {
        return this.getPrerequisites().size() == 1 && this.getPrerequisites().getFirst().getName().equals("none");
    }

    public String toString() {
        return "Course: " + this.getName() + " has: " + this.getCredits() + "\n";
    }
}
