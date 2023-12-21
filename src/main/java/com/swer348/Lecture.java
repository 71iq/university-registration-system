package com.swer348;

import java.time.*;
import java.util.*;

public class Lecture {
    private final LocalTime startTime, endTime;
    private Room room;
    private Section section;
    private int credits, lectureDuration;

    public Lecture(LocalTime st, Section section) {
        this.section = section;
        this.startTime = st;
        this.credits = section.getCredits();
        this.endTime = startTime.plusMinutes(this.getCredits() * 50L);
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
        return String.format("lecture of course %s in section %s starts at %s and ends at %s in %s room", this.getSection().getCourse().getName(), this.getSection().getSection(), this.getStartTime().toString(), this.getEndTime().toString(), this.getRoom());
    }
}
