package com.swer348;

import java.time.*;
import java.util.*;

public class Lecture {
    private LocalTime startTime, endTime;
    private Room room;
    private Section section;
    private int credits, lectureDuration;

    public Lecture(Section section) {
        this.section = section;
        this.credits = section.getCredits();
        this.lectureDuration = section.getLectureDuration();
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setSection(Section section) {
        this.section = section;
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

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Lecture(LocalTime st, LocalTime et) {
        this.endTime = et;
        this.startTime = st;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Room getRoom() {
        return room;
    }

    public Section getSection() {
        return section;
    }

    public List<LocalTime> getTimeSlots() {
        List<LocalTime> timeSlots = new ArrayList<>();
        LocalTime currentTime = startTime;

        while (currentTime.isBefore(endTime)) {
            timeSlots.add(currentTime);
            currentTime = currentTime.plusMinutes(5);
        }

        return timeSlots;
    }

    private List<LocalTime> generateTimeSlots() {
        List<LocalTime> timeSlots = new ArrayList<>();
        LocalTime currentTime = startTime;

        while (currentTime.isBefore(endTime)) {
            timeSlots.add(currentTime);
            currentTime = currentTime.plusMinutes(5);
        }

        return timeSlots;
    }

    public String toString() {
        return this.section.getCourse().getName() + " " + this.section.getSection() + " " + this.lectureDuration;
    }
}
