import htcc.common.constant.WeekDayEnum;
import htcc.common.util.DateTimeUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

public class TestUtil {

    @Test
    public void testTime(){
        String start1 = "19:30";
        String end1 = "09:31";
        String start2 = "09:32";
        String end2 = "20:00";
        System.out.println(DateTimeUtil.isConflictTime(start1, end1, start2, end2));
    }
}
