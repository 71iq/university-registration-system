package com.swer348;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Schedule {
    HashMap<DayOfWeek, ArrayList<Lecture>> schedule;

    public Schedule() {
        this.schedule = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            this.getSchedule().put(day, new ArrayList<>());
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
                this.getSchedule().get(day).add(new Lecture(LocalTime.MIN, LocalTime.MAX));
            else {
                this.getSchedule().get(day).add(new Lecture(LocalTime.MAX, LocalTime.MIN.plusMinutes(479)));
                this.getSchedule().get(day).add(new Lecture(LocalTime.MIN, LocalTime.NOON.plusMinutes(360)));
            }
        }
    }

    public Schedule(HashMap<DayOfWeek, ArrayList<Lecture>> s) {
        this.schedule = s;
    }

    public boolean isBusy(LocalTime startTime, LocalTime endTime) {
        DayOfWeek day = startTime.getDayOfWeek();
        LocalTime st = startTime.toLocalTime(), et = endTime.toLocalTime();
        return this.getSchedule().get(day).stream().allMatch(e -> e.getEndTime().isBefore(st) || e.getStartTime().isAfter(et));
    }

    public void addLecture(DayOfWeek day, Lecture lecture) {
        ArrayList<Lecture> todayLectures = this.getSchedule().get(day);
        todayLectures.sort(Comparator.comparing(Lecture::getStartTime));
        for (int i = 0; i < todayLectures.size() - 1; i++) {
            if (todayLectures.get(i).getEndTime().isBefore(lecture.getStartTime()) && todayLectures.get(i + 1).getStartTime().isAfter(lecture.getEndTime())) {
                this.getSchedule().get(day).add(i + 1, lecture);
                break;
            }
        }
    }

    public void setSchedule(HashMap<DayOfWeek, ArrayList<Lecture>> schedule) {
        this.schedule = schedule;
    }

    public HashMap<DayOfWeek, ArrayList<Lecture>> getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return "schedule=" + schedule.entrySet();
    }

}
