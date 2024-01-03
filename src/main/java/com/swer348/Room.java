package com.swer348;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The `Room` class represents a room in an educational institution.
 * Each room has a name, building, floor, and hall information, and a schedule.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-12-21
 */
public class Room {
    /**
     * Collection to store instances of rooms.
     */
    private static final ArrayList<Room> rooms = new ArrayList<>();

    /**
     * Collections to define building, floor, and hall information for rooms.
     */
    static ArrayList<String> building = new ArrayList<>(List.of("M", "E", "S", "P"));
    static ArrayList<String> floor = new ArrayList<>(List.of("1", "2", "3"));
    static ArrayList<String> hall = new ArrayList<>(List.of("01", "02", "03", "04", "05"));

    /**
     * Scanner object for user input.
     */
    static Scanner sc = Main.getScanner();

    /**
     * Attributes of the Room class.
     */
    private final String name;
    private Schedule schedule;

    /**
     * Constructor for the `Room` class.
     *
     * @param n The name of the room.
     */
    public Room(String n) {
        this.name = n;
        this.schedule = new Schedule();
    }

    /**
     * Method to initialize rooms based on building, floor, and hall information.
     */
    public static void initializeRooms() {
        building.forEach(b -> floor.forEach(f -> hall.forEach(h -> rooms.add(new Room(b + f + h)))));
    }

    /**
     * Getter for the room's schedule.
     *
     * @return The schedule of the room.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Getter for the room's name.
     *
     * @return The name of the room.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the room's schedule.
     *
     * @param schedule The schedule to set.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Getter for the collection of rooms.
     *
     * @return The collection of rooms.
     */
    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Method to print the schedule of a specific room based on user input.
     */
    public static void printRoomSchedule() {
        System.out.println("Enter a room's name");
        String name = sc.next().trim().toUpperCase();
        if (rooms.stream().anyMatch(e -> e.getName().equals(name)))
            System.out.println(rooms.stream().filter(e -> e.getName().equals(name)).toList().getFirst().getSchedule());
    }

    /**
     * toString method to provide a string representation of the room.
     *
     * @return The name of the room.
     */
    @Override
    public String toString() {
        return this.getName();
    }
}