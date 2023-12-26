package com.swer348;

import java.util.*;
import java.time.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Semester {
    private static ArrayList<Student> students;
    private static ArrayList<Course> courses;
    private static ArrayList<Faculty> faculty;
    private static ArrayList<Room> rooms;
    private static ArrayList<Section> sections;
    private static ArrayList<Lecture> lectures;

    public static void createSemester() {
        System.out.println("Creating new semester");

        lectures = new ArrayList<>();
        students = Student.getStudents();
        courses = CourseManager.getCourses();
        faculty = Person.getFaculty();
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

        System.out.println("New semester created successfully");
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
            if (instructor.getCredits() + section.getCredits() >= 18) continue;
            section.setInstructor(instructor);
            instructor.setSection(section);
            instructor.setCredits(instructor.getCredits() + section.getCredits());
            facultyIndex = (facultyIndex + 1) % faculty.size();
        }
    }

    private static void assignSectionsLectureDuration() {
        for (Section section : sections) {
            int credits = 1;
            if (section.getCredits() == 4) credits = new int[]{2, 4}[getRandomNumberInRange(0, 1)];
            else if (section.getCredits() == 3) credits = new int[]{1, 3}[getRandomNumberInRange(0, 1)];
            else if (section.getCredits() == 2) credits = new int[]{1, 2}[getRandomNumberInRange(0, 1)];
            section.setLectureDuration(credits * 50);
        }
    }

    private static void generateLectures() {
        for (Section section : sections)
            if (section.getStudentsNumber() > 0) for (int i = 0; i < section.getNumberOfLectures(); i++) {
                Lecture lecture = new Lecture(section, section.getLectureDuration());
                section.getLectures().add(lecture);
                lectures.add(lecture);
            }
    }

    private static void assignRoomToLecture() {
        Collections.shuffle(lectures);
        List<DayOfWeek> days = new ArrayList<>(List.of(DayOfWeek.values()));
        days.removeAll(List.of(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY));
        List<Integer> hours = new ArrayList<>(IntStream.range(8, 15).boxed().toList()), minutes = new ArrayList<>(IntStream.range(0, 6).map(e -> e * 10).boxed().toList());
        for (Lecture lecture : lectures) {
            boolean found = false;
            int tries = 0;
            while (!found && tries++ < 10) {
                Collections.shuffle(days, new Random(0));
                for (DayOfWeek day : days) {
                    lecture.setDay(day);
                    Collections.shuffle(hours, new Random(0));
                    for (int i : hours) {
                        Collections.shuffle(minutes, new Random(0));
                        for (int j : minutes) {
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
            }
            if (!found)
                System.out.println("No time found for lecture: " + lecture);
        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
