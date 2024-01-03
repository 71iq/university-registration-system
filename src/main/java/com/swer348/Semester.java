package com.swer348;

import java.util.*;
import java.time.*;
import java.util.stream.IntStream;

/**
 * The `Semester` class represents a semester in an educational institution.
 * It handles the creation and management of various components like students, courses, faculty, rooms, sections, and lectures.
 * The class includes methods for creating a new semester, assigning students to courses, assigning instructors, generating lectures,
 * assigning rooms to lectures, and calculating academic standings.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-12-19
 */
public class Semester {
    /**
     * The list of students in the semester.
     */
    private static ArrayList<Student> students;

    /**
     * The list of courses in the semester.
     */
    private static ArrayList<Course> courses;

    /**
     * The list of faculty members in the semester.
     */
    private static ArrayList<Faculty> faculty;

    /**
     * The list of rooms in the semester.
     */
    private static ArrayList<Room> rooms;

    /**
     * The list of sections in the semester.
     */
    private static ArrayList<Section> sections;

    /**
     * The list of lectures in the semester.
     */
    private static ArrayList<Lecture> lectures;

    /**
     * Creates a new semester with initialized components.
     */
    public static void createSemester() {
        System.out.println("Creating new semester");

        lectures = new ArrayList<>();
        students = Member.getStudents();
        courses = CourseManager.getCourses();
        faculty = Member.getFaculty();
        rooms = Room.getRooms();
        sections = Section.getSections();

        students.forEach(e -> {
            e.setSchedule(new Schedule());
            e.setCredits(0);
        });
        faculty.forEach(e -> {
            e.setSchedule(new Schedule());
            e.setCredits(0);
        });
        rooms.forEach(e -> e.setSchedule(new Schedule()));
        sections.forEach(e -> {
            e.getLectures().clear();
            e.getStudents().clear();
        });

        assignStudentsToCourses();
        assignInstructors();
        assignSectionsLectureDuration();
        generateLectures();
        assignRoomToLecture();
        assignAcademicStandings();

        System.out.println("New semester created successfully");
    }

    /**
     * Assigns students to courses based on random criteria.
     */
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
                    student.addGrade(course, Grade.assignRandomGrade());
                }
            }
        }
    }

    /**
     * Assigns instructors to sections.
     */
    private static void assignInstructors() {
        Collections.shuffle(faculty);
        int facultyIndex = 0;
        for (Section section : sections) {
            Faculty instructor = faculty.get(facultyIndex);
            if (instructor.getCredits() + section.getCredits() >= 18) continue;
            section.setInstructor(instructor);
            instructor.setSection(section);
            instructor.setCredits(instructor.getCredits() + section.getCredits());
            facultyIndex = (facultyIndex + 1) % faculty.size();
        }
    }

    /**
     * Assigns lecture durations to sections based on credits.
     */
    private static void assignSectionsLectureDuration() {
        for (Section section : sections) {
            int credits = 1;
            if (section.getCredits() == 4) credits = new int[]{2, 4}[getRandomNumberInRange(0, 1)];
            else if (section.getCredits() == 3) credits = new int[]{1, 3}[getRandomNumberInRange(0, 1)];
            else if (section.getCredits() == 2) credits = new int[]{1, 2}[getRandomNumberInRange(0, 1)];
            section.setLectureDuration(credits * 50);
        }
    }

    /**
     * Generates lectures for sections with enrolled students.
     */
    private static void generateLectures() {
        for (Section section : sections)
            if (section.getStudentsNumber() > 0) for (int i = 0; i < section.getNumberOfLectures(); i++) {
                Lecture lecture = new Lecture(section, section.getLectureDuration());
                section.getLectures().add(lecture);
                lectures.add(lecture);
            }
    }

    /**
     * Assigns rooms to lectures based on availability and schedules.
     */
    private static void assignRoomToLecture() {
        Collections.shuffle(lectures);
        List<DayOfWeek> days = new ArrayList<>(List.of(DayOfWeek.values()));
        days.removeAll(List.of(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY));
        List<Integer> hours = new ArrayList<>(IntStream.range(8, 17).boxed().toList()), minutes = new ArrayList<>(IntStream.range(0, 6).map(e -> e * 10).boxed().toList());
        // may seem slow but time complexity worst case (very rare) almost = 270 * 5 * 9 * 6 * 60 * 7(if statement conditions) * 5(isNotBusy method)= 153 million loop
        // but with amortized analysis it's
        for (Lecture lecture : lectures) {
            boolean found = false;
            Collections.shuffle(days, new Random(new Date().getTime()));
            for (DayOfWeek day : days) {
                lecture.setDay(day);
                Collections.shuffle(hours, new Random(new Date().getTime()));
                for (int i : hours) {
                    Collections.shuffle(minutes, new Random(new Date().getTime()));
                    for (int j : minutes) {
                        Collections.shuffle(rooms, new Random(new Date().getTime()));
                        for (Room room : rooms) {
                            LocalTime startTime = LocalTime.MIN.plusHours(i).plusMinutes(j);
                            LocalTime endTime = startTime.plusMinutes(lecture.getLectureDuration());
                            lecture.setStartTime(startTime);
                            lecture.setEndTime(endTime);
                            if (room.getSchedule().isNotBusy(day, startTime, endTime) && lecture.getSection().getStudents().stream().allMatch(e -> e.getSchedule().isNotBusy(day, startTime, endTime)) && lecture.getSection().getInstructor().getSchedule().isNotBusy(day, startTime, endTime)) {
                                lecture.setRoom(room);
                                room.getSchedule().addLecture(lecture);
                                lecture.getSection().getInstructor().getSchedule().addLecture(lecture);
                                lecture.getSection().getStudents().forEach(e -> e.getSchedule().addLecture(lecture));
                                found = true;
                            }
                            if (found) break;
                        }
                        if (found) break;
                    }
                    if (found) break;
                }
                if (found) break;
            }
            if (!found) System.out.println("No time found for lecture: " + lecture);
        }
    }

    /**
     * Assigns academic standings to students.
     */
    private static void assignAcademicStandings() {
        for (Student student : students)
            student.assignStanding();
    }

    /**
     * Calculates and prints honors for students based on GPA.
     */
    public static void calculateAndPrintHonors() {
        System.out.println("Calculating honors for students...");

        for (Student student : students) {
            double gpa = student.calculateGPA();

            System.out.printf("Student %s %s (ID: %s) - GPA: %.2f - Rate: %s\n", student.getFName(), student.getLName(), student.getStudentID(), gpa, (gpa >= 3.9 ? "Highest Honor" : (gpa >= 3.0 ? "Honor" : (gpa < 1.0 ? "Failure" : (gpa < 2.0 ? "Probation" : "Not Bad")))));
        }
    }

    /**
     * Generates a random number within a specified range.
     *
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return A random number within the specified range.
     */
    private static int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
