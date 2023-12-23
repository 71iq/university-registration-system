package com.swer348;

import java.util.*;
import java.time.*;

public class Semester {
    private static ArrayList<Student> students;
    private static ArrayList<Course> courses;
    private static ArrayList<Faculty> faculty;
    private static ArrayList<Room> rooms;
    private static ArrayList<Section> sections;

    public static void createSemester() {
        students = Student.getStudents();
        courses = CourseManager.getCourses();
        faculty = Person.getFaculty();
        rooms = Room.getRooms();
        sections = Course.getAllSections();
        assignStudentsToCourses();
        assignInstructors();
        assignCreditsToLectures();
        scheduleLectures();
    }

    private static void assignStudentsToCourses() {
        for (Student student : students) {
            int coursesToTake = getRandomNumberInRange(4, 6);
            Collections.shuffle(courses);
            for (int i = 0; i < coursesToTake && student.getCredits() < 21; i++) {
                Course course = courses.get(i);
                if (student.eligible(course) && !course.courseFull()) {
                    course.addStudent(student);
                    student.addCourse(course);
                    student.setCredits(student.getCredits() + course.getCredits());
                }
            }
        }
    }

    private static void assignInstructors() {
        Collections.shuffle(faculty);
        int facultyIndex = 0;
        for (Section section : sections) {
            Faculty instructor = faculty.get(facultyIndex);
            if (section.getStudentsNumber() == 0) continue;
            if (instructor.getCredits() + section.getCredits() >= 18) continue;
            section.setInstructor(instructor);
            instructor.setCredits(instructor.getCredits() + section.getCredits());
            facultyIndex = (facultyIndex + 1) % faculty.size();
        }
    }

    private static void assignCreditsToLectures() {
        for (Section section : sections) {
            int lectureDuration = 1;
            if (section.getCredits() == 4) lectureDuration = new int[]{1, 2, 4}[getRandomNumberInRange(0, 2)];
            else if (section.getCredits() == 3) lectureDuration = new int[]{1, 3}[getRandomNumberInRange(0, 1)];
            else if (section.getCredits() == 2) lectureDuration = new int[]{1, 2}[getRandomNumberInRange(0, 1)];
            section.setLectureDuration(lectureDuration * 50);

            for(int i = 0; i < section.getCredits() * 50 / section.getLectureDuration(); i++)
                section.addLecture(new Lecture(section));
            System.out.println(section.getLectures());
        }
    }

    private static void scheduleLectures() {
        List<Lecture> allLectures = new ArrayList<>();
        for (Section section : sections)
            if (section.getStudentsNumber() > 0)
                allLectures.addAll(section.getLectures());

        allLectures.sort(Comparator.comparing(Lecture::getStartTime));

        // Create a schedule map to keep track of occupied times for students, faculty, and rooms
        Map<Student, Set<LocalTime>> studentSchedule = new HashMap<>();
        Map<Faculty, Set<LocalTime>> facultySchedule = new HashMap<>();
        Map<Room, Set<LocalTime>> roomSchedule = new HashMap<>();

        for (Lecture lecture : allLectures) {
            Section section = lecture.getSection();
            Faculty instructor = section.getInstructor();
            Room room = lecture.getRoom();

            if (checkConflict(studentSchedule, lecture)) continue;
            if (checkConflict(facultySchedule, lecture)) continue;
            if (checkConflict(roomSchedule, lecture)) continue;

            studentSchedule.computeIfAbsent(section.getCourse().getAllStudents().getFirst(), k -> new HashSet<>()).addAll(lecture.getTimeSlots());
            facultySchedule.computeIfAbsent(instructor, k -> new HashSet<>()).addAll(lecture.getTimeSlots());
            roomSchedule.computeIfAbsent(room, k -> new HashSet<>()).addAll(lecture.getTimeSlots());
        }
    }

    private static boolean checkConflict(Map<?, Set<LocalTime>> scheduleMap, Lecture lecture) {
        for (Map.Entry<?, Set<LocalTime>> entry : scheduleMap.entrySet())
            if (Collections.disjoint(entry.getValue(), lecture.getTimeSlots()))
                return false;
        return true;
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
