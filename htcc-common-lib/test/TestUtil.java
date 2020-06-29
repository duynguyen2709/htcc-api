import htcc.common.constant.WeekDayEnum;
import htcc.common.util.DateTimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TestUtil {

    @Test
    public void testTime() throws ParseException {

        String yyyyMM = "202006";
        int n = 3;
        for (int i=0; i< n ;i++) {
            // do buz
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate         date      = LocalDate.parse(yyyyMM + "01", formatter);

            int totalDaysInMonth = date.lengthOfMonth();

            LocalDate startDate = date.plusDays(i * totalDaysInMonth / n);
            LocalDate endDate = startDate.plusDays(totalDaysInMonth / n - 1);

            if (i == n - 1) {
                endDate = date.plusDays(totalDaysInMonth - 1);
            }

            System.out.println(startDate.format(formatter) + " - " + endDate.format(formatter));

        }
    }

    @Test
    public void testString() {
        String TABLE_PREFIX = "LeavingRequestLog";
        String tableName = "LeavingRequestLog202007";
        String yyyyMM = tableName.substring(TABLE_PREFIX.length());
        int year = Integer.parseInt(yyyyMM.substring(0, 4));
        Assert.assertEquals(2020, year);
    }
}
