import org.junit.jupiter.api.Test;

import com.ehab.urs.Course;
import com.ehab.urs.Student;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class CourseTest {

    //Checking the method studentExist() in Course Class work correctly
    @Test
    void studentExistTest(){
        var c = new Course("history",3);
        var list = new ArrayList<Course>();
        var s = new Student("Maamoun", "Jamil", "0568209543", "Bethlehem", LocalDate.now(), "202109714", list);
        c.addStudent(s);
        assertTrue(c.studentExist(s));
    }

    //Checking the method courseFull() in Course Class work correctly
    @Test
    void CourseFullTest(){
        var c = new Course("history",3);
        var list = new ArrayList<Course>();
        for(int i=0;i<20;i++){
            var s = new Student("Ihab", "Maali", "0568209543", "Bethlehem", LocalDate.now(), i+"", list);
            c.addStudent(s);
        }
        assertTrue(c.courseFull());
    }

    //Checking the method getAllStudents() in Course Class work correctly
    @Test
    void CourseAllTest(){
        var c = new Course("history",3);
        var list = new ArrayList<Course>();
        var s1 = new Student("Maamoun", "Jamil", "0568209543", "Bethlehem", LocalDate.now(),"202109714", list);
        var s2 = new Student("Ihab", "Maali", "0568209543", "Bethlehem", LocalDate.now(),"202109371", list);
        c.addStudent(s1);
        c.addStudent(s2);
        var decider = new ArrayList<Student>();
        decider.add(s1);
        decider.add(s2);
        assertEquals(decider, c.getAllStudents());
    }

    //Checking the method getAllStudentsSection() in Course Class work correctly
    @Test
    void CourseSectionTest(){
        var c = new Course("history",3);
        var list = new ArrayList<Course>();
        var s1 = new Student("Maamoun", "Jamil", "0568209543", "Bethlehem", LocalDate.now(),"202109714", list);
        var s2 = new Student("Ihab", "Maali", "0568209543", "Bethlehem", LocalDate.now(),"202109713", list);
        c.addStudent(s1);
        c.addStudent(s2);
        var decider = new ArrayList<Student>();
        decider.add(s1);
        decider.add(s2);
        assertEquals(decider, c.getAllStudentsSection('A'));
    }

    //Checking the method studentExistById() in Course Class work correctly
    @Test
    void CourseStudentExistsByID(){
        var c = new Course("history",3);
        var list = new ArrayList<Course>();
        var s1 = new Student("Maamoun", "Jamil", "0568209543", "Bethlehem", LocalDate.now(),"202109714", list);
        c.addStudent(s1);
        String id = s1.getStudentID();
        assertTrue(c.studentExistById(id));
    }

    //Checking the method studentExistById() in Course Class work correctly after deleting the student
    @Test
    void CourseStudentExistsByIDAfterDeletion(){
        var c = new Course("history",3);
        var list = new ArrayList<Course>();
        var s1 = new Student("Maamoun", "Jamil", "0568209543", "Bethlehem", LocalDate.now(),"202109714", list);
        c.addStudent(s1);
        String id = s1.getStudentID();
        c.removeStudent(s1);
        assertFalse(c.studentExistById(id));
    }
}