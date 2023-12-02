package com.swer348;
import java.util.*;

public class User {

    public static void addUser() {
        System.out.println("Enter the desired value: ");
        System.out.println("Enter 1 to add new student: ");
        System.out.println("Enter 2 to add new faculty: ");
        System.out.println("Enter 3 to add new staff: ");
        System.out.println("Enter 0 to go back");
        Scanner sc = new Scanner(System.in);
        if(sc.hasNextInt()){
            int role = sc.nextInt();
            if (role == 1) Person.addStudent();
            if (role == 2) Person.addFaculty();
            if (role == 3) Person.addStaff();
        }
        sc.close();
    } 

    public static void addCourse(){
        CourseManager.addCourse();
    }
}
