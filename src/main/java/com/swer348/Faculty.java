package com.swer348;

import java.time.LocalDate;

/**
 * The `Faculty` class represents a faculty member in an educational system.
 * It extends the `Member` class and includes additional information such as faculty ID, schedule, and credits.
 * The class provides methods to access and modify the faculty member's information.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-11-19
 */
public class Faculty extends Member {

    /**
     * The unique identifier for the faculty member.
     */
    String facultyID;

    /**
     * The schedule of the faculty member.
     */
    private Schedule schedule;

    /**
     * The total credits associated with the faculty member.
     */
    private int credits = 0;

    /**
     * The section to which the faculty member is assigned.
     */
    private Section section;

    /**
     * Constructs a Faculty object with the specified information.
     *
     * @param fName     The first name of the faculty member.
     * @param lName     The last name of the faculty member.
     * @param phoneNum  The phone number of the faculty member.
     * @param city      The city of residence of the faculty member.
     * @param dob       The date of birth of the faculty member.
     * @param facultyID The unique identifier for the faculty member.
     */
    public Faculty(String fName, String lName, String phoneNum, String city, LocalDate dob, String facultyID) {
        super(fName, lName, phoneNum, city, dob);
        this.facultyID = facultyID;
        this.schedule = new Schedule();
    }

    /**
     * Sets the schedule for the faculty member.
     *
     * @param schedule The schedule to be set.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Sets the section to which the faculty member is assigned.
     *
     * @param section The section to be set.
     */
    public void setSection(Section section) {
        this.section = section;
    }

    /**
     * Retrieves the section to which the faculty member is assigned.
     *
     * @return The section of the faculty member.
     */
    public Section getSection() {
        return section;
    }

    /**
     * Sets the total credits associated with the faculty member.
     *
     * @param credits The total credits to be set.
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Retrieves the total credits associated with the faculty member.
     *
     * @return The total credits of the faculty member.
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Retrieves the faculty ID of the faculty member.
     *
     * @return The faculty ID.
     */
    public String getFacultyID() {
        return this.facultyID;
    }

    /**
     * Retrieves the schedule of the faculty member.
     *
     * @return The schedule of the faculty member.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Overrides the `toString` method to provide a string representation of the faculty member.
     *
     * @return A string representation of the faculty member.
     */
    @Override
    public String toString() {
        return String.format("%s facultyID=%s%n", super.toString(), getFacultyID());
    }
}
