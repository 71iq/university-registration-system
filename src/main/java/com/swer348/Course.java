package com.swer348;

import java.util.*;
import java.util.stream.*;

/**
 * The `Course` class represents a course in an educational system.
 * It includes information about the course name, credits, prerequisites, and sections.
 * The class provides methods to manage student enrollment, switch sections, and retrieve information about the course.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-11-19
 */
public class Course {
    /**
     * The name of the course.
     */
    private final String name;
    /**
     * The number of credits associated with the course.
     */
    private final int credits;
    /**
     * The list of prerequisites for the course.
     */
    private List<Course> prerequisites;
    /**
     * The list of sections available for the course.
     */
    private final List<Section> sec = new ArrayList<>();

    /**
     * Constructs a Course object with the specified name, credits, and prerequisites.
     *
     * @param name          The name of the course.
     * @param credits       The number of credits for the course.
     * @param prerequisites The list of prerequisite courses.
     */
    Course(String name, int credits, ArrayList<Course> prerequisites) {
        this.name = name;
        this.credits = credits;
        this.prerequisites = prerequisites.isEmpty() ? Collections.emptyList() : new ArrayList<>(prerequisites);
        sec.addAll(IntStream.rangeClosed('A', 'D').mapToObj(section -> new Section(this, (char) section)).toList());
    }

    /**
     * Constructs a Course object with the specified name, credits, and no prerequisites.
     *
     * @param name    The name of the course.
     * @param credits The number of credits for the course.
     */
    public Course(String name, int credits) {
        this.name = name;
        this.prerequisites = new ArrayList<>();
        this.credits = credits;
        sec.addAll(IntStream.rangeClosed('A', 'D').mapToObj(section -> new Section(this, (char) section)).toList());
    }

    /**
     * Sets the list of prerequisites for the course.
     *
     * @param prerequisites The list of prerequisite courses.
     */
    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    /**
     * Adds a student to the course, considering available sections.
     *
     * @param student The student to be added.
     */
    public void addStudent(Student student) {
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

    /**
     * Removes a student from the course.
     *
     * @param student The student to be removed.
     */
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

    /**
     * Switches the section of a student in the course.
     *
     * @param studentId   The ID of the student.
     * @param fromSection The current section of the student.
     * @param toSection   The section to which the student will switch.
     */
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

    /**
     * Checks if a student exists in the course.
     *
     * @param student The student
     * @return true if the student exists in the course, false otherwise.
     */
    public boolean studentExist(Student student) {
        return sec.stream().anyMatch(s -> s.studentExist(student));
    }

    /**
     * Checks if a student with the specified ID exists in the course.
     *
     * @param id The ID of the student.
     * @return true if the student with the ID exists in the course, false otherwise.
     */
    boolean studentExistById(String id) {
        return sec.stream().anyMatch(s -> s.studentExistByID(id));
    }

    /**
     * Checks if the course is full, i.e., all sections are at maximum capacity.
     *
     * @return true if the course is full, false otherwise.
     */
    public boolean courseFull() {
        return getStudentsNumber() == 20;
    }

    /**
     * Retrieves a list of all students enrolled in the course.
     *
     * @return The list of all students in the course.
     */
    ArrayList<Student> getAllStudents() {
        return (ArrayList<Student>) sec.stream().flatMap(s -> s.getStudents().stream()).collect(Collectors.toList());
    }

    /**
     * Retrieves a list of students in a specific section of the course.
     *
     * @param c The section identifier (A/B/C/D).
     * @return The list of students in the specified section.
     */
    public ArrayList<Student> getAllStudentsSection(Character c) {
        return sec.get(c - 'A').getStudents();
    }

    /**
     * Retrieves the total number of students enrolled in the course.
     *
     * @return The total number of students in the course.
     */
    int getStudentsNumber() {
        return sec.stream().mapToInt(Section::getStudentsNumber).sum();
    }

    /**
     * Retrieves the name of the course.
     *
     * @return The name of the course.
     */
    String getName() {
        return name;
    }

    /**
     * Retrieves the number of credits for the course.
     *
     * @return The number of credits for the course.
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Retrieves the list of prerequisites for the course.
     *
     * @return The list of prerequisites for the course.
     */
    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    /**
     * Checks if the course has prerequisites.
     *
     * @return true if the course has prerequisites, false otherwise.
     */
    public boolean hasPrerequisites() {
        return this.getPrerequisites().size() == 1 && this.getPrerequisites().getFirst().getName().equals("none");
    }

    /**
     * Returns a string representation of the course.
     *
     * @return A string representation of the course.
     */
    public String toString() {
        return "Course: " + this.getName() + " has: " + this.getCredits() + "\n";
    }
}
