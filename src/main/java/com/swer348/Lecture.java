package com.swer348;

import java.time.*;

/**
 * The `Lecture` class represents a lecture session for a specific section of a course.
 * It includes information about the start and end time, day, room, section, credits, and lecture duration.
 * The class provides methods to access and modify lecture details and a formatted string representation of the lecture.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-12-21
 */
public class Lecture {

    /**
     * The start time of the lecture.
     */
    private LocalTime startTime;

    /**
     * The end time of the lecture.
     */
    private LocalTime endTime;

    /**
     * The day of the week on which the lecture occurs.
     */
    private DayOfWeek day;

    /**
     * The room where the lecture takes place.
     */
    private Room room;

    /**
     * The section associated with the lecture.
     */
    private Section section;

    /**
     * The number of credits for the lecture.
     */
    private int credits;

    /**
     * The duration of the lecture in minutes.
     */
    private int lectureDuration;

    /**
     * Constructs a `Lecture` object for initializing lectures in a semester without a specific start time.
     *
     * @param section         The section associated with the lecture.
     * @param lectureDuration The duration of the lecture in minutes.
     */
    public Lecture(Section section, int lectureDuration) {
        this.section = section;
        this.lectureDuration = lectureDuration;
        this.credits = section.getCredits();
    }

    /**
     * Constructs a `Lecture` object for adding filler or template lectures.
     *
     * @param day       The day of the week for the lecture.
     * @param startTime The start time of the lecture.
     * @param endTime   The end time of the lecture.
     */
    public Lecture(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.endTime = endTime;
        this.startTime = startTime;
    }

    /**
     * Sets the room for the lecture.
     *
     * @param room The room to be set.
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Retrieves the duration of the lecture in minutes.
     *
     * @return The duration of the lecture.
     */
    public int getLectureDuration() {
        return lectureDuration;
    }

    /**
     * Retrieves the number of credits for the lecture.
     *
     * @return The number of credits for the lecture.
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Retrieves the start time of the lecture.
     *
     * @return The start time of the lecture.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the lecture.
     *
     * @param startTime The start time to be set.
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time of the lecture.
     *
     * @param endTime The end time to be set.
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Sets the day of the week for the lecture.
     *
     * @param day The day of the week to be set.
     */
    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    /**
     * Retrieves the end time of the lecture.
     *
     * @return The end time of the lecture.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Retrieves the room where the lecture takes place.
     *
     * @return The room where the lecture takes place.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Retrieves the section associated with the lecture.
     *
     * @return The section associated with the lecture.
     */
    public Section getSection() {
        return section;
    }

    /**
     * Retrieves the day of the week for the lecture.
     *
     * @return The day of the week for the lecture.
     */
    public DayOfWeek getDay() {
        return day;
    }

    /**
     * Overrides the `toString` method to provide a formatted string representation of the lecture.
     *
     * @return A formatted string representation of the lecture.
     */
    public String toString() {
        return String.format("lecture of course %s in section %s starts at %s and ends at %s in %s room", this.getSection().getCourse().getName(), this.getSection().getSection(), this.getStartTime().toString(), this.getEndTime().toString(), this.getRoom());
    }
}
