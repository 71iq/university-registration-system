package com.swer348;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The `Student` class represents a student in the educational institution.
 * It extends the `Member` class and includes additional attributes specific to students.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-11-19
 */
public class Student extends Member {

    /**
     * The unique identifier for the student.
     */
    private final String studentID;

    /**
     * The standing of the student based on their GPA.
     */
    private String standing;

    /**
     * The total credits earned by the student.
     */
    private int credits;

    /**
     * The list of courses taken by the student.
     */
    private ArrayList<Course> coursesTaken;

    /**
     * The grades obtained by the student for each course.
     */
    private HashMap<Course, Grade> grades;

    /**
     * The schedule of the student.
     */
    private Schedule schedule;

    /**
     * Constructs a new student with the provided information.
     *
     * @param fName        The first name of the student.
     * @param lName        The last name of the student.
     * @param phoneNum     The phone number of the student.
     * @param city         The city of residence of the student.
     * @param dob          The date of birth of the student.
     * @param studentID    The unique identifier for the student.
     * @param coursesTaken The list of courses taken by the student.
     */
    public Student(String fName, String lName, String phoneNum, String city, LocalDate dob, String studentID, ArrayList<Course> coursesTaken) {
        super(fName, lName, phoneNum, city, dob);
        this.studentID = studentID;
        this.coursesTaken = coursesTaken;
        this.schedule = new Schedule();
        this.grades = new HashMap<>();
        this.credits = 0;

        if (coursesTaken.size() == 1 && coursesTaken.get(0).getName().equals("none")) {
            this.coursesTaken.clear();
        }

        assignRandomGrades();
        assignStanding();
    }

    /**
     * Assigns the standing of the student based on their GPA.
     */
    public void assignStanding() {
        double gpa = this.calculateGPA();
        standing = (gpa >= 3.9 ? "Highest Honor" : (gpa >= 3.0 ? "Honor" : (gpa == 0.0 ? "No standing yet" : (gpa < 1.0 ? "Failure" : (gpa < 2.0 ? "Probation" : "Not Bad")))));
    }

    /**
     * Assigns random grades to the courses taken by the student.
     */
    public void assignRandomGrades() {
        for (Course course : coursesTaken) {
            addGrade(course, Grade.assignRandomGrade());
        }
    }

    /**
     * Calculates the GPA (Grade Point Average) of the student.
     *
     * @return The GPA of the student.
     */
    public double calculateGPA() {
        if (coursesTaken.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Course course : coursesTaken) {
            Grade grade = grades.get(course);
            totalCredits += course.getCredits();
            totalPoints += grade.getValue() * course.getCredits();
        }

        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    /**
     * Gets the unique identifier of the student.
     *
     * @return The student's unique identifier.
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Checks if the student is eligible to take a specific course.
     *
     * @param course The course to check eligibility for.
     * @return `true` if the student is eligible, `false` otherwise.
     */
    public boolean eligible(Course course) {
        if (course.hasPrerequisites()) {
            for (Course c : course.getPrerequisites()) {
                if (!this.getCoursesTaken().contains(c) || this.getCoursesTaken().stream().anyMatch(e -> e.getName().equals(c.getName()) && getGrades().get(e).getValue() < 1.0)) {
                    return false;
                }
            }
        }
        return !coursesTaken.contains(course);
    }

    /**
     * Sets the schedule of the student.
     *
     * @param schedule The schedule to set.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Sets the total credits earned by the student.
     *
     * @param credits The total credits to set.
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Gets the total credits earned by the student.
     *
     * @return The total credits earned by the student.
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Gets the schedule of the student.
     *
     * @return The schedule of the student.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Gets the list of courses taken by the student.
     *
     * @return The list of courses taken by the student.
     */
    public ArrayList<Course> getCoursesTaken() {
        return coursesTaken;
    }

    /**
     * Adds a course to the list of courses taken by the student.
     *
     * @param course The course to add.
     */
    public void addCourse(Course course) {
        this.getCoursesTaken().add(course);
    }

    /**
     * Removes a course from the list of courses taken by the student.
     *
     * @param course The course to remove.
     */
    public void removeCourse(Course course) {
        this.getCoursesTaken().remove(course);
    }

    /**
     * Adds a grade for a specific course.
     *
     * @param course The course for which the grade is added.
     * @param grade  The grade to add.
     */
    public void addGrade(Course course, Grade grade) {
        this.getGrades().put(course, grade);
    }

    /**
     * Gets the grades obtained by the student for each course.
     *
     * @return The grades obtained by the student for each course.
     */
    public HashMap<Course, Grade> getGrades() {
        return grades;
    }


    /**
     * Gets the standing obtained by the student.
     *
     * @return The standing obtained by the student.
     */
    public String getStanding() {
        return standing;
    }

    /**
     * Returns a string representation of the student, including inherited attributes from the `Member` class.
     *
     * @return A string representation of the student.
     */
    @Override
    public String toString() {
        return String.format("%s StudentID=%s%nGPA=%.2f\nStanding=%s", super.toString(), getStudentID(), calculateGPA(), getStanding());
    }
}
