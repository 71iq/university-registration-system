package com.ehab.urs;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The `Schedule` class manages the schedule for members and rooms in an educational institution.
 * It includes methods to check availability, add lectures, and remove courses from the schedule.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-12-21
 */
public class Schedule{
    /**
     * Collection to store the schedule, mapping each day to a list of lectures.
     */
    private final HashMap<DayOfWeek, ArrayList<Lecture>> schedule;

    /**
     * Scanner object for user input.
     */
    public static Scanner sc = Main.getScanner();

    /**
     * Constructor for the `Schedule` class.
     * Initializes the schedule with default lectures for each day of the week.
     */
    public Schedule() {
        this.schedule = new HashMap<>();
        for (DayOfWeek day : EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)) {
            this.schedule.put(day, new ArrayList<>());
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
                this.schedule.get(day).add(new Lecture(day, LocalTime.MIN, LocalTime.MAX));
            else {
                this.schedule.get(day).add(new Lecture(day, LocalTime.MIN, LocalTime.MIN.plusMinutes(479)));
                this.schedule.get(day).add(new Lecture(day, LocalTime.NOON.plusMinutes(360), LocalTime.MIN));
            }
        }
    }

    /**
     * Checks if a given time slot on a specific day is not busy.
     *
     * @param day       The day of the week.
     * @param startTime The start time of the slot.
     * @param endTime   The end time of the slot.
     * @return True if the slot is not busy, false otherwise.
     */
    public synchronized boolean isNotBusy(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        return this.getSchedule().get(day).stream().allMatch(e -> e.getEndTime().isBefore(startTime) || e.getStartTime().isAfter(endTime));
    }

    /**
     * Adds a lecture to the schedule.
     *
     * @param lecture The lecture to add.
     */
    public synchronized void addLecture(Lecture lecture) {
        this.getSchedule().get(lecture.getDay()).add(lecture);
        this.getSchedule().get(lecture.getDay()).sort(Comparator.comparing(Lecture::getStartTime));
    }

    /**
     * Getter for the schedule.
     *
     * @return The schedule.
     */
    public synchronized HashMap<DayOfWeek, ArrayList<Lecture>> getSchedule() {
        return schedule;
    }

    /**
     * Method to manage the schedule, allowing users to print schedules for members and rooms.
     */
    public static void manageSchedule() {
        int input;
        do {
            System.out.println("Enter 1 to print a schedule for a member: ");
            System.out.println("Enter 2 to print a schedule for a room: ");
            System.out.println("Enter 0 to go back: ");
            System.out.println("Enter -1 to exit the program: ");

            if (sc.hasNextInt()) {
                input = sc.nextInt();
                switch (input) {
                    case 1 -> Member.printSchedule();
                    case 2 -> Room.printRoomSchedule();
                    case 0 -> System.out.println("Going Back...");
                    case -1 -> Main.setExit();
                    default -> System.out.print("Invalid input. Please enter a valid option.");
                }
                if (input == 0) return;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine();
                input = 0;
            }
        } while (input != -1);
    }

    /**
     * Removes all lectures associated with a specific course from the schedule.
     *
     * @param course The course to remove.
     */
    public void removeCourse(Course course) {
        for (DayOfWeek day : EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)) {
            this.getSchedule().put(day, new ArrayList<>(this.getSchedule().get(day).stream().filter(e -> e.getSection() != null && !e.getSection().getCourse().equals(course)).toList()));
        }
    }

    /**
     * Provides a string representation of the schedule.
     *
     * @return A string representing the schedule.
     */
    @Override
    public String toString() {
        StringBuilder scheduleStringBuilder = new StringBuilder();
        for (DayOfWeek day : EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)) {
            List<Lecture> lectures = this.getSchedule().get(day);
            scheduleStringBuilder.append(day).append(":\n");
            lectures.forEach(lecture -> {
                Section section = lecture.getSection();
                if (section != null)
                    scheduleStringBuilder.append("  ").append(section.getCourse().getName()).append(" - Section ").append(section.getSection()).append(" from ").append(lecture.getStartTime()).append(" to ").append(lecture.getEndTime()).append(" in room ").append(lecture.getRoom()).append("\n");
            });
        }
        return scheduleStringBuilder.toString();
    }
}
