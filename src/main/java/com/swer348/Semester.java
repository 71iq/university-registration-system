package com.swer348;

import java.util.*;
import java.time.*;

public class Semester {
    private final ArrayList<Student> students;
    private final ArrayList<Course> courses;
    private final ArrayList<Faculty> faculty;
    private final ArrayList<Room> rooms;
    private final ArrayList<Section> sections;

    public Semester(ArrayList<Student> students, ArrayList<Course> courses, ArrayList<Faculty> faculty, ArrayList<Room> rooms) {
        this.students = students;
        this.courses = courses;
        this.faculty = faculty;
        this.rooms = rooms;
        this.sections = new ArrayList<>();
    }

    public void createSemester() {
        assignStudentsToCourses();
        assignInstructors();
        assignCreditsToLectures();
        scheduleLectures();
    }

    private void assignStudentsToCourses() {
        for (Student student : students) {
            int coursesToTake = getRandomNumberInRange(4, 6);
            Collections.shuffle(courses);
            for (int i = 0; i < coursesToTake && student.getCredits() < 21; ) {
                Course course = courses.get(i);
                if (student.eligible(course) && !course.courseFull()) {
                    i++;
                    course.addStudent(student);
                    student.addCourse(course);
                    student.setCredits(student.getCredits() + course.getCredits());
                }
            }
        }
    }

    private void assignInstructors() {
        Collections.shuffle(faculty);
        int facultyIndex = 0;
        for (Section section : sections) {
            Faculty instructor = faculty.get(facultyIndex);
            if (instructor.getCredits() + section.getCredits() >= 18) continue;
            section.setInstructor(instructor);
            instructor.setCredits(instructor.getCredits() + section.getCredits());
            facultyIndex = (facultyIndex + 1) % faculty.size();
        }
    }

    private void assignCreditsToLectures() {
        for (Section section : sections) {
            int lectureDuration = 1;
            if (section.getCredits() == 4) lectureDuration = new int[]{2, 4}[getRandomNumberInRange(0, 1)];
            else if (section.getCredits() == 3) lectureDuration = new int[]{1, 3}[getRandomNumberInRange(0, 1)];
            else if (section.getCredits() == 2) lectureDuration = new int[]{1, 2}[getRandomNumberInRange(0, 1)];
            section.setLectureDuration(lectureDuration * 50);

            for (Lecture lecture : section.getLectures()) {
                lecture.setCredits(lectureDuration);
            }
        }
    }

    private void scheduleLectures() {
        List<Lecture> allLectures = new ArrayList<>();
        for (Section section : sections) {
            if (section.getStudentsNumber() > 0)
                allLectures.addAll(section.getLectures());
        }

        allLectures.sort(Comparator.comparing(Lecture::getStartTime));

        // Create a schedule map to keep track of occupied times for students, faculty, and rooms
        Map<Student, Set<LocalTime>> studentSchedule = new HashMap<>();
        Map<Faculty, Set<LocalTime>> facultySchedule = new HashMap<>();
        Map<Room, Set<LocalTime>> roomSchedule = new HashMap<>();

        for (Lecture lecture : allLectures) {
            Section section = lecture.getSection();
            Faculty instructor = section.getInstructor();
            Room room = lecture.getRoom();

            // Check for conflicts with students
            if (checkConflict(studentSchedule, lecture)) continue;

            // Check for conflicts with faculty
            if (checkConflict(facultySchedule, lecture)) continue;

            // Check for conflicts with rooms
            if (checkConflict(roomSchedule, lecture)) continue;

            // If no conflicts, schedule the lecture
            studentSchedule.computeIfAbsent(section.getCourse().getAllStudents().getFirst(), k -> new HashSet<>()).addAll(lecture.getTimeSlots());
            facultySchedule.computeIfAbsent(instructor, k -> new HashSet<>()).addAll(lecture.getTimeSlots());
            roomSchedule.computeIfAbsent(room, k -> new HashSet<>()).addAll(lecture.getTimeSlots());
        }
    }

    private boolean checkConflict(Map<?, Set<LocalTime>> scheduleMap, Lecture lecture) {
        for (Map.Entry<?, Set<LocalTime>> entry : scheduleMap.entrySet()) {
            if (Collections.disjoint(entry.getValue(), lecture.getTimeSlots())) {
                return false; // No conflict
            }
        }
        return true; // Conflict found
    }

    // Utility method to get a random number in a given range
    private int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
