package htcc.common.util;

import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

@Log4j2
public class DateTimeUtil {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String HHMM_FORMAT = "HH:mm";

    public static String convertToOtherFormat(String raw, String sourceFormat, String targetFormat){
        try {
            Date d = parseStringToDate(raw, sourceFormat);
            String newDate = parseDateToString(d, targetFormat);
            return newDate;
        } catch (Exception e){
            log.warn("convertToOtherFormat {}, source format {}, target format {} , ex {}",
                    raw, sourceFormat, targetFormat, e.getMessage());
            return "";
        }
    }

    public static String parseTimestampToDateString(long timestamp){
        return parseTimestampToString(timestamp, DATETIME_FORMAT);
    }

    public static String parseTimestampToString(long timestamp, String format){
        try {
            String tempFormat = StringUtil.valueOf(format).isEmpty() ? DATETIME_FORMAT : format;
            return new SimpleDateFormat(tempFormat).format(new Timestamp(timestamp));
        } catch (Exception e){
            log.warn("parseTimestampToString {} format {}", timestamp, format);
            return "";
        }
    }

    public static String parseTimestampToFullDateString(long timestamp){
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(timestamp));
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            String weekDay = getWeekDayString(dayOfWeek);
            String date = parseTimestampToString(timestamp, "dd/MM/yyyy HH:mm");

            return String.format("%s %s", weekDay, date);
        } catch (Exception e){
            log.warn("parseTimestampToFullDateString {} ex : {}", timestamp, e.getMessage());
            return "T4 01/01/2020 00:00";
        }
    }

    private static String getWeekDayString(int dayOfWeek) {
        if (dayOfWeek == Calendar.SUNDAY) {
            return "CN";
        }

        return String.format("T%s", dayOfWeek);
    }

    public static String parseDateToString(Date dt, String format) {
        try {
            return new SimpleDateFormat(format).format(dt);
        } catch (Exception e){
            log.warn("parseDateToString {}, format {}, ex {}", dt, format, e.getMessage());
            return "";
        }
    }

    public static String parseDateToString(Date dt) {
        return parseDateToString(dt, DATE_FORMAT);
    }

    public static Date parseStringToDate(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (Exception e){
            log.warn("parseStringToDate {} - {}", str, format);
            return null;
        }
    }

    public static Date parseStringToDate(String str){
        return parseStringToDate(str, DATE_FORMAT);
    }

    public static long getSecondUntilEndOfDay() {
        LocalDate today = LocalDate.now();

        LocalDateTime zdtStart = LocalDateTime.now();
        LocalDateTime zdtStop  = today.atTime(23, 59, 59, 999);

        Instant start = zdtStart.toInstant(ZoneOffset.MIN);
        Instant stop  = zdtStop.toInstant(ZoneOffset.MIN);

        Duration timeElapsed = Duration.between(start, stop);
        return timeElapsed.toMillis() / 1000;
    }

    public static boolean isBefore(long time, String validTime) {
        try {
            String HHmm = parseTimestampToString(time, HHMM_FORMAT);
            Date   dt   = parseStringToDate(HHmm, HHMM_FORMAT);
            Date   dt2  = parseStringToDate(validTime, HHMM_FORMAT);
            return dt.compareTo(dt2) <= 0;
        } catch (Exception e){
            log.warn("isBefore time: {} - validTime {} ex", time, validTime, e);
            return false;
        }
    }

    public static boolean isAfter(long time, String validTime) {
        try {
            String HHmm = parseTimestampToString(time, HHMM_FORMAT);
            Date   dt   = parseStringToDate(HHmm, HHMM_FORMAT);
            Date   dt2  = parseStringToDate(validTime, HHMM_FORMAT);
            return dt.compareTo(dt2) >= 0;
        } catch (Exception e){
            log.warn("isAfter time: {} - validTime {} ex {}", time, validTime, e.getMessage());
            return false;
        }
    }

    public static boolean isRightFormat(String str, String format) {
        try {
            new SimpleDateFormat(format).parse(str);
            return true;
        } catch (Exception e){
            return false;
        }
    }


    public static boolean isBeforeToday(String date) {
        try {
            String today = parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");
            Long todayNum = Long.parseLong(today);
            Long dateNum = Long.parseLong(date);
            return dateNum < todayNum;
        } catch (Exception e) {
            return false;
        }
    }
}
