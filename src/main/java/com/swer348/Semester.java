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
        sections = Section.getSections(); 
        assignStudentsToCourses();
        assignInstructors();
        generateLectures();
        assignCreditsToLectures();
        scheduleLectures();
    }

    private static void assignStudentsToCourses() {
        for (Student student : students) {
            int coursesToTake = getRandomNumberInRange(4, 6);
            Collections.shuffle(courses);
            for (int i = 0; i < coursesToTake && student.getCredits() < 21; i++) {
                Course course = courses.get(i);
                System.out.println(i);
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
            instructor.setCredits(instructor.getCredits() + section.getCredits());
            facultyIndex = (facultyIndex + 1) % faculty.size();
        }
    }

    private static void assignCreditsToLectures() {
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

    private static void scheduleLectures() {
        List<Lecture> allLectures = new ArrayList<>();
        for (Section section : sections) {
            if (section.getStudentsNumber() > 0) {
                allLectures.addAll(section.getLectures());
            }
        }
    
        // Sort lectures by start time
        allLectures.sort(Comparator.comparing(Lecture::getStartTime));
    
        // Create schedule maps for students, faculty, and rooms
        Map<Student, Set<LocalTime>> studentSchedule = new HashMap<>();
        Map<Faculty, Set<LocalTime>> facultySchedule = new HashMap<>();
        Map<Room, Set<LocalTime>> roomSchedule = new HashMap<>();
    
        for (Lecture lecture : allLectures) {
            Section section = lecture.getSection();
            Faculty instructor = section.getInstructor();
            Room room = lecture.getRoom();
    
            // Check for conflicts with students, faculty, and rooms
            if (checkConflict(studentSchedule, lecture) ||
                checkConflict(facultySchedule, lecture) ||
                checkConflict(roomSchedule, lecture)) {
                // Handle conflicts here (e.g., reschedule or skip)
                continue;
            }
    
            // If no conflicts, schedule the lecture
            studentSchedule.computeIfAbsent(section.getCourse().getAllStudents().getFirst(), k -> new HashSet<>()).addAll(lecture.getTimeSlots());
            facultySchedule.computeIfAbsent(instructor, k -> new HashSet<>()).addAll(lecture.getTimeSlots());
            roomSchedule.computeIfAbsent(room, k -> new HashSet<>()).addAll(lecture.getTimeSlots());
        }
    }

    private static boolean checkConflict(Map<?, Set<LocalTime>> scheduleMap, Lecture lecture) {
        for (Map.Entry<?, Set<LocalTime>> entry : scheduleMap.entrySet()) {
            if (Collections.disjoint(entry.getValue(), lecture.getTimeSlots())) {
                return false; // No conflict
            }
        }
        return true; // Conflict found
    }

    // Utility method to get a random number in a given range
    private static int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private static void generateLectures() {
        for (Section section : sections) {
            if (section.getStudentsNumber() > 0) {
                for (int i = 0; i < 3; i++) {  // Generate 3 lectures per section
                    // Generate random lecture duration between 50 and 150 minutes
                    int lectureDuration = getRandomNumberInRange(50, 150);
                    Lecture lecture = createRandomLecture(section, lectureDuration);
                    section.getLectures().add(lecture);
    
                    // Add the lecture to the section's schedule
                    section.getInstructor().getSchedule().addLecture(lecture.getStartTime().getDayOfWeek(), lecture);
                }
            }
        }
    }

    private static LocalTime generateRandomStartTime() {
        // Assume classes start between 8 AM and 6 PM
        int startHour = getRandomNumberInRange(8, 17);
        int startMinute = getRandomNumberInRange(0, 59);
        return LocalTime.of(startHour, startMinute);
    }

    static ArrayList<Section> getSections(){
        return sections;
    }

    private static Lecture createRandomLecture(Section section, int lectureDuration) {
        // Generate a random start time for the lecture
        LocalTime startTime = generateRandomStartTime();
    
        // Create a new Lecture object with the generated start time and section
        Lecture lecture = new Lecture(startTime, section);
    
        // Set the lecture duration
        lecture.setLectureDuration(lectureDuration);
    
        // Assign a room to the lecture (you may need to implement this part)
        Room room = assignRoomToLecture(); // You need to implement this method
        lecture.setRoom(room);
    
        // Add the lecture to the section's schedule
        section.getInstructor().getSchedule().addLecture(lecture.getStartTime().getDayOfWeek(), lecture);
    
        return lecture;
    }
    
    private static Room assignRoomToLecture() {
        List<Room> availableRooms = Room.getRooms();
        Random random = new Random();

        // Try to find an available room, retrying up to 100 times
        for (int i = 0; i < 100; i++) {
            Room room = availableRooms.get(random.nextInt(availableRooms.size()));

            // Assume lecture duration is 1 hour (you can adjust based on your needs)
            LocalDateTime startDateTime = generateRandomStartDateTime();
            LocalDateTime endDateTime = startDateTime.plusHours(1);

            // Check if the room is available during the generated time slot
            if (room.isAvailable(startDateTime.toLocalTime(), endDateTime.toLocalTime())) {
                 // If the room is available, return it
                return room;
            }
            // If the room is not available, try again
    }

        // If no available room is found after 100 attempts, you might want to handle this case
        throw new RuntimeException("Unable to find an available room for the lecture");
    }

    private static LocalDateTime generateRandomStartDateTime() {
        // Assume classes start between 8 AM and 6 PM
        int startHour = getRandomNumberInRange(8, 17);
        int startMinute = getRandomNumberInRange(0, 59);
    
        // Assume we are generating lectures for the current date
        LocalDate currentDate = LocalDate.now();
    
        return LocalDateTime.of(currentDate, LocalTime.of(startHour, startMinute));
    }

}
