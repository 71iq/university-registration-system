package com.swer348;

import java.time.*;

public class Lecture {
    private LocalTime startTime, endTime;
    private DayOfWeek day;
    private Room room;
    private Section section;
    private int credits, lectureDuration;

    // for initializing lectures in semester before having start time
    public Lecture(Section section, int lectureDuration) {
        this.section = section;
        this.lectureDuration = lectureDuration;
        this.credits = section.getCredits();
    }

    // for adding filler or template lectures
    public Lecture(DayOfWeek day, LocalTime st, LocalTime et) {
        this.day = day;
        this.endTime = et;
        this.startTime = st;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getLectureDuration() {
        return lectureDuration;
    }

    public int getCredits() {
        return credits;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
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

    public DayOfWeek getDay() {
        return day;
    }

    public String toString() {
        return String.format("lecture of course %s in section %s starts at %s and ends at %s in %s room", this.getSection().getCourse().getName(), this.getSection().getSection(), this.getStartTime().toString(), this.getEndTime().toString(), this.getRoom());
    }
}
