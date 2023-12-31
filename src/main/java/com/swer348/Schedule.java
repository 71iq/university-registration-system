package com.swer348;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class Schedule {
    private final HashMap<DayOfWeek, ArrayList<Lecture>> schedule;

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

    public synchronized boolean isNotBusy(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        return this.getSchedule().get(day).stream().allMatch(e -> e.getEndTime().isBefore(startTime) || e.getStartTime().isAfter(endTime));
    }

    public synchronized void addLecture(Lecture lecture) {
        this.getSchedule().get(lecture.getDay()).add(lecture);
        this.getSchedule().get(lecture.getDay()).sort(Comparator.comparing((Lecture::getStartTime)));
    }

    public synchronized HashMap<DayOfWeek, ArrayList<Lecture>> getSchedule() {
        return schedule;
    }

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
