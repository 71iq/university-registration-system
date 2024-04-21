package com.ehab.urs;

import java.time.LocalDate;

/**
 * The `Staff` class represents a staff member in the educational institution.
 * It extends the `Member` class and includes additional attributes specific to staff members.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-11-19
 */
public class Staff extends Member {

    /**
     * The unique identifier for the staff member.
     */
    private String staffID;

    /**
     * Constructs a new staff member with the provided information.
     *
     * @param fName    The first name of the staff member.
     * @param lName    The last name of the staff member.
     * @param phoneNum The phone number of the staff member.
     * @param city     The city of residence of the staff member.
     * @param dob      The date of birth of the staff member.
     * @param staffID  The unique identifier for the staff member.
     */
    public Staff(String fName, String lName, String phoneNum, String city, LocalDate dob, String staffID) {
        super(fName, lName, phoneNum, city, dob);
        this.staffID = staffID;
    }

    /**
     * Gets the staff member's unique identifier.
     *
     * @return The staff member's unique identifier.
     */
    public String getStaffID() {
        return this.staffID;
    }

    /**
     * Returns a string representation of the staff member, including inherited attributes from the `Member` class.
     *
     * @return A string representation of the staff member.
     */
    @Override
    public String toString() {
        return String.format("%s staffID=%s%n", super.toString(), getStaffID());
    }
}
