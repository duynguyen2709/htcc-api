import htcc.common.constant.WeekDayEnum;
import htcc.common.util.DateTimeUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

public class TestUtil {

    @Test
    public void testTime(){
        for (int weekDay = 0;
             weekDay < 7 ;
             weekDay++) {

            String yyyyMMdd = DateTimeUtil.getDateStringFromWeek(weekDay, 20, 2020, "yyyyMMdd");
            System.out.println(yyyyMMdd);
        }
//
//        String yyyyMMdd = DateTimeUtil.getDateStringFromWeek(4, 20, 2020, "yyyyMMdd");
//        System.out.println(yyyyMMdd);
    }
}
