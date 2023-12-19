package com.swer348;

import java.time.LocalDate;

public class Staff extends Person{
    String staffID;
    public Staff(String fName, String lName, String phoneNum, String city, LocalDate dob, String staffID) {
        super(fName, lName, phoneNum, city, dob);
        this.staffID = staffID;
    }

    public String getStaffID() {
        return this.staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    @Override
    public String toString() {
        return super.toString() + " staffID=" + getStaffID() + "\n" ;
    }
}
