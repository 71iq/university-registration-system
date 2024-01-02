package com.swer348;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class Schedule {
    private final HashMap<DayOfWeek, ArrayList<Lecture>> schedule;
    public static Scanner sc = Main.getScanner();

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

    public void removeCourse(Course course) {
        for (DayOfWeek day : EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)) {
            this.getSchedule().put(day, new ArrayList<>(this.getSchedule().get(day).stream().filter(e -> e.getSection() != null && !e.getSection().getCourse().equals(course)).toList()));
        }
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
