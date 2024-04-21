import org.junit.jupiter.api.Test;

import com.ehab.urs.Course;
import com.ehab.urs.Section;
import com.ehab.urs.Student;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class SectionTest {
    
    //Checking the method sectionFull() in Section Class work correctly
    @Test
    void SectionFullTest(){
        var c = new Course("history",3);
        var s = new Section(c,'A');
        for(int i=0;i<5;i++){
            var list = new ArrayList<Course>();
            var s1 = new Student("Maamoun", "Jamil", "0568209543", "Bethlehem", LocalDate.now(),"202109714", list);
            s.addStudent(s1);
        }
        assertTrue(s.sectionFull());
    }

    @Test
    void SectionStudentIdFindTest(){
        var c = new Course("history",3);
        var s = new Section(c,'A');
        var list = new ArrayList<Course>();
        var s1 = new Student("Maamoun", "Jamil", "0568209543", "Bethlehem", LocalDate.now(),"202109714", list);
        s.addStudent(s1);
        String str = s1.getStudentID();
        assertEquals(s1, s.getStudentById(str));
    }
}
