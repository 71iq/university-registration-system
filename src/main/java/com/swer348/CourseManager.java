package com.swer348;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CourseManager {

    static ArrayList<Course> courses = new ArrayList<>();

    public static void addCourse(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name for the course you want to create: ");
        String name = sc.next().trim();
        if(courseExist(name)) {
            System.out.println("The course already EXIST!");
            sc.close();
            return;
        }
        courses.add(new Course(name));
        System.out.println("The course "+name+" has been added successfully");
        sc.close();
    }

    public static void removeCourse(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name for the course you want to remove: ");
        String name = sc.next().trim();
        if(!courseExist(name)) {
            System.out.println("The course does not EXIST!");
            sc.close();
            return;
        }
        courses.remove(courseIndex(name));
        sc.close();
    }

    public static void switchSection(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the id for the student: ");
        String str = sc.next().trim();
        System.out.println("Enter the course: ");
        String co = sc.next().trim();
        if(!courseExist(str)){
            System.out.println("The course does not EXIST!");
            sc.close();
            return;
        }
        System.out.println("Enter the section of the student: A/B/C/D");
        String s1 = sc.next().trim(); s1 = s1.toUpperCase(); Character c1 = s1.charAt(0);
        System.out.println("Enter the section that you want the student to go to: A/B/C/D");
        String s2 = sc.next(); s2 = s2.toUpperCase(); Character c2 = s2.charAt(0);
        courses.get(courseIndex(co)).switchSection(str, c1, c2);
        sc.close();
    }
    
    static void getAllStudents(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the course: ");
        String str = sc.next().trim();
        if(!courseExist(str)){
            System.out.println("The course does not EXIST!");
            sc.close();
            return;
        }
        courses.get(courseIndex(str)).getAllStudents().forEach(s -> s.toString());
        sc.close();
    }

    static void getAllStudentsSection(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the course: ");
        String str = sc.next().trim();
        if(!courseExist(str)){
            System.out.println("The course does not EXIST!");
            sc.close();
            return;
        }
        System.out.println("Enter the section that you want: A/B/C/D");
        String s2 = sc.next(); s2 = s2.toUpperCase(); Character c = s2.charAt(0);
        courses.get(courseIndex(str)).getAllStudentsSection(c).forEach(s -> s.toString());
        sc.close();
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
