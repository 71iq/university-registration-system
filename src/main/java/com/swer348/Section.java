package com.swer348;

import java.util.ArrayList;

/**
 * The `Section` class represents a section of a course in an educational institution.
 * It contains information about the section, including the course, instructor, students, and lectures.
 *
 * @author Ehab, Maamoun
 * @version 1.0
  @since 2023-12-01
 */
public class Section {

    /**
     * The section identifier.
     */
    private final Character section;

    /**
     * The associated course for the section.
     */
    private final Course course;

    /**
     * The number of credits associated with the section.
     */
    private final int credits;

    /**
     * The duration of each lecture in the section.
     */
    private int lectureDuration;

    /**
     * The list of students enrolled in the section.
     */
    private final ArrayList<Student> students = new ArrayList<>();

    /**
     * The faculty member who is the instructor for the section.
     */
    private Faculty instructor;

    /**
     * The list of lectures scheduled for the section.
     */
    private final ArrayList<Lecture> lectures = new ArrayList<>();

    /**
     * The list of all sections.
     */
    private final static ArrayList<Section> sections = new ArrayList<>();

    /**
     * Constructor for the `Section` class.
     *
     * @param c       The associated course.
     * @param section The section identifier.
     */
    Section(Course c, Character section) {
        this.section = section;
        this.course = c;
        this.credits = c.getCredits();
        Section.sections.add(this);
    }

    /**
     * Getter for the list of all sections.
     *
     * @return The list of all sections.
     */
    public static ArrayList<Section> getSections() {
        return sections;
    }

    /**
     * Getter for the instructor of the section.
     *
     * @return The faculty member who is the instructor for the section.
     */
    public Faculty getInstructor() {
        return instructor;
    }

    /**
     * Setter for the instructor of the section.
     *
     * @param instructor The faculty member to set as the instructor.
     */
    public void setInstructor(Faculty instructor) {
        this.instructor = instructor;
    }

    /**
     * Getter for the duration of each lecture in the section.
     *
     * @return The duration of each lecture in minutes.
     */
    public int getLectureDuration() {
        return lectureDuration;
    }

    /**
     * Setter for the duration of each lecture in the section.
     *
     * @param lectureDuration The duration of each lecture in minutes.
     */
    public void setLectureDuration(int lectureDuration) {
        this.lectureDuration = lectureDuration;
    }

    /**
     * Getter for the number of credits associated with the section.
     *
     * @return The number of credits.
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Checks if a student is enrolled in the section.
     *
     * @param student The student to check.
     * @return True if the student is enrolled, false otherwise.
     */
    boolean studentExist(Student student) {
        return students.contains(student);
    }

    /**
     * Adds a student to the section.
     *
     * @param student The student to add.
     */
    void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Removes a student from the section.
     *
     * @param student The student to remove.
     */
    void removeStudent(Student student) {
        students.removeIf(s -> s.getStudentID().equals(student.getStudentID()));
    }

    /**
     * Checks if the section is full, i.e., if the maximum number of students is enrolled.
     *
     * @return True if the section is full, false otherwise.
     */
    boolean sectionFull() {
        return getStudentsNumber() == 5;
    }

    /**
     * Getter for the section identifier.
     *
     * @return The section identifier.
     */
    Character getSection() {
        return section;
    }

    /**
     * Getter for the list of students enrolled in the section.
     *
     * @return The list of students.
     */
    ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Checks if a student with a given ID is enrolled in the section.
     *
     * @param id The student ID to check.
     * @return True if the student is enrolled, false otherwise.
     */
    boolean studentExistByID(String id) {
        return students.stream().anyMatch(s -> s.getStudentID().equals(id));
    }

    /**
     * Retrieves a student from the section based on their ID.
     *
     * @param id The student ID to search for.
     * @return The student with the given ID.
     * @throws RuntimeException if the student is not found.
     */
    Student getStudentById(String id) {
        return students.stream().filter(s -> s.getStudentID().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Student not found"));
    }

    /**
     * Getter for the number of students enrolled in the section.
     *
     * @return The number of students.
     */
    int getStudentsNumber() {
        return students.size();
    }

    /**
     * Getter for the associated course.
     *
     * @return The associated course.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Getter for the list of lectures scheduled for the section.
     *
     * @return The list of lectures.
     */
    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    /**
     * Calculates the number of lectures required for the section based on the credit hours and lecture duration.
     *
     * @return The number of lectures.
     */
    public int getNumberOfLectures() {
        return this.getCredits() * 50 / this.getLectureDuration();
    }

    /**
     * Generates a string representation of the `Section` object.
     *
     * @return A string representation of the `Section` object.
     */
    @Override
    public String toString() {
        return "course = " + course + " section = " + section;
    }
}
