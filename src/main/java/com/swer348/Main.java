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
            System.out.println("Enter 0 to exit the program: ");
            if (input == 1)
                User.addUser();
            if (input == 3)
                CourseManager.addCourse();
        } while (input != 0);
        sc.close();
    }
}