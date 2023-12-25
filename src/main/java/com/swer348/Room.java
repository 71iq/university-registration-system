package com.swer348;

import java.time.LocalTime;
import java.util.*;

public class Room {
    private static final ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<String> building = new ArrayList<>(List.of("M", "E", "S")), floor = new ArrayList<>(List.of("1", "2")), hall = new ArrayList<>(List.of("01", "02", "03", "04", "05"));

    private final String name;
    private Schedule schedule = new Schedule();

    public Room(String n) {
        this.name = n;
    }

    public static void initializeRooms() {
        building.forEach(b -> {
            floor.forEach(f -> {
                hall.forEach(h -> rooms.add(new Room(b + f + h)));
            });
        });
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }
    public boolean isAvailable(LocalTime startTime, LocalTime endTime) {
    Schedule roomSchedule = this.getSchedule();
    return roomSchedule.isBusy(startTime, endTime);
}


    @Override
    public String toString() {
        return String.format("Room = %s\n", this.getName());
    }
}
