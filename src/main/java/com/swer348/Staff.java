package com.swer348;

import java.time.LocalDate;

public class Staff extends Member {
    String staffID;
    public Staff(String fName, String lName, String phoneNum, String city, LocalDate dob, String staffID) {
        super(fName, lName, phoneNum, city, dob);
        this.staffID = staffID;
    }

    public String getStaffID() {
        return this.staffID;
    }

    @Override
    public String toString() {
        return String.format("%s staffID=%s%n", super.toString(), getStaffID());    }
}
