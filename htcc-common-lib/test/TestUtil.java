import htcc.common.constant.WeekDayEnum;
import htcc.common.util.DateTimeUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TestUtil {

    @Test
    public void testTime() throws ParseException {

        String d1 = "20200609";
        String d2 = "20200606";
        System.out.println(DateTimeUtil.calcDayDiff(d1, d2, "yyyyMMdd"));
    }
}
