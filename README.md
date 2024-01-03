# University Management System

The University Management System is a Java-based project that simulates the management of courses, students, faculty,
rooms, and schedules in an educational institution.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Classes](#classes)
- [HTML files documentation](#html-files-documentation)
- [License](#license)

## Overview

The University Management System is designed to handle various aspects of university management, including student
enrollment, course assignment, faculty management, room scheduling, and more. It provides a set of classes representing
different entities in the university and their interactions.
![class diagram](https://github.com/F23-SWER348/uni-registrat-project-lvl100/uni_diagram.jpg?raw=true)

## Features

- **Student Management:** Register and manage student information, including courses taken, grades, and schedules.
- **Course Management:** Create and manage courses, including prerequisites and credits.
- **Faculty Management:** Assign instructors to courses and manage faculty information.
- **Room Scheduling:** Schedule lectures in different rooms based on availability.
- **Section and Schedule Management:** Manage course sections and view schedules for both students and rooms.

## Getting Started

To run the University Management System, follow these steps:

1. Clone the repository:

    ```bash
    git clone https://github.com/F23-SWER348/uni-registrat-project-lvl100
    ```

2. Open the project in your preferred Java development environment.

3. Run the `Main` class to start the application.

## Usage

- Upon running the application, you will be prompted with a menu to perform various actions such as viewing schedules,
  managing courses, and more.

- Follow the on-screen instructions to interact with the system.

## Classes

The project consists of several classes, each representing a key entity or functionality. Here are some of the main
classes:

- **`Member`**: Represents a generic member of the university.
- **`Student`**: Extends `Member` and represents a student with specific attributes and functionalities.
- **`Faculty`**: Extends `Member` and represents a faculty member.
- **`Course`**: Represents a university course with information like name, credits, and prerequisites.
- **`Room`**: Represents a room where lectures can be scheduled.
- **`Section`**: Represents a section of a course, including information about the instructor and students.
- **`Schedule`**: Manages the schedule for members, rooms, and lectures.
- **`Semester`**: Handles the creation of a new semester, including student enrollment, course assignment, and
  scheduling.

## HTML files documentation by javaDoc

[**package summary**](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/package-summary.html)

**Classes Documentation**

- [Course](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Course.html)
- [CourseManger](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/CourseManager.html)
- [Faculty](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/JavaDoccom/swer348/Faculty.html)
- [Grade](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Grade.html)
- [Lecture](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Lecture.html)
- [Main](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Main.html)
- [Member](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Member.html)
- [Room](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Room.html)
- [Schedule](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Schedule.html)
- [Section](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Section.html)
- [Semester](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Semester.html)
- [Staff](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Staff.html)
- [Student](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/Student.html)

**Classes Usages**

- [Course](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/Course.html)
- [CourseManger](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/CourseManager.html)
- [Faculty](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/JavaDoccom/swer348/class-use/Faculty.html)
- [Grade](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/Grade.html)
- [Lecture](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/Lecture.html)
- [Main](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/Main.html)
- [Member](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/Member.html)
- [Room](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/Room.html)
- [Schedule](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/Schedule.html)
- [Section](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/Section.html)
- [Semester](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/Semester.html)
- [Staff](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/Staff.html)
- [Student](https://github.com/F23-SWER348/uni-registrat-project-lvl100/blob/master/javaDoc/com/swer348/class-use/class-use/Student.html)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
