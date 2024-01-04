import static org.junit.jupiter.api.Assertions.*;

import com.swer348.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberTest {
    // test method member (faculty/staff/student) exists in member class
    @Test
    void memberExistsTest() {
        Student student = new Student("Ihab", "Maali", "0568044395", "salfit", LocalDate.of(2003, 9, 12), "STU71", new ArrayList<>(List.of(new Course("none", 0))));
        Faculty faculty = new Faculty("Maamoun", "Jamil", "0568044395", "bl", LocalDate.of(2003, 9, 12), "FAC32");
        Staff staff = new Staff("bsbs", "lolo", "0568044395", "salfit", LocalDate.of(2003, 9, 12), "STA71");
        Member.getStudents().add(student);
        Member.getStaff().add(staff);
        Member.getFaculty().add(faculty);
        boolean stu = Member.memberExists(student.getStudentID());
        boolean fac = Member.memberExists(faculty.getFacultyID());
        boolean sta = Member.memberExists(staff.getStaffID());
        assertTrue(stu && sta && fac);
    }
}
