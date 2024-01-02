package com.swer348;

import java.util.*;

public class Room {
    private static final ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<String> building = new ArrayList<>(List.of("M", "E", "S", "P")), floor = new ArrayList<>(List.of("1", "2", "3")), hall = new ArrayList<>(List.of("01", "02", "03", "04", "05"));
    static Scanner sc = Main.getScanner();

    private final String name;
    private Schedule schedule;

    public Room(String n) {
        this.name = n;
        this.schedule = new Schedule();
    }

    public static void initializeRooms() {
        building.forEach(b -> floor.forEach(f -> hall.forEach(h -> rooms.add(new Room(b + f + h)))));
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getName() {
        return name;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static void printRoomSchedule() {
        System.out.println("Enter a room's name");
        String name = sc.next().trim().toUpperCase();
        if (rooms.stream().anyMatch(e -> e.getName().equals(name)))
            System.out.println(rooms.stream().filter(e -> e.getName().equals(name)).toList().getFirst().getSchedule());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
