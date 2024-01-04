import org.junit.jupiter.api.Test;

import com.swer348.Course;
import com.swer348.Student;

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
        assertEquals(true, c.studentExist(s));
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
        assertEquals(true, c.courseFull());
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
}