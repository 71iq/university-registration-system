import com.swer348.Lecture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.swer348.Schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ScheduleTest {
    // test isNotBusy method in Schedule class
    @Test
    void isNotBusyTest() {
        Schedule schedule = new Schedule();
        schedule.addLecture(new Lecture(DayOfWeek.MONDAY, LocalTime.MIN.plusHours(8), LocalTime.MIN.plusHours(9)));
        assertFalse(schedule.isNotBusy(DayOfWeek.MONDAY, LocalTime.MIN.plusHours(8).plusMinutes(30), LocalTime.MIN.plusHours(9).plusMinutes(30)));
    }
}
