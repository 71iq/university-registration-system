package com.swer348;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int input; // asks for action from the user
        do {
            input = sc.nextInt();
            System.out.println("Enter the value desired: ");
            System.out.println("Enter 1 to add new member: ");
            System.out.println("Enter 2 to add new semester: ");
            System.out.println("Enter 3 to add new course: ");
            System.out.println("Enter 4 to remove a course: ");
            System.out.println("Enter 5 to switch section for a student: ");
            System.out.println("Enter 6 to get a list for all students in a specific course: ");
            System.out.println("Enter 7 to get a list for all students in a specific course and section: ");
            System.out.println("Enter 0 to exit the program: ");
            if (input == 1)
                User.addUser();
            if (input == 3)
                CourseManager.addCourse();
            if(input == 4)
                CourseManager.removeCourse();
            if(input == 5)
                CourseManager.switchSection();
            if(input == 6)
                CourseManager.getAllStudents();
            if(input == 7)
                CourseManager.getAllStudentsSection();
            
        } while (input != 0);
        sc.close();
    }
}