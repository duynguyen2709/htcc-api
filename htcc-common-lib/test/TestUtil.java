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

        Date parse = new SimpleDateFormat("yyyyMMdd HH:mm").parse("20200609 07:30");
        System.out.println(parse.getTime());
    }
}
