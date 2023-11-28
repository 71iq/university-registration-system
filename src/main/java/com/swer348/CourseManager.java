package com.swer348;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CourseManager {

    static ArrayList<Course> courses = new ArrayList<>();

    public static void addCourse(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name for the course you want to create: ");
        String name = sc.next();
        if(courseExist(name)) {
            System.out.println("The course already EXIST!");
            return;
        }
        courses.add(new Course(name));
        System.out.println("The course "+name+" has been added successfully");
    }

    public static void removeCourse(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name for the course you want to remove: ");
        String name = sc.next();
        if(!courseExist(name)) {
            System.out.println("The course does not EXIST!");
            return;
        }
        courses.remove(courseIndex(name));
    }

    static boolean courseExist(String name){
        boolean exist = courses.stream().anyMatch(c -> c.getName().equals(name));
        return exist;
    }

    static int courseIndex(String name){
        int[] ind = {0};
        IntStream.range(0, courses.size()).forEach(i -> ind[0] = courses.get(i).getName().equals(name)?i:ind[0]);
        return ind[0];
    }

}
