package com.swer348;

import java.util.ArrayList;

public class Section {

    private final Character section;
    private final Course course;
    private int credits, lectureDuration;
    private final ArrayList<Student> students = new ArrayList<>();
    private Faculty instructor;
    private final ArrayList<Lecture> lectures = new ArrayList<>();
    private final static ArrayList<Section> sections = new ArrayList<>();

    Section(Course c, Character section) {
        this.section = section;
        this.course = c;
        this.credits = c.getCredits();
        Section.sections.add(this);
    }

    public static ArrayList<Section> getSections() {
        return sections;
    }

    public Faculty getInstructor() {
        return instructor;
    }

    public void setInstructor(Faculty instructor) {
        this.instructor = instructor;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getLectureDuration() {
        return lectureDuration;
    }

    public void setLectureDuration(int lectureDuration) {
        this.lectureDuration = lectureDuration;
    }

    public int getCredits() {
        return credits;
    }

    boolean studentExist(Student student) {
        return students.contains(student);
    }

    void addStudent(Student student) {
        students.add(student);
    }

    void removeStudent(Student student) {
        students.removeIf(s -> s.getStudentID().equals(student.getStudentID()));
    }

    boolean sectionFull() {
        return getStudentsNumber() == 5;
    }

    Character getSection() {
        return section;
    }

    ArrayList<Student> getStudents() {
        return students;
    }

    boolean studentExistByID(String id) {
        return students.stream().anyMatch(s -> s.getStudentID().equals(id));
    }

    Student getStudentById(String id) { return students.stream().filter(s -> s.getStudentID().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Student not found")); }

    int getStudentsNumber() {
        return students.size();
    }

    public Course getCourse() { return course; }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public int getNumberOfLectures() {
        return this.getCredits() * 50 / this.getLectureDuration();
    }

    @Override
    public String toString() {
        return "course = " + course +" section = " + section;
    }
}
