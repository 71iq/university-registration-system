package com.swer348;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CourseManager {

    private static final ArrayList<Course> courses = new ArrayList<>();

    public static Scanner sc = Main.getScanner();

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static void addCourse() {
        System.out.println("Enter the name for the course you want to create: ");
        String name = sc.next().trim();
        if (courseExist(name)) {
            System.out.println("The course already EXISTS!");
            return;
        }
        courses.add(new Course(name));
        try {
            FileWriter fw = new FileWriter("inputs/course.txt", true);
            fw.write(name + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("The course " + name + " has been added successfully");
    }

    public static void removeCourse() {
        System.out.println("Enter the name for the course you want to remove: ");
        String name = sc.next().trim();
        if (!courseExist(name)) {
            System.out.println("The course does not EXIST!");
            return;
        }
        courses.remove(courseIndex(name));
        try {
            FileWriter fw = new FileWriter("inputs/course.txt", false);
            courses.forEach(e -> {
                try {
                    fw.append(String.valueOf(e)).append("\n");
                } catch (IOException e1) {
                    System.out.println(e1);
                }
            });
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("The course " + name + " has been removed successfully");
    }

    public static void switchSection() {
        System.out.println("Enter the id for the student: ");
        String str = sc.next().trim();
        if (!Person.personExists(str)) {
            System.out.println("The student doesn't exist");
            return;
        }
        System.out.println("Enter the course: ");
        String co = sc.next().trim();
        if (!courseExist(str)) {
            System.out.println("The course does not EXIST!");
            return;
        }
        System.out.println("Enter the section of the student: A/B/C/D");
        String s1 = sc.next().trim();
        s1 = s1.toUpperCase();
        Character c1 = s1.charAt(0);
        System.out.println("Enter the section that you want the student to go to: A/B/C/D");
        String s2 = sc.next();
        s2 = s2.toUpperCase();
        Character c2 = s2.charAt(0);
        courses.get(courseIndex(co)).switchSection(str, c1, c2);
    }

    static void getAllStudents() {
        System.out.println("Enter the name of the course: ");
        String str = sc.next().trim();
        if (!courseExist(str)) {
            System.out.println("The course does not EXIST!");
            return;
        }
        courses.get(courseIndex(str)).getAllStudents().forEach(System.out::println);
    }

    static void getAllStudentsSection() {
        System.out.println("Enter the name of the course: ");
        String str = sc.next().trim();
        if (!courseExist(str)) {
            System.out.println("The course does not EXIST!");
            return;
        }
        System.out.println("Enter the section that you want: A/B/C/D");
        String s2 = sc.next();
        s2 = s2.toUpperCase();
        Character c = s2.charAt(0);
        courses.get(courseIndex(str)).getAllStudentsSection(c).forEach(System.out::println);
    }

    static boolean courseExist(String name) {
        return courses.stream().anyMatch(c -> c.getName().equals(name));
    }

    static int courseIndex(String name) {
        int[] ind = { 0 };
        IntStream.range(0, courses.size()).forEach(i -> ind[0] = courses.get(i).getName().equals(name) ? i : ind[0]);
        return ind[0];
    }

}
